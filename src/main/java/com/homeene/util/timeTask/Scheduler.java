package com.homeene.util.timeTask;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.homeene.model.User;
import com.homeene.service.UserService;
import com.homeene.utils.Iterables;

@Component  
public class Scheduler {  
    @Resource
    private UserService userService;
    @Scheduled(cron="0 0 0 30 9 *") //每分钟执行一次  
    public void statusCheck() {      
        System.out.println("每分钟执行一次。开始……");  
        //statusTask.healthCheck();  
        System.out.println("每分钟执行一次。结束。");  
        List<User> userlist=userService.selectByCollect(1);
        int sum=userlist.size();
        float average=9000/(sum-1);
        Iterables.forEach(userlist,(index, user)->{
        	if(index==0) {
        		user.setMoney(1000);
        	}else {
        		user.setMoney(average);
        	}
        	userService.update(user);
        });
    }    
  
//    @Scheduled(fixedRate=20000)  
//    public void testTasks() {      
//    	  System.out.println("每20秒执行一次。开始……");  
//        //statusTask.healthCheck();  
//    	  System.out.println("每20秒执行一次。结束。");  
//    }    
} 