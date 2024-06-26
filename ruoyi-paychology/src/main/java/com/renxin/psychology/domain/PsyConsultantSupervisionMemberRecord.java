package com.renxin.psychology.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 督导笔记、成员评价 标签评价对象 psy_consultant_supervision_member_record
 * 
 * @author renxin
 * @date 2024-06-26
 */
@NoArgsConstructor
@TableName("psy_consultant_supervision_member_record")
public class PsyConsultantSupervisionMemberRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 团体成员主键 */
    private Long id;

    /** 督导任务ID */
    @Excel(name = "督导任务ID")
    private Long taskId;

    /** 1:团督  2.个体督导  3个体体验 */
    @Excel(name = "1:团督  2.个体督导  3个体体验")
    private String supervisionType;

    /** 督导团员ID、个体督导ID、个体体验ID */
    @Excel(name = "督导团员ID、个体督导ID、个体体验ID")
    private Long supervisionId;

    /** 成员ID 、督导ID */
    @Excel(name = "成员ID 、督导ID")
    private String memberId;

    /** 评价标签 */
    @Excel(name = "评价标签")
    private String evaluateLabel;

    /** 评价分数 */
    @Excel(name = "评价分数")
    private BigDecimal evaluateScore;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setSupervisionType(String supervisionType) 
    {
        this.supervisionType = supervisionType;
    }

    public String getSupervisionType() 
    {
        return supervisionType;
    }
    public void setSupervisionId(Long supervisionId) 
    {
        this.supervisionId = supervisionId;
    }

    public Long getSupervisionId() 
    {
        return supervisionId;
    }
    public void setMemberId(String memberId) 
    {
        this.memberId = memberId;
    }

    public String getMemberId() 
    {
        return memberId;
    }
    public void setEvaluateLabel(String evaluateLabel) 
    {
        this.evaluateLabel = evaluateLabel;
    }

    public String getEvaluateLabel() 
    {
        return evaluateLabel;
    }
    public void setEvaluateScore(BigDecimal evaluateScore) 
    {
        this.evaluateScore = evaluateScore;
    }

    public BigDecimal getEvaluateScore() 
    {
        return evaluateScore;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("supervisionType", getSupervisionType())
            .append("supervisionId", getSupervisionId())
            .append("memberId", getMemberId())
            .append("evaluateLabel", getEvaluateLabel())
            .append("evaluateScore", getEvaluateScore())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
