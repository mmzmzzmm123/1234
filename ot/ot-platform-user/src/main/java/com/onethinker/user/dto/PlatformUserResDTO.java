package com.onethinker.user.dto;

import com.onethinker.user.domain.PlatformUser;
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

    private String dataId;

    public static PlatformUserResDTO foramtResponse(String token, PlatformUser platformUser) {
        PlatformUserResDTO platformUserResDTO = new PlatformUserResDTO();
        platformUserResDTO.setToken(token);
        platformUserResDTO.setNickName(platformUser.getNickName());
        platformUserResDTO.setAvatarUrl(platformUser.getAvatarUrl());
        platformUserResDTO.setDataId(platformUser.getDataId());
        return platformUserResDTO;
    }
}
