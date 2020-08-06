package com.ruoyi.project.tool.address;

/**
 * 地址节点类型
 *
 * @author lihe
 */
public enum AddressNodeType {
    DISTRICT(""),
    REGION(""),
    BLOCK(""),

    NOT_FIND("没有节点"),
    ROAD("路"),
    CNAME("小区"),
    NONG("弄"),
    HAO("号"),
    LIAN_HAO("连号"),
    MULTI_HAO("多号"),
    ZHUANG("幢"),
    ZUO("座"),
    CENG("层"),
    // 地下层、底商
    XIXIA_CENG("地下层"),
    SHI("室");

    private String name;

    private AddressNodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
