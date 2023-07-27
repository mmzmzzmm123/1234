package com.ruoyi.psychology.vo;

import com.ruoyi.common.core.domain.BaseValObj;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
    private String nickName;
    private String userName;

    /** 天 */
    private String day;

    /** 周 */
    private String week;

    /** 咨询时段 */
    private String timeStart;

    /** 咨询时段 */
    private String timeEnd;

    /** 状态（0正常 1休息） */
    private String status;

    private String live;
    private String used;

}
