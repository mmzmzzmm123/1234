package com.ruoyi.project.tool.address.service;

import com.ruoyi.project.tool.address.model.AddressType;

import java.util.List;

/**
 * 地址构建基类
 *
 * @author lihe
 */
public class DefaultAddressBuilder extends BaseAddressBuilder {

    public DefaultAddressBuilder(String text) {
        super(text);
    }

    @Override
    public AddressType getAddressType() {
        return AddressType.CONDO;
    }
}
