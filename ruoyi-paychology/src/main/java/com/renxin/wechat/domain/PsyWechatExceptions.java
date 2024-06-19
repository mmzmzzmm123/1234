package com.renxin.wechat.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.renxin.common.annotation.Excel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信异常对象 psy_wechat_exceptions
 * 
 * @author renxin
 * @date 2023-08-08
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_wechat_exceptions")
public class PsyWechatExceptions implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private Date createTime;

    private Date updateTime;

    /** 错误码 */
    @Excel(name = "错误码")
    private String errcode;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String errmsg;

    /** 回复数据 */
    @Excel(name = "回复数据")
    private String data;

    @Excel(name = "备注")
    private String remark;

}
