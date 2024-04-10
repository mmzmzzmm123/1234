package com.baoli.sysmanage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 银行管理对象 bl_sys_bank
 * 
 * @author niujs
 * @date 2024-04-08
 */
public class BlSysBank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 省份 */
    @Excel(name = "省份")
    private String provinceId;

    /** 城市 */
    @Excel(name = "城市")
    private String cityId;

    /** 省份 */
    @Excel(name = "省份")
    private String provinceName;

    /** 城市 */
    @Excel(name = "城市")
    private String cityName;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 编码 */
    @Excel(name = "编码")
    private String code;

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    /** 所属商业银行 */
    @Excel(name = "所属商业银行")
    private String agency;

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    /** 银行类型 */
    @Excel(name = "银行类型")
    private String bankType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProvinceId(String provinceId) 
    {
        this.provinceId = provinceId;
    }

    public String getProvinceId() 
    {
        return provinceId;
    }
    public void setCityId(String cityId) 
    {
        this.cityId = cityId;
    }

    public String getCityId() 
    {
        return cityId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCode()
    {
        return code;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("provinceId", getProvinceId())
            .append("cityId", getCityId())
            .append("name", getName())
            .append("code", getCode())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
