package com.ruoyi.project.tool.address.parse;

import com.ruoyi.project.tool.address.utils.ParseContext;
import com.ruoyi.project.tool.address.model.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Y解析
 * ABC弄(DEF支弄)
 * ABC号DEF号(室)
 * ABC、DEF、GHI号；ABC号、DEF号、GHI号
 *
 * @author lihe
 */
public class YParse {
    private static final String HAO = "号";

    private static final String DEFAULT_NONG_PATTERN = "[\\d一二三四五六七八九十]+弄([\\d一二三四五六七八九十]+支弄)?";
    private static final String DEFAULT_HAO_PATTERN = "([\\dA-Za-z]+)号";
    // 连号结束？号=室
    private static final String LIAN_HAO_PATTERN = "\\d+号\\d+号";
    // 多号
    private static final String MULTI_HAO_PATTERN = "(\\d+(号)?[、\\.\\-\\_]?)+号";
    private static final String NUMBER_PATTERN = "\\d+";
    private ParseContext context;
    private AdrNode rootNode;


    public YParse(AdrNode adrNode, ParseContext context) {
        this.context = context;
        this.rootNode = adrNode;
    }

    public void parse() {
        parseNong();
        parseHao();
        parseZuo();
    }

    private void parseZuo() {
    }

    /**
     * 连号
     *
     * @param text
     * @param matchText
     */
    private void parseLianHao(String text, String matchText) {
        if (text.endsWith(matchText)) {
            // ${1}号${2}室，修改最后一个字符
            this.context.getContent().replace(this.context.getContent().length() - 1,
                    this.context.getContent().length() - 1, "室");

            // 找到${1}号
            Matcher haoMatch = Pattern.compile(DEFAULT_HAO_PATTERN).matcher(matchText);
            if (haoMatch.find()) {
                String hao = haoMatch.group();
                int index = this.context.getIndexOf(hao);
                HaoNode haoNode = new HaoNode(hao, index, index + hao.length());
                this.rootNode.addNode(haoNode);
                this.context.setStartIndex(haoNode.getEndIndex());

                ZParse zParse = new ZParse(haoNode, this.context, haoNode.getEndIndex());
                zParse.parse();
            }
        } else {
            // 中间连号
            int index = this.context.getIndexOf(matchText);
            LianHaoNode haoNode = new LianHaoNode(matchText, index, index + matchText.length());
            this.rootNode.addNode(haoNode);
            this.context.setStartIndex(haoNode.getEndIndex());

            ZParse zParse = new ZParse(haoNode, this.context, haoNode.getEndIndex());
            zParse.parse();
        }
    }

    /**
     * 并列号
     */
    private void parseMultiHao(String matchText) {

        Pattern numberPattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = numberPattern.matcher(matchText);

        while (matcher.find()) {
            int index = this.context.getIndexOf(matcher.group());
            if (-1 == index) {
                continue;
            }

            LianHaoNode haoNode = new LianHaoNode(matcher.group() + "号", index, index + matcher.group().length());
            this.rootNode.addNode(haoNode);
            this.context.setStartIndex(haoNode.getEndIndex());

            ZParse zParse = new ZParse(haoNode, this.context, haoNode.getEndIndex());
            zParse.parse();
        }
    }


    private void parseHao() {
        // 连号
        String text = this.context.getContent().substring(this.context.getStartIndex());
        Pattern lianHaoPattern = Pattern.compile(LIAN_HAO_PATTERN);
        Matcher matcher = lianHaoPattern.matcher(text);
        if (matcher.find()) {
            parseLianHao(text, matcher.group());
            return;
        }

        // 并列号
        Pattern multiHaoPattern = Pattern.compile(MULTI_HAO_PATTERN);
        matcher = multiHaoPattern.matcher(text);
        if (matcher.find()) {
            parseMultiHao(matcher.group());
            return;
        }

        // 默认的号
        Pattern haoPattern = Pattern.compile(DEFAULT_HAO_PATTERN);
        matcher = haoPattern.matcher(text);
        if (matcher.find()) {
            int index = this.context.getIndexOf(matcher.group());
            HaoNode haoNode = new HaoNode(matcher.group(), index, index + matcher.group(0).length());
            this.rootNode.addNode(haoNode);
            this.context.setStartIndex(haoNode.getEndIndex());
            ZParse zParse = new ZParse(haoNode, this.context, haoNode.getEndIndex());
            zParse.parse();
            return;
        }
    }

    private void parseNong() {
        Pattern nongPattern = Pattern.compile(DEFAULT_NONG_PATTERN);
        Matcher matcher = nongPattern.matcher(this.context.getContent());
        if (matcher.find()) {
            int index = this.context.getIndexOf(matcher.group(0));
            NongNode nongNode = new NongNode(matcher.group(0), index, index + matcher.group(0).length());
            this.rootNode.addNode(nongNode);
            this.rootNode = nongNode;
            this.context.setStartIndex(nongNode.getEndIndex());
        }
    }
}
