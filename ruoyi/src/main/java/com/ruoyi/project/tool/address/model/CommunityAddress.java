package com.ruoyi.project.tool.address.model;

import java.util.List;

/**
 * 小区地址
 *
 * @author lihe
 */
public class CommunityAddress extends PartialAddress {

    public CommunityAddress(String address) {
        super(address);
    }

    @Override
    public List<PartialAddress> getPartialAddress() {

        return null;
    }
}
