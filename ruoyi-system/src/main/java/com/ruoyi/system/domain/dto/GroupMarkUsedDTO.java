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
@ApiModel("群使用状态变动 DTO")
public class GroupMarkUsedDTO extends GroupIdsDTO {

    @ApiModelProperty(value = "是否使用 0-未使用 1-已使用")
    private Integer groupUsed;
}
