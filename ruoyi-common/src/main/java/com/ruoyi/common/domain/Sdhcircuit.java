package com.ruoyi.common.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 电路信息对象 sdhcircuit
 *
 * @author ruoyi
 * @date 2023-12-23
 */
public class Sdhcircuit extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 城市;
     */
    @Excel(name = "城市;")
    private String circity;

    /**
     * 运行商;
     */
    @Excel(name = "运行商;")
    private String ciroperator;

    /**
     * 项目名称;
     */
    @Excel(name = "项目名称;")
    private String cirproject;

    /**
     * 电路名称;
     */
    @Excel(name = "电路名称;")
    private String cirname;

    /**
     * 电路编号;
     */
    @Excel(name = "电路编号;")
    private String circode;

    /**
     * 电路类型;
     */
    @Excel(name = "电路类型;")
    private String cirtype;

    /**
     * 带宽;
     */
    @Excel(name = "带宽;")
    private String cirbandwidth;

    /**
     * 电路信息;
     */
    @Excel(name = "电路信息;")
    private String cirinfo;

    /**
     * 网管信息;
     */
    @Excel(name = "网管信息;")
    private String manageinfo;

    /**
     * A端信息;
     */
    @Excel(name = "A端信息;")
    private String ainfo;

    /**
     * A端地址;
     */
    @Excel(name = "A端地址;")
    private String aaddress;

    /**
     * Z端信息;
     */
    @Excel(name = "Z端信息;")
    private String zinfo;

    /**
     * Z端地址;
     */
    @Excel(name = "Z端地址;")
    private String zaddress;

    /**
     * 项目经理;
     */
    @Excel(name = "项目经理;")
    private String projectname;

    /**
     * 电路状态;
     */
    @Excel(name = "电路状态;")
    private String status;

    /**
     * 创建人;
     */
    @Excel(name = "创建人;")
    private String operator;

    /**
     * 更新人;
     */
    @Excel(name = "更新人;")
    private String updateoperator;

    /**
     * $column.columnComment
     */
    @Excel(name = "south")
    private String south;
    private Date updatetime;


    public Date getUpdatetime() {
        return updatetime;
    }


    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCircity() {
        return circity;
    }

    public void setCircity(String circity) {
        this.circity = circity;
    }

    public String getCiroperator() {
        return ciroperator;
    }

    public void setCiroperator(String ciroperator) {
        this.ciroperator = ciroperator;
    }

    public String getCirproject() {
        return cirproject;
    }

    public void setCirproject(String cirproject) {
        this.cirproject = cirproject;
    }

    public String getCirname() {
        return cirname;
    }

    public void setCirname(String cirname) {
        this.cirname = cirname;
    }

    public String getCircode() {
        return circode;
    }

    public void setCircode(String circode) {
        this.circode = circode;
    }

    public String getCirtype() {
        return cirtype;
    }

    public void setCirtype(String cirtype) {
        this.cirtype = cirtype;
    }

    public String getCirbandwidth() {
        return cirbandwidth;
    }

    public void setCirbandwidth(String cirbandwidth) {
        this.cirbandwidth = cirbandwidth;
    }

    public String getCirinfo() {
        return cirinfo;
    }

    public void setCirinfo(String cirinfo) {
        this.cirinfo = cirinfo;
    }

    public String getManageinfo() {
        return manageinfo;
    }

    public void setManageinfo(String manageinfo) {
        this.manageinfo = manageinfo;
    }

    public String getAinfo() {
        return ainfo;
    }

    public void setAinfo(String ainfo) {
        this.ainfo = ainfo;
    }

    public String getAaddress() {
        return aaddress;
    }

    public void setAaddress(String aaddress) {
        this.aaddress = aaddress;
    }

    public String getZinfo() {
        return zinfo;
    }

    public void setZinfo(String zinfo) {
        this.zinfo = zinfo;
    }

    public String getZaddress() {
        return zaddress;
    }

    public void setZaddress(String zaddress) {
        this.zaddress = zaddress;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getUpdateoperator() {
        return updateoperator;
    }

    public void setUpdateoperator(String updateoperator) {
        this.updateoperator = updateoperator;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("circity", getCircity())
                .append("ciroperator", getCiroperator())
                .append("cirproject", getCirproject())
                .append("cirname", getCirname())
                .append("circode", getCircode())
                .append("cirtype", getCirtype())
                .append("cirbandwidth", getCirbandwidth())
                .append("cirinfo", getCirinfo())
                .append("manageinfo", getManageinfo())
                .append("ainfo", getAinfo())
                .append("aaddress", getAaddress())
                .append("zinfo", getZinfo())
                .append("zaddress", getZaddress())
                .append("projectname", getProjectname())
                .append("updatetime", getUpdatetime())
                .append("status", getStatus())
                .append("operator", getOperator())
                .append("updateoperator", getUpdateoperator())
                .append("south", getSouth())
                .toString();
    }
}
