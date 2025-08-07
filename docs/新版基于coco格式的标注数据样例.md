# 新版基于coco格式的标注数据样例

日期：2020-11-3

编写人：周展

说明：原有接口上，不同的标注方式使用的数据格式并不完全相同，体例不统一。现在按照统一的格式，参考coco的格式，设计了一套标注数据格式，支持所有已知的2D标注场景。

未来重构时使用这套格式作为规范。

对于识别结果的数据格式，也可以参考这套方案。

coco的标注格式是把图片和标注都视为是一组数据池，所以图片都在images数组中，标注都在annotations数组中，并不区分具体是哪张图片的标注，而是将多张图片的标注全部摊在一个数组之内。

对于算法模块返回的测试（推理 inference）结果，以一个外层数组包含多个coco格式的map的方式来处理，而不是使用coco的全摊平的方式。因为测试过程是一张图一张图处理的，测试结果回收后也是要一张图一张图地进行记录，没必要以摊平的方式处理，后面还得从摊平的结果中抽取每张图的识别结果，没必要人为引入不必要的复杂性。

备注: 
    1. categories: 添加color属性
    2. annotations: 添加category_name属性

## 新版标注数据样例

### OCR标注数据样例

```json
// 基于coco格式的OCR标注数据样例，对coco有所扩展
{
    "info": {
        "description": "xxx模型标注数据集",
        "url": "http://nb-ai.com",
        "version": "1.0",
        "year": 2020,
        "contributor": "Matrix Vision Co.",
        "date_created": "2020-11-2 19:54:58"
    },
    "licenses": [
        {
            "id": 1,
            "name": "OCR",  // 标注文件对应哪种标注方式
            "url": ""
        }
    ],
    "images": [
        {
            "id": 1,
            "license": 1,
            "file_name": "xxx-xxx-xxx.jpg",
            "coco_url": "http://static2.nb-ai.com/xxx/xxx.jpg",  // 用于显示图片的文件url
            "thumbnail_url": "http://static2.nb-ai.com/xxx/xxx.jpg!thumbnail",  // 小缩略图
            "width": 1000,
            "height": 500,
            "flickr_url": "",
            "date_captured": "2020-11-2 20:21:27"
        }
    ],
    "categories": [  // 标签列表
        {
            "id": 1,
            "name": "A",  // 标签名
            "color":"red",
            "supercategory": "A"  // 所有的标签都是一级标签，不设层级结构
        },
        {
            "id": 2,
            "name": "B",
            "color":"blue",
            "supercategory": "B"
        }
    ],
    "annotations": [
        {
            "id": 1,  // 如果要防止对手通过id获取数据量，就需要做id加密
            "image_id": 12345,  // 标注对应的图片的id
            "area": 200,  // 标注区域的面积，长×宽
            "category_id": 1,  // 标签的id
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "bbox": [100, 222, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]   注意：旋转框的是旋转之前的坐标
            "angle": 0,  // 标注框的旋转角度，以bbox对应的矩形框为基础，以bbox框中心点为轴，顺时针为+值，角度范围为0°到360°
            //算法自行转换为 逆时针为+值，顺时针为-值，角度范围为±90°，，数据类型为 float,不考虑头朝下的情况。
            "segmentation": []  // 目标识别只有框，没有点
        },{
            "id": 2,
            "image_id": 12345,  // 标注对应的图片的id
            "area": 200,  // 标注区域的面积，长×宽
            "category_id": 2,  // 标签的id，两个不同的标签用于在同一张图上标注不同类型的物体
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "bbox": [300, 222, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]   注意：旋转框的是旋转之前的坐标
            "angle": 0,  // 标注框的旋转角度，以bbox对应的矩形框为基础，以bbox框中心点为轴，顺时针为+值，角度范围为0°到360°
            //算法自行转换为 逆时针为+值，顺时针为-值，角度范围为±90°，不考虑头朝下的情况。
            "segmentation": []
        },
    ],
    "mask":[  // 不可训练区域，即掩模，定名为mask，独立一段，与annnotations段并列，都是一个或多个多边形区域。TODO：回头和产品确定具体业务逻辑，和设计、前端确定操作逻辑之后再说，不是一个急需的功能
        {
            "id": 1,
            "image_id": 12345,  // 标注对应的图片的id
            "area": 200,  // 标注区域的面积，像素分割的面积是bbox的长×宽，取这个外框的面积
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "segmentation": [[101, 111, 123, 145]]  // 格式为[x0, y0, x1, y1, ...]的标注区域中的点列表，构成一个封闭区域
        }
    ]
}

```



