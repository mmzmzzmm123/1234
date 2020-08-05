package com.ruoyi.project.tool.address.service;

import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.tool.address.model.AddressType;
import com.ruoyi.project.tool.address.model.CleanAddress;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 地址构建基类
 *
 * @author lihe
 */
public abstract class BaseAddressBuilder implements AddressBuilder {

    private Map<String, String> districtMap = LoadUtil.loadDict("address-dict/district.dict");
    private List<String> regionList = LoadUtil.loadList("address-dict/region.dict");
    private List<String> blockList = LoadUtil.loadList("address-dict/block.dict");
    private List<String> roadList = LoadUtil.loadList("address-dict/road.dict");
    private static final int DISTRICT_LENGTH = 8;
    private static final String SHANGHAI_SHI = "上海市";
    private static final String SHANGHAI = "上海";
    private static final String SHANG = "上";
    private static final String HAO = "号";
    private static final String SHANG_SHANG = "上上";
    private static final String CHINESE_FLOOR_PATTERN = "([一二三四五六七八九十]+)层";
    private static final String NUMBER_FLOOR_PATTERN = "(\\d+)层";
    private static final String DEFAULT_HAO_PATTERN = "([\\dA-Za-z]+)号";
    private static final String DEFAULT_SHI_PATTERN = "([\\dA-Za-z]+)(室?)$";
    private static final String DEFAULT_NONG_PATTERN = "[\\d一二三四五六七八九十]+弄([\\d一二三四五六七八九十]+支弄)?";
    protected CleanContext context;

    public BaseAddressBuilder(String text) {
        this.context = new CleanContext(text);
    }

    /**
     * 地址类别（单套、楼栋、小区）
     *
     * @return
     */
    public abstract AddressType getAddressType();


    @Override
    public String parseDistrict() {
        String shanghaiAndDistrict = null;
        if (this.context.getBoundAddress().length() >= DISTRICT_LENGTH) {
            shanghaiAndDistrict = (SHANG + this.context.getBoundAddress().substring(0, 7)).replace(SHANG_SHANG, SHANG);
        } else {
            shanghaiAndDistrict = (SHANG + this.context.getBoundAddress()).replace(SHANG_SHANG, SHANG);
        }

        if (!shanghaiAndDistrict.startsWith(SHANGHAI_SHI) && !shanghaiAndDistrict.startsWith(SHANGHAI) && shanghaiAndDistrict.startsWith(SHANG)) {
            shanghaiAndDistrict = shanghaiAndDistrict.substring(1, shanghaiAndDistrict.length() - 1);
        }

        for (Map.Entry<String, String> district : districtMap.entrySet()) {
            if (shanghaiAndDistrict.startsWith(district.getKey())) {
                return district.getValue();
            }
        }

        return null;
    }

    @Override
    public String parseRegion() {
        for (int i = 0; i < regionList.size(); i++) {
            if (-1 != this.context.getBoundAddress().indexOf(regionList.get(i))) {
                return regionList.get(i);
            }
        }
        return null;
    }

    @Override
    public String parseBlock() {
        for (int i = 0; i < blockList.size(); i++) {
            if (-1 != this.context.getBoundAddress().indexOf(blockList.get(i))) {
                return regionList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<String> parseRoad() {
        List<String> roadSegmentList = new LinkedList<>();

        for (int i = 0; i < roadList.size(); i++) {
            if (-1 != this.context.getBoundAddress().indexOf(roadList.get(i))) {
                roadSegmentList.add(roadList.get(i));
            }
        }

        if (0 != roadSegmentList.size()) {
            return roadSegmentList;
        }
        for (int i = 0; i < roadList.size(); i++) {
            // 砍掉“路”
            String noRoad = roadList.get(i);
            if (noRoad.endsWith("路")) {
                noRoad = noRoad.substring(0, noRoad.length() - 1);
            }

            if (-1 != this.context.getBoundAddress().indexOf(noRoad)) {
                roadSegmentList.add(noRoad);
            }
        }

        return roadSegmentList;
    }

    @Override
    public String parseCommunityName() {
        return null;
    }

    @Override
    public String parseNONG() {
        Pattern shiPattern = Pattern.compile(DEFAULT_NONG_PATTERN);
        Matcher matcher = shiPattern.matcher(this.context.getBoundAddress());
        if (matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }

    @Override
    public List<String> parseHao() {
        List<String> haoSegmentList = new LinkedList<>();
        Pattern shiPattern = Pattern.compile(DEFAULT_HAO_PATTERN);
        Matcher matcher = shiPattern.matcher(this.context.getBoundAddress());
        while (matcher.find()) {
            haoSegmentList.add(matcher.group());
        }
        if (0 != haoSegmentList.size()) {
            return haoSegmentList;
        } else {
            return null;
        }
    }

    @Override
    public String parseShi() {
        Pattern shiPattern = Pattern.compile(DEFAULT_SHI_PATTERN);
        Matcher matcher = shiPattern.matcher(this.context.getBoundAddress());
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    @Override
    public String parseFloor() {
        Pattern pattern = Pattern.compile(CHINESE_FLOOR_PATTERN);
        Matcher matcher = pattern.matcher(this.context.getBoundAddress());
        if (matcher.find()) {
            return matcher.group(1);
        }

        pattern = Pattern.compile(NUMBER_FLOOR_PATTERN);
        matcher = pattern.matcher(this.context.getBoundAddress());
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    @Override
    public Boolean parseIndependent() {
        Pattern shiPattern = Pattern.compile(DEFAULT_SHI_PATTERN);
        Matcher matcher = shiPattern.matcher(this.context.getBoundAddress());
        if (matcher.find()) {
            return false;
        }
        return true;
    }

    public List<CleanAddress> getResult() {
        return this.context.getResult();
    }
}
