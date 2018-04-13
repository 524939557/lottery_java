package com.homeene.utils;

import java.text.DecimalFormat;
import java.util.Random;
public class RandomUtils {

	/**
	 *
	 * 产生随机数0---10
	 *
	 * @return
	 */
	public static float getRandom() {
		int random = new Random().nextInt(10);
		float rand=0f;
		if (random > 8)
		{
			rand = (float) (Math.random()*5+1);
		} else
		{
			rand = (float) (Math.random()*5+1);
		}
		DecimalFormat Dformat = new DecimalFormat("0.00");
		// 根据格式化器格式化数据
		String df = Dformat.format(rand);
		System.out.println(df);
		return Float.valueOf(df);
	}

}