package com.ruoyi.system.service.impl;

import com.ruoyi.common.enums.AppType;
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
        List<TypeVO> appTypeList = new ArrayList<>();

        for (AppType appType : EnumSet.allOf(AppType.class)) {
            TypeVO appTypeVO = new TypeVO();
            appTypeVO.setId(appType.getId());
            appTypeVO.setName(appType.getName());
            appTypeList.add(appTypeVO);
        }

        return appTypeList;
    }

    @Override
    public List<TypeVO> getOrderList() {
        List<TypeVO> appTypeList = new ArrayList<>();

//        for (AppType appType : EnumSet.allOf(OrderType.class)) {
//            TypeVO appTypeVO = new TypeVO();
//            appTypeVO.setId(appType.getId());
//            appTypeVO.setName(appType.getName());
//            appTypeList.add(appTypeVO);
//        }
//
        return appTypeList;
    }
}
