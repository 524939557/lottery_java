package com.homeene.award.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.homeene.award.test.TestPrize.Award;
import com.homeene.award.test.TestPrize.User;

public class Test {
	public static void main(String[] args) {
//	    List<Integer> l1 = new ArrayList<>();
//	    l1.add(1);
//	    l1.add(2);
//	    l1.add(3);
//	    l1.add(4);
//	    List<Integer> l2 = new ArrayList<>();
//	    l2.add(5);
//	    l2.add(6);
//	    l2.add(7);
//	    l2.add(8);
//	    
//	    Stream.of(l1, l2).flatMap(n -> n.stream()).filter(n->n%2==0).forEach(System.out::println);
	    
//	    List<User> users=new ArrayList<TestPrize.User>();
//        users.add(new User(1, 1));
//        users.add(new User(1, 11));
//        users.add(new User(1, 21));
//        users.add(new User(1, 13));
//        List<Award>  awards = new ArrayList<>();
//		for (int i = 1; i < 22; i ++) {
//        	awards.add(new Award(i, "卡片" + i, 0.047f, 2000));
//        }
//		List<Integer> awardidList = awards.stream().map(award->award.getId()).collect(Collectors.toList());
//		List<Integer> useridList = users.stream().map(user->user.getAwardId()).collect(Collectors.toList());
//		awardidList.removeAll(useridList);
//		awardidList.forEach((award)->{
//			//award
//		});
//		if(1) {
//			
//		}
//		System.out.println(awardidList);
		
		
		  List<User> users=new ArrayList<User>();
	        users.add(new User(1, 1));
	        users.add(new User(1, 11));
	        users.add(new User(1, 21));
	        users.add(new User(1, 13));
	        List<Award> awards = new ArrayList<>();
	    	for (int i = 1; i < 22; i ++) {
	        	awards.add(new Award(i, "card" + i, 0.047f, 2000));
	        }
//	        int[] a = {1,2,3,4};
//	        boolean contains = IntStream.of(a).anyMatch(x -> x == 4);
	        
	        // List<User> filteredUsers = users.stream()
	        //    .filter(p -> p.getAwardId() > 16).collect(Collectors.toList());
	                
//	    	List<Award> result = awards.stream().map(award -> {
//	    		Integer awardId = award.getId();
//	    		int sum=awards.size();
//	           boolean hasCards = users
//	                    .stream()
//	                    .anyMatch(mward -> awardId.equals(mward.getAwardId()));
//	            if (hasCards) {
//	            	float probality=sum*0.01f/(21-sum);
//	    			probality=(float)(Math.round(probality*1000))/1000;
//	                award.setProbability(award.getProbability()+probality); 
//	            }else {
//	            	award.setProbability(award.getProbability()); 
//	            }
//	            return award;
//	        }).collect(Collectors.toList());
//	        result.forEach(u->System.out.println(u.getProbability()));
	        int[] result=new int[23];
	        for (int i = 0; i < 600; i++) {
	        	List<Award> awardlist=PrizeMathRandom.probabilitychange(new Test().getAward(), users);
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
	        	awards.add(new Award(i, "卡片" + i,0.47f, 2000));
	        }
			return awards;
		}
}
