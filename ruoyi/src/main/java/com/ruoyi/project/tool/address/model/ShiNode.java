package com.ruoyi.project.tool.address.model;

import com.ruoyi.project.tool.address.AddressNodeType;

/**
 * 室
 *
 * @author lihe
 */
public class ShiNode extends AdrNode {
    public ShiNode(String content, Integer startIndex, Integer endIndex) {
        super(content, startIndex, endIndex);
        this.nodeType = AddressNodeType.SHI;
    }
}