### 目标定位标注数据样例

```json
// 基于coco格式的目标识别标注数据样例，对coco有所扩展
{
    "info": {
        "description": "xxx模型标注数据集",
        "url": "http://nb-ai.com",
        "version": "1.0",
        "year": 2020,
        "contributor": "Matrix Vision Co.",
        "date_created": "2020-11-2 19:54:58"
    },
    "licenses": [
        {
            "id": 1,
            "name": "目标识别",  // 标注文件对应哪种标注方式
            "url": ""
        }
    ],
    "images": [
        {
            "id": 1,
            "license": 1,
            "file_name": "xxx-xxx-xxx.jpg",
            "coco_url": "http://static2.nb-ai.com/xxx/xxx.jpg",  // 用于显示图片的文件url
            "thumbnail_url": "http://static2.nb-ai.com/xxx/xxx.jpg!thumbnail",  // 小缩略图
            "width": 1000,
            "height": 500,
            "flickr_url": "",
            "date_captured": "2020-11-2 20:21:27"
        }
    ],
    "categories": [  // 标签列表
        {
            "id": 1,	// categories中的id必须是从1开始顺序递增的形式，严格要求，否则后面算法模块会踩雷。
            "name": "缺陷1-标签",  // 标签名
            "color":"red",
            "supercategory": "缺陷1-标签"  // 所有的标签都是一级标签，不设层级结构
        },
        {
            "id": 2,
            "name": "缺陷2-标签",
            "color":"blue",
            "supercategory": "缺陷2-标签"
        }
    ],
    "annotations": [
        {
            "id": 1,  // 如果要防止对手通过id获取数据量，就需要做id加密
            "image_id": 12345,  // 标注对应的图片的id
            "area": 200,  // 标注区域的面积，长×宽
            "category_id": 1,  // 标签的id
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "bbox": [100, 222, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]
            "angle": 0,  // 标注框的旋转角度，以bbox对应的矩形框为基础，以bbox框中心点为轴，逆时针为+值，顺时针为-值，角度范围为±90°，不考虑头朝下的情况。
            "segmentation": []  // 目标识别只有框，没有点
        },{
            "id": 2,
            "image_id": 12345,  // 标注对应的图片的id
            "area": 200,  // 标注区域的面积，长×宽
            "category_id": 2,  // 标签的id，两个不同的标签用于在同一张图上标注不同类型的物体
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "bbox": [300, 222, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]
            "angle": 0,  // 标注框的旋转角度，以bbox对应的矩形框为基础，以bbox框中心点为轴，逆时针为+值，顺时针为-值，角度范围为±90°，不考虑头朝下的情况。
            "segmentation": []
        },
    ],
    "mask":[  // 不可训练区域，即掩模，定名为mask，独立一段，与annnotations段并列，都是一个或多个多边形区域。TODO：回头和产品确定具体业务逻辑，和设计、前端确定操作逻辑之后再说，不是一个急需的功能
        {
            "id": 1,
            "image_id": 12345,  // 标注对应的图片的id
            "area": 200,  // 标注区域的面积，像素分割的面积是bbox的长×宽，取这个外框的面积
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "segmentation": [[101, 111, 123, 145]]  // 格式为[x0, y0, x1, y1, ...]的标注区域中的点列表，构成一个封闭区域
        }
    ]
}
```



### 像素分割标注数据样例

