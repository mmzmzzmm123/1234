package com.ruoyi.jxzxkhgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;

/**
 * 考核审核过程对象 tsbz_jzxzkhsh
 *
 * @author ruoyi
 * @date 2020-08-29
 */
public class TsbzJxzxkhsh extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 考核方案
     */
    @Excel(name = "考核方案")
    private Long faid;

    /**
     * 教师
     */
    @Excel(name = "教师")
    private Long jsid;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 校级审核人
     */
    @Excel(name = "校级审核人")
    private Long xjshr;

    /**
     * 校级审核意见
     */
    @Excel(name = "校级审核意见")
    private String xjshyj;

    /**
     * 校级审核建议
     */
    @Excel(name = "校级审核建议")
    private String xjshjy;

    /**
     * 区级审核人
     */
    @Excel(name = "区级审核人")
    private Long qjshr;

    /**
     * 区级审核意见
     */
    @Excel(name = "区级审核意见")
    private String qjshyj;

    /**
     * 区级审核建议
     */
    @Excel(name = "区级审核建议")
    private String qjshjy;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuseird;

    private TsbzJxjsjbxx tsbzJxjsjbxx;

    private TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFaid(Long faid) {
        this.faid = faid;
    }

    public Long getFaid() {
        return faid;
    }

    public void setJsid(Long jsid) {
        this.jsid = jsid;
    }

    public Long getJsid() {
        return jsid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setXjshr(Long xjshr) {
        this.xjshr = xjshr;
    }

    public Long getXjshr() {
        return xjshr;
    }

    public void setXjshyj(String xjshyj) {
        this.xjshyj = xjshyj;
    }

    public String getXjshyj() {
        return xjshyj;
    }

    public void setXjshjy(String xjshjy) {
        this.xjshjy = xjshjy;
    }

    public String getXjshjy() {
        return xjshjy;
    }

    public void setQjshr(Long qjshr) {
        this.qjshr = qjshr;
    }

    public Long getQjshr() {
        return qjshr;
    }

    public void setQjshyj(String qjshyj) {
        this.qjshyj = qjshyj;
    }

    public String getQjshyj() {
        return qjshyj;
    }

    public void setQjshjy(String qjshjy) {
        this.qjshjy = qjshjy;
    }

    public String getQjshjy() {
        return qjshjy;
    }

    public void setCreateuseird(Long createuseird) {
        this.createuseird = createuseird;
    }

    public Long getCreateuseird() {
        return createuseird;
    }

    public TsbzJxjsjbxx getTsbzJxjsjbxx() {
        return tsbzJxjsjbxx;
    }

    public void setTsbzJxjsjbxx(TsbzJxjsjbxx tsbzJxjsjbxx) {
        this.tsbzJxjsjbxx = tsbzJxjsjbxx;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("faid", getFaid())
                .append("jsid", getJsid())
                .append("status", getStatus())
                .append("xjshr", getXjshr())
                .append("xjshyj", getXjshyj())
                .append("xjshjy", getXjshjy())
                .append("qjshr", getQjshr())
                .append("qjshyj", getQjshyj())
                .append("qjshjy", getQjshjy())
                .append("createuseird", getCreateuseird())
                .append("createTime", getCreateTime())
                .append("tsbzJxjsjbxx", getTsbzJxjsjbxx())
                .append("tsbzJxzxkhgcwjsj", getTsbzJxzxkhgcwjsj())
                .toString();
    }

    public TsbzJxzxkhgcwjsj getTsbzJxzxkhgcwjsj() {
        return tsbzJxzxkhgcwjsj;
    }

    public void setTsbzJxzxkhgcwjsj(TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj) {
        this.tsbzJxzxkhgcwjsj = tsbzJxzxkhgcwjsj;
    }
}
