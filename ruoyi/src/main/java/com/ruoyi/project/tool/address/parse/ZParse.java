package com.ruoyi.project.tool.address.parse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.tool.address.model.*;
import com.ruoyi.project.tool.address.utils.ParseContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Z解析
 * <p>
 * 102.3室
 * 102、103室
 * 1-3层、1_3层——
 *
 * @author lihe
 */
public class ZParse {
    private static final String FU_SHI = "复式";
    private static final String NUMBER_PATTERN = "\\d+";
    private static final String LIAN_SHI_PATTERN = "(\\d+)[、\\.\\-\\_](\\d)(室)?$";
    private static final String DEFAULT_SHI_PATTERN = "[\\dA-Za-z]+室";
    private static final String STRICT_SHI_PATTERN = DEFAULT_SHI_PATTERN + "$";
    private static final String MULTI_SHI_PATTERN = "[号](\\d+(室)?[、\\.\\-\\_，,]?)+(室)?$";
    private static final String CHINESE_FLOOR_PATTERN = "([一二三四五六七八九十]+)层";
    private static final String DEFAULT_FLOOR_PATTERN = "地下?([\\d一二三四五六七八九十])层";
    private static final String TAIL_FLOOR_PATTERN = "(\\d[\\-\\_\\——\\—]?\\d)(层)?$";
    private static final String DEFAULT_ZHUANG_PATTERN = "(\\d+)幢";
    private ParseContext context;
    private AdrNode rootNode;
    private String floor;
    private Integer startIndex;
    private Boolean isPark = Boolean.FALSE;

    public ZParse(AdrNode adrNode, ParseContext context, Integer index) {
        this.context = context;
        this.rootNode = adrNode;
        this.startIndex = index;
    }

    public void parse() {
        tag();
        parseCeng();
        parseZhuang();
        parseShi();
    }