```json
// 基于coco格式的像素分割标注数据样例，对coco有所扩展
{
    "info": {
        "description": "xxx模型标注数据集",
        "url": "http://nb-ai.com",
        "version": "1.0",
        "year": 2020,
        "contributor": "Matrix Vision Co.",
        "date_created": "2020-11-2 19:54:58"
    },
    "licenses": [
        {
            "id": 1,
            "name": "像素分割",  // 标注文件对应哪种标注方式
            "url": ""
        }
    ],
    "images": [
        {
            "id": 1,
            "license": 1,
            "file_name": "xxx-xxx-xxx.jpg",
            "coco_url": "http://static2.nb-ai.com/xxx/xxx.jpg",  // 用于显示图片的文件url
            "thumbnail_url": "http://static2.nb-ai.com/xxx/xxx.jpg!thumbnail",  // 小缩略图
            "width": 1000,
            "height": 500,
            "flickr_url": "",
            "date_captured": "2020-11-2 20:21:27"
        }
    ],
    "categories": [  // 标签列表
        {
            "id": 1,	// categories中的id必须是从1开始顺序递增的形式，严格要求，否则后面算法模块会踩雷。
            "name": "缺陷1-标签",  // 标签名
            "color":"red",
            "supercategory": "缺陷1-标签"  // 所有的标签都是一级标签，不设层级结构
        },
        {
            "id": 2,
            "name": "缺陷2-标签",
            "color":"blue",
            "supercategory": "缺陷2-标签"
        }
    ],
    "annotations": [
        {
            "id": 1,  // 如果要防止对手通过id获取数据量，就需要做id加密
            "image_id": 12345,  // 标注对应的图片的id
            "category_id": 1,  // 标签的id
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "bbox": [],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]
            "area": 200,  // 标注区域的面积，像素分割的面积是bbox的长×宽，取这个外框的面积
            "angle": 0,  // 标注框的旋转角度，像素分割的角度都是0
            "segmentation": [[101, 111, 123, 145]]  // 格式为[x0, y0, x1, y1, ...]的标注区域中的点列表，构成一个封闭区域
        },{
            "id": 2,
            "image_id": 12345,  // 标注对应的图片的id
            "area": 200,  // 标注区域的面积，像素分割的面积是bbox的长×宽，取这个外框的面积
            "category_id": 2,  // 标签的id，两个不同的标签用于在同一张图上标注不同类型的物体、缺陷
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "bbox": [100, 222, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]，像素分割的矩形框范围是框住多边形的矩形范围
            "angle": 0,  // 标注框的旋转角度，像素分割的角度都是0
            "segmentation": [[101, 111, 123, 145]]  // 格式为[x0, y0, x1, y1,, ...]的标注区域中的点列表，构成一个封闭区域。像素分割的点使用2元组，(x,y)，不需要提供第3元的可见性类型
        },
    ],
    "mask":[  // 不可训练区域，即掩模，定名为mask，独立一段，与annnotations段并列，都是一个或多个多边形区域。TODO：回头和产品确定具体业务逻辑，和设计、前端确定操作逻辑之后再说，不是一个急需的功能
        {
            "id": 1,
            "image_id": 12345,  // 标注对应的图片的id
            "area": 200,  // 标注区域的面积，像素分割的面积是bbox的长×宽，取这个外框的面积
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "segmentation": [[101, 111, 123, 145]]  // 格式为[x0, y0, x1, y1, ...]的标注区域中的点列表，构成一个封闭区域
        }
    ]
}
```



### 尺寸测量标注数据样例

#### 说明：

尺寸测量第一版设计是在一张图中允许多个工件，每个工件上有多个定位点，每种工件（标签）上的定位点数量必须相同。

新版设计：由于算法层为了优化训练能力，从整图训练改成了抠图训练，把每个定位点周围200*200像素的范围抠出来（实际是训练集中这个点的浮动范围形成的包围框再外廓100px），拼成一张图去训练，测试时，也是用一个相同的抠图模板对测试图片抠图，将抠出来的马赛克拼成一张新图去测试。
由于这套抠图的操作，所以这一版尺寸测量不支持混料，没有标签的概念，如果要混料，只能是先用定位算法做个分割，再扔给尺寸测量的模型进行处理。

