package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/9/009 15:40
 * @Description :
 */
@Data
@ApiModel("查询混淆日志VO")
public class QueryConfoundLogVO {
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("发起时间")
    private Date createTime;

    @ApiModelProperty("混淆类型 2001-文案 2002-图片 2005-落地页")
    private Integer momentTypeId;

    @ApiModelProperty("原内容")
    private String confoundContent;

    @ApiModelProperty("混淆后")
    private String resultContent;

    @ApiModelProperty("混淆后")
    private List<String> resultList;

    @ApiModelProperty("执行结果 0-待混淆 1-成功 2-失败 3-重试中")
    private Integer state;

    @ApiModelProperty("重试次数")
    private Integer executeNum;

    @ApiModelProperty("操作编号")
    private String optSerialNo;

    @ApiModelProperty("失败原因")
    private String failMessage;
}
