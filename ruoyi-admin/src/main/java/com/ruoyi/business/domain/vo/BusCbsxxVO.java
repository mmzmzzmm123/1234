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

	private BusCbsxx cbsxx;

	private List<String> zzxxList;

	private List<BusCbsgrxxVO> ryxxList;

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

}
