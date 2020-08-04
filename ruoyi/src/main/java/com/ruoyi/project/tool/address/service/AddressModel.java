package com.ruoyi.project.tool.address.service;

import java.util.LinkedList;
import java.util.List;

public abstract class AddressModel {
    private String district;
    private String region;
    private String block;
    private String road;
    private String communityName;
    private String nong;
    private String complexHao;
    /**
     * 泛化的号（号、幢、座、连号）
     */
    private String hao;
    /**
     * 是否有多个号
     * x号y号=x弄y号
     * x号y幢=x号
     * x幢y号=y号
     * x幢y号=x幢
     */
    private Boolean multiHao;
    /**
     * 是否包含楼层
     * 复式、多层
     */
    private String floor;
    private String shi;
    /**
     * 是否独栋
     */
    private Boolean dependency;
    /**
     * 层级（每个层级只有一个，一对一）
     */
    private AddressModel hierarchy;
//    /**
//     * 兄弟节点
//     */
//    private List<AddressModel> sibling = new LinkedList<>();

}
