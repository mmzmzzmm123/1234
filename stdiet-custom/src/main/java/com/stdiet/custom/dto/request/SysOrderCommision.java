package com.stdiet.custom.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysOrderCommision implements Serializable {

    private static final long serialVersionUID = 1L;

    //后台用户ID
    private Long userId;

    //岗位ID
    private Long postId;

    //订单审核状态
    private String reviewStatus;

    //订单截止结束时间
    private String endTime;

}
