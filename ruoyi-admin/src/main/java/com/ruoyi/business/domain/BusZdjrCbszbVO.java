package com.ruoyi.business.domain;

import java.io.Serializable;
import java.util.List;

import com.ruoyi.common.annotation.Excel;

/**
 * 重点假日承包商值班信息
 * 
 * @author YW
 *
 */
public class BusZdjrCbszbVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 承包商ID
	 */
	private Long id;

	/** 承包商名称 */
	@Excel(name = "承包商名称")
	private String cbsName;

	/** 负责人 */
	@Excel(name = "负责人")
	private String fzr;

	/** 联系方式 */
	@Excel(name = "联系方式")
	private String lxfs;

	/** 注册地址 */
	@Excel(name = "注册地址")
	private String zcdz;

	/**
	 * 值班人员
	 */
	private List<BusZdjrzbry> zbryList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCbsName() {
		return cbsName;
	}

	public void setCbsName(String cbsName) {
		this.cbsName = cbsName;
	}

	public String getFzr() {
		return fzr;
	}

	public void setFzr(String fzr) {
		this.fzr = fzr;
	}

	public String getLxfs() {
		return lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}

	public String getZcdz() {
		return zcdz;
	}

	public void setZcdz(String zcdz) {
		this.zcdz = zcdz;
	}

	public List<BusZdjrzbry> getZbryList() {
		return zbryList;
	}

	public void setZbryList(List<BusZdjrzbry> zbryList) {
		this.zbryList = zbryList;
	}

}
