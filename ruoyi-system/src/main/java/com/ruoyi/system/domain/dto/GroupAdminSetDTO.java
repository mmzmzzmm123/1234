package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/15/015 15:18
 */
@Data
@ApiModel("群内管理员设置DTO")
public class GroupAdminSetDTO  {

    @ApiModelProperty(value = "名称")
    private String groupId;

    @ApiModelProperty(value = "成员编号集合")
    private List<String> memberIds;


}
