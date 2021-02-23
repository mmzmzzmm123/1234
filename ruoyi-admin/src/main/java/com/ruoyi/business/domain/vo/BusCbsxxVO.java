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
	private List<SelectedBusCbszdxxVO> cbzd001;
	/**
	 * 承包站点年份对应的单井巡线
	 */
	private List<SelectedBusCbszdxxVO> cbzd002;
	/**
	 * 承包站点年份对应的单井流程值守
	 */
	private List<SelectedBusCbszdxxVO> cbzd003;

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

	public List<SelectedBusCbszdxxVO> getCbzd001() {
		return cbzd001;
	}

	public void setCbzd001(List<SelectedBusCbszdxxVO> cbzd001) {
		this.cbzd001 = cbzd001;
	}

	public List<SelectedBusCbszdxxVO> getCbzd002() {
		return cbzd002;
	}

	public void setCbzd002(List<SelectedBusCbszdxxVO> cbzd002) {
		this.cbzd002 = cbzd002;
	}

	public List<SelectedBusCbszdxxVO> getCbzd003() {
		return cbzd003;
	}

	public void setCbzd003(List<SelectedBusCbszdxxVO> cbzd003) {
		this.cbzd003 = cbzd003;
	}

}