标注方面，由于不再支持混料，或者多零件测试，所以也就没有用于区分多零件的外框标注，bbox没用了，只有定位点。
为了辅助后续二次开发的工作，对于标注定位点这件事，优化为支持多种几何形状的标注，最终所有的几何形状都会转化为定位点进行训练和测试，而测试得到的定位点会被还原为集合形状用于回显。

目前支持的几何形状：

1. 点
2. 线段（起点，终点）
3. 圆（圆心，圆周上一点）
4. 矩形（4个顶点，由于支持旋转框，所以不以4个顶点的方式记录，而是中心点坐标，旋转角度逆时针为+值，宽高，这样的形式记录）
   测试结果中的矩形框数据，由于测试返回的是4个顶点的位置，而不是一个矩形，4个顶点的位置还可能有一定误差，所以回显时只能以一个Path对象显示一个4顶点多边形，而测试结果转标注时，需要有一个对应的矩形用于转标注。

最多只支持30个定位点，不能更多。

标注时，前端将画布上的各种几何形状收集下来，按照画到canvas上的顺序（久虎会给每个对象分配一个自增的id）统一放到一个数组中，

#### 标注数据格式：

##### 前端传给后端的coco

```json
{
	"info": {
        "contributor": "Matrix Vision Co.",
        "year": "2021",
        "date_created": "2021-8-13 10:47:08",
        "description": "xxx模型标注数据集",
        "version": "1.0",
        "url": "http://nb-ai.com"
    },
    "images": [
        {
            "license": 1,
            "file_name": "9B4B11DD075AF6BCAB5B83AFAB1976CE12217942.bmp",//文件名称
            "flickr_url": "",
            "width": 1000,
            "coco_url": "http://static2.nb-ai.com/xxx/xxx.jpg",// 用于显示图片的文件url
            "date_captured": "2021-8-13 10:49:18",
            "id": 1,
            "thumbnail_url": "http://static2.nb-ai.com/xxx/xxx.jpg!thumbnail",//小的缩略图
            "height": 500
        }
    ],
    "licenses": [
        {
            "name": "尺寸测量",// 标注文件对应哪种标注方式
            "id": 1,
            "url": ""
        }
    ],
	"categories": [ //标签列表  尺寸测量中的标注数量和标签数量的类型和顺序是严格一致的
        {
			"id": 1,// categories中的id必须是从1开始顺序递增的形式，严格要求，否则后面算法模块会踩雷。
            "color": "red",
            "name": "点1",// 标签名
            "supercategory": "点1",// 所有的标签都是一级标签，不设层级结构
            "type": "point" //类型 point：点，line：线段，circle：圆，rectangle：矩形
        },
        
        {
            "id": 2,
            "color": "green",
            "name": "线1",
            "supercategory": "线1",
            "type": "line"
        },
        {
             "id": 3,
             "color": "blue",
             "name": "圆1",
             "supercategory": "圆1",
             "type": "circle"
         },
         {
             "id": 4,
             "color": "blue",
             "name": "矩形1",
             "supercategory": "矩形1",
             "type": "rectangle"
         }
    ],
    "annotations": [
        {
            "id": 1,// 如果要防止对手通过id获取数据量，就需要做id加密
            "area": 200,//标注区域的面积，长×宽  这里暂时写死200
            "category_name": "点1",//标签名称
            "category_id": 1,// 标签的id
            "bbox": [0,0,1024,768],// 标注区域，默认是整张图
            "iscrowd": 0,// 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "angle": "0",// 尺寸测量不支持旋转框，旋转框是个比较讨厌的东西，fabricjs处理旋转框时，是记录原始框位置+旋转角度，回显也是用这套数据，所以4角坐标估计要自行计算。算法训练时用的是4角点坐标，测试结果也是4角点坐标，回显测试结果时，为了保证回显精确，就不能回显为矩形，只能是回显为Path四边形，但是测试结果转标注时，是用旋转的矩形做标注，所以需要在回收测试结果时，根据推理得到的4个点坐标，推算一个最匹配的矩形。推理方法：矩形中心位置为4个定位点的平均坐标，旋转角度为1-2,3-4两条线的平均旋转角度，长宽：1-4,2-3两条线中点的距离，1-2,3-4两条线中点的距离。回收测试结果时，这里需要有一个计算过程
            "segmentation": [],
            "shape_type": "point",//图形类型
            "image_id": 1,//图片ID
			"keypoints":[12231,1224],//标注点
            "box": [],//矩形才使用，存储宽和高
            "center": []  //矩形框中心点坐标
        },
        {
            "area": 200,
            "category_name": "线1",
            "category_id": 2,
            "bbox": [0,0,1024,768],
            "iscrowd": 0,
            "angle": "0",
            "segmentation": [],
            "shape_type": "line",
            "id": 2,
            "image_id": 1,
			"keypoints":[236,246],
            "box":[],
            "center": []
        },
        {
            "area": 200,
            "category_name": "圆1",
            "category_id": 3,
            "bbox": [0,0,1024,768],
            "iscrowd": 0,
            "angle": "0",
            "segmentation": [],
            "shape_type": "circle",
            "id": 3,
            "image_id": 1,
			"keypoints":[123,234,124,236],
            "box":[],
            "center": []
        },
         {
             "area": 200,
             "category_name": "矩形1",
             "category_id": 3,
             "bbox": [0,0,1024,768],
             "iscrowd": 0,
             "angle": "0",
             "segmentation": [],
             "shape_type": "rectangle",
             "id": 3,
             "image_id": 1,
            "keypoints":[222,222,444,222,444,666,222,666],
             "box":[222,444],
             "center": [333,444]
         }
    ]
    
}

```



