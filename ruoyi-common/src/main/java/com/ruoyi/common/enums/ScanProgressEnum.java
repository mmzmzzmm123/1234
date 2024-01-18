package com.ruoyi.common.enums;

/***
 * 0未开始 1调度修改群人设中 2 调用入群中 3 入群等待中 4 混淆 5 号分配,人设 6等待超群条件 7发剧本
 * 
 * @author Administrator
 *
 */
public enum ScanProgressEnum {
	/**
	 * 未开始
	 */
	Init(0),

	/**
	 * 调度修改群人设中
	 */
	Group_Settings(1),

	/**
	 * 调用入群中
	 */
	Group_ComeIn(2),

	/**
	 * 入群等待中
	 */
	Group_Wait(3),

	/**
	 * 混淆
	 */
	Confuse(4),

	/**
	 * 号分配,人设
	 */
	Robot(5),

	/**
	 * 等待超群条件
	 */
	Send_Wait(6),

	/**
	 * 剧本发送中
	 */
	Sending(7);

	private int val;

	ScanProgressEnum(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}

}
