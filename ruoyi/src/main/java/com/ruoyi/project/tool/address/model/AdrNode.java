package com.ruoyi.project.tool.address.model;

import com.ruoyi.project.tool.address.AddressNodeType;

import java.util.LinkedList;
import java.util.List;

/**
 * node
 *
 * @author lihe
 */
public abstract class AdrNode {
    protected AddressNodeType nodeType;
    private Integer startIndex;
    private Integer endIndex;
    private final String content;
    protected List<AdrNode> children;

    public AdrNode(String content, Integer startIndex) {
        this.content = content;
        this.startIndex = startIndex;
    }

    public AdrNode(String content, Integer startIndex, Integer endIndex) {
        this.content = content;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public AddressNodeType getNodeType() {
        return nodeType;
    }

    public String getContent() {
        return content;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public List<AdrNode> getChildren() {
        return children;
    }

    /**
     * 添加节点
     *
     * @param node 节点
     */
    public void addNode(AdrNode node) {
        if (null == this.children) {
            this.children = new LinkedList<>();
        }
        this.children.add(node);
    }


}
