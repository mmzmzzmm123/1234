package com.renxin.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 咨询师成长套餐对象 psy_consultant_package
 * 
 * @author renxin
 * @date 2024-06-26
 */
@NoArgsConstructor
@TableName("psy_consultant_package")
public class PsyConsultantPackage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 套餐主键 */
    private Long packageId;

    /** 套餐名称 */
    @Excel(name = "套餐名称")
    private String productName;

    /** 套餐产品图片地址 */
    @Excel(name = "套餐产品图片地址")
    private String productPicUrl;

    /** 详情图片地址 */
    @Excel(name = "详情图片地址")
    private String detailPicUrl;

    /** 1团督 2 个督 3体验  4课程 */
    @Excel(name = "1团督 2 个督 3体验  4课程")
    private String cycleType;

    /** 套餐次数 */
    @Excel(name = "套餐次数")
    private Long cycleNumber;

    /** 0:失效 1:有效 */
    @Excel(name = "0:失效 1:有效")
    private String status;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    public void setPackageId(Long packageId) 
    {
        this.packageId = packageId;
    }

    public Long getPackageId() 
    {
        return packageId;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setProductPicUrl(String productPicUrl) 
    {
        this.productPicUrl = productPicUrl;
    }

    public String getProductPicUrl() 
    {
        return productPicUrl;
    }
    public void setDetailPicUrl(String detailPicUrl) 
    {
        this.detailPicUrl = detailPicUrl;
    }

    public String getDetailPicUrl() 
    {
        return detailPicUrl;
    }
    public void setCycleType(String cycleType) 
    {
        this.cycleType = cycleType;
    }

    public String getCycleType() 
    {
        return cycleType;
    }
    public void setCycleNumber(Long cycleNumber) 
    {
        this.cycleNumber = cycleNumber;
    }

    public Long getCycleNumber() 
    {
        return cycleNumber;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("packageId", getPackageId())
            .append("productName", getProductName())
            .append("productPicUrl", getProductPicUrl())
            .append("detailPicUrl", getDetailPicUrl())
            .append("cycleType", getCycleType())
            .append("cycleNumber", getCycleNumber())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
