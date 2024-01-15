package com.ruoyi.system.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/15/015 14:54
 */
@Data
@ApiModel("群类型修改 DTO")
public class GroupTypeSetDTO extends GroupIdsDTO {


    @ApiModelProperty(value = "群类型  10私密群 20公开群")
    private Integer groupType;

}
