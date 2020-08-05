package com.ruoyi.project.tool.address.utils;

import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.tool.address.AddressContext;
import com.ruoyi.project.tool.address.AddressNode;
import com.ruoyi.project.tool.address.AddressNodeType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 地址段分析
 *
 * @author lihe
 */
public class AddressNodeParse {

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
    private static final String DEFAULT_FLOOR_PATTERN = "([\\d一二三四五六七八九十]+)层";
    private static final String NUMBER_FLOOR_PATTERN = "(\\d+)层";
    private static final String DEFAULT_HAO_PATTERN = "([\\dA-Za-z]+)号";
    private static final String DEFAULT_ZHUANG_PATTERN = "([\\dA-Za-z]+)幢";
    private static final String DEFAULT_CENG_PATTERN = "([\\d])层";
    private static final String DEFAULT_SHI_PATTERN = "([\\dA-Za-z]+)(室?)$";
    private static final String DEFAULT_NONG_PATTERN = "[\\d一二三四五六七八九十]+弄([\\d一二三四五六七八九十]+支弄)?";
    private static List<String> specialChar = new LinkedList<>();
    private AddressContext context;

    static {
        specialChar.add("。");
        specialChar.add(".");
        specialChar.add("，");
        specialChar.add(",");
        specialChar.add("-");
        specialChar.add("——");
        specialChar.add("_");
        specialChar.add("、");
        specialChar.add("（");
        specialChar.add("）");
        specialChar.add("(");
        specialChar.add(")");
        specialChar.add("《");
        specialChar.add("》");
    }

    public AddressNodeParse(AddressContext addressContext) {
        this.context = addressContext;
    }

    /**
     * 区域
     */
    public void parseDistrict() {
        String shanghaiAndDistrict = null;
        if (this.context.getContent().length() >= DISTRICT_LENGTH) {
            shanghaiAndDistrict = (SHANG + this.context.getContent().substring(0, 7)).replace(SHANG_SHANG, SHANG);
        } else {
            shanghaiAndDistrict = (SHANG + this.context.getContent()).replace(SHANG_SHANG, SHANG);
        }

        if (!shanghaiAndDistrict.startsWith(SHANGHAI_SHI) && !shanghaiAndDistrict.startsWith(SHANGHAI) && shanghaiAndDistrict.startsWith(SHANG)) {
            shanghaiAndDistrict = shanghaiAndDistrict.substring(1, shanghaiAndDistrict.length() - 1);
        }

        for (Map.Entry<String, String> district : districtMap.entrySet()) {
            int index = shanghaiAndDistrict.indexOf(district.getKey(), this.context.getStartIndex());
            if (-1 != index) {
                AddressNode addressNode = new AddressNode(AddressNodeType.DISTRICT, district.getValue());
                addressNode.setStartIndex(index);
                addressNode.setEndIndex(index + district.getKey().length() + 1);

                this.context.addAddressNode(addressNode);
            }
        }

    }

    /**
     * 镇
     */
    public void parseRegion() {
        for (int i = 0; i < regionList.size(); i++) {
            int index = this.context.getContent().indexOf(regionList.get(i), this.context.getStartIndex());
            if (-1 != index) {
                AddressNode addressNode = new AddressNode(AddressNodeType.REGION, regionList.get(i));
                addressNode.setStartIndex(index);
                addressNode.setEndIndex(index + regionList.get(i).length() + 1);

                this.context.addAddressNode(addressNode);
            }
        }
    }

    /**
     * 板块
     */
    public void parseBlock() {
        for (int i = 0; i < blockList.size(); i++) {
            int index = this.context.getContent().indexOf(blockList.get(i));
            if (-1 != index) {
                AddressNode addressNode = new AddressNode(AddressNodeType.BLOCK, blockList.get(i));
                addressNode.setStartIndex(index);
                addressNode.setEndIndex(index + blockList.get(i).length() + 1);

                this.context.addAddressNode(addressNode);
            }
        }
    }

