/**
 * 
 */
package com.forusoft.framework.ui.vo;


/**
 * @author gudong
 *
 */
public class LoginVo extends BaseVo{

  private static final long serialVersionUID = -7880818652433236776L;
  private String userName;
  private String password;
  
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
}
