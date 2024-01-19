package com.ruoyi.system.components;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.constant.ProductStatusConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.mapper.ProductSkuMapper;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ProductTools {

	public static String skuName(ProductSku sku) {
		if (sku == null) {
			return "";
		}
		return sku.getCountyName() + " " + sku.getPriceUnit() + " " + BigDecimal.valueOf(sku.getPrice()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP);
	}

	public static List<Product> list(List<Long> ids) {
		final ProductMapper productMapper = SpringUtils.getBean(ProductMapper.class);
		return productMapper.selectBatchIds(ids);
	}

	public static List<ProductSku> listSku(List<Long> productIdList) {
		final ProductSkuMapper productSkuMapper = SpringUtils.getBean(ProductSkuMapper.class);
		return productSkuMapper
				.selectList(new QueryWrapper<ProductSku>().lambda().in(ProductSku::getProductId, productIdList));
	}

	public static AjaxResult checkNormal(Long productId) {
		if (productId == null) {
			return AjaxResult.error(ErrInfoConfig.get(11001));
		}
		// 查询商品
		final ProductMapper productMapper = SpringUtils.getBean(ProductMapper.class);
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
		final ProductSkuMapper productSkuMapper = SpringUtils.getBean(ProductSkuMapper.class);
		List<ProductSku> skuList = productSkuMapper.selectBatchIds(skuIds);
		if (CollectionUtils.isEmpty(skuList)) {
			return AjaxResult.error(ErrInfoConfig.get(11003));
		}
		// 监测sku价格为0
		for (ProductSku sku : skuList) {
			if (sku.getPrice() == null || sku.getPrice() < 0) {
				return AjaxResult.error(ErrInfoConfig.getDynmic(11004, sku.getId() + ""));
			}
		}
		return AjaxResult.success(skuList);
	}

	public static R<Product> getOneProductByCategory(Integer category) {
		Product product = SpringUtils.getBean(ProductMapper.class)
				.selectOne(new LambdaQueryWrapper<Product>()
				.eq(Product::getCategoryId, category)
				.last(" limit 1 "));
		if (product == null) {
			return R.fail(ErrInfoConfig.getDynmic(11001));
		}
		if (product.getStatus() == null || product.getStatus() != ProductStatusConstants.LISTING) {
			return R.fail(ErrInfoConfig.getDynmic(11002));
		}
		if (product.getIsShow() == null || product.getIsShow() != ProductStatusConstants.SHOW) {
			return R.fail(ErrInfoConfig.getDynmic(11002));
		}
		return R.ok(product);
	}
}
