package com.ruoyi.business.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.ruoyi.business.domain.BusCbsxx;

/**
 * 承包商信息列表
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public class BusCbsxxVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 承包商信息
	 */
	private BusCbsxx cbsxx;
	/**
	 * 资质信息
	 */
	private List<String> zzxxList;
	/**
	 * 人员信息
	 */
	private List<BusCbsgrxxVO> ryxxList;
	/**
	 * 承包站点年份
	 */
	private String year;
	/**
	 * 承包站点年份对应的站库
	 */
	private List<SelectedBusCbszdxxVO> cbzd001List;
	/**
	 * 承包站点年份对应的单井巡线
	 */
	private List<SelectedBusCbszdxxVO> cbzd002List;
	/**
	 * 承包站点年份对应的单井流程值守
	 */
	private List<SelectedBusCbszdxxVO> cbzd003List;

	public BusCbsxx getCbsxx() {
		return cbsxx;
	}

	public void setCbsxx(BusCbsxx cbsxx) {
		this.cbsxx = cbsxx;
	}

	public List<String> getZzxxList() {
		return zzxxList;
	}

	public void setZzxxList(List<String> zzxxList) {
		this.zzxxList = zzxxList;
	}

	public List<BusCbsgrxxVO> getRyxxList() {
		return ryxxList;
	}

	public void setRyxxList(List<BusCbsgrxxVO> ryxxList) {
		this.ryxxList = ryxxList;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<SelectedBusCbszdxxVO> getCbzd001List() {
		return cbzd001List;
	}

	public void setCbzd001List(List<SelectedBusCbszdxxVO> cbzd001List) {
		this.cbzd001List = cbzd001List;
	}

	public List<SelectedBusCbszdxxVO> getCbzd002List() {
		return cbzd002List;
	}

	public void setCbzd002List(List<SelectedBusCbszdxxVO> cbzd002List) {
		this.cbzd002List = cbzd002List;
	}

	public List<SelectedBusCbszdxxVO> getCbzd003List() {
		return cbzd003List;
	}

	public void setCbzd003List(List<SelectedBusCbszdxxVO> cbzd003List) {
		this.cbzd003List = cbzd003List;
	}

}
