package com.ruoyi.project.tool.address;

import com.ruoyi.common.utils.StringUtils;

import java.util.AbstractList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 地址节点
 *
 * @author lihe
 */
public class AddressNode {

    private AddressNodeType nodeType;
    private Integer startIndex;
    private Integer endIndex;
    private String content;
    private List<AddressNode> contentList = new LinkedList<>();

    public AddressNode(AddressNodeType addressNodeType) {
        this.nodeType = addressNodeType;
    }

    public AddressNode(AddressNodeType addressNodeType, String content) {
        this.nodeType = addressNodeType;
        this.content = content;
    }

    public AddressNodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(AddressNodeType nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public String getContent() {
        return content;
    }

    /**
     * 数据节点
     *
     * @return
     */
    public Boolean dataNode() {
        return StringUtils.isNotEmpty(this.content);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addChildNode(AddressNode addressNode) {
        contentList.add(addressNode);
    }

    public List<AddressNode> getContentList() {
        return contentList;
    }
}
