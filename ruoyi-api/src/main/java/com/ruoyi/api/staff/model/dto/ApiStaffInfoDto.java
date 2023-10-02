package com.ruoyi.api.staff.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/9/25 11:47
 */
@ApiModel("api-员工信息")
@Data
@Accessors(chain = true)
public class ApiStaffInfoDto implements Serializable {

    @ApiModelProperty("员工标识-用户标识")
    @NotNull(message = "用户标识不能为空")
    private Long userId;

    @ApiModelProperty("头像")
    private String avatarUrl;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("状态 -1禁用、0正常、1待审核、2审核不通过")
    private String state;

    @ApiModelProperty("是否在线")
    private String ifOnline;

    @ApiModelProperty("出生年月")
    private String birthDate;

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

    @ApiModelProperty("地区信息（-分割）")
    private String region;

    @ApiModelProperty("个人介绍")
    private String selfIntroduction;

    @ApiModelProperty("个人标签")
    private String selfTags;

    @ApiModelProperty("录音时长")
    private Long voiceTime;

    @ApiModelProperty("推荐码")
    private Long referralCode;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("录音文件")
    private MultipartFile soundRecordFile;

    @ApiModelProperty("文件ossKey")
    private String ossKey;

    @ApiModelProperty("图片集合字符串（逗号分割）")
    private String imageArr;
}
