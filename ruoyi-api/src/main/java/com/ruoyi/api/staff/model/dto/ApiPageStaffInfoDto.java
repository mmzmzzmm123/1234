package com.ruoyi.api.staff.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/7 17:44
 */
@ApiModel("api-查询员工信息表单")
@Data
@Accessors(chain = true)
public class ApiPageStaffInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("页数")
    private Integer pageNum;

    @ApiModelProperty("分页数量")
    private Integer pageSize;

    @ApiModelProperty("排序类型 0创建时间 1创建时间 2排序字段倒叙 3排序正序 4等级倒序 5等级正序")
    private String sortType;

    @ApiModelProperty("是否展示置顶 Y是 N否")
    private String showIfTop;

    @ApiModelProperty("性别 0男 1女")
    private String sex;

    @ApiModelProperty("店员等级")
    private String staffLevel;

    @ApiModelProperty("服务标识集合字符串")
    private String serviceIdArrStr;
}
