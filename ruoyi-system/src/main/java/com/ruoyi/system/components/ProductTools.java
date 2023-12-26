package com.ruoyi.system.components;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.utils.App;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.mapper.ProductSkuMapper;

public class ProductTools {

	public static AjaxResult checkNormal(Long productId) {
		if (productId == null) {
			return AjaxResult.error(ErrInfoConfig.get(11001));
		}
		// 查询商品
		final ProductMapper productMapper = App.getBean(ProductMapper.class);
		Product producet = productMapper.selectById(productId);
		if (producet == null) {
			return AjaxResult.error(ErrInfoConfig.get(11001));
		}
		if (producet.getStatus() == null || producet.getStatus().intValue() != 2) {
			return AjaxResult.error(ErrInfoConfig.get(11002));
		}
		if (producet.getIsShow() == null || producet.getIsShow().intValue() == 0) {
			return AjaxResult.error(ErrInfoConfig.get(11002));
		}
		return AjaxResult.success(producet);
	}

	public static AjaxResult checkSkuList(List<Long> skuIds) {
		if (CollectionUtils.isEmpty(skuIds)) {
			return AjaxResult.error(ErrInfoConfig.get(11003));
		}
		final ProductSkuMapper productSkuMapper = App.getBean(ProductSkuMapper.class);
		List<ProductSku> skuList = productSkuMapper.selectBatchIds(skuIds);
		if (CollectionUtils.isEmpty(skuList)) {
			return AjaxResult.error(ErrInfoConfig.get(11003));
		}
		// 监测sku价格为0
		for (ProductSku sku : skuList) {
			if (sku.getPrice() == null || sku.getPrice().longValue() < 0) {
				return AjaxResult.error(ErrInfoConfig.getDynmic(11004, sku.getId() + ""));
			}
		}
		return AjaxResult.success(skuList);
	}
}
