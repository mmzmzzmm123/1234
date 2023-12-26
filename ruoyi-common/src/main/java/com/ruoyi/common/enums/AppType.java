package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppType {
    TG(1, "TG"),
    LINE(2, "LINE"),
    WS(3, "WS");

    int id;
    String name;
}
