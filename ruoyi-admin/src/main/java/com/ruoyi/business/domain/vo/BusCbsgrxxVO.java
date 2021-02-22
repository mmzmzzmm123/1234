package com.ruoyi.business.domain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

/**
 * 承包商工人信息对象 bus_cbsgrxx
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public class BusCbsgrxxVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	/** 承包商主键 */
	@Excel(name = "承包商主键")
	private Long cbsId;

	/** 姓名 */
	@Excel(name = "姓名")
	private String xm;

	/** 身份证号码 */
	@Excel(name = "身份证号码")
	private String sfzhm;

	/** 岗位 */
	@Excel(name = "岗位")
	private String gw;

	/** 定薪 */
	@Excel(name = "定薪")
	private String dx;

	/** 工作地点 */
	@Excel(name = "工作地点")
	private String gzdd;

	/** 入职时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "入职时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date rzjs;

	/** 离职时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "离职时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date lzsj;

	/** 联系方式 */
	@Excel(name = "联系方式")
	private String lxfs;

	private List<String> zzxxFileUrls;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCbsId() {
		return cbsId;
	}

	public void setCbsId(Long cbsId) {
		this.cbsId = cbsId;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getSfzhm() {
		return sfzhm;
	}

	public void setSfzhm(String sfzhm) {
		this.sfzhm = sfzhm;
	}

	public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}

	public String getDx() {
		return dx;
	}

	public void setDx(String dx) {
		this.dx = dx;
	}

	public String getGzdd() {
		return gzdd;
	}

	public void setGzdd(String gzdd) {
		this.gzdd = gzdd;
	}

	public Date getRzjs() {
		return rzjs;
	}

	public void setRzjs(Date rzjs) {
		this.rzjs = rzjs;
	}

	public Date getLzsj() {
		return lzsj;
	}

	public void setLzsj(Date lzsj) {
		this.lzsj = lzsj;
	}

	public String getLxfs() {
		return lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}

	public List<String> getZzxxFileUrls() {
		return zzxxFileUrls;
	}

	public void setZzxxFileUrls(List<String> zzxxFileUrls) {
		this.zzxxFileUrls = zzxxFileUrls;
	}

}
