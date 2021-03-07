package com.ruoyi.business.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;

/**
 * 重大节日管理对象 bus_zdjrgl
 * 
 * @author yaowei
 * @date 2021-02-24
 */
public class BusZdjrgl implements Serializable {

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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setJrrq(Date jrrq) {
		this.jrrq = jrrq;
	}

	public Date getJrrq() {
		return jrrq;
	}

	public void setJrmc(String jrmc) {
		this.jrmc = jrmc;
	}

	public String getJrmc() {
		return jrmc;
	}

	public void setJrys(String jrys) {
		this.jrys = jrys;
	}

	public String getJrys() {
		return jrys;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId()).append("jrrq", getJrrq())
				.append("jrmc", getJrmc()).append("jrys", getJrys()).toString();
	}
}
