package com.ruoyi.common.tencent.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2022/5/12 13:03
 */
@Data
@Accessors(chain = true)
public class OssUploadSingleFileVo implements Serializable {

    private static final long serialVersionUID = -1060216618475607933L;

    /**
     * 请求id
     * */
    private String requestId;

    /**
     * 资源访问url
     * */
    public String fileUrl;

    /**
     * 旧文件名
     * */
    public String fileName;

    /**
     * 新的文件名
     * */
    public String newFileName;
}
