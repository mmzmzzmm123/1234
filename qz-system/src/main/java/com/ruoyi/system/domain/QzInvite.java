package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 邀请对象 qz_invite
 * 
 * @author zlx
 * @date 2021-08-10
 */
public class QzInvite extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 圈子id */
    @Excel(name = "圈子id")
    private Long qzId;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date exp;

    /** 邀请用户 */
    @Excel(name = "邀请用户")
    private Long 
createUserId;

    /** 受邀用户 */
    @Excel(name = "受邀用户")
    private Long 
receiveUserId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 扩展字段1 */
    private String ext1;

    /** 扩展字段2 */
    private String ext2;

    /** 扩展字段3 */
    private String ext3;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setQzId(Long qzId) 
    {
        this.qzId = qzId;
    }

    public Long getQzId() 
    {
        return qzId;
    }
    public void setExp(Date exp) 
    {
        this.exp = exp;
    }

    public Date getExp() 
    {
        return exp;
    }
    public void setcreateUserId(Long
createUserId) 
    {
        this.
createUserId = 
createUserId;
    }

    public Long getcreateUserId()
    {
        return 
createUserId;
    }
    public void setreceiveUserId(Long
receiveUserId) 
    {
        this.
receiveUserId = 
receiveUserId;
    }

    public Long getreceiveUserId()
    {
        return 
receiveUserId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setExt1(String ext1) 
    {
        this.ext1 = ext1;
    }

    public String getExt1() 
    {
        return ext1;
    }
    public void setExt2(String ext2) 
    {
        this.ext2 = ext2;
    }

    public String getExt2() 
    {
        return ext2;
    }
    public void setExt3(String ext3) 
    {
        this.ext3 = ext3;
    }

    public String getExt3() 
    {
        return ext3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("qzId", getQzId())
            .append("exp", getExp())
            .append("createUserId", getcreateUserId())
            .append("receiveUserId", getreceiveUserId())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("ext1", getExt1())
            .append("ext2", getExt2())
            .append("ext3", getExt3())
            .toString();
    }
}
