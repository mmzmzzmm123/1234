package com.ruoyi.contest.domain;

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
 * @date 2023-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_teacher")
public class Teacher extends BaseEntity
{
    /** 教师ID */
    private Long teacherId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 职称 */
    @Excel(name = "职称")
    private String professional;

    /** 职务 */
    @Excel(name = "职务")
    private String post;

    /** 研究方向 */
    @Excel(name = "研究方向")
    private String research;

    /** 简介 */
    @Excel(name = "简介")
    private String brief;

    /** 状态 */
    @Excel(name = "状态")
    private String delFlag;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("teacherId", getTeacherId())
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
