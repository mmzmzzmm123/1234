package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 咨询服务对象 psy_consult
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult")
public class PsyConsult extends BasePlusEntity implements Serializable
{
    private static final long serialVersionUID = 5172540262606370812L;

//    @TableField(exist = false)
//    private List<PsyConsultServe> serveList;

    /** 分类 */
    @Excel(name = "分类")
    private Long catId;

    /** 登录名 */
    private Long userId;

    /** 登录名 */
    @Excel(name = "登录名")
    private String userName;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 标签 */
    @Excel(name = "标签")
    private String tabs;

    /** 咨询方向 */
    private String way;

    @Excel(name = "咨询方向")
    private String wayStr;

    /** 介绍文案 */
    @Excel(name = "介绍文案")
    private String info;

    /** 图文详情 */
    @Excel(name = "图文详情")
    private String detail;

    /** 咨询人数 */
    @Excel(name = "咨询人数")
    private Integer workNum;

    /** 服务时长 */
    @Excel(name = "服务时长")
    private Integer workTime;

    /** 从业时间 */
    @Excel(name = "从业时间")
    private Integer workHours;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;


    
}
