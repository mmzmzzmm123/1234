package com.ruoyi.business.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

/**
 * 承包商信息对象 bus_cbsxx
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public class BusCbsxx implements Serializable {
	private static final long serialVersionUID = 1L;

	/** $column.columnComment */
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

	/** 区块代码 */
	@Excel(name = "区块代码")
	private String qkdm;
	/** 区块名称 */
	@Excel(name = "区块名称")
	private String qkmc;

	/** 写入时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "写入时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date xrsj;

	/** 写入人id */
	@Excel(name = "写入人id")
	private Long xrrId;

	/** 最后修改时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date xgsj;

	/** 最后修改人id */
	@Excel(name = "最后修改人id")
	private Long xgrId;

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

	public String getQkdm() {
		return qkdm;
	}

	public void setQkdm(String qkdm) {
		this.qkdm = qkdm;
	}

	public String getQkmc() {
		return qkmc;
	}

	public void setQkmc(String qkmc) {
		this.qkmc = qkmc;
	}

	public Date getXrsj() {
		return xrsj;
	}

	public void setXrsj(Date xrsj) {
		this.xrsj = xrsj;
	}

	public Long getXrrId() {
		return xrrId;
	}

	public void setXrrId(Long xrrId) {
		this.xrrId = xrrId;
	}

	public Date getXgsj() {
		return xgsj;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public Long getXgrId() {
		return xgrId;
	}

	public void setXgrId(Long xgrId) {
		this.xgrId = xgrId;
	}

}
