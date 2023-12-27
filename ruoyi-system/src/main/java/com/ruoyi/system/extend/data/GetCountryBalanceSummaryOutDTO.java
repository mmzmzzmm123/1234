package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetCountryBalanceSummaryOutDTO {
    @ApiModelProperty(value = "手机余量集合")
    private List<Summary> phoneSummary;
    @ApiModelProperty(value = "用户名余量集合")
    private List<Summary> usernameSummary;

    @Data
    public static class Summary {
        @ApiModelProperty(value = "余量")
        private Integer balance;
        @ApiModelProperty(value = "国家code")
        private String countryCode;
        @ApiModelProperty(value = "国家名称")
        private String countryName;
    }
}
