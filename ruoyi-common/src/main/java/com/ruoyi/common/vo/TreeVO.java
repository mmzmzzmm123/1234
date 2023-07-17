package com.ruoyi.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class TreeVO {

    private Long key;

    private String value;

    private String label;

    private Boolean flag = false;

    private List<TreeVO> children;

}
