package com.ruoyi.project.tool.address.service;

/**
 * 地址构建者模式
 */
public interface AddressBuilder {

    String parseDistrict();
    String parseRegion();
    String parseBlock();
    String parseRoad();
    String parseCommunityName();
    String parseNONG();
    String parseHao();
    String parseShi();
    String parseFloor();

}
