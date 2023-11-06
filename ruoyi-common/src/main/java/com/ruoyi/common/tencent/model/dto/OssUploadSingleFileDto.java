package com.ruoyi.common.tencent.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/9/12 22:39
 */
@Data
@Accessors(chain = true)
public class OssUploadSingleFileDto implements Serializable {

    private static final long serialVersionUID = -1060216618475607933L;

    @ApiModelProperty("文件")
    @NotNull(message = "文件不能为空")
    private MultipartFile file;

    @ApiModelProperty(value = "ossKey")
    private String ossKey;
}
