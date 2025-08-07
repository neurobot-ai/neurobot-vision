package com.nbai.utils.coco;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.nbai.common.enums.ShapeTypeEnum;

import java.math.BigDecimal;
import java.util.Map;

/**尺寸测量的coco工具类
 * @Author liuxuli
 * @date 2021/7/15 19:31
 */
public class MeasureDealUtil extends BaseMLJson2Coco{

    private static int VISIBLE = 2;

    /**
     * 前端传过来的coco转换
     * @param measureCoco
     * @return
     */
    public static String cocoToExtra(String measureCoco){

        JSONObject jsonObject = JSONObject.parseObject(measureCoco);
        JSONArray annotations = jsonObject.getJSONArray("annotations");
        JSONObject annotation = annotations.getJSONObject(0);
        JSONArray shapes = annotation.getJSONArray("shapes");

        JSONObject infoShaps = getShapes(shapes);
        jsonObject.getJSONObject("info").put("shapes",infoShaps);

        annotation.remove("shapes");
        JSONArray keyPoints = getKeyPoints(infoShaps);
        annotation.put("keypoints", keyPoints);
        return jsonObject.toJSONString();
    }

    /**
     * 后端保存的json转换为coco返回前端
     * @param json
     * @return
     */
    public static String extraToCoCo(String json){

        JSONArray shapeArray = new JSONArray();

        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject info = jsonObject.getJSONObject("info");
        JSONObject shapes = info.getJSONObject("shapes");
        //点
        shapeArray.addAll(shapes.getJSONArray("points"));
        //线
        shapeArray.addAll(shapes.getJSONArray("lines"));
        //圆
        shapeArray.addAll(shapes.getJSONArray("circles"));
        //矩形
        shapeArray.addAll(shapes.getJSONArray("rectangles"));
        //info中移除shapes
        info.remove("shapes");
        //将shapes加入到annotations中
        jsonObject.getJSONArray("annotations").getJSONObject(0).put("shapes", shapeArray);
        jsonObject.getJSONArray("annotations").getJSONObject(0).put("keypoints", new JSONArray());

        return jsonObject.toJSONString();
    }

    /**
     * 转换为字段
     * @param shapes
     * @return
     */
    private static JSONObject getShapes(JSONArray shapes){
        JSONObject shapeobj = new JSONObject();
        JSONArray points = new JSONArray();
        JSONArray lines = new JSONArray();
        JSONArray circles = new JSONArray();
        JSONArray rectangles = new JSONArray();
        for (int i = 0; i < shapes.size(); i++) {
            JSONObject shape = shapes.getJSONObject(i);
            String type = shape.getString("type");
            if (ShapeTypeEnum.POINT.getCode().equals(type)){
                points.add(shape);
            }
            if (ShapeTypeEnum.LINE.getCode().equals(type)){
                lines.add(shape);
            }
            if (ShapeTypeEnum.CIRCLE.getCode().equals(type)){
                circles.add(shape);
            }
            if (ShapeTypeEnum.RECTANGLE.getCode().equals(type)){
                rectangles.add(shape);
            }
        }
        shapeobj.put("points",points);
        shapeobj.put("lines",lines);
        shapeobj.put("circles",circles);
        shapeobj.put("rectangles",rectangles);
        return shapeobj;
    }

