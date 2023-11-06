package com.ruoyi.api.distribution.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.api.user.model.vo.ApiUserVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/11/4 20:11
 */
@ApiModel("api-分销关系")
@Data
@Accessors(chain = true)
public class ApiDistributionRelationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("层级")
    private Long level;

    @ApiModelProperty("子级")
    private Long cId;

    @ApiModelProperty("子级用户信息")
    private ApiUserVo childUserVo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
