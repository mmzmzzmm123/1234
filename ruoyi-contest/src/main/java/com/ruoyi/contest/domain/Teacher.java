package com.ruoyi.contest.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师信息对象 t_teacher
 *
 * @author ruoyi
 * @date 2023-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_teacher")
public class Teacher extends BaseEntity
{
    /** 教师ID */
    @TableId(type = IdType.AUTO)
    private Long teacherId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 所在部门ID */
    @Excel(name = "所在部门ID")
    private Long deptId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 性别：0男、1女 */
    @Excel(name = "性别：0男、1女")
    private String gender;

    /** 职称：0教授、1副教授、2讲师、3助教、4高级实验师 */
    @Excel(name = "职称：0教授、1副教授、2讲师、3助教、4高级实验师")
    private String professional;

    /** 职务：0院长、1书记、2副院长、3副书记、4教师、5行政人员 */
    @Excel(name = "职务：0院长、1书记、2副院长、3副书记、4教师、5行政人员")
    private String post;

    /** 研究方向 */
    @Excel(name = "研究方向")
    private String research;

    /** 简介 */
    private String brief;

    /** 删除标志（0代表正常 1代表停用） */
    @Excel(name = "删除标志", readConverterExp = "0=代表正常,1=代表停用")
    private String delFlag;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("teacherId", getTeacherId())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("name", getName())
            .append("gender", getGender())
            .append("professional", getProfessional())
            .append("post", getPost())
            .append("research", getResearch())
            .append("brief", getBrief())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
