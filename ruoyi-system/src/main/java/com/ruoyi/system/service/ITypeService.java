package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.TypeVO;

import java.util.List;

public interface ITypeService {
    List<TypeVO> getAppList();

    List<TypeVO> getOrderList();
}
