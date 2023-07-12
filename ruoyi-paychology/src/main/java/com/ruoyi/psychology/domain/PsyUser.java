package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;


/**
 * 用户对象 psy_user
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PsyUser
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Integer id;

    /**  */
    @Excel(name = "用户名")
    private String name;

    /**  */
    @Excel(name = "手机号码")
    private String phone;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    @Excel(name = "用户剩余积分")
    private Integer integral;

    /**  */
    @Excel(name = "微信openId")
    private String wxOpenid;

    /** 创建时间 */
    @Excel(name = "创建时间" ,dateFormat = "yyyy-MM-dd HH:mm:SS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("phone", getPhone())
            .append("avatar", getAvatar())
            .append("status", getStatus())
            .append("wxOpenid", getWxOpenid())
            .append("createTime", getCreateTime())
            .append("integral", getIntegral())
            .toString();
    }


}
