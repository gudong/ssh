package com.forusoft.framework.ui.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.forusoft.framework.ui.vo.LoginVo;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @author gudong
 *
 */
public class LoginInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 2768526093445010969L;

	public static final String LOGIN_USER_KEY = "loginUser";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		LoginVo loginUser = (LoginVo)request.getSession().getAttribute(LOGIN_USER_KEY);
		if(loginUser == null){
			return "login";
		}
		return invocation.invoke();
	}

}
