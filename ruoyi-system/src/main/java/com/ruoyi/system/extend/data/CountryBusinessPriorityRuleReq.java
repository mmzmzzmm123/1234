package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

/**
 * @author Sharly
 */
@Data
public class CountryBusinessPriorityRuleReq {

    private Integer priorityRobots;

    private List<UtTouchCountryData> robotsCountryList;

}
