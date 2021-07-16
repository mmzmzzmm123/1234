package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 打卡社区点赞记录对象 sys_punch_thumbsup
 *
 * @author xzj
 * @date 2021-07-15
 */
@Data
public class SysPunchThumbsup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 打卡记录ID */
    @Excel(name = "打卡记录ID")
    private Long punchId;

    private String encPunchId;

    /** 客户微信openid */
    @Excel(name = "客户微信openid")
    private String cusOpenid;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;

    //操作标识  true点赞 false取消点赞
    private Boolean thumbsupFlag;
}