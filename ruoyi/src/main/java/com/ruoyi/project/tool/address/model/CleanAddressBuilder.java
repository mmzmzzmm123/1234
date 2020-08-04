package com.ruoyi.project.tool.address.model;

import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.common.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 清洗地址构建
 *
 * @author lihe
 */
public class CleanAddressBuilder {

    private Map<String, String> districtMap = LoadUtil.loadDict("address-dict/district.dict");
    private List<String> regionList = LoadUtil.loadList("address-dict/region.dict");
    private List<String> blockList = LoadUtil.loadList("address-dict/block.dict");
    private List<String> roadList = LoadUtil.loadList("address-dict/road.dict");
    private CleanAddress cleanAddress;
    private static final int DISTRICT_LENGTH = 8;
    private static final String SHANGHAI_SHI = "上海市";
    private static final String SHANGHAI = "上海";
    private static final String SHANG = "上";
    private static final String HAO = "号";
    private static final String SHANG_SHANG = "上上";
    private static final String SHI_PATTERN = "([\\dA-Za-z]+)(室?)$";
    private static final String CHINESE_FLOOR_PATTERN = "([一二三四五六七八九十]+)层";
    private static final String NUMBER_FLOOR_PATTERN = "(\\d+)层";
    private static final String HAO_PATTERN = "([\\dA-Za-z]+)号";

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
    public CleanAddressBuilder parseDistrict() {
        String shanghaiAndDistrict = null;
        if (cleanAddress.getBoundAddress().length() >= DISTRICT_LENGTH) {
            shanghaiAndDistrict = (SHANG + cleanAddress.getBoundAddress().substring(0, 7)).replace(SHANG_SHANG, SHANG);
        } else {
            shanghaiAndDistrict = (SHANG + cleanAddress.getBoundAddress()).replace(SHANG_SHANG, SHANG);
        }

        //
        if (!shanghaiAndDistrict.startsWith(SHANGHAI_SHI) && !shanghaiAndDistrict.startsWith(SHANGHAI) && shanghaiAndDistrict.startsWith(SHANG)) {
            shanghaiAndDistrict = shanghaiAndDistrict.substring(1, shanghaiAndDistrict.length() - 1);
        }

        for (Map.Entry<String, String> district : districtMap.entrySet()) {
            if (shanghaiAndDistrict.startsWith(district.getKey())) {
                cleanAddress.setDistrict(district.getValue());
                break;
            }
        }
        return this;
    }

    /**
     * 解析镇
     *
     * @return
     */
    public CleanAddressBuilder parseRegion() {
        for (int i = 0; i < regionList.size(); i++) {
            if (-1 != cleanAddress.getBoundAddress().indexOf(regionList.get(i))) {
                cleanAddress.setRegion(regionList.get(i));
            }
        }
        return this;
    }

    /**
     * 解析板块
     */
    public CleanAddressBuilder parseBlock() {
        for (int i = 0; i < blockList.size(); i++) {
            if (-1 != cleanAddress.getBoundAddress().indexOf(blockList.get(i))) {
                cleanAddress.setBlock(regionList.get(i));
            }
        }
        return this;
    }

    /**
     * 解析路（可能缺失“路”）
     */
    public CleanAddressBuilder parseRoad() {
        for (int i = 0; i < roadList.size(); i++) {
            if (-1 != cleanAddress.getBoundAddress().indexOf(roadList.get(i))) {
                cleanAddress.setRoad(roadList.get(i));
            }
        }
        //
        if (StringUtils.isEmpty(cleanAddress.getRoad())) {
            for (int i = 0; i < roadList.size(); i++) {
                // 砍掉“路”
                String noRoad = roadList.get(i);
                if (noRoad.endsWith("路")) {
                    noRoad = noRoad.substring(0, noRoad.length() - 1);
                }

                if (-1 != cleanAddress.getBoundAddress().indexOf(noRoad)) {
                    cleanAddress.setRoad(noRoad);
                }
            }
        }
        return this;
    }

    /**
     * 解析小区名称（路名和小区名称重定义）
     */
    public CleanAddressBuilder parseCommunityName() {
        return this;
    }


    /**
     * 是否独栋？“全”“全幢”
     * 别墅？
     */
    public CleanAddressBuilder parseIndependent() {
        Pattern shiPattern = Pattern.compile(SHI_PATTERN);
        Matcher matcher = shiPattern.matcher(cleanAddress.getBoundAddress());
        if (matcher.find()) {
            getCondoAddress();
            cleanAddress.setIndependent(Boolean.FALSE);
        } else {
            cleanAddress.setIndependent(Boolean.TRUE);
        }


        return this;
    }

    /**
     * 解析地址
     *
     * @return
     */
    public void parseAddress() {
        getCondoAddress();
    }

    /**
     * 获取楼层
     */
    private void getFloorText() {
        Pattern pattern = Pattern.compile(CHINESE_FLOOR_PATTERN);
        Matcher matcher = pattern.matcher(cleanAddress.getBoundAddress());
        if (matcher.find()) {
            cleanAddress.setFloor(matcher.group(1));
            return;
        }
        pattern = Pattern.compile(NUMBER_FLOOR_PATTERN);
        matcher = pattern.matcher(cleanAddress.getBoundAddress());
        if (matcher.find()) {
            cleanAddress.setFloor(matcher.group(1));
            return;
        }
    }

    /**
     * 小区地址
     */
    private void getCommunityAddress(String buildingAddressText) {
        // 只有号、号号，弄(支弄)号
        Pattern pattern = Pattern.compile(HAO_PATTERN);
        Matcher matcher = pattern.matcher(buildingAddressText);
        if (matcher.find()) {
            String communityText = buildingAddressText.replace(matcher.group(0), "");
            CommunityAddress communityAddress = new CommunityAddress(communityText);
            this.cleanAddress.addAddress(communityAddress);
        }
    }

    /**
     * 获取清洗地址
     */
    private void getCondoAddress() {
        getFloorText();
        // 有路
        if (!StringUtils.isEmpty(cleanAddress.getRoad())) {
            int roadIndex = cleanAddress.getBoundAddress().indexOf(cleanAddress.getRoad());
            String condoAddressText =
                    cleanAddress.getBoundAddress().substring(roadIndex);

            // 单套
            CondoAddress condoAddress = new CondoAddress(condoAddressText);
            cleanAddress.setCondoAddress(condoAddress);
            cleanAddress.addAddress(condoAddress);

            int haoIndex = condoAddressText.lastIndexOf(HAO);
            String buildingAddressText = condoAddressText.substring(0, haoIndex + 1);
            BuildingAddress buildingAddress = new BuildingAddress(buildingAddressText);
            // 楼栋
            condoAddress.addPartialAddress(buildingAddress);
            cleanAddress.addAddress(condoAddress);
            getCommunityAddress(buildingAddressText);
        } else if (!StringUtils.isEmpty(cleanAddress.getCommunityName())) {

        } else {
            // 没有路，没有小区
        }

        // 从路找到最后面。
        // 把室号去掉
        // 把号去掉
        // 单套地址、楼栋地址、小区地址
    }
}