#### 定位点数据格式转换

后端收到这个数据之后，需要进行一次转换将一个数组中的不同类型的标注形状，按照形状分类，组织成多个数组，之后按照形状，检查项目中第一个标注在每个形状上的数量，是否与最新的标注中每种形状的标注数量一致，只有数量一致，才允许保存，否则报错。

给算法的数据是coco.keypoints格式，所以在java层完成正确性检查之后，要将标注点按形状分类的标注数据转换成只有点坐标格式的coco.keypoints格式，供算法使用



```json
// 基于coco格式的尺寸测量标注数据样例，对coco有所扩展
{
    "info": {
        "description": "xxx模型标注数据集",
        "url": "http://nb-ai.com",
        "version": "1.0",
        "year": 2020,
        "contributor": "Matrix Vision Co.",
        "date_created": "2020-11-2 19:54:58",
		// 最终供java使用的形状标注要保存在info段中，因为算法层在使用标注数据生成测试结果时，info段是原样copy的，这样才能保证java层在回收结果时能够读到原始的形状信息，然后根据形状标注信息将测试结果还原到标注形状上，返回给前端。
        "shapes" : {
            "points": [
                {	"type": "point",
                 "keypoints": [x1, y1]  },
                {	"type": "point",
                 "keypoints": [x1, y1]  }
            ],
            "lines": [
                {	"type": "line",
                 "keypoints": [x1, y1, x2, y2]  },
                {	"type": "line",
                 "keypoints": [x1, y1, x2, y2]  }
            ],
            "circles": [
                {	"type": "circle",
                 "keypoints": [x1, y1, x2, y2]  },
                {	"type": "circle",
                 "keypoints": [x1, y1, x2, y2]  }
            ],
            "rectangles": [
                {   "type": "rectangle", 
                 "keypoints": [x1, y1, x2, y2, x3, y3, x4, y4],
                 "angle": 12,
                 "center": [x1, y1],  // 中心点坐标
                 "box": [width, height],  // 宽高
                },
                {   "type": "rectangle", 
                 "keypoints": [x1, y1, x2, y2, x3, y3, x4, y4],
                 "angle": 12,
                 "center": [x1, y1],  // 中心点坐标
                 "box": [width, height],  // 宽高
                }
            ]
        }
    },
    "licenses": [
        {
            "id": 1,
            "name": "尺寸测量",  // 标注文件对应哪种标注方式
            "url": ""
        }
    ],
    "images": [
        {
            "id": 1,
            "license": 1,
            "file_name": "xxx-xxx-xxx.jpg",
            "coco_url": "http://static2.nb-ai.com/xxx/xxx.jpg",  // 用于显示图片的文件url
            "thumbnail_url": "http://static2.nb-ai.com/xxx/xxx.jpg!thumbnail",  // 小缩略图
            "width": 1000,
            "height": 500,
            "flickr_url": "",
            "date_captured": "2020-11-2 20:21:27"
        }
    ],
    "categories": [  // 标签列表
        {
            "id": 1,	// categories中的id必须是从1开始顺序递增的形式，严格要求，否则后面算法模块会踩雷。
            "name": "尺寸测量-标签",  // 标签名
            "color":"red",
            "supercategory": "尺寸测量-标签"  // 所有的标签都是一级标签，不设层级结构
        }
    ],
    "annotations": [
        {
            "id": 1,  // 如果要防止对手通过id获取数据量，就需要做id加密
            "image_id": 12345,  // 标注对应的图片的id
            "area": 0,  // 标注区域的面积，长×宽
            "category_id": 1 ,  // 标签的id
            "category_name": "尺寸测量-标签" ,  // 标签的名称
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "bbox": [100, 222, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]，定位点的闭包边框
            "angle": 0,  // 尺寸测量不支持旋转框
            "segmentation": [],
            "keypoints": [101, 111, 2, 123, 145, 2]  // 格式为[x0, y0, v0, x1, y1, v1, ...]的标注区域中的位置标注点，
                // v是标注点的可见性，v=0表示这个关键点没有标注（这种情况下x=y=v=0），v为1时表示这个关键点标注了但是不可见（被遮挡了），v为2时表示这个关键点标注了同时也可见。
                // 同一个项目中的所有图片，每种形状的标注的数量必须一致，这样关键点的数量才会一致，否则模型无法训练。
            	// 将shapes中的各种标注形状，按照 [点，线，圆，矩]的顺序，先打包点，然后是线，将所有标注形状中的关键点顺序记入keypoints中
        }
    ]
}
```

