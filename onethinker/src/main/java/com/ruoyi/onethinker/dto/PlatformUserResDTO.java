package com.ruoyi.onethinker.dto;

import com.ruoyi.onethinker.domain.PlatformUserDetail;
import org.springframework.util.ObjectUtils;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:28
 */
public class PlatformUserResDTO {
    private String token;

    private String nickName;

    private String avatarUrl;

    private String openId;

    public static PlatformUserResDTO foramtResponse(String token, PlatformUserDetail platformUserDetail) {
        PlatformUserResDTO platformUserResDTO = new PlatformUserResDTO();
        platformUserResDTO.setToken(token);
        platformUserResDTO.setNickName(platformUserDetail.getNickName());
        platformUserResDTO.setAvatarUrl(platformUserDetail.getAvatarUrl());
        platformUserResDTO.setOpenId(platformUserDetail.getDataId());
        return platformUserResDTO;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
