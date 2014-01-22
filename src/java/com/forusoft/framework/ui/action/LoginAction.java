/**
 * 
 */
package com.forusoft.framework.ui.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.forusoft.framework.bll.service.LoginService;
import com.forusoft.framework.ui.interceptor.LoginInterceptor;
import com.forusoft.framework.ui.vo.LoginVo;
import com.forusoft.stock.dal.po.StockPo;

/**
 * @author gudong
 * 
 */
public class LoginAction extends BaseStruts2Action<LoginVo> {

  private static final long serialVersionUID = 4891746307053671604L;
  private static Log logger = LogFactory.getLog(LoginAction.class);
  @Autowired
  private LoginService loginService;

  /**
   * 
   * @return
   * @throws Exception
   */
  public String login() throws Exception {
    logger.info("start:" + getClass() + ".login()");
    getBaseService().queryByQBC(DetachedCriteria.forClass(StockPo.class));
    if (loginService.isLogin(getModel().getUserName(), getModel().getPassword())) {
      getHttpSession().setAttribute(LoginInterceptor.LOGIN_USER_KEY, getModel());
      return SUCCESS;
    } else {
      return INPUT;
    }
  }

  /**
   * 
   */
  public void validateLogin() {
    // addActionError(getText("user.name.empty"));
  }
}
