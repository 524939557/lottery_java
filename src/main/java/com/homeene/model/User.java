package com.homeene.model;

import java.util.Date;

public class User {
    private Integer id;

    private String userid;

    private String name;

    private String tel;

    private String mobile;

    private Boolean active;

    private Date createTime;
    
    private int collect;
    
    private Date collectTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean boolean1) {
        this.active = boolean1;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	
	public int getCollect() {
		return collect;
	}

	
	public void setCollect(int collect) {
		this.collect = collect;
	}

	
	public Date getCollectTime() {
		return collectTime;
	}

	
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
    
    
}