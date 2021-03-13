package com.gox.basic.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;

/**
 *   对象 node_role
 * 
 * @author gox
 * @date 2021-03-13
 */
public class NodeRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 节点主键 */
    private Long id;

    /** 角色主键 */
    private Long roleid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoleid(Long roleid)
    {
        this.roleid = roleid;
    }

    public Long getRoleid() 
    {
        return roleid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("roleid", getRoleid())
            .toString();
    }
}
