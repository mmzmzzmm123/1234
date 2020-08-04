package com.ruoyi.project.tool.address.service;

import com.ruoyi.project.tool.address.model.AddressType;

public abstract class BaseAddressBuilder implements AddressBuilder {

    protected StringBuilder stringBuilder;

    public BaseAddressBuilder(String text) {
        this.stringBuilder = new StringBuilder(text);
    }

    public abstract AddressType getAddressType();
}
