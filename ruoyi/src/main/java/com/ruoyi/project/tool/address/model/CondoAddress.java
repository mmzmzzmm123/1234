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
    }

    @Override
    public List<PartialAddress> getPartialAddress() {
        Pattern pattern = Pattern.compile(SHI_PATTERN);
        Matcher matcher = pattern.matcher(this.address);
        // 这个需要优化，地下层没有考虑
        if (matcher.find()) {
            this.shi = parseShi();
            this.floor = parseFloor();
            this.hao = parseHAO();
        }

        return null;
    }
}
