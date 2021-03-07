package com.ruoyi.business.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.ruoyi.common.annotation.Excel;

/**
 * 重大节日值班人员对象 bus_zdjrzbry
 * 
 * @author yaowei
 * @date 2021-02-24
 */
public class BusZdjrzbry implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	/** 重大节日主键 */
	@NotNull(message = "重点节假日ID不能为空")
	@Excel(name = "重大节日主键")
	private Long zdjrId;

	/** 承包商ID */
	@Excel(name = "承包商ID")
	private Long cbsId;

	/** 承包商名称 */
	@Excel(name = "承包商名称")
	private String cbsmc;

	/** 工人ID */
	@Excel(name = "工人ID")
	private Long grId;

	/** 工人姓名 */
	@Excel(name = "工人姓名")
	private String grmc;

	/** 工人联系方式 */
	@Excel(name = "工人联系方式")
	private String grlxfs;

	/** 岗位 */
	@Excel(name = "岗位")
	private String gw;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getZdjrId() {
		return zdjrId;
	}

	public void setZdjrId(Long zdjrId) {
		this.zdjrId = zdjrId;
	}

	public Long getCbsId() {
		return cbsId;
	}

	public void setCbsId(Long cbsId) {
		this.cbsId = cbsId;
	}

	public String getCbsmc() {
		return cbsmc;
	}

	public void setCbsmc(String cbsmc) {
		this.cbsmc = cbsmc;
	}

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

	public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}

}
