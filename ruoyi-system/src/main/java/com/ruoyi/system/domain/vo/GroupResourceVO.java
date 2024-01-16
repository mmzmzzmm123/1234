package com.ruoyi.system.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.GroupInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 朱少波
 * @date 2024/1/16/016 10:38
 */
@Data
@ApiModel("导入群资源VO")
public class GroupResourceVO {
    @Excel(name = "群编号（必填）")
    private String groupSerialNo;

    @Excel(name = "群主机器人编号（必填）")
    private String robotId;

    @Excel(name = "群注册时间")
    private LocalDateTime registrationTime;

}
