/**
 * 
 */
package com.forusoft.stock.dal.po;

import java.util.Date;

import com.forusoft.framework.dal.po.BasePo;

/**
 * @author GuDong
 * 
 */
public class StockPo extends BasePo {
  private String code;
  private String name;
  private String shortName;
  private Date marketedDate;
  private Date delistedDate;
  private Date updateDate;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public Date getMarketedDate() {
    return marketedDate;
  }

  public void setMarketedDate(Date marketedDate) {
    this.marketedDate = marketedDate;
  }

  public Date getDelistedDate() {
    return delistedDate;
  }

  public void setDelistedDate(Date delistedDate) {
    this.delistedDate = delistedDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

}
