package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 咨询服务对象 psy_consult_serve
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_serve")
public class PsyConsultServe implements Serializable
{
    private static final long serialVersionUID = 5551542030169381349L;

    /** 服务 */
    private Long serveId;

    /** 咨询师 */
    private Long consultId;

    /** 销量 */
    private Integer num;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    private String status;
}
