package com.stdiet.custom.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SysWxAdLog {

    private String userId;

    private String ip;

    private String userAgent;

    private String channelId;

    private String wxId;

    private Integer type;

    private Date date;
}