注意：后端还给前端回显的测试结果，需要转换成前端传给后端的shape字段格式，每一层都要保证在与其他层的界面上输入输出的数据格式保持一致。



### 场景分类标注数据样例

```json
// 基于coco格式的场景分类标注数据样例，对coco有所扩展
{
    "info": {
        "description": "xxx模型标注数据集",
        "url": "http://nb-ai.com",
        "version": "1.0",
        "year": 2020,
        "contributor": "Matrix Vision Co.",
        "date_created": "2020-11-2 19:54:58"
    },
    "licenses": [
        {
            "id": 1,
            "name": "场景分类",  // 标注文件对应哪种标注方式
            "url": ""
        }
    ],
    "images": [
        {
            "id": 1,
            "license": 1,
            "file_name": "xxx-xxx-xxx.jpg",
            "coco_url": "http://static2.nb-ai.com/xxx/xxx.jpg",  // 用于显示图片的文件url
            "thumbnail_url": "http://static2.nb-ai.com/xxx/xxx.jpg!thumbnail",  // 小缩略图
            "width": 1000,
            "height": 500,
            "flickr_url": "",
            "date_captured": "2020-11-2 20:21:27"
        }
    ],
    "categories": [  // 标签列表
        {
            "id": 1,	// categories中的id必须是从1开始顺序递增的形式，严格要求，否则后面算法模块会踩雷。
            "name": "类型1-标签",  // 标签名
            "color":"red",
            "supercategory": "类型1-标签"  // 所有的标签都是一级标签，不设层级结构
        },
        {
            "id": 2,
            "name": "类型2-标签",
            "color":"blue",
            "supercategory": "类型2-标签"
        }
    ],
    "annotations": [
        {
            "id": 1,  // 如果要防止对手通过id获取数据量，就需要做id加密
            "image_id": 12345,  // 标注对应的图片的id
            "area": 0,  // 标注区域的面积，长×宽
            "category_id": 1,  // 标签的id
            "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
            "bbox": [0, 0, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 图片宽度, 图片高度]
            "angle": 0,  // 标注框的旋转角度，以bbox对应的矩形框为基础，以bbox框中心点为轴，逆时针为+值，顺时针为-值，角度范围为±90°，不考虑头朝下的情况。
            "segmentation": []  // 目标识别只有框，没有点
        }
    ]
}
```