    private void parseZhuang() {
        Pattern haoPattern = Pattern.compile(DEFAULT_ZHUANG_PATTERN);
        Matcher matcher = haoPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(0), this.startIndex);
            if (-1 != index) {
                HaoNode haoNode = new HaoNode(matcher.group(), index, index + matcher.group().length());
                this.rootNode.addNode(haoNode);
                startIndex = haoNode.getEndIndex();
            }
        }
    }

    /**
     * 复式
     */
    private void parseFUSHI() {
        String content = this.context.getContent().substring(this.startIndex);
        Pattern shiPattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = shiPattern.matcher(content);
        if (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(0), this.startIndex);
            ShiNode shiNode = new ShiNode(matcher.group(0) + "室", index, index + matcher.group(0).length());
            this.rootNode.addNode(shiNode);
            startIndex = shiNode.getEndIndex();
            this.context.addFeature("复式");
        }
    }

    /**
     * 连室号
     * 连着的 102.3室
     *
     * @param shi1
     * @param shi2
     */
    private void parseLianShi(String shi1, String shi2) {

        int startIndex = this.context.getContent().indexOf(shi1, this.startIndex);
        ShiNode shiNode1 = new ShiNode(shi1 + "室", startIndex, startIndex + shi1.length());
        this.rootNode.addNode(shiNode1);
        startIndex = shiNode1.getEndIndex();

        startIndex = this.context.getContent().indexOf(shi2, startIndex);
        ShiNode shiNode2 = new ShiNode(shi1.substring(0, shi1.length() - shi2.length()) + shi2 + "室", startIndex,
                startIndex + shi2.length());
        this.rootNode.addNode(shiNode2);
    }

    /**
     * 多室号
     *
     * @param matcherText 室字符文本
     */
    private void parseMultiShi(String matcherText) {
        Pattern numberPattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = numberPattern.matcher(matcherText);
        int startIndex = this.startIndex;
        while (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(), startIndex);
            if (-1 == index) {
                continue;
            }

            ShiNode shiNode = new ShiNode(matcher.group() + "室", index, index + matcher.group().length());
            this.rootNode.addNode(shiNode);
            startIndex = shiNode.getEndIndex();
        }
    }

    private void parseShi() {
        String text = this.context.getContent().substring(this.startIndex);
        // 复式
        if (-1 != text.indexOf(FU_SHI)) {
            parseFUSHI();
            return;
        }
        // 连室
        Pattern lianShiPattern = Pattern.compile(LIAN_SHI_PATTERN);
        Matcher matcher = lianShiPattern.matcher(text);
        if (matcher.find()) {
            parseLianShi(matcher.group(1), matcher.group(2));
            return;
        }
        // 多室
        Pattern shiPattern = Pattern.compile(MULTI_SHI_PATTERN);
        matcher = shiPattern.matcher(text);
        if (matcher.find()) {
            parseMultiShi(matcher.group());
            return;
        }
        // 单室
        String matcherText = null;
        if (this.isPark) {
            shiPattern = Pattern.compile(DEFAULT_SHI_PATTERN);
        } else {
            shiPattern = Pattern.compile(STRICT_SHI_PATTERN);
        }
        matcher = shiPattern.matcher(text);
        if (matcher.find()) {
            matcherText = matcher.group();
        }
        if (StringUtils.isNotEmpty(matcherText)) {
            int index = this.context.getContent().indexOf(matcherText, startIndex);
            ShiNode shiNode = new ShiNode(matcherText, index, index + matcherText.length());
            this.rootNode.addNode(shiNode);
        }

    }

    private void tag() {
        // 车位、车库、地块
        if (-1 != this.context.getContent().indexOf("车位") || -1 != this.context.getContent().indexOf("车库")
                || -1 != this.context.getContent().indexOf("停车")) {
            this.context.addFeature("车位");
            this.isPark = Boolean.TRUE;
        }

        if (-1 != this.context.getContent().indexOf("地块")) {
            this.context.addFeature("地块");
        }

        if (-1 != this.context.getContent().indexOf("别墅")) {
            this.context.addFeature("别墅");
        }
    }

    /**
     * 判断这个层是否可取？
     *
     * @param floorIndex
     * @return
     */
    private Boolean invalidCeng(int floorIndex) {
        Pattern shiPattern = Pattern.compile(DEFAULT_SHI_PATTERN);
        Matcher shiMatcher = shiPattern.matcher(this.context.getContent());
        if (shiMatcher.find()) {
            int shiIndex = this.context.getContent().indexOf(shiMatcher.group(0), this.startIndex);
            return floorIndex > shiIndex;
        }

        return true;
    }

    private void parseCeng() {
        Pattern floorPattern = Pattern.compile(CHINESE_FLOOR_PATTERN);
        Matcher matcher = floorPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(0), this.startIndex);
            if (invalidCeng(index)) {
                return;
            }
            CengNode cengNode = new CengNode(matcher.group(0), index, index + matcher.group(0).length());
            this.rootNode.addNode(cengNode);
            rootNode.addNode(cengNode);
            return;
        }

        // 层结尾
        floorPattern = Pattern.compile(TAIL_FLOOR_PATTERN);
        matcher = floorPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            return;
        }

        // 地下层
        floorPattern = Pattern.compile(DEFAULT_FLOOR_PATTERN);
        matcher = floorPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            String num = matcher.group(1)
                    .replace("一", "1")
                    .replace("二", "2")
                    .replace("三", "3")
                    .replace("四", "4")
                    .replace("五", "5");
            int index = this.context.getContent().indexOf(matcher.group(0), this.startIndex);
            if (invalidCeng(index) || this.isPark) {
                return;
            }
            // 并且是否包含车位？如果有包含车位，忽略
            CengNode cengNode = new CengNode("-" + num, index, index + matcher.group(0).length());
            this.rootNode.addNode(cengNode);
            rootNode.addNode(cengNode);
            return;
        }
    }

}

