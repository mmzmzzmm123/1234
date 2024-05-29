package com.onethinker.common.enums;

import lombok.Getter;

/**
 * 文件关联类型
 * @author yangyouqi
 * @date 2024/5/15
 */
@Getter
public enum FileRelationTypeEnum {
    /**
     * 头像
     */
    AVATAR("头像"),
    /**
     * 佰星-客户信息文件上传导入
     */
    BX_CUSTOMER("客户信息"),

    /**
     * 后台上传
     */
    SYS_ADMIN("后台管理页面")
    ;

    private final String desc;

    FileRelationTypeEnum(String desc) {
        this.desc = desc;
    }

}
