package com.renxin.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 督导成员对象 psy_consultant_supervision_member
 * 
 * @author renxin
 * @date 2024-06-26
 */
@NoArgsConstructor
@TableName("psy_consultant_supervision_member")
public class PsyConsultantSupervisionMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 团体成员主键 */
    private Long id;

    /** 督导团员ID、个体督导ID、个体体验ID */
    @Excel(name = "督导团员ID、个体督导ID、个体体验ID")
    private Long supervisionId;

    /** 1:团督  2.个体督导  3个体体验 */
    @Excel(name = "1:团督  2.个体督导  3个体体验")
    private String supervisionType;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderNo;

    /** 成员ID(咨询师ID） */
    @Excel(name = "成员ID(咨询师ID）")
    private String memberId;

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
    public void setSupervisionId(Long supervisionId) 
    {
        this.supervisionId = supervisionId;
    }

    public Long getSupervisionId() 
    {
        return supervisionId;
    }
    public void setSupervisionType(String supervisionType) 
    {
        this.supervisionType = supervisionType;
    }

    public String getSupervisionType() 
    {
        return supervisionType;
    }
    public void setOrderNo(Long orderNo) 
    {
        this.orderNo = orderNo;
    }

    public Long getOrderNo() 
    {
        return orderNo;
    }
    public void setMemberId(String memberId) 
    {
        this.memberId = memberId;
    }

    public String getMemberId() 
    {
        return memberId;
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
            .append("supervisionId", getSupervisionId())
            .append("supervisionType", getSupervisionType())
            .append("orderNo", getOrderNo())
            .append("memberId", getMemberId())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
