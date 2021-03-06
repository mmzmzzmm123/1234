package com.ruoyi.business.domain.vo;

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 表格分页数据对象-承包商
 * 
 * @author ruoyi
 */
public class CbsxxTableDataInfo extends TableDataInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 承包商总数
	 */
	private long cbsTotalCount = 0;
	/**
	 * 工人总数-未离职的
	 */
	private long grTotalCount = 0;
	/**
	 * 站库总数
	 */
	private int cbzd001TotalCount = 0;
	/**
	 * 巡线总数
	 */
	private int cbzd002TotalCount = 0;

	public long getCbsTotalCount() {
		return cbsTotalCount;
	}

	public void setCbsTotalCount(long cbsTotalCount) {
		this.cbsTotalCount = cbsTotalCount;
	}

	public long getGrTotalCount() {
		return grTotalCount;
	}

	public void setGrTotalCount(long grTotalCount) {
		this.grTotalCount = grTotalCount;
	}

	public int getCbzd001TotalCount() {
		return cbzd001TotalCount;
	}

	public void setCbzd001TotalCount(int cbzd001TotalCount) {
		this.cbzd001TotalCount = cbzd001TotalCount;
	}

	public int getCbzd002TotalCount() {
		return cbzd002TotalCount;
	}

	public void setCbzd002TotalCount(int cbzd002TotalCount) {
		this.cbzd002TotalCount = cbzd002TotalCount;
	}

}
