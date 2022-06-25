package com.ruoyi.udef.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报警对象 df_robot_alarm
 * 
 * @author ruoyi
 * @date 2022-06-25
 */
@Data
@Accessors(chain = true)
public class DfRobotAlarm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 开多编码 */
    @Excel(name = "开多编码")
    private String longCode;

    /** 开空编码 */
    @Excel(name = "开空编码")
    private String shortCode;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 机器人 */
    @Excel(name = "机器人")
    private Long robotId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("longCode", getLongCode())
            .append("shortCode", getShortCode())
            .append("phone", getPhone())
            .append("status", getStatus())
            .append("robotId", getRobotId())
            .toString();
    }
}
