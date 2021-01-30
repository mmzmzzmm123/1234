package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 幼儿园机构对象 by_school
 * 
 * @author tsbz
 * @date 2020-04-08
 */



public class BySchool extends BaseEntity
{
    private static final long serialVersionUID = 1L;



    /** 幼儿园ID */
    private Long id;

//    学校代码（与dept相关联）
    private String xxdm;

    /** 机构名称 */
    @Excel(name = "机构名称")
    private String schoolName;

    /** 幼儿园简称 */
    @Excel(name = "幼儿园简称")
    private String nameShort;

    /** 幼儿园类型1、独立2、集团3、集团下属 */
    @Excel(name = "幼儿园类型1、独立2、集团3、集团下属")
    private String type;

    /** 集团下属有parentid */
    @Excel(name = "集团下属有parentid")
    private Long parentId;

    /** 所在省 */
    @Excel(name = "所在省")
    private String province;

    /** 所在省 */
    @Excel(name = "所在省")
    private String provincename;

    /** 所在城市 */
    @Excel(name = "所在城市")
    private String regionid;

    /** 所在城市 */
    @Excel(name = "所在城市")
    private String regionname;

    /** 所在区 */
    @Excel(name = "所在区")
    private String area;

    /** 所在区 */
    @Excel(name = "所在区")
    private String areaname;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    /** 联系人 */
    @Excel(name = "联系人")
    private String mastername;

    /** 电话 */
    @Excel(name = "电话")
    private String tel;

    /** 紧急联系人 */
    @Excel(name = "紧急联系人")
    private String emMan;

    /** 紧急联系电话 */
    @Excel(name = "紧急联系电话")
    private String emTel;

    /** 状态（0正常，1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常，1停用")
    private String status;

    /** 幼儿园规模 */
    @Excel(name = "幼儿园规模")
    private String scale;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long createUser;

    /** 最后审核人ID */
    @Excel(name = "最后审核人ID")
    private Long approvalUser;

    /** 审核时间 */
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approvalTime;

    /** 幼儿园位置 */
    @Excel(name = "幼儿园位置")
    private String location;

    /** 园所会员性质 */
    @Excel(name = "园所会员性质")
    private String isDemonstr;

    /** 营业执照图片 */
    @Excel(name = "营业执照图片")
    private String businesslicenseimg;

    /** 家长手册发布状态（0取消，1发布） */
    @Excel(name = "家长手册发布状态", readConverterExp = "0=取消，1发布")
    private Long openBook;

    /** 幼儿园缴费状态（0未缴费，1已缴费，2已到期） */
    @Excel(name = "幼儿园缴费状态", readConverterExp = "0=未缴费，1已缴费，2已到期")
    private String feeStatus;

