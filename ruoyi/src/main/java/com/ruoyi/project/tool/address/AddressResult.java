package com.ruoyi.project.tool.address;

import com.ruoyi.project.tool.address.model.AdrNode;
import com.ruoyi.project.tool.address.parse.ParseContext;

import java.util.LinkedList;
import java.util.List;

/**
 * 地址结果
 *
 * @author lihe
 */
public class AddressResult {
    private ParseContext context;
    private String district;
    private String region;
    private String block;
    private List<String> cleanAddress;

    public AddressResult(ParseContext context) {
        this.context = context;
    }

    public String getDistrict() {
        return context.getDistrict();
    }

    public String getRegion() {
        return context.getRegion();
    }

    public String getBlock() {
        return context.getBlock();
    }

    public List<String> getCleanAddress() {
        List<AdrNode> nodeList = context.getNodeList();
        if (0 == nodeList.size()) {
            return null;
        }

        cleanAddress = new LinkedList<>();

        for (int i = 0; i < nodeList.size(); i++) {

        }

        nodeList.forEach(rootNode -> {
            StringBuilder sb = new StringBuilder();
            loop(sb, rootNode);
        });

        return cleanAddress;
    }

    private void loop(StringBuilder sb, AdrNode rootNode) {
        sb.append(rootNode.getContent());
        if (null == rootNode.getChildren()) {
            cleanAddress.add(sb.toString());
            return;
        }

        if (rootNode.getChildren().size() > 1) {
            for (int i = 0; i < rootNode.getChildren().size(); i++) {
                StringBuilder newSb = new StringBuilder(sb);
                loop(newSb, rootNode.getChildren().get(i));
            }
        } else {
            loop(sb, rootNode.getChildren().get(0));
        }

    }
}
