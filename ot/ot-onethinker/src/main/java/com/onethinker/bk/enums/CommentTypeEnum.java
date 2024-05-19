package com.onethinker.bk.enums;

import java.util.Objects;

/**
 * @author : yangyouqi
 * @date : 2024/1/18 17:19
 */
public enum CommentTypeEnum {

    /**
     * 文章评论
     */
    COMMENT_TYPE_ARTICLE("article", "文章评论"),
    /**
     * 树洞留言
     */
    COMMENT_TYPE_MESSAGE("message", "树洞留言"),
    /**
     * 表白墙留言
     */
    COMMENT_TYPE_LOVE("love", "表白墙留言");

    String code;
    String msg;

    CommentTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CommentTypeEnum getSysConfigKeyEnumByCode(String code) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
