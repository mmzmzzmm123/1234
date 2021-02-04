package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 微信分配管理对象 sys_wx_distribution
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
@Data
public class SysWxDistribution extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 后台用户id
     */
    private Long userId;

    @Excel(name = "销售")
    private String userName;

    /**
     * 字典表中销售小组对应键值
     */
    private Integer saleGroupId;

    @Excel(name = "销售组别")
    private String saleGroup;

    /**
     * 字典表中账号对应键值
     */
    private Integer accountId;

    @Excel(name = "收款账号")
    private String account;

    /**
     * 微信账号id
     */
    private Long wechatAccount;

    @Excel(name = "微信昵称")
    private String wxNickName;

    @Excel(name = "微信号")
    private String wxAccount;

    /**
     * 微信账号类型，0接粉号 1新号，默认0
     */
    private Integer wechatAccountType;

    /**
     * 删除标识 0未删除 1已删除，默认0
     */
    private Integer delFlag;
}