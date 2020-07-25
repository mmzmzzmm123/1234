package com.muster.web.controller.muster.weixin;


/**
 * @ClassName WeiXinUserInfo
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2020/4/10 12:59
 * @Version 1.0
 **/

public class WeiXinUserInfo {

    // 用户的标识
    private String wxOpenid;
    // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
    private int subscribe;
    // 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
    private String subscribeTime;
    // 昵称
    private String nickName;
    // 用户的性别（1是男性，2是女性，0是未知）
    private int sex;
    // 用户所在国家
    private String country;
    // 用户所在省份
    private String province;
    // 用户所在城市
    private String city;
    // 用户的语言，简体中文为zh_CN
    private String language;
    // 用户头像
    private String headImgUrl;

    //用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的
    private String unionId;

    //错误码
    private int errcode;
    //错误信息
    private String errmsg;

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeiXinUserInfo{");
        sb.append("wxOpenid='").append(wxOpenid).append('\'');
        sb.append(", subscribe=").append(subscribe);
        sb.append(", subscribeTime='").append(subscribeTime).append('\'');
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", sex=").append(sex);
        sb.append(", country='").append(country).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", headImgUrl='").append(headImgUrl).append('\'');
        sb.append(", unionId='").append(unionId).append('\'');
        sb.append(", errcode=").append(errcode);
        sb.append(", errmsg='").append(errmsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