    /**
     * 路
     */
    public void parseRoad() {
        AddressNode addressNode = new AddressNode(AddressNodeType.ROAD);
        for (int i = 0; i < roadList.size(); i++) {
            int index = this.context.getContent().indexOf(roadList.get(i), this.context.getStartIndex());
            if (-1 != index) {
                if (null == addressNode.getStartIndex()) {
                    addressNode.setStartIndex(index);
                }
                AddressNode childAddressNode = new AddressNode(AddressNodeType.ROAD, roadList.get(i));
                childAddressNode.setStartIndex(index);
                childAddressNode.setEndIndex(index + roadList.get(i).length() + 1);
                addressNode.setEndIndex(index + roadList.get(i).length() + 1);
                addressNode.addChildNode(childAddressNode);
            }
        }

        if (null != addressNode.getStartIndex()) {
            this.context.addAddressNode(addressNode);
            return;
        }

        for (int i = 0; i < roadList.size(); i++) {
            // 砍掉“路”
            String noRoad = roadList.get(i);
            if (noRoad.endsWith("路")) {
                noRoad = noRoad.substring(0, noRoad.length() - 1);
            }
            int index = this.context.getContent().indexOf(noRoad);
            if (-1 != index) {
                if (null == addressNode.getStartIndex()) {
                    addressNode.setStartIndex(index);
                }
                AddressNode childAddressNode = new AddressNode(AddressNodeType.ROAD, noRoad);
                childAddressNode.setStartIndex(index);
                childAddressNode.setEndIndex(index + roadList.get(i).length() + 1);
                addressNode.setEndIndex(index + roadList.get(i).length() + 1);
                addressNode.addChildNode(childAddressNode);
            }
        }
    }

    /**
     * 弄
     */
    public void parseNONG() {
        Pattern nongPattern = Pattern.compile(DEFAULT_NONG_PATTERN);
        Matcher matcher = nongPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(0), this.context.getStartIndex());
            if (-1 != index) {
                AddressNode addressNode = new AddressNode(AddressNodeType.NONG, matcher.group(0));
                addressNode.setStartIndex(index);
                addressNode.setEndIndex(index + matcher.group(0).length() + 1);
                addressNode.addChildNode(addressNode);
                this.context.addAddressNode(addressNode);
            }
        }
    }

    /**
     * 号
     */
    public void parseHao() {
        Pattern haoPattern = Pattern.compile(DEFAULT_HAO_PATTERN);
        Matcher matcher = haoPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(0), this.context.getStartIndex());
            if (-1 != index) {
                AddressNode addressNode = new AddressNode(AddressNodeType.HAO, matcher.group(0));
                addressNode.setStartIndex(index);
                addressNode.setEndIndex(index + matcher.group(0).length() + 1);
                addressNode.addChildNode(addressNode);
                this.context.addAddressNode(addressNode);
            }
        }
    }

    /**
     * 幢
     */
    public void parseZhuang() {
        Pattern haoPattern = Pattern.compile(DEFAULT_ZHUANG_PATTERN);
        Matcher matcher = haoPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(0), this.context.getStartIndex());
            if (-1 != index) {
                AddressNode addressNode = new AddressNode(AddressNodeType.ZHUANG, matcher.group(0));
                addressNode.setStartIndex(index);
                addressNode.setEndIndex(index + matcher.group(0).length() + 1);
                addressNode.addChildNode(addressNode);
                this.context.addAddressNode(addressNode);
            }
        }
    }

    /**
     * 层
     */
    public void parseCeng() {
        Pattern haoPattern = Pattern.compile(DEFAULT_FLOOR_PATTERN);
        Matcher matcher = haoPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(0), this.context.getStartIndex());
            if (-1 != index) {
                AddressNode addressNode = new AddressNode(AddressNodeType.CENG, matcher.group(0));
                addressNode.setStartIndex(index);
                addressNode.setEndIndex(index + matcher.group(0).length() + 1);
                addressNode.addChildNode(addressNode);
                this.context.addAddressNode(addressNode);
            }
        }
    }

    /**
     * 室
     */
    public void parseShi() {
        Pattern haoPattern = Pattern.compile(DEFAULT_SHI_PATTERN);
        Matcher matcher = haoPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(0),this.context.getStartIndex());
            if (-1 != index) {
                AddressNode addressNode = new AddressNode(AddressNodeType.SHI, matcher.group(0));
                addressNode.setStartIndex(index);
                addressNode.setEndIndex(index + matcher.group(0).length() + 1);
                addressNode.addChildNode(addressNode);
                this.context.addAddressNode(addressNode);
            }
        }
    }
}
