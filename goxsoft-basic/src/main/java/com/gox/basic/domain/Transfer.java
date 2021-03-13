package com.gox.basic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import com.gox.common.plugin.AutoId;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 移交表 Transfer
 *
 * @author gox
 * @date 2021-03-05
 */
@Alias(value = "Transfer")
public class Transfer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @AutoId
    private Long id;

    /**
     * 部门ID
     **/
    private Long deptId;

    /**
     * 移交说明
     */
    @Excel(name = "移交说明")
    private String remark;

    /**
     * 移交人
     */
    @Excel(name = "移交人")
    private String transuser;


    /**
     * 移交数量
     */
    @Excel(name = "移交数量")
    private int transcount;


    /**
     * 移交部门
     */
    @Excel(name = "移交部门")
    private String transdep;

    /**
     * 移交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /**
     * 移交状态
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransuser() {
        return transuser;
    }

    public void setTransuser(String transuser) {
        this.transuser = transuser;
    }

    public int getTranscount() {
        return transcount;
    }

    public void setTranscount(int transcount) {
        this.transcount = transcount;
    }

    public String getTransdep() {
        return transdep;
    }

    public void setTransdep(String transdep) {
        this.transdep = transdep;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }
}
