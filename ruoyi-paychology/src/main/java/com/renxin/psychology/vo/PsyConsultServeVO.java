package com.renxin.psychology.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 咨询服务对象 psy_consult_serve
 *
 * @author renxin
 * @date 2023-06-25
 */
@Data
public class PsyConsultServeVO implements Serializable
{
    private static final long serialVersionUID = 5551542030169381349L;

    /** 服务 */
    private Long serveId;

    /** 咨询师 */
    private Long consultId;

    /** 销量 */
    private Integer num;

    private String nickName;
    private String userName;
    private String status;
}