## 现有测试结果数据格式

```json
[
    {
        "model_id": "48527100",	// 模型id
        "path": "5e72a275-39d0-46b6-a6b5-afd21e4c4ad3.jpg",	// 图片名称
        "mark": [
            {
                "value": "缺陷",				   // 缺陷的标签
                "score": 0.8312975168228149,	// 检出区域的得分
                "x": 430.2046813964844,			// 检出区域的外廓方框
                "y": 487.57720947265625,
                "width": 542.0629577636719,
                "height": 84.71893310546875
            }, {...}
        ],
        "result": "4852710031608218595.jpg"	// 像素分割缺陷检测会带有这个返回图片
    },
    {...}
]
```



## 新版测试结果数据格式

### 目标定位返回结果数据样例

```json
// 基于coco格式的目标识别结果数据样例，对coco有所扩展
[
    {
        "info": {
            "description": "xxx模型测试数据集",
            "url": "http://nb-ai.com",
            "version": "1.0",
            "year": 2020,
            "contributor": "Matrix Vision Co.",
            "date_created": "2020-11-2 19:54:58"
        },
        "licenses": [	// licenses用来说明是哪种业务
            {
                "id": 1,
                "name": "目标识别",  // 标注文件对应哪种标注方式
                "url": ""
            }
        ],
        "images": [	// 图片中只有一项，核心内容是图片的id
            {
                "id": 12345,
                "license": 1,
                "file_name": "xxx-xxx-xxx.jpg",
            }
        ],
        "categories": [  // 标签列表
            {
                "id": 1,
                "name": "A",  // 标签名
                "supercategory": "A"  // 所有的标签都是一级标签，不设层级结构
            },
            {
                "id": 2,
                "name": "B",
                "supercategory": "B"
            }
        ],
        "annotations": [
            {
                "id": 1,  // 如果要防止对手通过id获取数据量，就需要做id加密
                "image_id": 12345,  // 标注对应的图片的id
                "category_id": 1,  // 标签的id
                "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
                "bbox": [100, 222, 10, 20],  // 识别结果区域，格式为 [左上角x, 左上角y, 宽度, 高度]
                "angle": 0,  // 标注框的旋转角度，暂定以左上角为基准记录，由于目前前端尚未支持旋转框，基准点问题可以再讨论
                "segmentation": [],  // 目标识别只有框，没有点
                "scores": [0.8765]
            },{
                "id": 2,  // 如果要防止对手通过id获取数据量，就需要做id加密
                "image_id": 12345,  // 标注对应的图片的id
                "category_id": 2,  // 标签的id，两个不同的标签用于在同一张图上标注不同类型的物体
                "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
                "bbox": [100, 222, 10, 20],  // 识别结果区域，格式为 [左上角x, 左上角y, 宽度, 高度]
                "angle": 0,  // 标注框的旋转角度，暂定以左上角为基准记录，由于目前前端尚未支持旋转框，基准点问题可以再讨论
                "segmentation": [],
                "scores": [0.9876]
            },
        ]
    }, {...}
]

```



### 像素分割返回结果数据样例

