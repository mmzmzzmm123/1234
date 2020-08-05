package com.ruoyi.project.tool.address.service;

import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;

import java.util.List;

/**
 * 地址构建者模式
 * @author lihe
 */
public interface AddressBuilder {

    /**
     * 区域
     *
     * @return
     */
    String parseDistrict();

    /**
     * 镇
     *
     * @return
     */
    String parseRegion();

    /**
     * 板块
     *
     * @return
     */
    String parseBlock();

    /**
     * 路
     *
     * @return
     */
    List<String> parseRoad();

    /**
     * 小区名称
     *
     * @return
     */
    String parseCommunityName();

    /**
     * 弄
     *
     * @return
     */
    String parseNONG();

    /**
     * “号”、幢、座
     *
     * @return
     */
    List<String> parseHao();

    /**
     * 室
     *
     * @return
     */
    String parseShi();

    /**
     * 楼层（地下）
     *
     * @return
     */
    String parseFloor();

    /**
     * 是否独栋
     * @return
     */
    Boolean parseIndependent();

}
