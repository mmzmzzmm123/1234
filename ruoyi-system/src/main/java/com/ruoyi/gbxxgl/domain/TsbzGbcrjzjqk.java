package com.ruoyi.gbxxgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 干部出入境证件情况对象 tsbz_gbcrjzjqk
 * 
 * @author ruoyi
 * @date 2020-12-21
 */
public class TsbzGbcrjzjqk extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 干部ID */
    @Excel(name = "干部ID")
    private Long gbid;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String zjlx;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String zjhm;

    /** 有效期起始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期起始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yxqqsrq;

    /** 有效期终止日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效期终止日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yxqzzrq;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private String isdel;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    private TsbzGbjbqk tsbzGbjbqk;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGbid(Long gbid) 
    {
        this.gbid = gbid;
    }

    public Long getGbid() 
    {
        return gbid;
    }
    public void setZjlx(String zjlx) 
    {
        this.zjlx = zjlx;
    }

    public String getZjlx() 
    {
        return zjlx;
    }
    public void setZjhm(String zjhm) 
    {
        this.zjhm = zjhm;
    }

    public String getZjhm() 
    {
        return zjhm;
    }
    public void setYxqqsrq(Date yxqqsrq) 
    {
        this.yxqqsrq = yxqqsrq;
    }

    public Date getYxqqsrq() 
    {
        return yxqqsrq;
    }
    public void setYxqzzrq(Date yxqzzrq) 
    {
        this.yxqzzrq = yxqzzrq;
    }

    public Date getYxqzzrq() 
    {
        return yxqzzrq;
    }
    public void setIsdel(String isdel) 
    {
        this.isdel = isdel;
    }

    public String getIsdel() 
    {
        return isdel;
    }
    public void setCreateUser(Long createUser) 
    {
        this.createUser = createUser;
    }

    public Long getCreateUser() 
    {
        return createUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gbid", getGbid())
            .append("zjlx", getZjlx())
            .append("zjhm", getZjhm())
            .append("yxqqsrq", getYxqqsrq())
            .append("yxqzzrq", getYxqzzrq())
            .append("isdel", getIsdel())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("tsbzGbjbqk", getTsbzGbjbqk())
            .toString();
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public TsbzGbjbqk getTsbzGbjbqk() {
        return tsbzGbjbqk;
    }

    public void setTsbzGbjbqk(TsbzGbjbqk tsbzGbjbqk) {
        this.tsbzGbjbqk = tsbzGbjbqk;
    }
}
