package com.ruoyi.office.domain;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 设备列表对象 t_equipment
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
@Data
public class TEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 发生地? */
    @Excel(name = "名称")
    private String name;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String equipType;

    /** 标题 */
    @Excel(name = "标题")
    private String online;

    /** 开关状态 */
    @Excel(name = "开关状态")
    private String onOff;

    /** 设备控制 */
    @Excel(name = "设备控制")
    private String equipControl;

    /** 最近打开时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recentOpenTime;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

}
