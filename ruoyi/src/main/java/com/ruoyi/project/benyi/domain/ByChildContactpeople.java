package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 幼儿紧急情况联系人对象 by_child_contactpeople
 *
 * @author tsbz
 * @date 2020-08-13
 */
public class ByChildContactpeople extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 幼儿id
     */
    @Excel(name = "幼儿id")
    private Long childid;

    /**
     * 父亲姓名
     */
    @Excel(name = "父亲姓名")
    private String fathername;

    /**
     * 父亲联系电话
     */
    @Excel(name = "父亲联系电话")
    private String fphone;

    /**
     * 父亲办公电话
     */
    @Excel(name = "父亲办公电话")
    private String foffphone;

    /**
     * 母亲姓名
     */
    @Excel(name = "母亲姓名")
    private String mothername;

    /**
     * 母亲联系电话
     */
    @Excel(name = "母亲联系电话")
    private String mphone;

    /**
     * 母亲办公电话
     */
    @Excel(name = "母亲办公电话")
    private String moffphone;

    /**
     * 其他紧急联系姓名
     */
    @Excel(name = "其他紧急联系姓名")
    private String grandfathername;

    /**
     * 与幼儿关系
     */
    @Excel(name = "与幼儿关系")
    private String gfgx;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String gfphone;

    /**
     * 办公电话
     */
    @Excel(name = "办公电话")
    private String gfoffphone;

    /**
     * 住址
     */
    @Excel(name = "住址")
    private String gfaddress;

    /**
     * 其他联系人姓名2
     */
    @Excel(name = "其他联系人姓名2")
    private String grandmothername;

    /**
     * 与幼儿关系2
     */
    @Excel(name = "与幼儿关系2")
    private String gmgx;

    /**
     * 联系电话2
     */
    @Excel(name = "联系电话2")
    private String gmphone;

    /**
     * 住址2
     */
    @Excel(name = "住址2")
    private String gmaddress;

    /**
     * 办公电话2
     */
    @Excel(name = "办公电话2")
    private String gmoffphone;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setChildid(Long childid) {
        this.childid = childid;
    }

    public Long getChildid() {
        return childid;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFphone(String fphone) {
        this.fphone = fphone;
    }

    public String getFphone() {
        return fphone;
    }

    public void setFoffphone(String foffphone) {
        this.foffphone = foffphone;
    }

    public String getFoffphone() {
        return foffphone;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMoffphone(String moffphone) {
        this.moffphone = moffphone;
    }

    public String getMoffphone() {
        return moffphone;
    }

    public void setGrandfathername(String grandfathername) {
        this.grandfathername = grandfathername;
    }

    public String getGrandfathername() {
        return grandfathername;
    }

    public void setGfgx(String gfgx) {
        this.gfgx = gfgx;
    }

    public String getGfgx() {
        return gfgx;
    }

    public void setGfphone(String gfphone) {
        this.gfphone = gfphone;
    }

    public String getGfphone() {
        return gfphone;
    }

    public void setGfoffphone(String gfoffphone) {
        this.gfoffphone = gfoffphone;
    }

    public String getGfoffphone() {
        return gfoffphone;
    }

    public void setGfaddress(String gfaddress) {
        this.gfaddress = gfaddress;
    }

    public String getGfaddress() {
        return gfaddress;
    }

    public void setGrandmothername(String grandmothername) {
        this.grandmothername = grandmothername;
    }

    public String getGrandmothername() {
        return grandmothername;
    }

    public void setGmgx(String gmgx) {
        this.gmgx = gmgx;
    }

    public String getGmgx() {
        return gmgx;
    }

    public void setGmphone(String gmphone) {
        this.gmphone = gmphone;
    }

    public String getGmphone() {
        return gmphone;
    }

    public void setGmaddress(String gmaddress) {
        this.gmaddress = gmaddress;
    }

    public String getGmaddress() {
        return gmaddress;
    }

    public void setGmoffphone(String gmoffphone) {
        this.gmoffphone = gmoffphone;
    }

    public String getGmoffphone() {
        return gmoffphone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("childid", getChildid())
                .append("fathername", getFathername())
                .append("fphone", getFphone())
                .append("foffphone", getFoffphone())
                .append("mothername", getMothername())
                .append("mphone", getMphone())
                .append("moffphone", getMoffphone())
                .append("grandfathername", getGrandfathername())
                .append("gfgx", getGfgx())
                .append("gfphone", getGfphone())
                .append("gfoffphone", getGfoffphone())
                .append("gfaddress", getGfaddress())
                .append("grandmothername", getGrandmothername())
                .append("gmgx", getGmgx())
                .append("gmphone", getGmphone())
                .append("gmaddress", getGmaddress())
                .append("gmoffphone", getGmoffphone())
                .append("createTime", getCreateTime())
                .toString();
    }
}
