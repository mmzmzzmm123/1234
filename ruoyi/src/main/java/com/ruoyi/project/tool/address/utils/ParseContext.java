package com.ruoyi.project.tool.address.utils;

import com.ruoyi.project.tool.address.model.AdrNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * context
 *
 * @author lihe
 */
public class ParseContext {
    private String district;
    private String region;
    private String block;
    private Integer startIndex = 0;
    private final StringBuilder sb;
    private List<String> features = new LinkedList<>();
    private List<AdrNode> nodeList = new ArrayList<>(20);

    public ParseContext(String text) {
        this.sb = new StringBuilder(text);
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
        this.startIndex = this.sb.indexOf(district);
    }

    public String getRegion() {
        return region;
    }

    public void addRegion(String region, Integer startIndex) {
        this.region = region;
        this.startIndex = startIndex;
    }

    public String getBlock() {
        return block;
    }

    public void addBlock(String block, Integer startIndex) {
        this.block = block;
        this.startIndex = startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void addNode(AdrNode node) {
        this.nodeList.add(node);
        this.startIndex = node.getEndIndex();
    }

    public StringBuilder getContent() {
        return this.sb;
    }

    public List<AdrNode> getNodeList() {
        return nodeList;
    }

    public void addFeature(String feature) {
        this.features.add(feature);
    }

    public int getIndexOf(String text) {
        return this.getContent().indexOf(text, this.getStartIndex());
    }
}
