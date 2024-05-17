package com.jjpt.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 试题管理对象 el_qu
 * 
 * @author 卢亚峰
 * @date 2024-05-17
 */
public class ElQu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 题目ID */
    private String id;

    /** 题目类型 */
    @Excel(name = "题目类型")
    private Long quType;

    /** 1普通,2较难 */
    @Excel(name = "1普通,2较难")
    private Long level;

    /** 题目图片 */
    @Excel(name = "题目图片")
    private String image;

    /** 题目内容 */
    @Excel(name = "题目内容")
    private String content;

    /** 整题解析 */
    @Excel(name = "整题解析")
    private String analysis;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setQuType(Long quType) 
    {
        this.quType = quType;
    }

    public Long getQuType() 
    {
        return quType;
    }
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getLevel() 
    {
        return level;
    }
    public void setImage(String image) 
    {
        this.image = image;
    }

    public String getImage() 
    {
        return image;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setAnalysis(String analysis) 
    {
        this.analysis = analysis;
    }

    public String getAnalysis() 
    {
        return analysis;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("quType", getQuType())
            .append("level", getLevel())
            .append("image", getImage())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("analysis", getAnalysis())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .toString();
    }
}
