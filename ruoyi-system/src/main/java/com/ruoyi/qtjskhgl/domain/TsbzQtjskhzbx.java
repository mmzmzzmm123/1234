package com.ruoyi.qtjskhgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 群体教师考核指标项对象 tsbz_qtjskhzbx
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
public class TsbzQtjskhzbx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 所属考核方案 */
    @Excel(name = "所属考核方案")
    private Long faid;

    /** 考核模块
            字典项 */
    @Excel(name = "考核模块")
    private String khmk;

    /** 考核内容 */
    @Excel(name = "考核内容")
    private String khnr;

    /** 提交数量 */
    @Excel(name = "提交数量")
    private Long tjsl;

    /** 关键字段 */
    @Excel(name = "关键字段")
    private String gjzd;

    /** 同步来源
            字典项所属系统 */
    @Excel(name = "同步来源")
    private String tbly;

    /** 接口标记预留 */
    @Excel(name = "接口标记预留")
    private String jkbj;

    /** 执行开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "执行开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date starttime;

    /** 执行截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "执行截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endtime;

    /** 执行任务预留 */
    @Excel(name = "执行任务预留")
    private String zxrw;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuserid;

    /** 预留1 */
    @Excel(name = "预留1")
    private String yly;

    /** 预留2 */
    @Excel(name = "预留2")
    private String yle;

    /** 考核类型：必做;二选一;选择性项目 */
    @Excel(name = "考核类型：必做;二选一;选择性项目")
    private String khlx;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFaid(Long faid) 
    {
        this.faid = faid;
    }

    public Long getFaid() 
    {
        return faid;
    }
    public void setKhmk(String khmk) 
    {
        this.khmk = khmk;
    }

    public String getKhmk() 
    {
        return khmk;
    }
    public void setKhnr(String khnr) 
    {
        this.khnr = khnr;
    }

    public String getKhnr() 
    {
        return khnr;
    }
    public void setTjsl(Long tjsl) 
    {
        this.tjsl = tjsl;
    }

    public Long getTjsl() 
    {
        return tjsl;
    }
    public void setGjzd(String gjzd) 
    {
        this.gjzd = gjzd;
    }

    public String getGjzd() 
    {
        return gjzd;
    }
    public void setTbly(String tbly) 
    {
        this.tbly = tbly;
    }

    public String getTbly() 
    {
        return tbly;
    }
    public void setJkbj(String jkbj) 
    {
        this.jkbj = jkbj;
    }

    public String getJkbj() 
    {
        return jkbj;
    }
    public void setStarttime(Date starttime) 
    {
        this.starttime = starttime;
    }

    public Date getStarttime() 
    {
        return starttime;
    }
    public void setEndtime(Date endtime) 
    {
        this.endtime = endtime;
    }

    public Date getEndtime() 
    {
        return endtime;
    }
    public void setZxrw(String zxrw) 
    {
        this.zxrw = zxrw;
    }

    public String getZxrw() 
    {
        return zxrw;
    }
    public void setCreateuserid(Long createuserid) 
    {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() 
    {
        return createuserid;
    }
    public void setYly(String yly) 
    {
        this.yly = yly;
    }

    public String getYly() 
    {
        return yly;
    }
    public void setYle(String yle) 
    {
        this.yle = yle;
    }

    public String getYle() 
    {
        return yle;
    }
    public void setKhlx(String khlx) 
    {
        this.khlx = khlx;
    }

    public String getKhlx() 
    {
        return khlx;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("faid", getFaid())
            .append("khmk", getKhmk())
            .append("khnr", getKhnr())
            .append("tjsl", getTjsl())
            .append("gjzd", getGjzd())
            .append("tbly", getTbly())
            .append("jkbj", getJkbj())
            .append("starttime", getStarttime())
            .append("endtime", getEndtime())
            .append("zxrw", getZxrw())
            .append("createuserid", getCreateuserid())
            .append("createTime", getCreateTime())
            .append("yly", getYly())
            .append("yle", getYle())
            .append("khlx", getKhlx())
            .toString();
    }
}
