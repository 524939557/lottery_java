package com.homeene.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.homeene.dao.AwardMapper;
import com.homeene.model.Award;
import com.homeene.model.MyAward;
import com.homeene.utils.PrizeMathRandom;

@Service
public class AwardService{

	@Resource
	private AwardMapper awardMapper;
	
	public List<Award> getAward(){
		return awardMapper.selectAll();
	}
	public Award selectById(int id) {
		return awardMapper.selectByPrimaryKey(id);
	}
	public int updateAward(Award award) {
		return awardMapper.updateByPrimaryKey(award);
	}
	public Award lotter(List<Award> awardList,List<MyAward> myward) {
		List<Award> result = awardList.stream().map(award -> {
    		Integer awardId = award.getId();
    		int sum=myward.size();
           boolean hasCards = myward
                    .stream()
                    .anyMatch(mward -> awardId.equals(mward.getAwardId()));
            if (hasCards) {
            	float probality=sum*0.01f/(22-sum);
    			probality=(float)(Math.round(probality*1000))/1000;
                award.setProbability(award.getProbability()+probality); 
            }else {
            	award.setProbability(award.getProbability()); 
            }
            return award;
        }).collect(Collectors.toList());
		
		return PrizeMathRandom.lottery(result);
	}
}
