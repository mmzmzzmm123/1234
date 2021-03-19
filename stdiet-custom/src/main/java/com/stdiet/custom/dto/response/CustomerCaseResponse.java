package com.stdiet.custom.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CustomerCaseResponse {

    private String id;

    private String name;

    private String keyword;

    private String remark;

    private Boolean wxShow;

    private List<String> fileList;
}
