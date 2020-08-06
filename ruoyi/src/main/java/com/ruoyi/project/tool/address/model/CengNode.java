package com.ruoyi.project.tool.address.model;

import com.ruoyi.project.tool.address.AddressNodeType;

/**
 * 层
 *
 * @author lihe
 */
public class CengNode extends AdrNode {
    public CengNode(String content, Integer startIndex, Integer endIndex) {
        super(content, startIndex, endIndex);
        this.nodeType = AddressNodeType.CENG;
    }
}
