package com.ruoyi.project.tool.address.model;

import com.ruoyi.project.tool.address.AddressNodeType;

import java.util.LinkedList;
import java.util.List;

/**
 * 号
 *
 * @author lihe
 */
public class LianHaoNode extends AdrNode {
    private List<String> shiList = new LinkedList<>();

    public LianHaoNode(String content, Integer startIndex, Integer endIndex) {
        super(content, startIndex, endIndex);
        this.nodeType = AddressNodeType.LIAN_HAO;
    }

    public void addShi(String shi) {
        shiList.add(shi);
    }
}