    /**
     * 组装keypoint
     * @param infoShapes
     * @return
     */
    public static JSONArray getKeyPoints(JSONObject infoShapes){
        JSONArray result = new JSONArray();
        //处理点
        JSONArray points = infoShapes.getJSONArray("points");
        for (int i = 0; i < points.size(); i++) {
            JSONObject point = points.getJSONObject(i);
            JSONArray keypoints = point.getJSONArray("keypoints");
            result.addAll(keypoints);
            result.add(VISIBLE);
        }
        //处理线段
        JSONArray lines = infoShapes.getJSONArray("lines");
        for (int i = 0; i < lines.size(); i++) {
            JSONObject line = lines.getJSONObject(i);
            JSONArray keypoints = line.getJSONArray("keypoints");
            result.add(keypoints.getDouble(0));
            result.add(keypoints.getDouble(1));
            result.add(VISIBLE);
            result.add(keypoints.getDouble(2));
            result.add(keypoints.getDouble(3));
            result.add(VISIBLE);
        }
        //处理圆
        JSONArray circles = infoShapes.getJSONArray("circles");
        for (int i = 0; i < circles.size(); i++) {
            JSONObject circle = circles.getJSONObject(i);
            JSONArray keypoints = circle.getJSONArray("keypoints");
            result.add(keypoints.getDouble(0));
            result.add(keypoints.getDouble(1));
            result.add(VISIBLE);
            result.add(keypoints.getDouble(2));
            result.add(keypoints.getDouble(3));
            result.add(VISIBLE);
        }
        //处理矩形
        JSONArray rectangles = infoShapes.getJSONArray("rectangles");
        for (int i = 0; i < rectangles.size(); i++) {
            JSONObject rectangle = rectangles.getJSONObject(i);
            JSONArray keypoints = rectangle.getJSONArray("keypoints");
            result.add(keypoints.getDouble(0));
            result.add(keypoints.getDouble(1));
            result.add(VISIBLE);
            result.add(keypoints.getDouble(2));
            result.add(keypoints.getDouble(3));
            result.add(VISIBLE);
            result.add(keypoints.getDouble(4));
            result.add(keypoints.getDouble(5));
            result.add(VISIBLE);
            result.add(keypoints.getDouble(6));
            result.add(keypoints.getDouble(7));
            result.add(VISIBLE);
        }
        return result;
    }

    /**
     * 算法返回的keypoints转换为前端需要的coco
     * @param keypoints
     * @param categories
     * @return
     */
    public static JSONArray keypointToShape(JSONArray keypoints, JSONArray categories){
        JSONArray annotations = new JSONArray();

        //测试结果keypoints的索引（我把它当作是游标的位置），从0开始计算，每3个加1
        int index = 0;
        for (int i = 0; i < categories.size(); i++) {
            JSONObject category = categories.getJSONObject(i);
            Integer categoryId = category.getInteger("id");
            String categoryName = category.getString("name");
            String categoryType = category.getString("type");
            //返回结果的annotation中keypoints

            JSONObject annotation = new JSONObject();
            annotation.put("area", 200);
            annotation.put("id", categoryId);//尺寸测量标注和标签要一一对应
            annotation.put("category_id", categoryId);
            annotation.put("category_name", categoryName);
            annotation.put("shape_type",categoryType);
            annotation.put("iscrowd",0);
            annotation.put("segmentation", new JSONArray());
            annotation.put("image_id", 1);
            //矩形特殊处理如果是旋转框则需要计算
            if (ShapeTypeEnum.RECTANGLE.getCode().equals(categoryType)){
                JSONObject rectAngleInfo = getRectAngleInfo(keypoints, index);
                annotation.putAll(rectAngleInfo);
            }else {
                //其他图形就是统计点就行了
                JSONArray annotationKeypoints = new JSONArray();
                for (int j = 0; j < ShapeTypeEnum.getByCode(categoryType).getPointNum(); j++) {
                    annotationKeypoints.add(keypoints.get(3 * index));
                    annotationKeypoints.add(keypoints.get(3 * index + 1));
                    index ++;
                }
                annotation.put("keypoints", annotationKeypoints);
                annotation.put("angle", 1);
                annotation.put("center", new JSONArray());
                annotation.put("box", new JSONArray());
            }
            annotations.add(annotation);
        }
        return annotations;
    }

