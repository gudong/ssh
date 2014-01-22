/**
 * 
 */
package com.forusoft.framework.ui.action;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.forusoft.framework.bll.service.BaseService;
import com.forusoft.framework.common.util.StringUtil;
import com.forusoft.framework.ui.vo.Vo;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author gudong
 * 
 * @version $Date:2013-12-22$
 */
public abstract class BaseStruts2Action<V extends Vo> extends ActionSupport implements Action, ServletRequestAware, ServletResponseAware,
    ModelDriven<V> {
  /**
   * 
   */
  private static final long serialVersionUID = 9068130057927579929L;
  private static Log logger = LogFactory.getLog(BaseStruts2Action.class);

  private BaseService baseService;

  private HttpServletRequest request;
  private HttpServletResponse response;

  private V vo;

  @Override
  public V getModel() {
    if (vo == null) {
      ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
      try {
        vo = (V) ((Class) type.getActualTypeArguments()[0]).newInstance();
      } catch (InstantiationException e) {
        logger.error("getModel error", e);
      } catch (IllegalAccessException e) {
        logger.error("getModel error", e);
      }
    }
    return vo;
  }

  public HttpServletRequest getHttpRequest() {
    return request;
  }

  public HttpServletResponse getHttpResponse() {
    return response;
  }

  public HttpSession getHttpSession() {
    return this.request.getSession();
  }

  @Override
  public void setServletRequest(HttpServletRequest arg0) {
    this.request = arg0;
  }

  @Override
  public void setServletResponse(HttpServletResponse arg0) {
    this.response = arg0;
  }

  public BaseService getBaseService() {
    return baseService;
  }

  public void setBaseService(BaseService baseService) {
    this.baseService = baseService;
  }

  /**
   * @return
   * @throws Exception
   */
  @Override
  public String execute() throws Exception {
    String method = getHttpRequest().getParameter("method");
    if (StringUtils.isNotBlank(method)) {
      Method validationMethod = MethodUtils.getAccessibleMethod(this.getClass(), "validate" + StringUtil.toUpperCaseFirst(method));
      if (validationMethod != null) {
        Object returnVal = validationMethod.invoke(this);
        if (!super.getActionErrors().isEmpty()) {
          if(returnVal != null){
            return returnVal.toString();
          }else{
            return INPUT;
          }
        }
      }
      return (String) MethodUtils.invokeExactMethod(this, method);
    } else {
      return executeAction();
    }
  }

  /**
   * 
   * @return
   * @throws Exception
   */
  public String executeAction() throws Exception {
    logger.info("start:" + getClass() + ".executeAction()");
    return INPUT;
  }

}
