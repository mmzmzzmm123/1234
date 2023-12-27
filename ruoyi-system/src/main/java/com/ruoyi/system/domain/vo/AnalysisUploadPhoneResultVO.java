package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Jing.Zhang
 */
@Data
@ApiModel("解析后的手机号码")
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisUploadPhoneResultVO {

    @ApiModelProperty("手机号归属及数量")
    private Map<String, Long> phoneOwnership;

    @ApiModelProperty("手机号码")
    private List<String> phones;


}
