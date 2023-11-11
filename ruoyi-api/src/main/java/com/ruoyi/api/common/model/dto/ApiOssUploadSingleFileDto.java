package com.ruoyi.api.common.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Lam
 * @Date 2022/3/15 23:03
 */
@Data
@Accessors(chain = true)
public class ApiOssUploadSingleFileDto implements Serializable {

    private static final long serialVersionUID = -1060216618475607933L;

    @ApiModelProperty("文件")
    @NotNull(message = "文件不能为空")
    private MultipartFile file;

    @ApiModelProperty(value = "ossKey")
    private String ossKey;
}