```json
// 基于coco格式的像素分割结果数据样例，对coco有所扩展
[
    {
        "info": {
            "description": "xxx模型测试数据集",
            "url": "http://nb-ai.com",
            "version": "1.0",
            "year": 2020,
            "contributor": "Matrix Vision Co.",
            "date_created": "2020-11-2 19:54:58"
        },
        "licenses": [
            {
                "id": 1,
                "name": "像素分割",  // 标注文件对应哪种标注方式
                "url": ""
            }
        ],
        "images": [
            {
                "id": 12345,
                "license": 1,
                "file_name": "xxx-xxx-xxx.jpg",
            }
        ],
        "categories": [  // 标签列表
            {
                "id": 1,
                "name": "A",  // 标签名
                "supercategory": "A"  // 所有的标签都是一级标签，不设层级结构
            },
            {
                "id": 2,
                "name": "B",
                "supercategory": "B"
            }
        ],
        "annotations": [
            {
                "id": 1,  // 如果要防止对手通过id获取数据量，就需要做id加密
                "image_id": 12345,  // 标注对应的图片的id
                "category_id": 1,  // 标签的id
                "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
                "bbox": [100, 222, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]，像素分割的矩形框范围是框住多边形的矩形范围
                "angle": 0,  // 标注框的旋转角度，像素分割的角度都是0
                "segmentation": [[101, 111, 123, 145]],  // 格式为[x0, y0, x1, y1, ...]的标注区域中的点列表，构成一个封闭区域
                "scores": [0.8765]
            },{
                "id": 1,  // 如果要防止对手通过id获取数据量，就需要做id加密
                "image_id": 12345,  // 标注对应的图片的id
                "category_id": 2,  // 标签的id，两个不同的标签用于在同一张图上标注不同类型的物体、缺陷
                "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
                "bbox": [100, 222, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]，像素分割的矩形框范围是框住多边形的矩形范围
                "angle": 0,  // 标注框的旋转角度，像素分割的角度都是0
                "segmentation": [[101, 111, 123, 145]],  // 格式为[x0, y0, x1, y1,, ...]的标注区域中的点列表，构成一个封闭区域
                "scores": [0.9876]
            },
        ]
    }, {...}
]

```



### 尺寸测量返回结果格式样例

```json
// 基于coco格式的尺寸测量测试结果
[
    {
        "info": {
            "description": "xxx模型测试数据集",
            "url": "http://nb-ai.com",
            "version": "1.0",
            "year": 2020,
            "contributor": "Matrix Vision Co.",
            "date_created": "2020-11-2 19:54:58",
            "categories": [ //标签列表  尺寸测量中的标注数量和标签数量的类型和顺序是严格一致的
                    {
                        "id": 1,// categories中的id必须是从1开始顺序递增的形式，严格要求，否则后面算法模块会踩雷。
                        "color": "red",
                        "name": "点1",// 标签名
                        "supercategory": "点1",// 所有的标签都是一级标签，不设层级结构
                        "type": "point" //类型 point：点，line：线段，circle：圆，rectangle：矩形
                    },
                    
                    {
                        "id": 2,
                        "color": "green",
                        "name": "线1",
                        "supercategory": "线1",
                        "type": "line"
                    },
                    {
                         "id": 3,
                         "color": "blue",
                         "name": "圆1",
                         "supercategory": "圆1",
                         "type": "circle"
                     },
                     {
                         "id": 4,
                         "color": "blue",
                         "name": "矩形1",
                         "supercategory": "矩形1",
                         "type": "rectangle"
                     }
                ]
        },
        "licenses": [	// licenses用来说明是哪种业务
            {
                "id": 1,
                "name": "尺寸测量",  // 标注文件对应哪种标注方式
                "url": ""
            }
        ],
        "images": [	// 图片中只有一项，核心内容是图片的id
            {
                "id": 1,
                "license": 1,
                "file_name": "xxx-xxx-xxx.jpg",
            }
        ],
        "categories": [  // 标签列表
            {
                "id": 1,
                "color": "red",
                "name": "尺寸测量标签",
                "supercategory": "尺寸测量标签"
            }
        ],
        "annotations": [
            {
                "id": 2,  // 如果要防止对手通过id获取数据量，就需要做id加密
                "image_id": 12345,  // 标注对应的图片的id
	            "category_id": 2,  // 标签的id
                "iscrowd": 0,  // 必须是0，1表示后面使用RLE游程编码，很麻烦的
                "bbox": [300, 222, 10, 20],  // 标注区域，格式为 [左上角x, 左上角y, 宽度, 高度]
                "angle": 0,  // 标注框的旋转角度，暂定以左上角为基准记录，由于目前前端尚未支持旋转框，基准点问题可以再讨论
                "segmentation": [],
                "keypoints": [101, 111, 2, 123, 145, 2,231,223,2],  // 格式为[x0, y0, v0, x1, y1, v1, ...]的识别结果
                "scores": [0.8765, 0.9876]
            }
        ]
    }, {...}
]

```



