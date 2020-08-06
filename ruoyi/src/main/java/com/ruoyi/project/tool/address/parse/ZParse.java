package com.ruoyi.project.tool.address.parse;

import com.ruoyi.project.tool.address.ParseContext;
import com.ruoyi.project.tool.address.model.AdrNode;
import com.ruoyi.project.tool.address.model.CengNode;
import com.ruoyi.project.tool.address.model.HaoNode;
import com.ruoyi.project.tool.address.model.ShiNode;

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
    private static final String DEFAULT_SHI_PATTERN1 = "([\\dA-Za-z\\_\\-\\.\\——\\、，]+)(室?)$";
    private static final String DEFAULT_SHI_PATTERN = "([\\dA-Za-z\\-\\_\\.，]+)室";
    private static final String CHINESE_FLOOR_PATTERN = "([一二三四五六七八九十]+)层";
    private static final String DEFAULT_FLOOR_PATTERN = "地下?([\\d一二三四五六七八九十])层";
    private static final String TAIL_FLOOR_PATTERN = "(\\d[\\-\\_\\——\\—]?\\d)(层)?$";
    private static final String DEFAULT_ZHUANG_PATTERN = "(\\d+)幢";
    private ParseContext context;
    private AdrNode rootNode;
    private String floor;
    private Integer startIndex;

    public ZParse(AdrNode adrNode, ParseContext context, Integer index) {
        this.context = context;
        this.rootNode = adrNode;
        this.startIndex = index;
    }

    public void parse() {
        parseCeng();
        parseZhuang();
        parseShi();
        tag();
    }

    private void parseZhuang() {
        Pattern haoPattern = Pattern.compile(DEFAULT_ZHUANG_PATTERN);
        Matcher matcher = haoPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            int index = this.context.getContent().indexOf(matcher.group(0), this.startIndex);
            HaoNode haoNode = new HaoNode(matcher.group(0), index, index + matcher.group(0).length());
            this.rootNode.addNode(haoNode);
            startIndex = haoNode.getEndIndex();
        }
    }

    private void parseShi() {
        // 复式
        if (-1 != this.context.getContent().indexOf(FU_SHI)) {
            String content = this.context.getContent().substring(this.startIndex);
            Pattern shiPattern = Pattern.compile(NUMBER_PATTERN);
            Matcher matcher = shiPattern.matcher(content);
            if (matcher.find()) {
                int index = this.context.getContent().indexOf(matcher.group(0), this.startIndex);
                ShiNode shiNode = new ShiNode(matcher.group(0) + "室", index, index + matcher.group(0).length());
                this.rootNode.addNode(shiNode);
                startIndex = shiNode.getEndIndex();
            }
        } else {
            Pattern shiPattern = Pattern.compile(DEFAULT_SHI_PATTERN);
            Matcher matcher = shiPattern.matcher(this.context.getContent());
            if (matcher.find()) {
                int index = this.context.getContent().indexOf(matcher.group(0), this.startIndex);
                ShiNode shiNode = new ShiNode(matcher.group(0), index, index + matcher.group(0).length());
                this.rootNode.addNode(shiNode);
                startIndex = shiNode.getEndIndex();
            }
        }
    }

    private void tag() {
        // 车位、车库、地块
        if (-1 != this.context.getContent().indexOf("车位") || -1 != this.context.getContent().indexOf("车库")
                || -1 != this.context.getContent().indexOf("停车")) {
            this.context.addFeature("车位");
        }

        if (-1 != this.context.getContent().indexOf("地块")) {
            this.context.addFeature("地块");
        }
    }

    private Boolean invalidCeng(int floorIndex) {
        // 判断这个层是否可取？
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
            if (invalidCeng(index)) {
                return;
            }
            CengNode cengNode = new CengNode("-" + num, index, index + matcher.group(0).length());
            this.rootNode.addNode(cengNode);
            rootNode.addNode(cengNode);
            return;
        }
    }

}

