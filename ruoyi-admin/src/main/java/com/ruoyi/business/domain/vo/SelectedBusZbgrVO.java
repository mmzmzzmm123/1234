package com.ruoyi.business.domain.vo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ruoyi.common.annotation.Excel;

/**
 * 重大节日值班人员对象 bus_zdjrzbry
 * 
 * @author yaowei
 * @date 2021-02-24
 */
public class SelectedBusZbgrVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 工人ID */
	@NotNull(message = "工人ID不能为空")
	@Excel(name = "工人ID")
	private Long grId;

	/** 工人姓名 */
	@NotEmpty(message = "承包商ID不能为空")
	@Excel(name = "工人姓名")
	private String grmc;

	/** 工人联系方式 */
	@Excel(name = "工人联系方式")
	private String grlxfs;

	public Long getGrId() {
		return grId;
	}

	public void setGrId(Long grId) {
		this.grId = grId;
	}

	public String getGrmc() {
		return grmc;
	}

	public void setGrmc(String grmc) {
		this.grmc = grmc;
	}

	public String getGrlxfs() {
		return grlxfs;
	}

	public void setGrlxfs(String grlxfs) {
		this.grlxfs = grlxfs;
	}

}
