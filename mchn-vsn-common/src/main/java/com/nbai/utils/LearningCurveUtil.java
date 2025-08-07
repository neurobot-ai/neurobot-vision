package com.nbai.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * 学习曲线工具类
 * 
 * @author zhenmeng
 * @date 2021-5-7
 *
 */
public class LearningCurveUtil {

	/**
	 * 生成学习曲线
	 * 
	 * @param loss 损失函数值json数组
	 * @return
	 */
	public static List<Double> generateLearningCurve(JSONArray lossJarray) {
		if (lossJarray == null || lossJarray.isEmpty())
			return Collections.emptyList();
		List<Double> curve = new ArrayList<>(lossJarray.size());
		for (int i = 0; i < lossJarray.size(); i++) {
			double pow = Math.pow(Math.E, lossJarray.getDouble(i));
			if (Double.isInfinite(pow) || Double.isNaN(pow)){
				curve.add(1d);
				continue;
			}
			double result = new BigDecimal(1).divide((new BigDecimal(1).add(new BigDecimal(pow))), 3, RoundingMode.HALF_UP).doubleValue();
			curve.add(result);
		}
		return curve;
	}

}
