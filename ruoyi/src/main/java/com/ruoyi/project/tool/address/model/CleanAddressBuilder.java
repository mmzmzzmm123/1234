package com.ruoyi.project.tool.address.model;

import com.ruoyi.common.utils.LoadUtil;

import java.util.List;
import java.util.Map;

/**
 * 清洗地址构建
 *
 * @author lihe
 */
public class CleanAddressBuilder {

    private Map<String, String> districtMap = LoadUtil.loadDict("address-dict/district.dict");
    private List<String> regionList = LoadUtil.loadList("address-dict/region.dict");
    private List<String> blockList = LoadUtil.loadList("address-dict/block.dict");
    private CleanAddress cleanAddress;

    private CleanAddressBuilder(CleanAddress cleanAddress) {
        this.cleanAddress = cleanAddress;
    }

    public static CleanAddressBuilder builder(CleanAddress cleanAddress) {
        return new CleanAddressBuilder(cleanAddress);
    }

    /**
     * 解析区域
     * 前面7个字符
     *
     * @return
     */
    public void parseDistrict() {
        String shanghaiAndDistrict = "上" + cleanAddress.getBoundAddress().substring(0, 7).replace("上上", "");
        for (Map.Entry<String, String> district : districtMap.entrySet()) {
            if (shanghaiAndDistrict.startsWith(district.getKey())) {
                cleanAddress.setDistrict(district.getKey());
                break;
            }
        }
    }

    /**
     * 解析镇
     *
     * @return
     */
    public void parseRegion() {
        for (int i = 0; i < regionList.size(); i++) {
            if (-1 != cleanAddress.getBoundAddress().indexOf(regionList.get(i))) {
                cleanAddress.setRegion(regionList.get(i));
            }
        }
    }

    /**
     * 解析板块
     */
    public void parseBlock() {
        for (int i = 0; i < blockList.size(); i++) {
            if (-1 != cleanAddress.getBoundAddress().indexOf(blockList.get(i))) {
                cleanAddress.setBlock(regionList.get(i));
            }
        }
    }

    /**
     * 解析路（可能缺失“路”）
     */
    public void parseRoad() {

    }

    /**
     * 解析小区名称（路名和小区名称重定义）
     */
    public void parseCommunityName() {

    }

    /**
     * 是否独栋？“全”“全幢”
     */
    public void parseIndependent() {

    }

}
