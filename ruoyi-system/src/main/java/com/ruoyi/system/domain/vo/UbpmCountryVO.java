package com.ruoyi.system.domain.vo;

import com.ruoyi.system.domain.Country;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jing.Zhang
 */
@Data
public class UbpmCountryVO {


    @ApiModelProperty(value = "国家代码,多个使用逗号拼接")
    private String countryCode;

    /**
     * 国家
     */
    @ApiModelProperty(value = "国家,多个使用逗号拼接")
    private String countryName;

    /**
     * 手机区号
     */
    @ApiModelProperty(value = "手机区号")
    private String phoneArea;


    public UbpmCountryVO() {

    }

    public UbpmCountryVO init(List<Country> countries) {
        if (CollectionUtils.isEmpty(countries)) {
            return this;
        }

        Country ubpmCountry = countries.get(0);
        this.phoneArea = ubpmCountry.getPhoneArea();
        this.countryName = countries.stream().map(Country::getCountryName).collect(Collectors.joining(","));
        this.countryCode = countries.stream().map(Country::getCountryCode).collect(Collectors.joining(","));
        return this;
    }

}
