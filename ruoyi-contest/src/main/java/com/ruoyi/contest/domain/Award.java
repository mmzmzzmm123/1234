package com.ruoyi.contest.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 获奖登记对象 t_award
 *
 * @author lsyonlygoddes
 * @date 2023-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_award")
public class Award extends BaseEntity
{
    /** 获奖ID */
    @TableId(type = IdType.AUTO)
    private Long awardId;

    /** 竞赛ID */
    private Long contestId;

    /** 子竞赛ID */
    @Excel(name = "子竞赛ID")
    private Long subContestId;

    /** 获奖日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "获奖日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date awardDate;

    /** 获奖等级 */
    @Excel(name = "获奖等级")
    private String awardGrade;

    /** 支撑材料 */
    @Excel(name = "支撑材料")
    private String attachmentUrl;

    /** 状态 */
    @Excel(name = "状态")
    private String delFlag;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("awardId", getAwardId())
            .append("contestId", getContestId())
            .append("subContestId", getSubContestId())
            .append("awardDate", getAwardDate())
            .append("awardGrade", getAwardGrade())
            .append("attachmentUrl", getAttachmentUrl())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