    private static JSONObject getRectAngleInfo(JSONArray keypoints, int index){

        JSONObject rectangle = new JSONObject();
        rectangle.put("type", ShapeTypeEnum.RECTANGLE.getCode());
        JSONArray points = new JSONArray();
        //第一个点
        double x1 = keypoints.getDouble( 3 * index);
        double y1 = keypoints.getDouble( 3 * index + 1);
        index ++;
        //第二个点
        double x2 = keypoints.getDouble( 3 * index);
        double y2 = keypoints.getDouble( 3 * index + 1);
        index ++;
        //第三个点
        double x3 = keypoints.getDouble( 3 * index);
        double y3 = keypoints.getDouble( 3 * index + 1);
        index ++;
        //第四个点
        double x4 = keypoints.getDouble( 3 * index);
        double y4 = keypoints.getDouble( 3 * index + 1);
        index ++;
        points.add(x1);
        points.add(y1);
        points.add(x2);
        points.add(y2);
        points.add(x3);
        points.add(y3);
        points.add(x4);
        points.add(y4);
        rectangle.put("keypoints", points);
        //求角度(矩形可能是不规则的，两条边的旋转角度取平均值)
        Double angle1 = getAngle(x1, y1, x2, y2);
        Double angle2 = getAngle(x4, y4, x3, y3);
        double angle = (angle1 + angle2) / 2;//取平均值
        rectangle.put("angle",angle);
        //求中心点
        double minx = x1;
        minx = Math.min(minx, x2);
        minx = Math.min(minx, x3);
        minx = Math.min(minx, x4);
        double maxx = x1;
        maxx = Math.max(maxx, x2);
        maxx = Math.max(maxx, x3);
        maxx = Math.max(maxx, x4);
        double miny = y1;
        miny = Math.min(miny, y2);
        miny = Math.min(miny, y3);
        miny = Math.min(miny, y4);
        double maxy = y1;
        maxy = Math.max(maxy, y2);
        maxy = Math.max(maxy, y3);
        maxy = Math.max(maxy, y4);

        double centerX = (maxx + minx)/2;
        double centerY = (maxy + miny)/2;
        JSONArray center = new JSONArray();
        center.add(centerX);
        center.add(centerY);
        rectangle.put("center", center);

        //求宽和高
        //第一个点和第二个点之间的距离就是宽
        double width = getDistance(x1, y1, x2 , y2);
        //第一个点和第四个点之间的距离就是高
        double height = getDistance(x1, y1, x4, y4);
        JSONArray box = new JSONArray();
        box.add(width);
        box.add(height);
        rectangle.put("box", box);

        return rectangle;
    }

