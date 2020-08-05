package com.ruoyi.project.tool.address;

import com.ruoyi.project.tool.address.model.AddressType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * context
 *
 * @author lihe
 */
public class AddressContext {
    /**
     * 有序列表
     * 优化成队列，FIFO
     */
    private ArrayList<AddressNode> nodeList = new ArrayList<>();
    private StringBuilder stringBuilder;
    private Integer startIndex = 0;

    public AddressContext(String text) {
        this.stringBuilder = new StringBuilder(text);
    }

    public StringBuilder getContent() {
        return this.stringBuilder;
    }

    public void addAddressNode(AddressNode node) {
        startIndex = node.getEndIndex() - 1;
        this.nodeList.add(node);
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public StandardAddress getResult() {
        StandardAddress standardAddress = new StandardAddress(this.stringBuilder.toString());
        nodeList.forEach(node -> {
            AddressContent addressContent = new AddressContent(AddressType.CONDO);
            if (node.dataNode()) {
                addressContent.appendContent(node.getContent());
            } else {
                if (1 == node.getContentList().size()) {
                    addressContent.appendContent(node.getContentList().get(0).getContent());
                } else {
                    // 多个先组合，再拆分
                }
            }

            standardAddress.addAddressContent(addressContent);
        });

        return standardAddress;
    }
}
