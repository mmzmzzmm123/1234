package com.ruoyi.business.domain.vo;

import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 承包商工人信息对象 bus_cbsgrxx
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public class BusCbsgrxxVO extends BaseEntity
{
	/**
	 * 
	 */
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

	/** 擅长工作 */
	@Excel(name = "擅长工作")
	private String scgz;

	private List<String> zzxxFileUrls;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCbsName(String cbsName) {
		this.cbsName = cbsName;
	}

	public String getCbsName() {
		return cbsName;
	}

	public void setFzr(String fzr) {
		this.fzr = fzr;
	}

	public String getFzr() {
		return fzr;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}

	public String getLxfs() {
		return lxfs;
	}

	public void setScgz(String scgz) {
		this.scgz = scgz;
	}

	public String getScgz() {
		return scgz;
	}

	public List<String> getZzxxFileUrls() {
		return zzxxFileUrls;
	}

	public void setZzxxFileUrls(List<String> zzxxFileUrls) {
		this.zzxxFileUrls = zzxxFileUrls;
	}
}
