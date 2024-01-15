package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/15/015 14:50
 */
@Data
@ApiModel("群修改分组DTO")
public class ChangeGroupClusterDTO extends GroupClusterIdDTO {

    @ApiModelProperty(value = "群id集合")
    private List<String> groupIds;
}
