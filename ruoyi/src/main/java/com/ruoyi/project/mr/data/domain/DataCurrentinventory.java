package com.ruoyi.project.mr.data.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 当前库存存储对象 data_currentinventory
 *
 * @author mr
 * @date 2020-01-14
 */
public class DataCurrentinventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** UUID */
    private String id;

    /** 记账日期 */
    @Excel(name = "记账日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date accountdate;

    /** 单号 */
    @Excel(name = "单号")
    private String billnumber;

    /** 公司UUID */
    @Excel(name = "公司UUID")
    private String companyuuid;

    /** 公司代码 */
    @Excel(name = "公司代码")
    private String companyid;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String companyname;

    /** 工厂UUID */
    @Excel(name = "工厂UUID")
    private String factoryuuid;

    /** 工厂代码 */
    @Excel(name = "工厂代码")
    private String factoryid;

    /** 工厂名称 */
    @Excel(name = "工厂名称")
    private String factoryname;

    /** 仓库UUID */
    @Excel(name = "仓库UUID")
    private String warehouseuuid;

    /** 仓库代码 */
    @Excel(name = "仓库代码")
    private String warehouseid;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehousename;

    /** 库位UUID */
    @Excel(name = "库位UUID")
    private String locationuuid;

    /** 库位代码 */
    @Excel(name = "库位代码")
    private String locationid;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String locationname;

    /** 商品UUID */
    @Excel(name = "商品UUID")
    private String gooduuid;

    /** 商品代码 */
    @Excel(name = "商品代码")
    private String goodid;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String goodname;

    /** 计量单位名称 */
    @Excel(name = "计量单位名称")
    private String unitname;

    /** 规格 */
    @Excel(name = "规格")
    private String standard;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Double inamount;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private Double outamount;

    /** 数量 */
    @Excel(name = "数量")
    private Double amount;

    /** 冻结数量 */
    @Excel(name = "冻结数量")
    private Double freezeamount;

    /** 批次管理 */
    @Excel(name = "批次管理")
    private Integer isbatch;

    /** 批次单号 */
    @Excel(name = "批次单号")
    private String batchbillnumber;

    /** 专供号 */
    @Excel(name = "专供号")
    private String specialnumber;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 单据类型 */
    @Excel(name = "单据类型")
    private String billtype;

    /** 创建人代码 */
    @Excel(name = "创建人代码")
    private String createuserid;

    /** 创建人名称 */
    @Excel(name = "创建人名称")
    private String createusername;

    /** 创建人时间 */
    @Excel(name = "创建人时间")
    private String createtime;

    /** 单据类型名称 */
    @Excel(name = "单据类型名称")
    private String billtypename;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String typename;

    /** 生产日期 */
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productdate;

    /** 订单号 */
    @Excel(name = "订单号")
    private String salebillnumber;

    /** 产地 */
    @Excel(name = "产地")
    private String productionarea;

    /** 版本号 */
    @Excel(name = "版本号")
    private String versionnumber;

    /** 客户UUID */
    @Excel(name = "客户UUID")
    private String customeruuid;

    /** 客户代码 */
    @Excel(name = "客户代码")
    private String customerid;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customername;

    /** 明细UUID */
    @Excel(name = "明细UUID")
    private String detailuuid;

    /** 专供号代码 */
    @Excel(name = "专供号代码")
    private String specialcode;

    /** 单据类型（入库、红冲、退库） */
    @Excel(name = "单据类型", readConverterExp = "入=库、红冲、退库")
    private String billstatus;

    /** 仓储日期 */
    @Excel(name = "仓储日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date storedate;

    /** 数量 */
    @Excel(name = "可用数量")
    private Double availableamount;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setAccountdate(Date accountdate)
    {
        this.accountdate = accountdate;
    }

    public Date getAccountdate()
    {
        return accountdate;
    }
    public void setBillnumber(String billnumber)
    {
        this.billnumber = billnumber;
    }

    public String getBillnumber()
    {
        return billnumber;
    }
    public void setCompanyuuid(String companyuuid)
    {
        this.companyuuid = companyuuid;
    }

    public String getCompanyuuid()
    {
        return companyuuid;
    }
    public void setCompanyid(String companyid)
    {
        this.companyid = companyid;
    }

    public String getCompanyid()
    {
        return companyid;
    }
    public void setCompanyname(String companyname)
    {
        this.companyname = companyname;
    }

    public String getCompanyname()
    {
        return companyname;
    }
    public void setFactoryuuid(String factoryuuid)
    {
        this.factoryuuid = factoryuuid;
    }

    public String getFactoryuuid()
    {
        return factoryuuid;
    }
    public void setFactoryid(String factoryid)
    {
        this.factoryid = factoryid;
    }

    public String getFactoryid()
    {
        return factoryid;
    }
    public void setFactoryname(String factoryname)
    {
        this.factoryname = factoryname;
    }

    public String getFactoryname()
    {
        return factoryname;
    }
    public void setWarehouseuuid(String warehouseuuid)
    {
        this.warehouseuuid = warehouseuuid;
    }

    public String getWarehouseuuid()
    {
        return warehouseuuid;
    }
    public void setWarehouseid(String warehouseid)
    {
        this.warehouseid = warehouseid;
    }

    public String getWarehouseid()
    {
        return warehouseid;
    }
    public void setWarehousename(String warehousename)
    {
        this.warehousename = warehousename;
    }

    public String getWarehousename()
    {
        return warehousename;
    }
    public void setLocationuuid(String locationuuid)
    {
        this.locationuuid = locationuuid;
    }

    public String getLocationuuid()
    {
        return locationuuid;
    }
    public void setLocationid(String locationid)
    {
        this.locationid = locationid;
    }

    public String getLocationid()
    {
        return locationid;
    }
    public void setLocationname(String locationname)
    {
        this.locationname = locationname;
    }

    public String getLocationname()
    {
        return locationname;
    }
    public void setGooduuid(String gooduuid)
    {
        this.gooduuid = gooduuid;
    }

    public String getGooduuid()
    {
        return gooduuid;
    }
    public void setGoodid(String goodid)
    {
        this.goodid = goodid;
    }

    public String getGoodid()
    {
        return goodid;
    }
    public void setGoodname(String goodname)
    {
        this.goodname = goodname;
    }

    public String getGoodname()
    {
        return goodname;
    }
    public void setUnitname(String unitname)
    {
        this.unitname = unitname;
    }

    public String getUnitname()
    {
        return unitname;
    }
    public void setStandard(String standard)
    {
        this.standard = standard;
    }

    public String getStandard()
    {
        return standard;
    }
    public void setInamount(Double inamount)
    {
        this.inamount = inamount;
    }

    public Double getInamount()
    {
        return inamount;
    }
    public void setOutamount(Double outamount)
    {
        this.outamount = outamount;
    }

    public Double getOutamount()
    {
        return outamount;
    }
    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public Double getAmount()
    {
        return amount;
    }
    public void setFreezeamount(Double freezeamount)
    {
        this.freezeamount = freezeamount;
    }

    public Double getFreezeamount()
    {
        return freezeamount;
    }
    public void setIsbatch(Integer isbatch)
    {
        this.isbatch = isbatch;
    }

    public Integer getIsbatch()
    {
        return isbatch;
    }
    public void setBatchbillnumber(String batchbillnumber)
    {
        this.batchbillnumber = batchbillnumber;
    }

    public String getBatchbillnumber()
    {
        return batchbillnumber;
    }
    public void setSpecialnumber(String specialnumber)
    {
        this.specialnumber = specialnumber;
    }

    public String getSpecialnumber()
    {
        return specialnumber;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setBilltype(String billtype)
    {
        this.billtype = billtype;
    }

    public String getBilltype()
    {
        return billtype;
    }
    public void setCreateuserid(String createuserid)
    {
        this.createuserid = createuserid;
    }

    public String getCreateuserid()
    {
        return createuserid;
    }
    public void setCreateusername(String createusername)
    {
        this.createusername = createusername;
    }

    public String getCreateusername()
    {
        return createusername;
    }
    public void setBilltypename(String billtypename)
    {
        this.billtypename = billtypename;
    }

    public String getBilltypename()
    {
        return billtypename;
    }
    public void setTypename(String typename)
    {
        this.typename = typename;
    }

    public String getTypename()
    {
        return typename;
    }
    public void setProductdate(Date productdate)
    {
        this.productdate = productdate;
    }

    public Date getProductdate()
    {
        return productdate;
    }
    public void setSalebillnumber(String salebillnumber)
    {
        this.salebillnumber = salebillnumber;
    }

    public String getSalebillnumber()
    {
        return salebillnumber;
    }
    public void setProductionarea(String productionarea)
    {
        this.productionarea = productionarea;
    }

    public String getProductionarea()
    {
        return productionarea;
    }
    public void setVersionnumber(String versionnumber)
    {
        this.versionnumber = versionnumber;
    }

    public String getVersionnumber()
    {
        return versionnumber;
    }
    public void setCustomeruuid(String customeruuid)
    {
        this.customeruuid = customeruuid;
    }

    public String getCustomeruuid()
    {
        return customeruuid;
    }
    public void setCustomerid(String customerid)
    {
        this.customerid = customerid;
    }

    public String getCustomerid()
    {
        return customerid;
    }
    public void setCustomername(String customername)
    {
        this.customername = customername;
    }

    public String getCustomername()
    {
        return customername;
    }
    public void setDetailuuid(String detailuuid)
    {
        this.detailuuid = detailuuid;
    }

    public String getDetailuuid()
    {
        return detailuuid;
    }
    public void setSpecialcode(String specialcode)
    {
        this.specialcode = specialcode;
    }

    public String getSpecialcode()
    {
        return specialcode;
    }
    public void setBillstatus(String billstatus)
    {
        this.billstatus = billstatus;
    }

    public String getBillstatus()
    {
        return billstatus;
    }
    public void setStoredate(Date storedate)
    {
        this.storedate = storedate;
    }

    public Date getStoredate()
    {
        return storedate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accountdate", getAccountdate())
            .append("billnumber", getBillnumber())
            .append("companyuuid", getCompanyuuid())
            .append("companyid", getCompanyid())
            .append("companyname", getCompanyname())
            .append("factoryuuid", getFactoryuuid())
            .append("factoryid", getFactoryid())
            .append("factoryname", getFactoryname())
            .append("warehouseuuid", getWarehouseuuid())
            .append("warehouseid", getWarehouseid())
            .append("warehousename", getWarehousename())
            .append("locationuuid", getLocationuuid())
            .append("locationid", getLocationid())
            .append("locationname", getLocationname())
            .append("gooduuid", getGooduuid())
            .append("goodid", getGoodid())
            .append("goodname", getGoodname())
            .append("unitname", getUnitname())
            .append("standard", getStandard())
            .append("inamount", getInamount())
            .append("outamount", getOutamount())
            .append("amount", getAmount())
            .append("freezeamount", getFreezeamount())
            .append("isbatch", getIsbatch())
            .append("batchbillnumber", getBatchbillnumber())
            .append("specialnumber", getSpecialnumber())
            .append("type", getType())
            .append("billtype", getBilltype())
            .append("remark", getRemark())
            .append("createuserid", getCreateuserid())
            .append("createusername", getCreateusername())
            .append("createtime", getCreatetime())
            .append("billtypename", getBilltypename())
            .append("typename", getTypename())
            .append("productdate", getProductdate())
            .append("salebillnumber", getSalebillnumber())
            .append("productionarea", getProductionarea())
            .append("versionnumber", getVersionnumber())
            .append("customeruuid", getCustomeruuid())
            .append("customerid", getCustomerid())
            .append("customername", getCustomername())
            .append("detailuuid", getDetailuuid())
            .append("specialcode", getSpecialcode())
            .append("billstatus", getBillstatus())
            .append("storedate", getStoredate())
            .append("availableamount", getAvailableamount())
            .toString();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Double getAvailableamount() {
        return availableamount;
    }

    public void setAvailableamount(Double availableamount) {
        this.availableamount = availableamount;
    }
}
