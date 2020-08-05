package com.ruoyi.project.tool.address;

import com.ruoyi.project.tool.address.model.AddressType;

/**
 * 地址
 *
 * @author lihe
 */
public class AddressContent {
    private AddressType addressType;
    private StringBuilder sb;

    public AddressContent(AddressType addressType) {
        this.addressType = addressType;
        sb = new StringBuilder();
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void appendContent(String addressNodeContent) {
        sb.append(addressNodeContent);
    }

    public String getResult() {
        return sb.toString();
    }
}
