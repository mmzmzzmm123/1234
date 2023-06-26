package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 咨询服务对象 psy_consult_work
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_work")
public class PsyConsultWork extends BasePlusEntity implements Serializable
{
    private static final long serialVersionUID = -6845493044334815109L;

    /** 咨询师 */
//    @Excel(name = "咨询师")
    private Long consultId;

    @Excel(name = "咨询师")
    private String consultName;


    /** 服务 */
//    @Excel(name = "服务")
    private Long serveId;

    /** 服务 */
    @Excel(name = "服务")
    private String serveName;

    /** 可选班次0全天 1上午 2下午 3晚上 */
    @Excel(name = "可选班次0全天 1上午 2下午 3晚上")
    private String type;

    /** 咨询时段 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "咨询时段start", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;

    /** 咨询时段 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "咨询时段end", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date timeEnd;

    /** 咨询时长(分钟) */
    @Excel(name = "咨询时长(分钟)")
    private Integer time;

    /** 可预约数量 */
    @Excel(name = "可预约数量")
    private Integer num;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

}
