package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

/**
 * @author Sharly
 */
@Data
public class PriorityTarget {

    private List<TargetList> targetList;

    @Data
    public static class TargetList {

        private String countryCode;

        private String countryName;

        private List<String> priorityRobots;
    }
}
