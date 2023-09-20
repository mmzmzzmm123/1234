package com.ruoyi.api.user.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/9/19 17:34
 */
@Data
@Accessors(chain = true)
public class ApiUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    @NotNull(message = "标识不能为空")
    private Long id;

    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("名称")
    private String nickName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("隐藏等级")
    private String hideLevel;

    @ApiModelProperty("隐藏排名")
    private String hideRanking;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;
}
