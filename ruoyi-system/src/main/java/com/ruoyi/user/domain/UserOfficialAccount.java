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
 * 微信公众号用户对象 bus_user_official_account
 *
 * @author Lam
 * @date 2023-10-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-微信公众号用户对象")
public class UserOfficialAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 组标识 */
    @Excel(name = "组标识")
    @ApiModelProperty("组标识")
    private Long groupId;

    /** openId */
    @Excel(name = "openId")
    @ApiModelProperty("openId")
    private String openId;

    /** unionId */
    @Excel(name = "unionId")
    @ApiModelProperty("unionId")
    private String unionId;

    /** 昵称 */
    @Excel(name = "昵称")
    @ApiModelProperty("昵称")
    private String nickName;

    /** 头像 */
    @Excel(name = "头像")
    @ApiModelProperty("头像")
    private String headImgUrl;

    /** 语言 */
    @Excel(name = "语言")
    @ApiModelProperty("语言")
    private String language;

    @Excel(name = "是否订阅")
    @ApiModelProperty("是否订阅（字典-》系统是否）")
    private String ifSubscribe;

    /** 订阅场景 */
    @Excel(name = "订阅场景")
    @ApiModelProperty("订阅场景")
    private String subscribeScene;

    /** 订阅时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订阅时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("订阅时间")
    private Date subscribeTime;

    /** 二维码扫码场景 */
    @Excel(name = "二维码扫码场景")
    @ApiModelProperty("二维码扫码场景")
    private String qrScene;

    /** 二维码扫码场景字符串 */
    @Excel(name = "二维码扫码场景字符串")
    @ApiModelProperty("二维码扫码场景字符串")
    private String qrSceneStr;


}
