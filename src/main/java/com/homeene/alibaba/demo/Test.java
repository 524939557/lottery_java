package com.homeene.alibaba.demo;

import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.Department;
import com.homeene.alibaba.auth.AuthHelper;
import com.homeene.alibaba.department.DepartmentHelper;
import com.homeene.alibaba.user.UserHelper;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub 18283815
		// 获取access token
		String accessToken = AuthHelper.getAccessToken();
		System.out.println("成功获取access token: "+ accessToken);
		CorpUserDetail userDetail11 = UserHelper.getUser(accessToken,"63572364184");
		System.out.println("成功获取成员"+ "成员userid="+ userDetail11.getUserid());
		Department department=DepartmentHelper.getDepartment(accessToken,"31590192");
		System.out.println(department.getName());
	}

}
