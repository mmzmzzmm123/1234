package com.ruoyi.project.tool.address.service.impl;

import com.ruoyi.project.tool.address.model.CleanAddress;
import com.ruoyi.project.tool.address.model.CleanAddressBuilder;
import com.ruoyi.project.tool.address.service.AddressHandler;

/**
 * 没有特殊符号的地址清洗
 *
 * @author lihe
 */
public class NoSignalAddressHandler implements AddressHandler {

    @Override
    public void clear(CleanAddress cleanAddress) {
        CleanAddressBuilder.builder(cleanAddress)
                .parseDistrict()
                .parseRegion()
                .parseBlock()
                .parseRoad()
                .parseIndependent()
                .parseAddress();
    }
}
