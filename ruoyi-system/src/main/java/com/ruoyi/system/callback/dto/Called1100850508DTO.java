package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class Called1100850508DTO {

    private Integer num;

    @JSONField(name = "origin_img_url")
    private String  originImgUrl;

    @JSONField(name = "discrete_list")
    private List<String> discreteList;
}
