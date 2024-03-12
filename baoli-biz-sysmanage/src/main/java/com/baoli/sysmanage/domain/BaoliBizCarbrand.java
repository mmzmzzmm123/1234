package com.baoli.sysmanage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 汽车品牌对象 baoli_biz_carbrand
 * 
 * @author niujs
 * @date 2024-03-12
 */
public class BaoliBizCarbrand extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String letter;

    /** 品牌名 */
    @Excel(name = "品牌名")
    private String name;

    /** 品牌Logo图片名 */
    @Excel(name = "品牌Logo图片名")
    private String imgname;

    /** 品牌Logo图片路径 */
    @Excel(name = "品牌Logo图片路径")
    private String imgpath;

    /** 说明 */
    @Excel(name = "说明")
    private String explain;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLetter(String letter) 
    {
        this.letter = letter;
    }

    public String getLetter() 
    {
        return letter;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setImgname(String imgname) 
    {
        this.imgname = imgname;
    }

    public String getImgname() 
    {
        return imgname;
    }
    public void setImgpath(String imgpath) 
    {
        this.imgpath = imgpath;
    }

    public String getImgpath() 
    {
        return imgpath;
    }
    public void setExplain(String explain) 
    {
        this.explain = explain;
    }

    public String getExplain() 
    {
        return explain;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("letter", getLetter())
            .append("name", getName())
            .append("imgname", getImgname())
            .append("imgpath", getImgpath())
            .append("explain", getExplain())
            .toString();
    }
}
