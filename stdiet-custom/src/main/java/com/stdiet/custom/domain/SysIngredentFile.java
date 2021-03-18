package com.stdiet.custom.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SysIngredentFile {

    Long id;

    Long igdId;

    String url;

    String previewUrl;

    String name;

    Integer delFlag;

    String createBy;

    Date createTime;

    String updateBy;

    Date updateTime;
}
