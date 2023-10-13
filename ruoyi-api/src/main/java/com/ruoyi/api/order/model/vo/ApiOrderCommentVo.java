package com.ruoyi.api.order.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/10/12 23:38
 */
@Data
@Accessors(chain = true)
@ApiModel("api-订单评论实体")
public class ApiOrderCommentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("订单标识")
    private Long orderId;

    @ApiModelProperty("店员标识")
    private Long staffUserId;

    @ApiModelProperty("用户标识")
    private Long userId;

    @ApiModelProperty("用户头像")
    private String userAvatar;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("评论内容")
    private String comment;

    @ApiModelProperty("服务内容")
    private String serviceContent;

    @ApiModelProperty("是否展示")
    private String ifShow;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
