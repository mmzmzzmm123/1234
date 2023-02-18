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
 * 参赛人员信息对象 t_award_person
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_award_person")
public class AwardPerson extends BaseEntity
{
    /** 参赛人员信息ID */
    @TableId(type = IdType.AUTO)
    private Long awardPersonId;

    /** 获奖ID */
    private Long awardId;

    /** 参赛人员ID */
    @Excel(name = "参赛人员ID")
    private Long personId;

    /** 参赛人员类型：0教师、1学生 */
    @Excel(name = "参赛人员类型：0教师、1学生")
    private String personType;

    /** 排序 */
    @Excel(name = "排序")
    private Long orderNum;

    /** 贡献度 */
    @Excel(name = "贡献度")
    private Long conDegree;

    /** 工作内容 */
    @Excel(name = "工作内容")
    private String workContent;

    /** 状态 */
    @Excel(name = "状态")
    private String delFlag;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("awardPersonId", getAwardPersonId())
            .append("awardId", getAwardId())
            .append("personId", getPersonId())
            .append("personType", getPersonType())
            .append("orderNum", getOrderNum())
            .append("conDegree", getConDegree())
            .append("workContent", getWorkContent())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
