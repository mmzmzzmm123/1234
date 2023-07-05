package com.ruoyi.stu.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class StuInfo extends BaseEntity {
    /** 主键id */
    private Long stuId;

    /** 学号 */
    @Excel(name = "学号")
    private String stuNo;

    /** 姓名 */
    @Excel(name = "姓名")
    private String stuName;

    /** 班级id */
    @Excel(name = "班级")
    private Integer clsId;

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

//    private StuCls stuCls;
}
