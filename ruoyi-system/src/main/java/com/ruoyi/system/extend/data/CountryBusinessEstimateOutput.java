package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

@Data
public class CountryBusinessEstimateOutput {

    private long releaseRobotsNum;

    private List<ReleaseList> releaseRobotsNumList;

    private long releaseTimes;

    private List<ReleaseList> releaseTimesList;

    private long robotsNum;

    private long useTimes;

    private long usersNum;

    @Data
    public static class ReleaseList {
        private long num;
        private long time;
    }
}


