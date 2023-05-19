package com.ruoyi.stu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 提交材料参数对象 stu_material
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
public class StuMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 材料名 */
    @Excel(name = "材料名")
    private String name;

    /** 提交状态 */
    @Excel(name = "提交状态")
    private Long flag;

    /** 提交地址 */
    @Excel(name = "提交地址")
    private String url;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setFlag(Long flag) 
    {
        this.flag = flag;
    }

    public Long getFlag() 
    {
        return flag;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("flag", getFlag())
            .append("url", getUrl())
            .toString();
    }
}
