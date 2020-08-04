package com.ruoyi.project.tool.address.model;

import java.util.List;

/**
 * 室地址
 *
 * @author lihe
 */
public class BuildingAddress extends PartialAddress {

    public BuildingAddress(String address) {
        super(address);
    }

    @Override
    public AddressType getAddressType() {
        return null;
    }

    @Override
    public Boolean multiAddress() {
        return null;
    }
}
