package com.renxin.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.renxin.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 咨询服务对象 psy_consult_work
 *
 * @author renxin
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

    /** 咨询时段 凌晨,上午,下午,晚上 */
    private String times;

    /** 初始时间 */
    private String live;

    /** 已预约时间 */
    private String used;

    /** 咨询时段 */
    private String timeEnd;

    /** 状态（0正常 1休息） */
    private String status;

    /** 咨询类型 1 咨询 2倾听 3督导 */
    private String consultType;

}
