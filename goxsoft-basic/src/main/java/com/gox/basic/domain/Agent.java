package com.gox.basic.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 机构人员对象 agent
 *
 * @author gox
 * @date 2020-12-29
 */
public class Agent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long agentIdentifier;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long mdId;

    /**
     * 机构人员名称
     */
    @Excel(name = "机构人员名称")
    private String agentName;

    /**
     * 机构人员类型
     */
    @Excel(name = "机构人员类型")
    private String agentType;

    /**
     * 机构人员隶属
     */
    @Excel(name = "机构人员隶属")
    private String agentBelongsTo;

    public void setAgentIdentifier(Long agentIdentifier) {
        this.agentIdentifier = agentIdentifier;
    }

    public Long getAgentIdentifier() {
        return agentIdentifier;
    }

    public void setMdId(Long mdId) {
        this.mdId = mdId;
    }

    public Long getMdId() {
        return mdId;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentBelongsTo(String agentBelongsTo) {
        this.agentBelongsTo = agentBelongsTo;
    }

    public String getAgentBelongsTo() {
        return agentBelongsTo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("agentIdentifier", getAgentIdentifier())
                .append("mdId", getMdId())
                .append("agentName", getAgentName())
                .append("agentType", getAgentType())
                .append("agentBelongsTo", getAgentBelongsTo())
                .toString();
    }
}
