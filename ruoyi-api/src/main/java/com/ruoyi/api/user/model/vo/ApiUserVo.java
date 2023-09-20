package com.ruoyi.api.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/9/19 17:09
 */
@Data
@Accessors(chain = true)
public class ApiUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("推荐码")
    private Long referralCode;

    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("名称")
    private String nickName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("隐藏等级")
    private String hideLevel;

    @ApiModelProperty("隐藏排名")
    private String hideRanking;

    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;

    @ApiModelProperty("用户等级")
    private ApiUserLevelVo userLevelVo;

    @ApiModelProperty("用户钱包")
    private ApiUserWalletVo userWalletVo;
}
