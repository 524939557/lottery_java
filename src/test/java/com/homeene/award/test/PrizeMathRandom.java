package com.homeene.award.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.homeene.award.test.TestPrize.User;


import com.homeene.award.test.TestPrize.Award;


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
    		totalPro += award.getProbability() * 1000;
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
    public static List<Award> probabilitychange(List<Award> awardList,List<User> myward){
    	int sum=myward.size();
    	float probality=sum*0.01f/(21-sum);
    	float pro=(float)(Math.round(probality*1000))/1000;
		
    	List<Award> result = awardList.stream().map(award -> {
    	  Integer awardId = award.getId();
           boolean hasCards = myward
                    .stream()
                    .anyMatch(mward -> awardId.equals(mward.getAwardId()));
            if (hasCards) {
                award.setProbability(award.getProbability()-0.1f); 
            }else {
            	award.setProbability(award.getProbability()+pro); 
            }
            return award;
        }).collect(Collectors.toList());
		return result;
	}
    
    public  static float formatFloat(float f) {
		  return new  BigDecimal(Double.toString(f)).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
    }
		
}
