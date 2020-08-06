package com.ruoyi.project.tool.address.parse;

import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.tool.address.utils.ParseContext;
import com.ruoyi.project.tool.address.model.RoadNode;

import java.util.List;
import java.util.Map;

/**
 * X解析
 *
 * @author lihe
 */
public class XParse {
    private static final int DISTRICT_LENGTH = 8;
    private static final String SHANGHAI_SHI = "上海市";
    private static final String SHANGHAI = "上海";
    private static final String SHANG_SHANG = "上上";
    private static final String SHANG = "上";
    private Map<String, String> districtMap = LoadUtil.loadDict("address-dict/district.dict");
    private List<String> regionList = LoadUtil.loadList("address-dict/region.dict");
    private List<String> blockList = LoadUtil.loadList("address-dict/block.dict");
    private List<String> roadList = LoadUtil.loadList("address-dict/road.dict");
    private List<String> communityNameList = LoadUtil.loadList("address-dict/community.dict");
    private ParseContext context;

    public XParse(ParseContext context) {
        this.context = context;
    }

    /**
     * 区域、板块、
     * 路、小区
     */
    public void parse() {
        parseDistrict();
        parseRegion();
        parseBlock();
        parseRoad();
        parseCommunityName();
    }

    /**
     * 小区名称
     */
    private void parseCommunityName() {
        RoadNode communityNameNode = null;
        for (int i = 0; i < communityNameList.size(); i++) {
            int index = this.context.getContent().indexOf(communityNameList.get(i), this.context.getStartIndex());
            if (-1 != index) {
                communityNameNode = new RoadNode(communityNameList.get(i), index,
                        index + communityNameList.get(i).length());
                break;
            }
        }

        if (null != communityNameNode) {
            this.context.addNode(communityNameNode);

            YParse yParse = new YParse(communityNameNode, context);
            yParse.parse();
            return;
        }
    }

    /**
     * 路名
     */
    private void parseRoad() {
        int startIndex = this.context.getStartIndex();
        int endIndex = 0;
        RoadNode firstNode = null;
        for (int i = 0; i < roadList.size(); i++) {
            int index = this.context.getContent().indexOf(roadList.get(i), startIndex);
            if (-1 != index) {
                RoadNode roadNode = new RoadNode(roadList.get(i), index);
                if (null == firstNode) {
                    firstNode = roadNode;
                } else {
                    // 连路；
                    if (endIndex + 1 == index) {
                        firstNode.addRoad(firstNode.getContent());
                        firstNode.addRoad(roadList.get(i));
                    } else {
                        // 新旧地址
                    }
                }
                startIndex = index;
                endIndex = index + roadList.get(i).length();
            }
        }

        // 单点
        if (null != firstNode) {
            firstNode.setEndIndex(endIndex);
            this.context.addNode(firstNode);

            YParse yParse = new YParse(firstNode, context);
            yParse.parse();
            return;
        }


        for (int i = 0; i < roadList.size(); i++) {
            // 砍掉“路”
            // XX街加上“路”
            String noRoad = roadList.get(i);
            if (noRoad.endsWith("路")) {
                noRoad = noRoad.substring(0, noRoad.length() - 1);
            }

            int index = this.context.getContent().indexOf(noRoad, startIndex);
            if (-1 != index) {
                RoadNode roadNode = new RoadNode(noRoad, index);
                if (null == firstNode) {
                    firstNode = roadNode;
                } else {
                    // 连路；
                    if (endIndex + 1 == index) {
                        firstNode.addRoad(firstNode.getContent());
                        firstNode.addRoad(roadList.get(i));
                    } else {
                        // 新旧地址
                    }
                }
                startIndex = index;
                endIndex = index + roadList.get(i).length();
            }
        }
    }


    private void parseDistrict() {
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
            if (shanghaiAndDistrict.startsWith(district.getKey())) {
                this.context.setDistrict(district.getValue());
            }
        }
    }

    private void parseRegion() {
        for (int i = 0; i < regionList.size(); i++) {
            int index = this.context.getContent().indexOf(regionList.get(i), this.context.getStartIndex());
            if (-1 != index) {
                this.context.addRegion(regionList.get(i), index);
            }
        }
    }

    private void parseBlock() {
        for (int i = 0; i < blockList.size(); i++) {
            int index = this.context.getContent().indexOf(blockList.get(i), this.context.getStartIndex());
            if (-1 != 1) {
                this.context.addBlock(blockList.get(i), index);
            }
        }
    }
}
