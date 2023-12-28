package com.ruoyi.common.core.domain.app;

import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderProduceRequest {

	/**
	 * 商品id
	 */
	@ApiModelProperty("商品id")
	private long productId;

	/**
	 * json参数
	 */
	@ApiModelProperty("提交信息参数")
	private Params params;

	/**
	 * 购买的商品sku集合
	 */
	@ApiModelProperty("购买的sku集合")
	private List<Long> skuList;

	// 用户
	@ApiModelProperty(hidden = true)
	private LoginUser loginUser;

	@Data
	public static class Params {

		/**
		 * 入群目标类型 0-手机号 1-用户名
		 */
		@ApiModelProperty("入群目标类型 0-手机号 1-用户名")
		private int inGroupType;

		/**
		 * 具体目标
		 */
		@ApiModelProperty("具体目标id集合")
		private List<String> targetIds;

		/**
		 * 格式类型： 1-导入文件 2-手动输入
		 */
		@ApiModelProperty("格式类型： 1-导入文件 2-手动输入")
		private int formatType;

		/**
		 * 格式类型： 1-导入文件 2-手动输入
		 */
		@ApiModelProperty("excel下载链接")
		private String excelUrl;

		/**
		 * 群来源 0-自有群 1-平台群
		 */
		@ApiModelProperty("群来源 0-自有群")
		private int groupSource;

		/**
		 * 群来源的id集合
		 */
		@ApiModelProperty("群来源的id集合")
		private List<String> groupIds;

		/**
		 * 设置群人数类型 0-单群拉人数 1-单群人数上限
		 */
		@ApiModelProperty("设置群人数类型 0-单群拉人数 1-单群人数上限")
		private int groupPersonType;

		/**
		 * 单群拉人数
		 */
		@ApiModelProperty("单群拉人数")
		private int singleGroupPerson;

	}

}
