package com.ruoyi.contest.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 子竞赛信息对象 t_sub_contest
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sub_contest")
public class SubContest extends BaseEntity
{
    /** 子竞赛ID */
    @TableId(type = IdType.AUTO)
    private Long subContestId;

    /** 竞赛ID */
    @Excel(name = "竞赛ID")
    private Long contestId;

    /** 竞赛名称 */
    @Excel(name = "竞赛名称")
    private String name;

    /** 竞赛通知网址 */
    @Excel(name = "竞赛通知网址")
    private String url;

    /** 附件 */
    @Excel(name = "附件")
    private String attachmentsUrl;

    /** 年度 */
    @Excel(name = "年度")
    private Long year;

    /** 等级 */
    @Excel(name = "等级")
    private String rank;

    /** 竞赛日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "竞赛日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date competitionDate;

    /** 报名费用 */
    @Excel(name = "报名费用")
    private Long fee;

    /** 状态 */
    @Excel(name = "状态")
    private String delFlag;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("subContestId", getSubContestId())
            .append("contestId", getContestId())
            .append("name", getName())
            .append("url", getUrl())
            .append("attachmentsUrl", getAttachmentsUrl())
            .append("year", getYear())
            .append("rank", getRank())
            .append("competitionDate", getCompetitionDate())
            .append("fee", getFee())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
