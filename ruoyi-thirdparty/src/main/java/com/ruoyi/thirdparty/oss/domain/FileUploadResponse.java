package com.ruoyi.thirdparty.oss.domain;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

/**
 * @author : yelifeng
 * @date : 2020/10/16 0016 11:03 xuc5566@gmail.com
 */
@JsonInclude(Include.NON_NULL)
@Builder
@Data
public class FileUploadResponse {

    private String filePath;
    private String errorMessage;

}
