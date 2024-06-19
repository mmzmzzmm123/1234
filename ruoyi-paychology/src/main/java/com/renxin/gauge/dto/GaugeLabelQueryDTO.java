package com.renxin.gauge.dto;

import com.renxin.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @User hogan
 * @Time 2022/10/17 16:55
 * @e-mail hkcugwh@163.com
 **/
@Data
@ApiModel("测评标签分页查询入参")
public class GaugeLabelQueryDTO extends BaseEntity {

    @ApiModelProperty("测评标签(0-精选测评，1-热门推荐)")
    private Integer labelCode;

}
