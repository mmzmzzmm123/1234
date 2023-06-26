package com.ruoyi.psychology.vo;

import com.ruoyi.common.core.domain.BaseValObj;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 咨询服务对象 psy_consult
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultVO extends BaseValObj implements Serializable
{
    private static final long serialVersionUID = 5172540262606370812L;

    /** 分类 */
    private Long catId;

    /** 登录名 */
    private String userName;

    /** 头像 */
    private String avatar;

    /** 用户邮箱 */
    private String email;

    /** 手机号码 */
    private String phonenumber;

    /** 用户性别（0男 1女 2未知） */
    private String sex;

    /** 标签 */
    private String tabs;

    /** 咨询方向 */
    private String way;

    /** 介绍文案 */
    private String info;

    /** 图文详情 */
    private String detail;

    /** 咨询人数 */
    private String workNum;

    /** 服务时长 */
    private Integer workTime;

    /** 从业时间 */
    private Integer workHours;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    private String status;
    
}
