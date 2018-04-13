package com.homeene.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.homeene.common.Constants;
import com.homeene.req.ReqRedPack;

public class MsgDigest{

	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random =new Random();
		StringBuffer sb =new StringBuffer();
		for(int i=0;i<length;i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
	}

	public static String getSign(ReqRedPack redPack) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> list =new ArrayList<String>();
        System.out.println(redPack.getNonce_str());
        BeanInfo beanInfo=Introspector.getBeanInfo(redPack.getClass());
        PropertyDescriptor[] props=beanInfo.getPropertyDescriptors();
    	 Arrays.asList(props).forEach(p-> {if(!p.getName().equals("sign")&&!p.getName().equals("class")) {
         	Object value=null;
			try
			{
				value = p.getReadMethod().invoke(p.getName());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
         	list.add(p.getName()+"="+value+"&");
         }});
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<list.size();i++) {
            sb.append(list.get(i));
        }
        String result = sb.toString();
        result += "key=" + Constants.KEY;
        System.out.println("result1:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        System.out.println("result2:" + result);
        return result;
	}
	
	
}