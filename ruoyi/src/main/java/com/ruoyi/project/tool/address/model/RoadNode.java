package com.ruoyi.project.tool.address.model;

import com.ruoyi.project.tool.address.AddressNodeType;

import java.util.LinkedList;
import java.util.List;

/**
 * 路名或者小区名称
 *
 * @author lihe
 */
public class RoadNode extends AdrNode {
    private List<String> roadList = new LinkedList<>();

    public RoadNode(String content, Integer startIndex) {
        super(content, startIndex);
        this.nodeType = AddressNodeType.ROAD;
    }

    public RoadNode(String content, Integer startIndex, Integer endIndex) {
        super(content, startIndex, endIndex);
        this.nodeType = AddressNodeType.ROAD;
    }

    /**
     * 交叉路
     *
     * @param road
     */
    public void addRoad(String road) {
        roadList.add(road);
    }
}
