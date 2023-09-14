package com.ruoyi.staff.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工信息对象 bus_staff_info
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-员工信息对象")
public class StaffInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户标识 */
    private Long userId;

    /** 组长标识 */
    @Excel(name = "组长标识")
    @ApiModelProperty("组长标识")
    private Long referralUserId;

    /** 头像 */
    @Excel(name = "头像")
    @ApiModelProperty("头像")
    private String avatarUrl;

    /** 昵称 */
    @Excel(name = "昵称")
    @ApiModelProperty("昵称")
    private String nickName;

    /** 状态（-1禁用、0正常、1待审核） */
    @Excel(name = "状态", readConverterExp = "-=1禁用、0正常、1待审核")
    @ApiModelProperty("状态")
    private String state;

    /** 是否在线 */
    @Excel(name = "是否在线")
    @ApiModelProperty("是否在线")
    private String ifOnline;

    /** 等级 */
    @Excel(name = "等级")
    @ApiModelProperty("等级")
    private Long staffLevel;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("出生年月")
    private Date birthDate;

    /** 性别 */
    @Excel(name = "性别")
    @ApiModelProperty("性别")
    private String sex;

    /** 微信 */
    @Excel(name = "微信")
    @ApiModelProperty("微信")
    private String weChatNum;

    /** 手机号码 */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号码")
    private String phone;

    /** 省份 */
    @Excel(name = "省份")
    @ApiModelProperty("省份")
    private String province;

    /** 城市 */
    @Excel(name = "城市")
    @ApiModelProperty("城市")
    private String city;

    /** 区 */
    @Excel(name = "区")
    @ApiModelProperty("区")
    private String region;

    /** 个人介绍 */
    @Excel(name = "个人介绍")
    @ApiModelProperty("个人介绍")
    private String selfIntroduction;

    /** 个人标签 */
    @Excel(name = "个人标签")
    @ApiModelProperty("个人标签")
    private String selfTags;

    /** 录音文件 */
    @Excel(name = "录音文件")
    @ApiModelProperty("录音文件")
    private String voiceUrl;

    /** 录音时长 */
    @Excel(name = "录音时长")
    @ApiModelProperty("录音时长")
    private Long voiceTime;

    /** 是否置顶 */
    @Excel(name = "是否置顶")
    @ApiModelProperty("是否置顶")
    private String ifTop;

    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long sortNum;

    /** 推荐码 */
    @Excel(name = "推荐码")
    @ApiModelProperty("推荐码")
    private Long referralCode;


}
