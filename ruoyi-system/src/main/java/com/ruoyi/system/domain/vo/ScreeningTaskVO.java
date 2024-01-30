package com.ruoyi.system.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * @author 朱少波
 * @date 2024/1/25/025 10:31
 */
@Data
@ApiModel("筛选任务信息VO")
public class ScreeningTaskVO  {

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "任务状态：  0-待检查 1-检查中 3-已暂停 4-已取消 5-已完成 ")
    private Integer taskState;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "完成时间")
    private LocalDateTime completionTime;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    public void setPrice(BigDecimal price) {
        if (price == null) {
            this.price = BigDecimal.ZERO;
            return;
        }
        this.price = price.divide(BigDecimal.valueOf(10000L), 4, RoundingMode.HALF_UP);
    }

    @ApiModelProperty(value = "号码总数")
    private Long targetCount  =0L;

    @ApiModelProperty(value = "筛查总数")
    private Long screeningCount = 0L;

    @ApiModelProperty(value = "进度")
    private BigDecimal screeningRadio = BigDecimal.ZERO;


    private String merchantId;

    private String merchantName;
}
