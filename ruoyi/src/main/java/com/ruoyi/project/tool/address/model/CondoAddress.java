package com.ruoyi.project.tool.address.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单套地址
 *
 * @author lihe
 */
public class CondoAddress extends PartialAddress {

    public CondoAddress(String address) {
        super(address);
        this.shi = parseShi();
        this.floor = parseFloor();
        this.hao = parseHAO();
    }

    @Override
    public AddressType getAddressType() {
        return AddressType.CONDO;
    }

    @Override
    public Boolean multiAddress() {
        return childrenAddress.size() > 0;
    }

    public String getHao() {
        return hao;
    }

    public String getShi() {
        return shi;
    }

    public Integer getFloor() {
        return floor;
    }

}
