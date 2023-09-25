package com.ruoyi.api.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author LAM
 * @date 2023/9/21 14:12
 */
@Data
@Accessors(chain = true)
public class ApiUserLikeDataVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("收藏员工标识集合")
    private List<Long> likeStaffUserIdList;

    @ApiModelProperty("点赞员工动态标识集合")
    private List<Long> likeStaffTrendsIdList;
}
