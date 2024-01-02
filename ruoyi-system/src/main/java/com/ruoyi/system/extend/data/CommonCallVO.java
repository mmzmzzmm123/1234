package com.ruoyi.system.extend.data;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.system.extend.UtTouchApiEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jing.Zhang
 */
@Data
@ApiModel("公共调用参数")
public class CommonCallVO {

    @ApiModelProperty("接口名")
    private UtTouchApiEnum api;

    @ApiModelProperty("请求参数")
    private JSONObject requestParams;

}
