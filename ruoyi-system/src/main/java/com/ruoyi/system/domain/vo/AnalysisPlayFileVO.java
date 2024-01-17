package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AnalysisPlayFileVO {
    @ApiModelProperty(value = "文件id")
    private String fileId;

    @ApiModelProperty(value = "每次获取数量")
    private Integer pageSize;

    private List<ContentInfo> contentInfoList;

    @Data
    public static class ContentInfo {
        @ApiModelProperty("本段内容编号")
        private int no;

        @ApiModelProperty("内容")
        private String content;
    }
}
