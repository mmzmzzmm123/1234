package com.stdiet.custom.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SysIngredentFile {

    Long id;

    Long igdId;

    String fileUrl;

    String fileName;

    Integer delFlag;

    String createBy;

    Date createTime;

    String updateBy;

    Date updateTime;
}
