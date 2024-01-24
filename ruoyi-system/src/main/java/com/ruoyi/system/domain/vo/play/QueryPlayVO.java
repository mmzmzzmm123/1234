package com.ruoyi.system.domain.vo.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/13/013 16:46
 * @Description :
 */
@ApiModel("查询剧本任务VO")
@Data
public class QueryPlayVO {

    @ApiModelProperty("任务id")
    private String id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty(value = "群来源：0-平台提供 1-外部群")
    private Integer groupSource;

    @ApiModelProperty("剧本状态：1-调度中 2-炒群中 3-已暂停 4-已取消 5-已完成")
    private Integer state;

    @ApiModelProperty("商家ID")
    private String merchantId;
    @ApiModelProperty("商家名称")
    private String merchantName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("剧本任务进度VO")
    private PlayTaskProgressVO playTaskProgressVO;

}
