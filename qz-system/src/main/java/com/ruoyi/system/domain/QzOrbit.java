package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 圈子对象 qz_orbit
 * 
 * @author zlx
 * @date 2021-08-10
 */
public class QzOrbit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 圈子ID */
    private Long quanId;

    /** 名称 */
    @Excel(name = "名称")
    private String quanName;

    /** 简介 */
    @Excel(name = "简介")
    private String quanDesc;

    /** 类型 */
    @Excel(name = "类型")
    private String quanType;

    /** 封面 */
    @Excel(name = "封面")
    private String picUrl;

    /** 最大成员数 */
    @Excel(name = "最大成员数")
    private Long membershipNum;

    /** 允许成员加入 */
    @Excel(name = "允许成员加入")
    private String memJoinFlag;

    /** 可分享 */
    @Excel(name = "可分享")
    private String shareFlag;

    /** 可浏览 */
    @Excel(name = "可浏览")
    private String showViewsFlag;

    /** 成员加入通知 */
    @Excel(name = "成员加入通知")
    private String memJoinNotify;

    /** 圈子变更发送通知 */
    @Excel(name = "圈子变更发送通知")
    private String descChangNotify;

    /** 新碎片通知 */
    @Excel(name = "新碎片通知")
    private String fragmentNotify;

    /** 碎片置顶通知 */
    @Excel(name = "碎片置顶通知")
    private String fragmentTopNotify;

    /** 圈子状态（0正常 1停用） */
    @Excel(name = "圈子状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 扩展字段1 */
    @Excel(name = "扩展字段1")
    private String ext1;

    /** 扩展字段2 */
    @Excel(name = "扩展字段2")
    private String ext2;

    /** 扩展字段3 */
    @Excel(name = "扩展字段3")
    private String ext3;

    public void setQuanId(Long quanId) 
    {
        this.quanId = quanId;
    }

    public Long getQuanId() 
    {
        return quanId;
    }
    public void setQuanName(String quanName) 
    {
        this.quanName = quanName;
    }

    public String getQuanName() 
    {
        return quanName;
    }
    public void setQuanDesc(String quanDesc) 
    {
        this.quanDesc = quanDesc;
    }

    public String getQuanDesc() 
    {
        return quanDesc;
    }
    public void setQuanType(String quanType) 
    {
        this.quanType = quanType;
    }

    public String getQuanType() 
    {
        return quanType;
    }
    public void setPicUrl(String picUrl) 
    {
        this.picUrl = picUrl;
    }

    public String getPicUrl() 
    {
        return picUrl;
    }
    public void setMembershipNum(Long membershipNum) 
    {
        this.membershipNum = membershipNum;
    }

    public Long getMembershipNum() 
    {
        return membershipNum;
    }
    public void setMemJoinFlag(String memJoinFlag) 
    {
        this.memJoinFlag = memJoinFlag;
    }

    public String getMemJoinFlag() 
    {
        return memJoinFlag;
    }
    public void setShareFlag(String shareFlag) 
    {
        this.shareFlag = shareFlag;
    }

    public String getShareFlag() 
    {
        return shareFlag;
    }
    public void setShowViewsFlag(String showViewsFlag) 
    {
        this.showViewsFlag = showViewsFlag;
    }

    public String getShowViewsFlag() 
    {
        return showViewsFlag;
    }
    public void setMemJoinNotify(String memJoinNotify) 
    {
        this.memJoinNotify = memJoinNotify;
    }

    public String getMemJoinNotify() 
    {
        return memJoinNotify;
    }
    public void setDescChangNotify(String descChangNotify) 
    {
        this.descChangNotify = descChangNotify;
    }

    public String getDescChangNotify() 
    {
        return descChangNotify;
    }
    public void setFragmentNotify(String fragmentNotify) 
    {
        this.fragmentNotify = fragmentNotify;
    }

    public String getFragmentNotify() 
    {
        return fragmentNotify;
    }
    public void setFragmentTopNotify(String fragmentTopNotify) 
    {
        this.fragmentTopNotify = fragmentTopNotify;
    }

    public String getFragmentTopNotify() 
    {
        return fragmentTopNotify;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setExt1(String ext1) 
    {
        this.ext1 = ext1;
    }

    public String getExt1() 
    {
        return ext1;
    }
    public void setExt2(String ext2) 
    {
        this.ext2 = ext2;
    }

    public String getExt2() 
    {
        return ext2;
    }
    public void setExt3(String ext3) 
    {
        this.ext3 = ext3;
    }

    public String getExt3() 
    {
        return ext3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("quanId", getQuanId())
            .append("quanName", getQuanName())
            .append("quanDesc", getQuanDesc())
            .append("quanType", getQuanType())
            .append("picUrl", getPicUrl())
            .append("membershipNum", getMembershipNum())
            .append("memJoinFlag", getMemJoinFlag())
            .append("shareFlag", getShareFlag())
            .append("showViewsFlag", getShowViewsFlag())
            .append("memJoinNotify", getMemJoinNotify())
            .append("descChangNotify", getDescChangNotify())
            .append("fragmentNotify", getFragmentNotify())
            .append("fragmentTopNotify", getFragmentTopNotify())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("ext1", getExt1())
            .append("ext2", getExt2())
            .append("ext3", getExt3())
            .toString();
    }
}
