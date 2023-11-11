package com.ruoyi.api.common.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2022/5/12 13:03
 */
@Data
@Accessors(chain = true)
public class ApiOssUploadSingleFileVo implements Serializable {

    private static final long serialVersionUID = -1060216618475607933L;

    @ApiModelProperty("请求id")
    private String requestId;

    @ApiModelProperty("资源访问url")
    public String fileUrl;

    @ApiModelProperty("旧文件名")
    public String fileName;

    @ApiModelProperty("新的文件名")
    public String newFileName;
}
