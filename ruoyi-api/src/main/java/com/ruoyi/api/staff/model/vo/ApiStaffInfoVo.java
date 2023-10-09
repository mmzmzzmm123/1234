package com.ruoyi.api.staff.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author LAM
 * @date 2023/9/23 21:52
 */
@ApiModel("api-员工信息")
@Data
@Accessors(chain = true)
public class ApiStaffInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("员工标识")
    private Long userId;

    @ApiModelProperty("组长标识")
    private Long referralUserId;

    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("状态（-=1禁用、0正常、1待审核、2审核不通过）")
    private String state;

    @ApiModelProperty("是否在线")
    private String ifOnline;

    @ApiModelProperty("等级")
    private Long staffLevel;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出生年月")
    private Date birthDate;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("微信")
    private String weChatNum;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区")
    private String region;

    @ApiModelProperty("个人介绍")
    private String selfIntroduction;

    @ApiModelProperty("个人标签")
    private String selfTags;

    @ApiModelProperty("录音文件")
    private String voiceUrl;

    @ApiModelProperty("录音时长")
    private Long voiceTime;

    @ApiModelProperty("语音播放状态（-1 暂停 0播放）")
    private int audioState = -1;

    @ApiModelProperty("是否置顶")
    private String ifTop;

    @ApiModelProperty("排序")
    private Long sortNum;

    @ApiModelProperty("推荐码")
    private Long referralCode;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("不通过原因")
    private String notPassReason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("员工相册")
    private List<ApiStaffPhotoVo> photoVoList;

    @ApiModelProperty("店员服务id集合")
    private List<Long> serviceIdList;
}
