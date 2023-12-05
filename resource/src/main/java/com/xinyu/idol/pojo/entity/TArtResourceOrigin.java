package com.xinyu.idol.domain;

import com.xinyu.idol.common.annotation.Excel;
import com.xinyu.idol.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 美术资源上传原件对象 t_art_resource_origin
 * 
 * @author liyu
 * @date 2023-10-31
 */
public class TArtResourceOrigin extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "id")
    private String id;

    /** 展示名称 */
    @Excel(name = "展示名称")
    private String displayName;

    /** 替换资源guid */
    @Excel(name = "替换资源guid")
    private String repalceGuid;

    /** 关联guid(选填 */
    @Excel(name = "关联guid(选填")
    private String linkedGuid;

    /** 图标ossKey */
    @Excel(name = "图标ossKey")
    private String iconOsskey;

    /** 原件osskey */
    @Excel(name = "原件osskey")
    private String originFileOsskey;


    private String originOssKeysStr;


    public String getOriginOssKeysStr() {
        return originOssKeysStr;
    }

    public void setOriginOssKeysStr(String originOssKeysStr) {
        this.originOssKeysStr = originOssKeysStr;
    }

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setDisplayName(String displayName) 
    {
        this.displayName = displayName;
    }

    public String getDisplayName() 
    {
        return displayName;
    }
    public void setRepalceGuid(String repalceGuid) 
    {
        this.repalceGuid = repalceGuid;
    }

    public String getRepalceGuid() 
    {
        return repalceGuid;
    }
    public void setLinkedGuid(String linkedGuid) 
    {
        this.linkedGuid = linkedGuid;
    }

    public String getLinkedGuid() 
    {
        return linkedGuid;
    }
    public void setIconOsskey(String iconOsskey) 
    {
        this.iconOsskey = iconOsskey;
    }

    public String getIconOsskey() 
    {
        return iconOsskey;
    }
    public void setOriginFileOsskey(String originFileOsskey) 
    {
        this.originFileOsskey = originFileOsskey;
    }

    public String getOriginFileOsskey() 
    {
        return originFileOsskey;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("displayName", getDisplayName())
            .append("repalceGuid", getRepalceGuid())
            .append("linkedGuid", getLinkedGuid())
            .append("iconOsskey", getIconOsskey())
            .append("originFileOsskey", getOriginFileOsskey())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
