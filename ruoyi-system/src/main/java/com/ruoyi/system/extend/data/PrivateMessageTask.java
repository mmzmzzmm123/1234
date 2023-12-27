package com.ruoyi.system.extend.data;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class PrivateMessageTask {

    @ApiModelProperty("任务Id")
    @Excel(name = "任务ID")
    private String privateMessageTaskId;

    @ApiModelProperty("任务名称")
    @Excel(name = "任务名称")
    private String name;

    @ApiModelProperty("任务状态:Wait(Wait)=0 Runing(Runing)=1 Complete(Complete)=2 Cancel(Cancel)=-1")
    private Integer executeStatus;

    @ApiModelProperty("任务状态说明（导出使用）")
    @Excel(name = "任务状态")
    private String executeStatusStr;

    @ApiModelProperty("话术内容说明（导出使用）")
    @Excel(name = "话术内容")
    private String materialGroupStr;

    @ApiModelProperty("私信数")
    @Excel(name = "总私信数")
    private Integer totalMessageQuantity;

    @ApiModelProperty("已完成数")
    @Excel(name = "已完成数")
    private Integer executeQuantity;

    @ApiModelProperty("进度")
    private Double executePercent;

    @Excel(name = "进度")
    private String executePercentStr;

    @ApiModelProperty("私信成功数")
    @Excel(name = "私信成功数")
    private Integer messageSuccessQuantity;

    @ApiModelProperty("私信成功率")
    private Double messageSuccessPercent;

    @Excel(name = "私信成功率")
    private String messageSuccessPercentStr;

    @ApiModelProperty("已读数")
    @Excel(name = "已读数")
    private Integer readQuantity;

    @ApiModelProperty("已读率")
    private Double readSuccessPercent;

    @Excel(name = "已读率")
    private String readSuccessPercentStr;

    @ApiModelProperty("回复数")
    @Excel(name = "回复数")
    private Integer receiveReplyQuantity;

    @ApiModelProperty("回复率")
    private Double receiveReplyPercent;

    @Excel(name = "回复率")
    private String receiveReplyPercentStr;

    @ApiModelProperty("转化数")
    @Excel(name = "转化数")
    private Integer conversionQuantity;

    @ApiModelProperty("转化率")
    private Double conversionPercent;

    @Excel(name = "转化率")
    private String conversionPercentStr;

    @ApiModelProperty("消耗点券数")
    @Excel(name = "点券消耗数")
    private Integer couponQuantity;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @Excel(name = "创建时间")
    private String createTimeStr;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @Excel(name = "更新时间")
    private String updateTimeStr;

    @ApiModelProperty("任务发送数量")
    private Integer objectCount;

    @ApiModelProperty("成功数量")
    private Integer successCount;

    @ApiModelProperty("失败数量")
    private Integer failedCount;

    @ApiModelProperty("待执行数")
    private Integer pendingQuantity;

    @ApiModelProperty("素材Id")
    private List<String> materialGroupIds;

    @ApiModelProperty("素材类型")
    private List<Integer> materialTypes;

    @ApiModelProperty("私信失败数")
    private Integer messageFailQuantity;

    @ApiModelProperty("耗时/单位分钟")
    private Long timeConsuming;

    @ApiModelProperty("耗时")
    @Excel(name = "耗时")
    private String timeConsumingStr;

    @ApiModelProperty("创建人Id")
    private String createrId;
    @ApiModelProperty("创建人")
    private String creater;
    @ApiModelProperty("完成数")
    private Integer completeCount;
    @ApiModelProperty("总数")
    private Integer totalCount;
    @ApiModelProperty("私信中数量")
    private Integer priavateMessagingCount;
    @ApiModelProperty("私聊成功数")
    private Integer privateMessageSuccessCount;
    @ApiModelProperty("私聊失败数")
    private Integer privateMessageFailCount;
    @ApiModelProperty("取消数量")
    private Integer cancelCount;
    @ApiModelProperty("未开通数")
    private Integer notOpenCount;
    @ApiModelProperty("私聊已读数")
    private Integer privateMessageReadCount;
    @ApiModelProperty("私聊回复数")
    private Integer privateMessageReplyCount;
    @ApiModelProperty("自动回复数")
    private Integer autoReplyCount;
    @ApiModelProperty("执行时间")
    private LocalDateTime executeTime;
    @ApiModelProperty("素材信息集合")
    private List<GetPrivateMaterialGroup> materialGroupList;
    @ApiModelProperty("执行方式：1定时执行、2立即执行")
    private Integer executeMode;
    @ApiModelProperty("是否开启小尾巴")
    private Integer isOpenTail;
    @ApiModelProperty("号量")
    private Integer tgCount;
    @ApiModelProperty("人群包id")
    private Long contactPackageTaskId;
    @ApiModelProperty("展示信息")
    private String showMessage;
    @ApiModelProperty("是否触发取号上限 1：风控间隔中 2：触发上限")
    private Integer isTriggerRobotType;
    @ApiModelProperty("结算状态（0未结算、1已结算）")
    private Integer settlementStatus;
    @ApiModelProperty("Desc:规则速度等级 Default: Nullable:True")
    private Integer nLevelId;
    @ApiModelProperty("是否开启自动回复")
    private Integer isOpenAutoReply;
    @ApiModelProperty("自动回复话术分组Id")
    private List<String> autoReplyMaterialGroupIds;
    @ApiModelProperty("自动回复话术分组Id")
    private List<Integer> autoReplyMaterialTypes;
    @ApiModelProperty("自动回复延迟秒数")
    private Integer autoReplyDelaySeconds;

    @ApiModelProperty("商家Id")
    private String merchantId;

    @ApiModelProperty("创建者")
    private String createBy;

    @ApiModelProperty("是否开启混淆")
    private Boolean openDiscrete;

    public String getExecuteStatusStr() {
        if (executeStatus != null) {
            executeStatusStr = null;
            switch (executeStatus) { //0=未开始,-2=暂停,1=进行中,2=已完成,-1=已取消
                case 0:
                    executeStatusStr = "未开始";
                    break;
                case -2:
                    executeStatusStr = "暂停";
                    break;
                case 1:
                    executeStatusStr = "进行中";
                    break;
                case 2:
                    executeStatusStr = "已完成";
                    break;
                case -1:
                    executeStatusStr = "已取消";
                    break;
            }
            return executeStatusStr;
        }
        return null;
    }

    public Long getTimeConsuming() {
        LocalDateTime endTime = updateTime == null ? LocalDateTime.now() : updateTime;
        if (createTime != null) {
            timeConsuming = (endTime.toEpochSecond(ZoneOffset.ofHours(8)) - createTime.toEpochSecond(ZoneOffset.ofHours(8))) / 60;
            return timeConsuming;
        }
        return null;
    }


    public Double getMessageSuccessPercent() {
        if (messageSuccessQuantity == null || totalMessageQuantity == null) {
            return 0d;
        }
        messageSuccessPercent = BigDecimal.valueOf(messageSuccessQuantity)
                .divide(BigDecimal.valueOf(totalMessageQuantity), 2, RoundingMode.HALF_UP)
                .doubleValue();
        return messageSuccessPercent;
    }

    public Double getReadSuccessPercent() {
        if (readQuantity == null || totalMessageQuantity == null) {
            return 0d;
        }
        readSuccessPercent = BigDecimal.valueOf(readQuantity)
                .divide(BigDecimal.valueOf(totalMessageQuantity), 2, RoundingMode.HALF_UP)
                .doubleValue();
        return readSuccessPercent;
    }

    public Double getReceiveReplyPercent() {
        if (receiveReplyQuantity == null || totalMessageQuantity == null) {
            return 0d;
        }
        receiveReplyPercent = BigDecimal.valueOf(receiveReplyQuantity)
                .divide(BigDecimal.valueOf(totalMessageQuantity), 2, RoundingMode.HALF_UP)
                .doubleValue();
        return receiveReplyPercent;
    }

    public Double getConversionPercent() {
        if (conversionQuantity == null || totalMessageQuantity == null) {
            return 0d;
        }
        conversionPercent = BigDecimal.valueOf(conversionQuantity)
                .divide(BigDecimal.valueOf(totalMessageQuantity), 2, RoundingMode.HALF_UP)
                .doubleValue();
        return conversionPercent;
    }


    public Double getExecutePercent() {
        if (executeQuantity == null || totalMessageQuantity == null) {
            return 0d;
        }
        executePercent = BigDecimal.valueOf(executeQuantity)
                .divide(BigDecimal.valueOf(totalMessageQuantity), 2, RoundingMode.HALF_UP)
                .doubleValue();
        return executePercent;
    }

    public String getConversionPercentStr() {
        if (getConversionPercent() != null) {
            return (int) (getConversionPercent() * 100) + "%";
        }
        return "";
    }

    public String getReceiveReplyPercentStr() {
        if (getReceiveReplyPercent() != null) {
            return (int) (getReceiveReplyPercent() * 100) + "%";
        }
        return "";
    }

    public String getReadSuccessPercentStr() {
        if (getReadSuccessPercent() != null) {
            return (int) (getReadSuccessPercent() * 100) + "%";
        }
        return "";
    }

    public String getExecutePercentStr() {
        if (getExecutePercent() != null) {
            return (int) (getExecutePercent() * 100) + "%";
        }
        return "";
    }

    public String getMessageSuccessPercentStr() {
        if (getMessageSuccessPercent() != null) {
            return (int) (getMessageSuccessPercent() * 100) + "%";
        }
        return "";
    }

    public String getTimeConsumingStr() {
        Long timeConsuming = getTimeConsuming();
        if (timeConsuming == null) {
            return "-";
        }
        String format;
        Object[] array;
        Integer days = (int) (timeConsuming / (60 * 24)); //天
        Integer hours = (int) (timeConsuming / (60) - days * 24); //小时
        Integer minutes = (int) (timeConsuming - hours * 60 - days * 24 * 60); //分钟
        if (days > 0) {
            format = "%1$,d天%2$,d小时%3$,d分钟";
            array = new Object[]{days, hours, minutes};
        } else if (hours > 0) {
            format = "%1$,d小时%2$,d分钟";
            array = new Object[]{hours, minutes};
        } else {
            format = "%1$,d分钟";
            array = new Object[]{minutes};
        }
        return String.format(format, array);
    }

    public String getCreateTimeStr() {
        if (createTime != null) {
            return createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }

    public String getUpdateTimeStr() {
        if (updateTime != null) {
            return updateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }

    public String getMaterialGroupStr() {
        if (CollectionUtils.isNotEmpty(materialGroupList)) {
            materialGroupStr = "";
            for (int i = 0; i < materialGroupList.size(); i++) {
                String name = "";
                if (materialGroupList.get(i) != null) {
                    name = materialGroupList.get(i).getName();
                }
                materialGroupStr = String.format("%s%s、%s，", materialGroupStr, i + 1, name);
            }
            materialGroupStr = materialGroupStr.substring(0, materialGroupStr.lastIndexOf("，"));
            return materialGroupStr;
        }
        return "-";
    }
}
