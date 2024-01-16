package com.onethinker.user.dto;

import com.onethinker.user.domain.PlatformUserDetail;
import lombok.Data;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:28
 */
@Data
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
}
