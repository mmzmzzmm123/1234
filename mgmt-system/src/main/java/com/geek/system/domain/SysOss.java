package com.geek.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.geek.common.annotation.Excel;
import com.geek.common.core.domain.BaseEntity;

/**
 * 文件上传对象 sys_oss
 * 
 * @author xuek
 * @date 2023-04-01
 */
public class SysOss extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** URL地址 */
    @Excel(name = "URL地址")
    private String url;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("url", getUrl())
            .append("createDate", getCreateDate())
            .toString();
    }
}
