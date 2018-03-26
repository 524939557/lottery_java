package com.homeene.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.Department;
import com.homeene.alibaba.auth.AuthHelper;
import com.homeene.alibaba.department.DepartmentHelper;
import com.homeene.alibaba.user.UserHelper;
import com.homeene.model.User;
import com.homeene.model.UserRsp;
import com.homeene.service.UserService;
import com.homeene.utils.ExcelTest;

@RestController
public class UserController {

	@Resource
	private UserService userService;

	public String login(String token) {
		return token;
	}
		
	@RequestMapping(value="/test",method = RequestMethod.GET)
	public void test() throws Exception {
		JSONArray ja = new JSONArray();
		List<User> ulist=userService.selectUser();
		String accessToken = AuthHelper.getAccessToken();
		for(User u:ulist) {
			CorpUserDetail userDetail=UserHelper.getUser(accessToken, u.getUserid());
			if(userDetail!=null) {
				List<Long> depart=userDetail.getDepartment();
				StringBuffer name=new StringBuffer();
				for(Long l:depart) {
					Department department=DepartmentHelper.getDepartment(accessToken,l+"");
					name.append(department.getName()+",");
				}
				String dename=name.toString().substring(0,name.length()-1);
				UserRsp data=new UserRsp(u.getUserid(),
						u.getName(),u.getMobile(),dename,depart.toString());
				ja.add(data);
			}
		}
		 Map<String,String> headMap = new LinkedHashMap<String,String>();
		    headMap.put("userid","userId");
	        headMap.put("name","姓名");
	        headMap.put("mobile","手机");
	        headMap.put("department","部门");
	        headMap.put("departmentId","部门id");
	        String title = "测试";
	        OutputStream outXlsx = new FileOutputStream("E://b.xlsx");
	        System.out.println("正在导出xlsx....");
	        Date d2 = new Date();
	        ExcelTest.exportExcelX(title,headMap,ja,null,0,outXlsx);
	        System.out.println("共"+ulist.size()+"条数据,执行"+(new Date().getTime()-d2.getTime())+"ms");
	        outXlsx.close();
	}

}
