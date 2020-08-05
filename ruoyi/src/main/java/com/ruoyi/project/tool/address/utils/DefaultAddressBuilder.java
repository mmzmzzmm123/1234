package com.ruoyi.project.tool.address.utils;

import com.ruoyi.project.tool.address.AddressBuilder;
import com.ruoyi.project.tool.address.AddressContext;
import com.ruoyi.project.tool.address.AddressNode;
import com.ruoyi.project.tool.address.StandardAddress;

import java.util.List;

/**
 * 默认地址构建
 *
 * @author lihe
 */
public class DefaultAddressBuilder implements AddressBuilder {
    private List<AddressNode> addressNodeList;
    private AddressContext addressContext;

    @Override
    public StandardAddress clear(String text) {
        this.addressContext = new AddressContext(text);

        AddressNodeParse addressNodeParse = new AddressNodeParse(this.addressContext);
        // 找到区域
        addressNodeParse.parseDistrict();
        addressNodeParse.parseRegion();
        addressNodeParse.parseBlock();
        addressNodeParse.parseRoad();
        addressNodeParse.parseNONG();
        addressNodeParse.parseHao();
        addressNodeParse.parseCeng();
        addressNodeParse.parseShi();

        return addressContext.getResult();
    }
}
