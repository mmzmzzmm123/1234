package com.ruoyi.business.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.ruoyi.business.domain.BusCbsxx;
import com.ruoyi.business.domain.BusCbszzxx;

/**
 * 承包商信息列表
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public class BusCbsxxVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private BusCbsxx cbsxx;

	private List<BusCbszzxx> zzxxList;

	private List<String> ryxxList;

	public BusCbsxx getCbsxx() {
		return cbsxx;
	}

	public void setCbsxx(BusCbsxx cbsxx) {
		this.cbsxx = cbsxx;
	}

	public List<BusCbszzxx> getZzxxList() {
		return zzxxList;
	}

	public void setZzxxList(List<BusCbszzxx> zzxxList) {
		this.zzxxList = zzxxList;
	}

	public List<String> getRyxxList() {
		return ryxxList;
	}

	public void setRyxxList(List<String> ryxxList) {
		this.ryxxList = ryxxList;
	}

	
}
