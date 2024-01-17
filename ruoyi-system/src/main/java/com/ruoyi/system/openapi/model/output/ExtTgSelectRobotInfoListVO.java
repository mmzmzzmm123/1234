package com.ruoyi.system.openapi.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class ExtTgSelectRobotInfoListVO {
	/**
	 * 机器人编号
	 */
	@ApiModelProperty(value = "机器人编号")
	private String robotSerialNo;

	/**
	 * 机器人ID
	 */
	@ApiModelProperty(value = "机器人ID")
	private String robotId;

	/**
	 * 用户号
	 */
	@ApiModelProperty(value = "用户号")
	private String userName;

	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private String headImgUrl;

	/**
	 * 名字
	 */
	@ApiModelProperty(value = "名字")
	private String firstName;

	/**
	 * 姓氏
	 */
	@ApiModelProperty(value = "姓氏")
	private String lastName;

	/**
	 * 机器人类型10：平台号
	 */
	@ApiModelProperty(value = "机器人类型10：平台号")
	private Integer type;

	/**
	 * 机器人状态 10:正常 20:出库 30:退回 40:找回 50:禁止登录 60:迁移 70:轮登
	 */
	@ApiModelProperty(value = "机器人状态 10:正常 20:出库 30:退回 40:找回 50:禁止登录 60:迁移 70:轮登")
	private Integer status;

	/**
	 * 封号状态 10:未封号 20:疑似封号 30:封号离线
	 */
	@ApiModelProperty(value = "封号状态 10:未封号 20:疑似封号 30:封号离线")
	private Integer sealStatus;
	/***
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

	/**
	 * 20 在线 10 离线
	 */
	@ApiModelProperty(value = "20 在线 10 离线")
	private Integer heartbeatStatus;

	@ApiModelProperty(value = "手机号")
	private String account;

	/**
	 * 0-TG号 1-WS号
	 */
	private Integer platform;

	/**
	 * 国家地区编号
	 */
	private String countryCode;

    @ApiModelProperty("国籍")
    private String country;

    @ApiModelProperty("回收状态,0-未回收,1-已回收")
    private String recycleStatus;
    
    /**
     * 机器人密码base6
     */
    private String robotPwd;

    /**
     *
     * 纬度。范围：-90~90
     */
    @ApiModelProperty("纬度")
    private String latitude;

    /**
     * 经度。范围：-180~180
     */
    @ApiModelProperty("经度")
    private String longitude;

    /**
     * 站点
     */
    @ApiModelProperty("站点")
    private String site;


}