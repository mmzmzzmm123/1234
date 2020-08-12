package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 文件管理对象 by_files
 *
 * @author tsbz
 * @date 2020-08-12
 */
public class ByFiles extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String name;

    /**
     * 文件类型
     */
    @Excel(name = "文件类型")
    private String filetype;

    /**
     * 文件所属类型
     */
    @Excel(name = "文件所属类型")
    private String type;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String fileurl;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("filetype", getFiletype())
                .append("type", getType())
                .append("fileurl", getFileurl())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
