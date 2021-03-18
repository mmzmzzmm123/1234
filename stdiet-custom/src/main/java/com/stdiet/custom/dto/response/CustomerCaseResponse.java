package com.stdiet.custom.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CustomerCaseResponse {

    private Long id;

    private String name;

    private String keyword;

    private String remark;

    private List<String> fileList;
}
