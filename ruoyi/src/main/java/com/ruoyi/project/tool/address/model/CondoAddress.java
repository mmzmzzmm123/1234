package com.ruoyi.project.tool.address.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 室地址
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
    public List<PartialAddress> getPartialAddress() {
        return null;
    }
}
