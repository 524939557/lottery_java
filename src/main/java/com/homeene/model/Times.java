package com.homeene.model;

import java.util.Date;

public class Times {
    private Integer id;

    private Integer times;

    private String userId;

    private String date;
    
    private Integer share;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	
	public String getDate() {
		return date;
	}

	
	public void setDate(String date) {
		this.date = date;
	}

	
	public Integer getShare() {
		return share;
	}

	
	public void setShare(Integer share) {
		this.share = share;
	}
    
    

}