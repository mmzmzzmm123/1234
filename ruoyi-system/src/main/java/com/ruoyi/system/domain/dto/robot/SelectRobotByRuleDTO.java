package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("SelectRobotByRuleDTO")
public class SelectRobotByRuleDTO extends GetRobotDTO{
    @ApiModelProperty(value = "号每日入群数量")
    private Integer oneDayJoinGroupCount;
    @ApiModelProperty(value = "号累计入群数量")
    private Integer totalJoinGroupCount;
    @ApiModelProperty(value = "号每日设管理员数量")
    private Integer oneDaySetAdminCount;
    @ApiModelProperty(value = "号累计设管理员数量")
    private Integer totalSetAdminCount;
    @ApiModelProperty(value = "号带群数")
    private Integer groupCount;
    @ApiModelProperty(value = "是否需要设置管理员,0-否,1-是")
    private Integer isSetAdmin = 0;
    @ApiModelProperty(value = "要排除的ip")
    private List<String> ips;
    @ApiModelProperty(value = "要排除的号")
    private List<String> excludeRobotSerialNos;
    private Integer limit;
    private Integer isLock;

    /**
     * 是否过滤空用户名: 0-否 1-是
     */
    private String filterUserName;
}
