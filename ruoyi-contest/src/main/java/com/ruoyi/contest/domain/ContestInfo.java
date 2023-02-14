package com.ruoyi.contest.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 竞赛基础信息对象 t_contest_info
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_contest_info")
public class ContestInfo extends BaseEntity
{
    /** 竞赛ID */
    @TableId(type = IdType.AUTO)
    private Long contestId;

    /** 竞赛名称 */
    @Excel(name = "竞赛名称")
    private String name;

    /** 竞赛官网 */
    @Excel(name = "竞赛官网")
    private String url;

    /** 竞赛简介 */
    @Excel(name = "竞赛简介")
    private String brief;

    /** 属于57项赛事 */
    @Excel(name = "属于57项赛事")
    private String inMinistry;

    /** 57项赛事中的编号 */
    @Excel(name = "57项赛事中的编号")
    private Long ministryNo;

    /** 竞赛类别 */
    @Excel(name = "竞赛类别")
    private String type;

    /** 竞赛等级 */
    @Excel(name = "竞赛等级")
    private String grade;

    /** 竞赛频次 */
    @Excel(name = "竞赛频次")
    private String freq;

    /** 省赛/区域赛 */
    @Excel(name = "省赛/区域赛")
    private String need_fee1;

    /** 国赛/决赛 */
    @Excel(name = "国赛/决赛")
    private String need_fee2;

    /** 状态 */
    @Excel(name = "状态")
    private String delFlag;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("contestId", getContestId())
            .append("name", getName())
            .append("url", getUrl())
            .append("brief", getBrief())
            .append("inMinistry", getInMinistry())
            .append("ministryNo", getMinistryNo())
            .append("type", getType())
            .append("grade", getGrade())
            .append("freq", getFreq())
            .append("need_fee1", getNeed_fee1())
            .append("need_fee2", getNeed_fee2())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
