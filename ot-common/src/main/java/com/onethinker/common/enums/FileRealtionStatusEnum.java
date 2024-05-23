package com.onethinker.common.enums;

import lombok.Getter;

/**
 * @author yangyouqi
 * @date 2024/5/23
 * 文件状态
 */
@Getter
public enum FileRealtionStatusEnum {

    INIT(0,"初始化"),
    DOING(1,"进行中"),
    SUCCESS(2,"已处理"),
    FAIL(3,"处理失败");

    final Integer code;
    final String msg;

    FileRealtionStatusEnum(Integer name, String msg) {
        this.code = name;
        this.msg = msg;
    }

}
