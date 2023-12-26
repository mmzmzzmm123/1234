package com.ruoyi.system.components;

import java.util.List;
import com.ruoyi.common.core.domain.entity.ProductSku;

public interface OrderPriceHandler {

	/**
	 * 
	 * @param skuList               sku集合
	 * @param groupCount            群数
	 * @param singlePullPersonCount 单群拉人数
	 * @return
	 */
	public long handle(List<ProductSku> skuList, int groupCount, int singlePullPersonCount);

	/**
	 * 多sku组合商品 价格计算器
	 * 
	 * @author Administrator
	 *
	 */
	public static class MultiAssemblyOrderPriceHandler implements OrderPriceHandler {
		@Override
		public long handle(List<ProductSku> skuList, int groupCount, int singlePullPersonCount) {
			long total = 0;
			for (ProductSku sku : skuList) {
				total += (sku.getPrice() * groupCount * singlePullPersonCount);
			}
			return total;
		}
	}

}
