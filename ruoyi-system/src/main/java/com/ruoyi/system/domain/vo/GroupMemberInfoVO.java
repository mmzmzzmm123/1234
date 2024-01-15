package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 朱少波
 * @date 2024/1/15/015 9:51
 */
@Data
@ApiModel("群内成员信息VO")
public class GroupMemberInfoVO {

    @ApiModelProperty(value = "名称")
    private String fullName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "成员编号")
    private String memberId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "最后在线时间")
    private LocalDateTime wasOnline;


    @ApiModelProperty(value = "成员身份 0普通 1群主 2管理员")
    private Integer memberType;


    @ApiModelProperty(value = "是否bot 0=否 1-是")
    private Integer bot;
}
