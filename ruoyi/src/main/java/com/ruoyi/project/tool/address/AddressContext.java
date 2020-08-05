package com.ruoyi.project.tool.address;

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

    public AddressContext(String text) {
        this.stringBuilder = new StringBuilder(text);
    }

    public StringBuilder getContent() {
        return this.stringBuilder;
    }

    public void addAddressNode(AddressNode node) {
        this.nodeList.add(node);
    }

    public StandardAddress getResult() {
        StandardAddress standardAddress = new StandardAddress(this.stringBuilder.toString());
//        nodeList.forEach(node->{
////            AddressContent addressContent = new AddressContent();
//
////            standardAddress.addAddressContent();
//        });

        return standardAddress;
    }
}
