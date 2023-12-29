package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jing.Zhang
 */
@Data
@ApiModel("文件地址URL")
public class PhoneNumberFileUrlVO {

    @ApiModelProperty("文件地址")
    private String filePathUrl;

}
