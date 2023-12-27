package com.ruoyi.system.service.impl;

import com.ruoyi.common.enums.AppType;
import com.ruoyi.common.enums.ProductCategoryType;
import com.ruoyi.system.domain.vo.TypeVO;
import com.ruoyi.system.service.ITypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class TypeServiceImpl implements ITypeService {
    @Override
    public List<TypeVO> getAppList() {
        List<TypeVO> typeList = new ArrayList<>();

        for (AppType appType : EnumSet.allOf(AppType.class)) {
            TypeVO appTypeVO = new TypeVO();
            appTypeVO.setId(appType.getId());
            appTypeVO.setName(appType.getName());
            typeList.add(appTypeVO);
        }

        return typeList;
    }

    @Override
    public List<TypeVO> getProductCategoryList() {
        List<TypeVO> typeList = new ArrayList<>();

        for (ProductCategoryType type : EnumSet.allOf(ProductCategoryType.class)) {
            TypeVO appTypeVO = new TypeVO();
            appTypeVO.setId(type.getId());
            appTypeVO.setName(type.getName());
            typeList.add(appTypeVO);
        }

        return typeList;
    }
}
