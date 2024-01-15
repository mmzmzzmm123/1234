package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 演员包装
 */
@Data
public class Performer {
    @ApiModelProperty("发言人昵称")
    private String robotNickname;

    @ApiModelProperty(value = "发言顺序")
    private Integer messageSort;

    @ApiModelProperty(value = "头像")
    private String pic;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "姓氏")
    private String surname;

    @ApiModelProperty(value = "是否管理员：0-否 1-是")
    private Integer isAdmin;
}
