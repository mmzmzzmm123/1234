package com.ruoyi.user.domain;

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
 * 用户信息管理对象 bus_user_info
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-用户信息管理对象")
public class UserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 头像 */
    @Excel(name = "头像")
    @ApiModelProperty("头像")
    private String avatarUrl;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String nickName;

    /** 性别 */
    @Excel(name = "性别", dictType = "sys_user_sex")
    @ApiModelProperty("性别")
    private String sex;

    /** openId */
    @Excel(name = "openId")
    @ApiModelProperty("openId")
    private String openId;

    /** sessionKey */
    @Excel(name = "sessionKey")
    @ApiModelProperty("sessionKey")
    private String sessionKey;

    /** unionId */
    @Excel(name = "unionId")
    @ApiModelProperty("unionId")
    private String unionId;

    /** 手机号码 */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号码")
    private String phone;

    /** 推荐码 */
    @Excel(name = "推荐码")
    @ApiModelProperty("推荐码")
    private Long referralCode;

    /** 隐藏等级 */
    @Excel(name = "隐藏等级", dictType = "sys_show_hide")
    @ApiModelProperty("隐藏等级")
    private String hideLevel;

    /** 隐藏排名 */
    @Excel(name = "隐藏排名", dictType = "sys_show_hide")
    @ApiModelProperty("隐藏排名")
    private String hideRanking;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("最后登录时间")
    private Date lastLoginTime;


}
