package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

@Data
public class CountryBusinessEstimateInput {

    private long priorityOp;

    private List<String> robotsCountryCode;

    private String vcToCountryCode;

}