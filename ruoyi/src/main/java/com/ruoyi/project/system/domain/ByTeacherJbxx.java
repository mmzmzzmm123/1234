package com.ruoyi.project.system.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 教师基本信息对象 by_teacher_jbxx
 * 
 * @author tsbz
 * @date 2020-04-21
 */
public class ByTeacherJbxx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标识 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userid;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String zjhm;

    /** 出生日期 */
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date csrq;

    /** 毕业院校 */
    @Excel(name = "毕业院校")
    private String byyx;

    /** 专业 */
    @Excel(name = "专业")
    private String zy;

    /** 学历 */
    @Excel(name = "学历")
    private String xl;

    /** 学位 */
    @Excel(name = "学位")
    private String xw;

    /** 参加工作日期 */
    @Excel(name = "参加工作日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cjgzrq;

    /** 资格证书 */
    @Excel(name = "资格证书")
    private String zgzs;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuserid;

    //创建时间
    @Excel(name = "创建时间")
    private Date createtime;

    /** user对象 */
    @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT)
    private SysUser user;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserid(Long userid) 
    {
        this.userid = userid;
    }

    public Long getUserid() 
    {
        return userid;
    }
    public void setZjhm(String zjhm) 
    {
        this.zjhm = zjhm;
    }

    public String getZjhm() 
    {
        return zjhm;
    }
    public void setCsrq(Date csrq) 
    {
        this.csrq = csrq;
    }

    public Date getCsrq() 
    {
        return csrq;
    }
    public void setByyx(String byyx) 
    {
        this.byyx = byyx;
    }

    public String getByyx() 
    {
        return byyx;
    }
    public void setZy(String zy) 
    {
        this.zy = zy;
    }

    public String getZy() 
    {
        return zy;
    }
    public void setXl(String xl) 
    {
        this.xl = xl;
    }

    public String getXl() 
    {
        return xl;
    }
    public void setXw(String xw) 
    {
        this.xw = xw;
    }

    public String getXw() 
    {
        return xw;
    }
    public void setCjgzrq(Date cjgzrq) 
    {
        this.cjgzrq = cjgzrq;
    }

    public Date getCjgzrq() 
    {
        return cjgzrq;
    }
    public void setZgzs(String zgzs) 
    {
        this.zgzs = zgzs;
    }

    public String getZgzs() 
    {
        return zgzs;
    }
    public void setCreateuserid(Long createuserid) 
    {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() 
    {
        return createuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userid", getUserid())
            .append("zjhm", getZjhm())
            .append("csrq", getCsrq())
            .append("byyx", getByyx())
            .append("zy", getZy())
            .append("xl", getXl())
            .append("xw", getXw())
            .append("cjgzrq", getCjgzrq())
            .append("zgzs", getZgzs())
            .append("createuserid", getCreateuserid())
            .append("createtime", getCreatetime())
            .append("user", getUser())
            .toString();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }


    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }
}
