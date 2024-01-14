package com.ruoyi.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 *  城市对象 city
 *
 * @author ruoyi
 * @date 2024-01-08
 */
public class City extends BaseEntity
        {
private static final long serialVersionUID=1L;

    /** 城市代码 */
    private Long code;

    /** 城市名 */
            @Excel(name = "城市名")
    private String cityName;

    /** 省份 */
            @Excel(name = "省份")
    private String province;

    public void setCode(Long code)
            {
            this.code = code;
            }

    public Long getCode()
            {
            return code;
            }
    public void setCityName(String cityName)
            {
            this.cityName = cityName;
            }

    public String getCityName()
            {
            return cityName;
            }
    public void setProvince(String province)
            {
            this.province = province;
            }

    public String getProvince()
            {
            return province;
            }

@Override
public String toString(){
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("code",getCode())
            .append("cityName",getCityName())
            .append("province",getProvince())
        .toString();
        }
        }
