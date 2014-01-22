/**
 * 
 */
package com.forusoft.framework.bll.service;

/**
 * @author gudong
 * 
 */
public interface LoginService extends BaseService {
  boolean isLogin(String userName, String password);
}
