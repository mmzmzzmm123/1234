package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 首页图片配置对象 t_index_img
 * 
 * @author ruoyi
 * @date 2024-04-24
 */
public class TIndexImg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String imgName;

    private String imgPath;

    private Integer sorted;

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setImgName(String imgName) 
    {
        this.imgName = imgName;
    }

    public String getImgName() 
    {
        return imgName;
    }
    public void setImgPath(String imgPath) 
    {
        this.imgPath = imgPath;
    }

    public String getImgPath() 
    {
        return imgPath;
    }

    @Override
    public String toString() {
        return "TIndexImg{" +
                "id=" + id +
                ", imgName='" + imgName + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", sorted=" + sorted +
                '}';
    }
}
