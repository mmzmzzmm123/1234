package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategoryType {
    PULL_PEOPLE(0, "拉人进群"),
    MULTI_GROUP_TRADING(1, "多群交易"),
    GROUP_PRODUCTION(2, "群发"),
    CROWD_PACK(3, "人群包"),
    ELABORATE_CONVERSATION(4, "精聊")
    ;

    int id;
    String name;
}
