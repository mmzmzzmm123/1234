package com.renxin.psychology.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 咨询配置对象
 * 
 * @author renxin
 * @date 2023-06-16
 */
@Data
public class PsyConsultConfigByGroupVO implements Serializable
{
    /** 标题 */
    private String title;

    private Boolean flag = false;

    private List<PsyConsultConfigVO> child;

}
