package com.homeene.utils;

import java.util.Calendar;
import java.util.Date;


public class DateUtils {

	public static boolean checkDay(Date date) {
		Calendar calendar=Calendar.getInstance();
		Date now=calendar.getTime();
		calendar.setTime(date);
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
		Date nextRed=calendar.getTime();
		System.out.println("now="+now+"   nexttime="+nextRed);
		return now.after(nextRed);
		
	}
}