    /** 开通截至日期 */
    @Excel(name = "开通截至日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date openDeadline;

    /** 当前学年 */
    @Excel(name = "当前学年")
    private String dqxn;

    /** 当前学期 */
    @Excel(name = "当前学期")
    private String dqxq;




    /** 删除标志（0代表存在 2代表删除） */
    private String isDel;

    //部门
    private SysDept dept;

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSchoolName(String schoolName) 
    {
        this.schoolName = schoolName;
    }

    public String getSchoolName() 
    {
        return schoolName;
    }
    public void setNameShort(String nameShort) 
    {
        this.nameShort = nameShort;
    }

    public String getNameShort() 
    {
        return nameShort;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setProvincename(String provincename) 
    {
        this.provincename = provincename;
    }

    public String getProvincename() 
    {
        return provincename;
    }
    public void setRegionid(String regionid) 
    {
        this.regionid = regionid;
    }

    public String getRegionid() 
    {
        return regionid;
    }
    public void setRegionname(String regionname) 
    {
        this.regionname = regionname;
    }

    public String getRegionname() 
    {
        return regionname;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setAreaname(String areaname) 
    {
        this.areaname = areaname;
    }

    public String getAreaname() 
    {
        return areaname;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setMastername(String mastername) 
    {
        this.mastername = mastername;
    }

    public String getMastername() 
    {
        return mastername;
    }
    public void setTel(String tel) 
    {
        this.tel = tel;
    }

    public String getTel() 
    {
        return tel;
    }
    public void setEmMan(String emMan) 
    {
        this.emMan = emMan;
    }

    public String getEmMan() 
    {
        return emMan;
    }
    public void setEmTel(String emTel) 
    {
        this.emTel = emTel;
    }

    public String getEmTel() 
    {
        return emTel;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setScale(String scale)
    {
        this.scale = scale;
    }

    public String getScale()
    {
        return scale;
    }
    public void setCreateUser(Long createUser) 
    {
        this.createUser = createUser;
    }

    public Long getCreateUser() 
    {
        return createUser;
    }
    public void setApprovalUser(Long approvalUser) 
    {
        this.approvalUser = approvalUser;
    }

    public Long getApprovalUser() 
    {
        return approvalUser;
    }
    public void setApprovalTime(Date approvalTime) 
    {
        this.approvalTime = approvalTime;
    }

    public Date getApprovalTime() 
    {
        return approvalTime;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setIsDemonstr(String isDemonstr)
    {
        this.isDemonstr = isDemonstr;
    }

    public String getIsDemonstr()
    {
        return isDemonstr;
    }
    public void setBusinesslicenseimg(String businesslicenseimg) 
    {
        this.businesslicenseimg = businesslicenseimg;
    }

    public String getBusinesslicenseimg() 
    {
        return businesslicenseimg;
    }
    public void setOpenBook(Long openBook) 
    {
        this.openBook = openBook;
    }

    public Long getOpenBook() 
    {
        return openBook;
    }
    public void setFeeStatus(String feeStatus)
    {
        this.feeStatus = feeStatus;
    }

    public String getFeeStatus()
    {
        return feeStatus;
    }
    public void setOpenDeadline(Date openDeadline) 
    {
        this.openDeadline = openDeadline;
    }

    public Date getOpenDeadline() 
    {
        return openDeadline;
    }
    public void setDqxn(String dqxn) 
    {
        this.dqxn = dqxn;
    }

    public String getDqxn() 
    {
        return dqxn;
    }
    public void setDqxq(String dqxq) 
    {
        this.dqxq = dqxq;
    }

    public String getDqxq() 
    {
        return dqxq;
    }

    public String getXxdm() {
        return xxdm;
    }

    public void setXxdm(String xxdm) {
        this.xxdm = xxdm;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("xxdm",getXxdm())
            .append("schoolName", getSchoolName())
            .append("nameShort", getNameShort())
            .append("type", getType())
            .append("parentId", getParentId())
            .append("province", getProvince())
            .append("provincename", getProvincename())
            .append("regionid", getRegionid())
            .append("regionname", getRegionname())
            .append("area", getArea())
            .append("areaname", getAreaname())
            .append("address", getAddress())
            .append("mastername", getMastername())
            .append("tel", getTel())
            .append("emMan", getEmMan())
            .append("emTel", getEmTel())
            .append("status", getStatus())
            .append("scale", getScale())
            .append("createTime", getCreateTime())
            .append("createUser", getCreateUser())
            .append("approvalUser", getApprovalUser())
            .append("approvalTime", getApprovalTime())
            .append("remark", getRemark())
            .append("location", getLocation())
            .append("isDemonstr", getIsDemonstr())
            .append("businesslicenseimg", getBusinesslicenseimg())
            .append("openBook", getOpenBook())
            .append("feeStatus", getFeeStatus())
            .append("openDeadline", getOpenDeadline())
            .append("dqxn", getDqxn())
            .append("dqxq", getDqxq())
            .append("dept", getDept())
            .append("isDel",getIsDel())
            .toString();
    }


}
