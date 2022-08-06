package com.ruoyi.data.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    /** 用户id */
    private Long id;

    private String userName;

    private String token;
}
