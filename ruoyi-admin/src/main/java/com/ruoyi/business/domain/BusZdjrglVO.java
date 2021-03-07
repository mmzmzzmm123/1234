package com.ruoyi.business.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;

/**
 * 重大节日值班详情
 * 
 * @author yaowei
 * @date 2021-02-24
 */
public class BusZdjrglVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	/** 节日日期 */
	@JsonProperty("date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "节日日期", width = 30, dateFormat = "yyyy-MM-dd")
	private Date jrrq;

	/** 节日名称 */
	@JsonProperty("tag")
	@Excel(name = "节日名称")
	private String jrmc = "值班";

	/** 节日样式 */
	@JsonProperty("tagColor")
	@Excel(name = "节日样式")
	private String jrys = "1";

	private List<BusZdjrCbszbVO> cbszbList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getJrrq() {
		return jrrq;
	}

	public void setJrrq(Date jrrq) {
		this.jrrq = jrrq;
	}

	public String getJrmc() {
		return jrmc;
	}

	public void setJrmc(String jrmc) {
		this.jrmc = jrmc;
	}

	public String getJrys() {
		return jrys;
	}

	public void setJrys(String jrys) {
		this.jrys = jrys;
	}

	public List<BusZdjrCbszbVO> getCbszbList() {
		return cbszbList;
	}

	public void setCbszbList(List<BusZdjrCbszbVO> cbszbList) {
		this.cbszbList = cbszbList;
	}

}
