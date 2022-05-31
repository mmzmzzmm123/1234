package com.ruoyi.system.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author renjf
 * @date 2022年05月26日 16:13
 */
public class ComUserRegister {
    // 法人类型(企业法人: c01 社会组织法人:c02 事业单位法人 c03 个体工商户:c04 )
    @JsonProperty(value="LEGALPERSON_TYPE")  // 前端json转class
    @JSONField(name ="LEGALPERSON_TYPE") // class转json使用
    private String LEGALPERSON_TYPE;

    // 手机号
    @JsonProperty(value="USER_MOBILE")
    @JSONField(name ="USER_MOBILE")
    private String USER_MOBILE;

    // 用户id
    @JsonProperty(value="USER_ID")
    @JSONField(name ="USER_ID")
    private String USER_ID;

    // 用户密码，密文形式
    @JsonProperty(value="USER_PASS")
    @JSONField(name ="USER_PASS")
    private String USER_PASS;

    // 认证级别(0:未认证 1:初级 2:高级)
    @JsonProperty(value="USER_AUTHLEVEL")
    @JSONField(name ="USER_AUTHLEVEL")
    private Long USER_AUTHLEVEL;

    // 用户类型(1:个人用户 2:法人用户)
    @JsonProperty(value="USER_TYPE")
    @JSONField(name ="USER_TYPE")
    private String USER_TYPE;

    // 法人身份证
    @JsonProperty(value="LEGALPERSON_IDCARD")
    @JSONField(name ="LEGALPERSON_IDCARD")
    private String LEGALPERSON_IDCARD;

    // 注册用户的证件类型，可选值如下:居民身份证:111,普通护照:414, 台湾居民来往大陆通行证:511, 港澳居民来往内地通行证: 516, 往来港澳通行证：513, 往来台湾通行证:517
    @JsonProperty(value="CARD_TYPE")
    @JSONField(name ="CARD_TYPE")
    private String CARD_TYPE;

    //法定代表人
    @JsonProperty(value="LEGALPERSON_NAME")
    @JSONField(name ="LEGALPERSON_NAME")
    private String LEGALPERSON_NAME;

    //经办人姓名
    @JsonProperty(value="LEGALPERSON_JBRNAME")
    @JSONField(name ="LEGALPERSON_JBRNAME")
    private String LEGALPERSON_JBRNAME;

    //用户姓名或单位名称
    @JsonProperty(value="USER_NAME")
    @JSONField(name ="USER_NAME")
    private String USER_NAME;

    //用户登录凭证
    @JsonProperty(value="USER_TOKEN")
    @JSONField(name ="USER_TOKEN")
    private String USER_TOKEN;

    //用户头像的绝对路径
    @JsonProperty(value="USER_PHOTOURL")
    @JSONField(name ="USER_PHOTOURL")
    private String USER_PHOTOURL;

    //证件号码或者统一社会信用代码
    @JsonProperty(value="USER_IDCARD")
    @JSONField(name ="USER_IDCARD")
    private String USER_IDCARD;

    //经办人身份证
    @JsonProperty(value="LEGALPERSON_JBRIDCARD")
    @JSONField(name ="LEGALPERSON_JBRIDCARD")
    private String LEGALPERSON_JBRIDCARD;

    //ukey 唯一标识号
    @JsonProperty(value="LEGAL_KEYID")
    @JSONField(name ="LEGAL_KEYID")
    private String LEGAL_KEYID;

