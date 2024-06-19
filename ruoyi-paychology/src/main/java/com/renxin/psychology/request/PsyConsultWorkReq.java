package com.renxin.psychology.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.renxin.psychology.domain.PsyConsultWork;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 咨询订单对象 psy_consult_order
 * 
 * @author renxin
 * @date 2023-06-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultWorkReq extends PsyConsultWork implements Serializable
{

    private static final long serialVersionUID = 269755586237920868L;

    /** 咨询时段 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDay;

    /** 咨询时段 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDay;

    /** 月份 */
    private String month;

    /** 咨询师 */
    private List<Long> ids;

}
