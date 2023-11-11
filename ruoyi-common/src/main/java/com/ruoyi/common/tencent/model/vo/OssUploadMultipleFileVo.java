package com.ruoyi.common.tencent.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author LAM
 * @date 2022/5/19 17:09
 */
@Data
@Accessors(chain = true)
public class OssUploadMultipleFileVo implements Serializable {

    private static final long serialVersionUID = -1060216618475607933L;


    private List<String> fileUrls;
}
