package com.gox.basic.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 权限管理对象 rights_management
 * 
 * @author gox
 * @date 2020-12-29
 */
public class RightsManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long metadataId;

    /** 知识产权说明 */
    @Excel(name = "知识产权说明")
    private String intellectualPropertyStatement;

    /** 授权对象 */
    @Excel(name = "授权对象")
    private String authorizedAgent;

    /** 控制标识 */
    @Excel(name = "控制标识")
    private String permissionAssignment;

    /** 控制标识 */
    @Excel(name = "控制标识")
    private String controlIdentifier;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMetadataId(Long metadataId) 
    {
        this.metadataId = metadataId;
    }

    public Long getMetadataId() 
    {
        return metadataId;
    }
    public void setIntellectualPropertyStatement(String intellectualPropertyStatement) 
    {
        this.intellectualPropertyStatement = intellectualPropertyStatement;
    }

    public String getIntellectualPropertyStatement() 
    {
        return intellectualPropertyStatement;
    }
    public void setAuthorizedAgent(String authorizedAgent) 
    {
        this.authorizedAgent = authorizedAgent;
    }

    public String getAuthorizedAgent() 
    {
        return authorizedAgent;
    }
    public void setPermissionAssignment(String permissionAssignment) 
    {
        this.permissionAssignment = permissionAssignment;
    }

    public String getPermissionAssignment() 
    {
        return permissionAssignment;
    }
    public void setControlIdentifier(String controlIdentifier) 
    {
        this.controlIdentifier = controlIdentifier;
    }

    public String getControlIdentifier() 
    {
        return controlIdentifier;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("metadataId", getMetadataId())
            .append("intellectualPropertyStatement", getIntellectualPropertyStatement())
            .append("authorizedAgent", getAuthorizedAgent())
            .append("permissionAssignment", getPermissionAssignment())
            .append("controlIdentifier", getControlIdentifier())
            .toString();
    }
}
