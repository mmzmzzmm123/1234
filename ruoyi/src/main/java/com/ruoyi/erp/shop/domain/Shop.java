package com.ruoyi.erp.shop.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excels;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysRole;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 电商店铺对象 erp_shop
 * 
 * @author ruoyi
 * @date 2020-06-26
 */
public class Shop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    private Long adminId;

    /** 平台类型 */
    @Excel(name = "平台类型")
    private Long shopTypeId;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private String shopName;

    /** 店铺配置信息:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}] */
    @Excel(name = "店铺配置信息")
    private String shopData;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyDate;

    /** 授权时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "授权时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date authDate;

    /** 平台类型 */
    @Excels({
            @Excel(name = "平台类型", targetAttr = "name", type = Excel.Type.EXPORT)
    })
    private ShopType shopType;

    /** 平台类型对象 */
    private List<ShopType> shopTypes;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAdminId(Long adminId) 
    {
        this.adminId = adminId;
    }

    public Long getAdminId() 
    {
        return adminId;
    }
    public void setShopTypeId(Long shopTypeId) 
    {
        this.shopTypeId = shopTypeId;
    }

    public Long getShopTypeId() 
    {
        return shopTypeId;
    }
    public void setShopName(String shopName) 
    {
        this.shopName = shopName;
    }

    public String getShopName() 
    {
        return shopName;
    }
    public void setShopData(String shopData) 
    {
        this.shopData = shopData;
    }

    public String getShopData() 
    {
        return shopData;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }
    public void setModifyDate(Date modifyDate) 
    {
        this.modifyDate = modifyDate;
    }

    public Date getModifyDate() 
    {
        return modifyDate;
    }

    public void setAuthDate(Date authDate)
    {
        this.authDate = authDate;
    }
    public Date getAuthDate()
    {
        return authDate;
    }

    public ShopType getShopType()
    {
        return shopType;
    }

    public void setShopType(ShopType shopType)
    {
        this.shopType = shopType;
    }


    public List<ShopType> getShopTypes()
    {
        return shopTypes;
    }

    public void setShopTypes(List<ShopType> shopTypes)
    {
        this.shopTypes = shopTypes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("adminId", getAdminId())
            .append("shopTypeId", getShopTypeId())
            .append("shopName", getShopName())
            .append("shopData", getShopData())
            .append("createDate", getCreateDate())
            .append("modifyDate", getModifyDate())
            .append("authDate", getAuthDate())
            .append("shopType", getShopType())
            .toString();
    }
}
