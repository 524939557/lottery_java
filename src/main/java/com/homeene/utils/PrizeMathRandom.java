package com.homeene.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.homeene.model.Award;


public class PrizeMathRandom {
    
	/**
	 * 随机抽卡
	 * @param awards
	 * @return
	 */
    public static Award lottery(List<Award> awards){
        //总的概率区间
        float totalPro = 0f;
        //存储每个奖品新的概率区间
        List<Float> proSection = new ArrayList<Float>();
        proSection.add(0f);
        //遍历每个奖品，设置概率区间，总的概率区间为每个概率区间的总和
        
        for (Award award : awards) {
            //每个概率区间为奖品概率乘以1000（把三位小数转换为整）再乘以剩余奖品数量
        	//SELECT * from wx_address ORDER BY RAND() LIMIT 3
        	if(award.getCount()<=0){
        		award.setProbability(0);
        	}
    		totalPro += award.getProbability()* 10 * award.getCount();
            proSection.add(totalPro);
        }
        //获取总的概率区间中的随机数
        Random random = new Random();
        float randomPro = (float)random.nextInt((int)totalPro);
        //判断取到的随机数在哪个奖品的概率区间中
        for (int i = 0,size = proSection.size(); i < size; i++) {
            if(randomPro >= proSection.get(i) && randomPro < proSection.get(i + 1)){
                return awards.get(i);
            }
        }
        return null;
    }

}
