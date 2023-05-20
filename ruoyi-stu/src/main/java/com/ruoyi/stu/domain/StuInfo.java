package com.ruoyi.stu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息对象 stu_info
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
@Data
public class StuInfo extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long stuId;

    /** 学号 */
    @Excel(name = "学号")
    private String stuNo;

    /** 姓名 */
    @Excel(name = "姓名")
    private String stuName;

    /** 班级 */
    @Excel(name = "班级")
    private String stuCls;

    /** 年级 */
    @Excel(name = "年级")
    private String stuClsYear;

    /** 性别 */
    @Excel(name = "性别")
    private String stuSex;

    /** 学生类型 */
    @Excel(name = "学生类型")
    private String stuType;

    /** 录取专业 */
    @Excel(name = "录取专业")
    private String stuMajor;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String stuAddress;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String stuTel;


}
