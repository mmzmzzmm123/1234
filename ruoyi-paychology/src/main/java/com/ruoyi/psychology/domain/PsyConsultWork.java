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
    private Long consultId;

    /** 天 */
    private String day;

    /** 周 */
    private String week;

    /** 咨询时段 */
    private String timeStart;

    /** 初始时间 */
    private String live;

    /** 已预约时间 */
    private String death;

    /** 咨询时段 */
    private String timeEnd;

    /** 状态（0正常 1休息） */
    private String status;

}
