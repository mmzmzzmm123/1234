//package com.ruoyi.system.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.ruoyi.common.core.domain.entity.ProductCategory;
//import com.ruoyi.system.domain.vo.CategoryVo;
//import com.ruoyi.system.mapper.ProductCategoryMapper;
//import com.ruoyi.system.service.IProductCategoryService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ProductCategoryServiceImpl implements IProductCategoryService {
//    @Resource
//    private ProductCategoryMapper productCategoryMapper;
//
//    @Override
//    public List<CategoryVo> selectAll() {
//        List<CategoryVo> ret = new ArrayList<>();
//
//        List<ProductCategory> categories = productCategoryMapper.selectList(new QueryWrapper<ProductCategory>().lambda()
//                .eq(ProductCategory::getStatus, 1));
//        if (categories.isEmpty()) {
//            return ret;
//        }
//
//         ret = categories.stream()
//                .filter(e -> e.getParentId().equals(0))
//                .map(this::category2CategoryVo)
//                .collect(Collectors.toList());
//
//        //设置子分类
//        findSubCategory(ret, categories);
//        return ret;
//    }
//
//    private void findSubCategory(List<CategoryVo> categoryVoList, List<ProductCategory> categories) {
//        for (CategoryVo categoryVo : categoryVoList) {
//            List<CategoryVo> subCategoryVoList = new ArrayList<>();
//
//            for (ProductCategory category : categories) {
//                if (categoryVo.getCategoryId().equals(category.getParentId())) {
//                    CategoryVo subCategoryVo = category2CategoryVo(category);
//                    subCategoryVoList.add(subCategoryVo);
//                }
//
//                categoryVo.setSubCategories(subCategoryVoList);
//            }
//        }
//    }
//
//    private CategoryVo category2CategoryVo(ProductCategory category) {
//        CategoryVo categoryVo = new CategoryVo();
//        BeanUtils.copyProperties(category, categoryVo);
//        return categoryVo;
//    }
//}