    @Override
    public String toString() {
        return "{" +
                "LEGALPERSON_TYPE:'" + LEGALPERSON_TYPE + '\'' +
                ", USER_MOBILE:'" + USER_MOBILE + '\'' +
                ", USER_ID:'" + USER_ID + '\'' +
                ", USER_PASS:'" + USER_PASS + '\'' +
                ", USER_AUTHLEVEL=" + USER_AUTHLEVEL +
                ", USER_TYPE:'" + USER_TYPE + '\'' +
                ", LEGALPERSON_IDCARD:'" + LEGALPERSON_IDCARD + '\'' +
                ", CARD_TYPE:'" + CARD_TYPE + '\'' +
                ", LEGALPERSON_NAME:'" + LEGALPERSON_NAME + '\'' +
                ", LEGALPERSON_JBRNAME:'" + LEGALPERSON_JBRNAME + '\'' +
                ", USER_NAME:'" + USER_NAME + '\'' +
                ", USER_TOKEN:'" + USER_TOKEN + '\'' +
                ", USER_PHOTOURL:'" + USER_PHOTOURL + '\'' +
                ", USER_IDCARD:'" + USER_IDCARD + '\'' +
                ", LEGALPERSON_JBRIDCARD:'" + LEGALPERSON_JBRIDCARD + '\'' +
                ", LEGAL_KEYID:'" + LEGAL_KEYID + '\'' +
                '}';
    }

    public String getLEGALPERSON_TYPE() {
        return LEGALPERSON_TYPE;
    }

    public void setLEGALPERSON_TYPE(String LEGALPERSON_TYPE) {
        this.LEGALPERSON_TYPE = LEGALPERSON_TYPE;
    }

    public String getUSER_MOBILE() {
        return USER_MOBILE;
    }

    public void setUSER_MOBILE(String USER_MOBILE) {
        this.USER_MOBILE = USER_MOBILE;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_PASS() {
        return USER_PASS;
    }

    public void setUSER_PASS(String USER_PASS) {
        this.USER_PASS = USER_PASS;
    }

    public Long getUSER_AUTHLEVEL() {
        return USER_AUTHLEVEL;
    }

    public void setUSER_AUTHLEVEL(Long USER_AUTHLEVEL) {
        this.USER_AUTHLEVEL = USER_AUTHLEVEL;
    }

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    public String getLEGALPERSON_IDCARD() {
        return LEGALPERSON_IDCARD;
    }

    public void setLEGALPERSON_IDCARD(String LEGALPERSON_IDCARD) {
        this.LEGALPERSON_IDCARD = LEGALPERSON_IDCARD;
    }

    public String getCARD_TYPE() {
        return CARD_TYPE;
    }

    public void setCARD_TYPE(String CARD_TYPE) {
        this.CARD_TYPE = CARD_TYPE;
    }

    public String getLEGALPERSON_NAME() {
        return LEGALPERSON_NAME;
    }

    public void setLEGALPERSON_NAME(String LEGALPERSON_NAME) {
        this.LEGALPERSON_NAME = LEGALPERSON_NAME;
    }

    public String getLEGALPERSON_JBRNAME() {
        return LEGALPERSON_JBRNAME;
    }

    public void setLEGALPERSON_JBRNAME(String LEGALPERSON_JBRNAME) {
        this.LEGALPERSON_JBRNAME = LEGALPERSON_JBRNAME;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getUSER_TOKEN() {
        return USER_TOKEN;
    }

    public void setUSER_TOKEN(String USER_TOKEN) {
        this.USER_TOKEN = USER_TOKEN;
    }

    public String getUSER_PHOTOURL() {
        return USER_PHOTOURL;
    }

    public void setUSER_PHOTOURL(String USER_PHOTOURL) {
        this.USER_PHOTOURL = USER_PHOTOURL;
    }

    public String getUSER_IDCARD() {
        return USER_IDCARD;
    }

    public void setUSER_IDCARD(String USER_IDCARD) {
        this.USER_IDCARD = USER_IDCARD;
    }

    public String getLEGALPERSON_JBRIDCARD() {
        return LEGALPERSON_JBRIDCARD;
    }

    public void setLEGALPERSON_JBRIDCARD(String LEGALPERSON_JBRIDCARD) {
        this.LEGALPERSON_JBRIDCARD = LEGALPERSON_JBRIDCARD;
    }

    public String getLEGAL_KEYID() {
        return LEGAL_KEYID;
    }

    public void setLEGAL_KEYID(String LEGAL_KEYID) {
        this.LEGAL_KEYID = LEGAL_KEYID;
    }
}
