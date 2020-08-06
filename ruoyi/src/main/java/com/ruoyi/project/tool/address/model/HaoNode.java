package com.ruoyi.project.tool.address.model;

import com.ruoyi.project.tool.address.AddressNodeType;

import java.util.LinkedList;
import java.util.List;

/**
 * 号
 *
 * @author lihe
 */
public class HaoNode extends AdrNode {
    private List<String> shiList = new LinkedList<>();
    public HaoNode(String content, Integer startIndex, Integer endIndex) {
        super(content, startIndex, endIndex);
        this.nodeType = AddressNodeType.HAO;
    }

    /**
     * 交叉路
     *
     * @param shi
     */
    public void addShi(String shi) {
        shiList.add(shi);
    }
}
