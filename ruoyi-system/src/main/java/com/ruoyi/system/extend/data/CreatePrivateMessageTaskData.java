package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class CreatePrivateMessageTaskData {

    /**
     * 用户编号
     */
    @ApiModelProperty(hidden = true)
    private String userCode;

    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String name;

    /**
     * 联系电话/用户名
     */
    @ApiModelProperty("联系电话/用户名")
    private List<String> contentList;

    /**
     * 话术分组Id
     */
    @ApiModelProperty("话术分组Id")
    private List<String> materialGroupIds;

    /**
     * 素材内容类型 Text(文本)=2001 Img(图片)=2002 Video(视频)=2004 PostBot(PostBot)=2035 ImgText(图片+文字（业务类型）)=20021 VideoText(视频+文字（业务类型）)=20041
     */
    @ApiModelProperty("素材内容类型 Text(文本)=2001 Img(图片)=2002 Video(视频)=2004 PostBot(PostBot)=2035 ImgText(图片+文字（业务类型）)=20021 VideoText(视频+文字（业务类型）)=20041")
    private List<Integer> materialTypes;

    @ApiModelProperty("占位符内容")
    private List<TailInfo> tailContentList = new ArrayList<>();

    @ApiModelProperty("小尾巴内容")
    private String tailContent = "";

    @ApiModelProperty("小尾巴开关 0-关闭 1-开启")
    private Integer tailIsOpne = 0;

    //    是否开启自动回复
    @ApiModelProperty("是否开启自动回复")
    private Integer isOpenAutoReply = 0;

    //    自动回复话术分组Id
    @ApiModelProperty("自动回复话术分组Id")
    private List<String> autoReplyMaterialGroupIds = new ArrayList<>();

    //    自动回复话术类型：文本2001，图片2002，postbot2035
    @ApiModelProperty("自动回复话术类型 Text(文本)=2001 Img(图片)=2002 Video(视频)=2004 PostBot(PostBot)=2035 ImgText(图片+文字（业务类型）)=20021 VideoText(视频+文字（业务类型）)=20041")
    private List<Integer> autoReplyMaterialTypes = new ArrayList<>();

    //    自动回复延迟秒数
    @ApiModelProperty("自动回复延迟秒数")
    private Integer autoReplyDelaySeconds;

    /**
     * 导入类型（0手机、1用户号）
     */
    @ApiModelProperty("导入类型（0手机、1用户号）")
    private Integer importType;

    /**
     * 是否开启文本混淆 - 默认开启
     */
    @ApiModelProperty("是否开启文本混淆 - 默认开启")
    private Boolean openDiscrete;


    @ApiModelProperty("目标国家[用户名模式下必传]")
    private String targetCountryCode;


    @ApiModelProperty("是否开启SOP:0否、1是")
    private Integer isOpenSOP = 0;


    @ApiModelProperty("触发条件：1用户已读、2活跃度筛选")
    private Integer triggerMode;


    @ApiModelProperty("活跃度筛选类型：1七天内在线、2指定天数在线")
    private Integer activityFilterType;


    @ApiModelProperty("活跃天数")
    private Integer activityDays;

    @ApiModelProperty("SOP触发内容")
    private List<SopContent> sopContents = new ArrayList<>();


    @ApiModelProperty("是否SOP失败终止任务：0否、1是")
    private Integer isSOPFailStopTask = 0;


    @ApiModelProperty("是否收到回复终止任务：0否、1是")
    private Integer isReceiveReplyStopTask = 0;


}