    /**
     * 求两点之间的距离（使用勾股定理）
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static Double getDistance(Double x1,Double y1, Double x2, Double y2){
        double x = Math.abs(x1 - x2);
        double y = Math.abs(y1 - y2);
        double sqrt = Math.sqrt(x * x + y * y);
        return new BigDecimal(sqrt).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     *获取角度(使用反三角函数来进行)顺时针为-值  逆时针为+值
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static Double getAngle(Double x1, Double y1, Double x2, Double y2){
        double x = Math.abs(x1 - x2);
        double y = Math.abs(y1 - y2);
        double tan = Math.atan(y / x);
        double angle =  new BigDecimal(tan / Math.PI * 180).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (y1 - y2 < 0){
            return  angle - angle*2;
        }
        return angle;
    }

    public static void main(String[] args) {
        JSONArray keypoints = JSONObject.parseArray("[\n" +
                "                1887.7133105802047,\n" +
                "                894.5392491467576,\n" +
                "                2,\n" +
                "                2270.469798657718,\n" +
                "                898.3221476510067,\n" +
                "                2,\n" +
                "                1879.7752808988764,\n" +
                "                1718.7265917602997,\n" +
                "                2,\n" +
                "                2265.7320872274145,\n" +
                "                1722.7414330218069,\n" +
                "                2\n" +
                "            ]");
        Map<String, Integer> shapMap = Maps.newHashMap();
        shapMap.put(ShapeTypeEnum.POINT.getCode(), 2);
        shapMap.put(ShapeTypeEnum.LINE.getCode(), 1);
        shapMap.put(ShapeTypeEnum.CIRCLE.getCode(), 0);
        shapMap.put(ShapeTypeEnum.RECTANGLE.getCode(), 0);
//        Double angle = getAngle(200.0, 700.0, 500.0, 300.0);
//        System.out.println(angle);
//        String extra = "{\"licenses\":[{\"name\":\"尺寸测量\",\"id\":1,\"url\":\"\"}],\"images\":[{\"license\":1,\"file_name\":\"xxx-xxx-xxx.jpg\",\"flickr_url\":\"\",\"coco_url\":\"http://static2.nb-ai.com/xxx/xxx.jpg\",\"width\":1000,\"date_captured\":\"2020-11-2 20:21:27\",\"id\":1,\"thumbnail_url\":\"http://static2.nb-ai.com/xxx/xxx.jpg!thumbnail\",\"height\":500}],\"annotations\":[{\"area\":0,\"category_name\":\"尺寸测量-标签\",\"category_id\":1,\"iscrowd\":0,\"bbox\":[],\"angle\":0,\"segmentation\":[],\"id\":1,\"image_id\":12345,\"keypoints\":[125.0,126.0,2,136.0,150.1,2,156.2,180.3,2,360.5,330.2,2,406.3,330.2,2,660.0,770.0,2,760.0,770.0,2,760.0,900.0,2,660.0,900.0,2]}],\"categories\":[{\"color\":\"red\",\"name\":\"尺寸测量-标签\",\"supercategory\":\"尺寸测量-标签\",\"id\":1}],\"info\":{\"contributor\":\"Matrix Vision Co.\",\"year\":2020,\"date_created\":\"2020-11-2 19:54:58\",\"shapes\":{\"rectangles\":[{\"center\":[710,835],\"angle\":0,\"box\":[100,130],\"comment\":\"juxing\",\"type\":\"rectangle\",\"keypoints\":[660.0,770.0,760.0,770.0,760.0,900.0,660.0,900.0]}],\"circles\":[{\"type\":\"circle\",\"keypoints\":[360.5,330.2,406.3,330.2]}],\"lines\":[{\"type\":\"line\",\"keypoints\":[136.0,150.1,156.2,180.3]}],\"points\":[{\"type\":\"point\",\"keypoints\":[125.0,126.0]}]},\"description\":\"xxx模型标注数据集\",\"version\":\"1.0\",\"url\":\"http://nb-ai.com\"}}";
//        String coco = "{\"licenses\":[{\"name\":\"尺寸测量\",\"id\":1,\"url\":\"\"}],\"images\":[{\"license\":1,\"file_name\":\"xxx-xxx-xxx.jpg\",\"flickr_url\":\"\",\"coco_url\":\"http://static2.nb-ai.com/xxx/xxx.jpg\",\"width\":1000,\"date_captured\":\"2020-11-2 20:21:27\",\"id\":1,\"thumbnail_url\":\"http://static2.nb-ai.com/xxx/xxx.jpg!thumbnail\",\"height\":500}],\"annotations\":[{\"area\":0,\"category_name\":\"尺寸测量-标签\",\"category_id\":1,\"shapes\":[{\"type\":\"point\",\"keypoints\":[125.0,126.0]},{\"type\":\"line\",\"keypoints\":[136.0,150.1,156.2,180.3]},{\"type\":\"circle\",\"keypoints\":[360.5,330.2,406.3,330.2]},{\"center\":[710,835],\"angle\":0,\"box\":[100,130],\"comment\":\"juxing\",\"type\":\"rectangle\",\"keypoints\":[660.0,770.0,760.0,770.0,760.0,900.0,660.0,900.0]}],\"iscrowd\":0,\"bbox\":[],\"angle\":0,\"segmentation\":[],\"id\":1,\"image_id\":12345,\"keypoints\":[]}],\"categories\":[{\"color\":\"red\",\"name\":\"尺寸测量-标签\",\"supercategory\":\"尺寸测量-标签\",\"id\":1}],\"info\":{\"contributor\":\"Matrix Vision Co.\",\"year\":2020,\"date_created\":\"2020-11-2 19:54:58\",\"description\":\"xxx模型标注数据集\",\"version\":\"1.0\",\"url\":\"http://nb-ai.com\"}}";
//        String result = cocoToExtra(coco);
//        String result = extraToCoCo(extra);
//        System.out.println(result);

    }
}
