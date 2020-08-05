package com.ruoyi.project.tool.address;

import com.ruoyi.project.tool.address.model.AddressType;

import java.util.LinkedList;
import java.util.List;

/**
 * context
 *
 * @author lihe
 */
public class StandardAddress {

    private String rawAddress;
    private List<AddressContent> children = new LinkedList<>();

    public StandardAddress(String text) {
        this.rawAddress = text;
    }

    public void addAddressContent(AddressContent addressContent) {
        children.add(addressContent);
    }

    public List<AddressContent> getResult() {
        return children;
    }
}
