package com.homeene.award.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.homeene.model.Award;


public class TestPrize {
	public static class User{
		private Integer id;
		private Integer awardId;
		public User() {
		}
		public User(Integer id, Integer awardId) {
			super();
			this.id = id;
			this.awardId = awardId;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getAwardId() {
			return awardId;
		}
		public void setAwardId(Integer awardId) {
			this.awardId = awardId;
		}
		
	}
	public static class Award{
		private Integer id;
		private String name;
		private float probability;
		private Integer count;
		public Award() {
		}
		public Award(Integer id, String awardId,float f,Integer i) {
			super();
			this.id = id;
			this.name=awardId;
			this.probability=f;
			this.count=i;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public float getProbability() {
			return probability;
		}
		
		public void setProbability(float probability) {
			this.probability = probability;
		}
		
		public Integer getCount() {
			return count;
		}
		
		public void setCount(Integer count) {
			this.count = count;
		}
		
		
	}
	public static void main(String[] args) {
        List<User> users=new ArrayList<TestPrize.User>();
        users.add(new User(1, 1));
        users.add(new User(1, 11));
        users.add(new User(1, 21));
        users.add(new User(1, 13));
       
        int[] result=new int[23];
        for (int i = 0; i < 60; i++) {
        	List<Award> awardlist=PrizeMathRandom.probabilitychange(new TestPrize().getAward(), users);
    		Award a=PrizeMathRandom.lottery(awardlist);
        	result[a.getId()]++;
            System.out.println("恭喜您["+i+"]，抽到了：" +a.getName());
            users.add(new User(1,a.getId()));
        }
        for(int i=0;i<result.length;i++){
        	System.out.println("卡片"+i+"的次数："+result[i]);
        }
    }
	
	public List<Award> getAward(){
		List<Award>  awards = new ArrayList<>();
		for (int i = 1; i < 22; i ++) {
        	awards.add(new Award(i, "卡片" + i, 0.047f, 2000));
        }
		return awards;
	}
	
}
