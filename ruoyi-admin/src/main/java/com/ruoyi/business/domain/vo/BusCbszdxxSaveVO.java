package com.ruoyi.business.domain.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.ruoyi.common.annotation.Excel;

/**
 * 承包商站点信息对象 bus_cbszdxx
 * 
 * @author yaowei
 * @date 2021-02-22
 */
@Validated
public class BusCbszdxxSaveVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "承包商ID不能为空")
	/** 承包商ID */
	@Excel(name = "承包商ID")
	private Long cbsId;

	@NotEmpty(message = "承包年份不能为空")
	/** 承包年份 */
	@Excel(name = "承包年份")
	private String year;

	@NotEmpty(message = "服务项目")
	@Excel(name = "服务项目")
	private String fwxm;

	@NotEmpty(message = "承包的站点")
	@Excel(name = "选择承包的站点")
	private List<Long> zdIdList;

	public Long getCbsId() {
		return cbsId;
	}

	public void setCbsId(Long cbsId) {
		this.cbsId = cbsId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFwxm() {
		return fwxm;
	}

	public void setFwxm(String fwxm) {
		this.fwxm = fwxm;
	}

	public List<Long> getZdIdList() {
		return zdIdList;
	}

	public void setZdIdList(List<Long> zdIdList) {
		this.zdIdList = zdIdList;
	}

}
