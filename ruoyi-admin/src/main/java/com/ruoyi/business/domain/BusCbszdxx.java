package com.ruoyi.business.domain;

import java.io.Serializable;

import com.ruoyi.common.annotation.Excel;

/**
 * 承包商站点信息对象 bus_cbszdxx
 * 
 * @author yaowei
 * @date 2021-02-22
 */
public class BusCbszdxx implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	private Long id;

	/** 承包商ID */
	@Excel(name = "承包商ID")
	private Long cbsId;

	/** 站点ID */
	@Excel(name = "站点ID")
	private Long zdId;

	/** 承包年份 */
	@Excel(name = "承包年份")
	private String year;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCbsId() {
		return cbsId;
	}

	public void setCbsId(Long cbsId) {
		this.cbsId = cbsId;
	}

	public Long getZdId() {
		return zdId;
	}

	public void setZdId(Long zdId) {
		this.zdId = zdId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
