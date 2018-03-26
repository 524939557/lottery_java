package com.homeene.model;

import java.util.Date;

public class UserRsp {


	private String userid;

	private String name;

	private String mobile;

	private String department;
	
	private String departmentId;

	
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

	
	public String getMobile() {
		return mobile;
	}

	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	public String getDepartment() {
		return department;
	}

	
	public void setDepartment(String department) {
		this.department = department;
	}

	
	public String getDepartmentId() {
		return departmentId;
	}

	
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	public UserRsp() {
		
	}

	public UserRsp(String userid, String name, String mobile, String department, String departmentId) {
		super();
		this.userid = userid;
		this.name = name;
		this.mobile = mobile;
		this.department = department;
		this.departmentId = departmentId;
	}

}