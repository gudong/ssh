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
public class StockWeekExchangePo extends BasePo {
	private Long id;
	private String code;

	private Double lastPrice;
	private Double openPrice;
	private Double closedPrice;
	private Double highPrice;
	private Double lowPrice;
	private Double increase;
	private Double increasePrice;
	private int volume;

	private Date exchangeDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public Date getExchangeDate() {
		return exchangeDate;
	}

	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getClosedPrice() {
		return closedPrice;
	}

	public void setClosedPrice(Double closedPrice) {
		this.closedPrice = closedPrice;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getIncrease() {
		return increase;
	}

	public void setIncrease(Double increase) {
		this.increase = increase;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getIncreasePrice() {
		return increasePrice;
	}

	public void setIncreasePrice(Double increasePrice) {
		this.increasePrice = increasePrice;
	}

}
