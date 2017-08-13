package com.homeene.interceptor;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.homeene.alibaba.demo.Env;
import com.homeene.model.PersistentLogins;
import com.homeene.model.User;
import com.homeene.service.PersistentLoginService;
import com.homeene.service.UserService;
import com.homeene.utils.EncryptionUtil;

@Controller
public class UserInterceptor implements HandlerInterceptor  {
	@Autowired
	private PersistentLoginService persistentLoginsService;
	@Autowired
	private UserService userService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		String value=request.getHeader("Authorization");
		
			if (value != null) {
				String cookieValue = EncryptionUtil.base64Decode(value);

				String[] cValues = cookieValue.split(":");
				if (cValues.length == 2) {
					String userId = cValues[0]; 
					String uuid= cValues[1]; 
					PersistentLogins pLogins = persistentLoginsService.selectByUsernameAndSeries(userId,
							uuid);
					if (pLogins != null) {
						String savedToken = pLogins.getToken(); 

						Date savedValidtime = pLogins.getValidtime();
						Date currentTime = new Date();

						if (currentTime.before(savedValidtime)) {
							User u = userService.selectByUserId(userId);
							if (u != null) {
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(pLogins.getValidtime());
							
								String timeString = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
										+ "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-"
										+ calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE);
								
								String newToken = EncryptionUtil.sha256Hex(u.getUserid() + "_" + u.getMobile() + "_"
										+ timeString + "_" +  Env.CORP_ID);

								if (savedToken.equals(newToken)) {
								
									String uuidNewString = UUID.randomUUID().toString();
									String newCookieValue = EncryptionUtil
											.base64Encode(u.getUserid() + ":" + uuidNewString);
									response.setHeader("token", newCookieValue);

									pLogins.setSeries(uuidNewString);
									persistentLoginsService.updateByPrimaryKeySelective(pLogins);

									return true; 
								} else { 
									persistentLoginsService.deleteByPrimaryKey(pLogins.getId());
								}
							}
						} else { 
							persistentLoginsService.deleteByPrimaryKey(pLogins.getId());
						}
					}
				}
			}
			response.setStatus(HttpStatus.SC_UNAUTHORIZED);
			return false;
		}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	} 

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
