package com.ruoyi.common.core.domain.app;

import java.util.List;
import lombok.Data;

@Data
public class OrderProduceRequest {

	/**
	 * 商品id
	 */
	private long productId;

	/**
	 * json参数
	 */
	private Params params;

	/**
	 * 购买的商品sku集合
	 */
	private List<String> skuList;

	@Data
	public static class Params {

		/**
		 * 入群目标类型 0-手机号 1-用户名
		 */
		private int inGroupType;

		/**
		 * 具体目标
		 */
		private List<String> targetIds;

		/**
		 * 群来源 0-自有群 1-平台群
		 */
		private int groupSource;

		/**
		 * 群来源的id集合
		 */
		private List<String> groupIds;

		/**
		 * 设置群人数类型 0-单群拉人数 1-单群人数上限
		 */
		private int groupPersonType;

		/**
		 * 单群拉人数
		 */
		private int singleGroupPerson;

	}

}
