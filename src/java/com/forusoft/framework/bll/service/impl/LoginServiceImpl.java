/**
 * 
 */
package com.forusoft.framework.bll.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.forusoft.framework.bll.service.LoginService;
import com.forusoft.framework.ui.action.LoginAction;

/**
 * @author gudong
 * 
 */
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {
  private static Log logger = LogFactory.getLog(LoginAction.class);

  @Override
  public boolean isLogin(String userName, String password) {
    logger.info("=======LoginService");
    if ("hello".equals(userName) && "world".equals(password))
      return true;
    else
      return false;
  }

}
