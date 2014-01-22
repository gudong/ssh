/**
 * 
 */
package com.forusoft.stock.dal.po;

import com.forusoft.framework.dal.po.BasePo;

/**
 * @author GuDong
 * 
 */
public class StockCommentPo extends BasePo{
	private Long id;
	private String comment;
	private Double weight;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

}
