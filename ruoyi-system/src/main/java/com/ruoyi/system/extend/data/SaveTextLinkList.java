package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
@ApiModel("PostBot按钮数据")
public class SaveTextLinkList {

    @ApiModelProperty("按钮名称")
    private String name;

    @ApiModelProperty("是否换行")
    private boolean isWrap;

    @ApiModelProperty("按钮链接")
    private List<String> urls;

}
