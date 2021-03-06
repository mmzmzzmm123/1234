package com.ruoyi.business.domain.vo;

/**
 * 工人岗位统计结果
 * 
 * @author YW
 *
 */
public class GrgwStatistics {
	/**
	 * 岗位
	 */
	private String gw;
	/**
	 * 岗位工人总数
	 */
	private int grTotalCount;

	public GrgwStatistics(String gw, int grTotalCount) {
		super();
		this.gw = gw;
		this.grTotalCount = grTotalCount;
	}

	public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}

	public int getGrTotalCount() {
		return grTotalCount;
	}

	public void setGrTotalCount(int grTotalCount) {
		this.grTotalCount = grTotalCount;
	}

}
