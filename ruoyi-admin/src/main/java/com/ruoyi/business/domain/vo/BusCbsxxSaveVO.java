package com.ruoyi.business.domain.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.ruoyi.common.annotation.Excel;

/**
 * 承包商信息列表
 * 
 * @author yaowei
 * @date 2021-02-21
 */
@Validated
public class BusCbsxxSaveVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message="ID不能为空")
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
	private String zcdz;

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

	public String getZcdz() {
		return zcdz;
	}

	public void setZcdz(String zcdz) {
		this.zcdz = zcdz;
	}

	public List<String> getZzxxFileUrls() {
		return zzxxFileUrls;
	}

	public void setZzxxFileUrls(List<String> zzxxFileUrls) {
		this.zzxxFileUrls = zzxxFileUrls;
	}

}
