package com.ruoyi.psychology.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseValObj;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 咨询服务对象 psy_consult_serve
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultServeVO extends BaseValObj implements Serializable
{

    private static final long serialVersionUID = -6225882703437217633L;

    /** 咨询师 */
    private Long consultId;

    /** 服务名称 */
    private String name;

    /** 服务介绍 */
    private String info;

    /** 价格 */
    private BigDecimal price;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    private String status;
    
}
