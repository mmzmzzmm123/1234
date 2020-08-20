package com.ruoyi.web.controller.common;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SchoolCommonController {
    public boolean isStringEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    // 生成UUID
    public String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
