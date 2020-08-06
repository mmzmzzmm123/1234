package com.ruoyi.project.tool.address.model;

import com.ruoyi.project.tool.address.AddressNodeType;

import java.util.LinkedList;
import java.util.List;

/**
 * 弄
 *
 * @author lihe
 */
public class NongNode extends AdrNode {
    private List<String> haoList = new LinkedList<>();

    public NongNode(String content, Integer startIndex, Integer endIndex) {
        super(content, startIndex, endIndex);
        this.nodeType = AddressNodeType.NONG;
    }

    /**
     * 交叉路
     *
     * @param road
     */
    public void addHao(String road) {
        haoList.add(road);
    }

}
