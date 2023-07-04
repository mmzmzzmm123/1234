package com.ruoyi.psychology.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseValObj;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 咨询服务对象 psy_consult_work
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultWorkVO extends BaseValObj implements Serializable
{
    private static final long serialVersionUID = -6845493044334815109L;

    /** 咨询师 */
    private Long consultId;
    private String consultName;

    /** 服务 */
    private Long serveId;
    private String serveName;

    /** 可选班次0全天 1上午 2下午 3晚上 */
    private String type;

    /** 天 */
    private String day;

    /** 周 */
    private String week;

    /** 咨询时段 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;

    /** 咨询时段 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeEnd;

    /** 咨询时长(分钟) */
    private Integer time;

    /** 可预约数量 */
    private Integer num;

    /** 已预约数量 */
    private Integer buyNum;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    private String status;
}
