package com.ruoyi.business.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

/**
 * 承包商资质信息对象 bus_cbszzxx
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public class BusCbszzxx implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	/** 承包商主键 */
	@Excel(name = "承包商主键")
	private Long cbsId;

	/** 资质名称 */
	@Excel(name = "资质名称")
	private String zzmc;

	/** 有效期 */
	@Excel(name = "有效期")
	private String yxq;

	/** 资质图片 */
	@Excel(name = "资质图片")
	private String zztp;

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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCbsId(Long cbsId) {
		this.cbsId = cbsId;
	}

	public Long getCbsId() {
		return cbsId;
	}

	public void setZzmc(String zzmc) {
		this.zzmc = zzmc;
	}

	public String getZzmc() {
		return zzmc;
	}

	public void setYxq(String yxq) {
		this.yxq = yxq;
	}

	public String getYxq() {
		return yxq;
	}

	public void setZztp(String zztp) {
		this.zztp = zztp;
	}

	public String getZztp() {
		return zztp;
	}

	public void setXrsj(Date xrsj) {
		this.xrsj = xrsj;
	}

	public Date getXrsj() {
		return xrsj;
	}

	public void setXrrId(Long xrrId) {
		this.xrrId = xrrId;
	}

	public Long getXrrId() {
		return xrrId;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public Date getXgsj() {
		return xgsj;
	}

	public void setXgrId(Long xgrId) {
		this.xgrId = xgrId;
	}

	public Long getXgrId() {
		return xgrId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("cbsId", getCbsId()).append("zzmc", getZzmc()).append("yxq", getYxq()).append("zztp", getZztp())
				.append("xrsj", getXrsj()).append("xrrId", getXrrId()).append("xgsj", getXgsj())
				.append("xgrId", getXgrId()).toString();
	}
}
