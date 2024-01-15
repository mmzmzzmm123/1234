package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/15/015 14:47
 */
@Data
@ApiModel("群信息修改DTO")
public class GroupUpdateInfoDTO {

    @ApiModelProperty(value = "群id集合")
    private List<String> groupIds;

    @ApiModelProperty(value = "图片地址集合")
    private List<String> imageUrls;

    @ApiModelProperty(value = "群名称")
    private String groupName;

    @ApiModelProperty(value = "群名称编号开始")
    private Integer nameStart;

    @ApiModelProperty(value = "群名称编号结束")
    private Integer nameEnd;

}
