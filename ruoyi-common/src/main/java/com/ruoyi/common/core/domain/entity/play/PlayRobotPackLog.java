package com.ruoyi.common.core.domain.entity.play;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.utils.Ids;

import lombok.Data;

@Data
@TableName("t_play_robot_pack_log")
public class PlayRobotPackLog {

	@TableId
	private String opt;

	private String playId;

	private String chatroomId;

	private Integer total;

	private String robotId;

	private String radioId;

	private String errMsg;

	/**
	 * -1等待前置调度完成 0-请求中 1-成功 2-失败
	 */
	private Integer status;

	private Date createTime;

	private Integer isFinish;

	private Integer retryCount;

	/**
	 * 是否是备用号 0-主号 1-备用号
	 */
	private Integer isBackup;

	/**
	 * 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员
	 */
	private Integer op;

	/***
	 * 等待某个操作码执行完成才能开始调佣
	 */
	private String waitOpt;

	/**
	 * 回调附加的值
	 */
	private String attchContent;
	
	private Integer pushDetailId;

	public static boolean finished(List<PlayRobotPackLog> datas) {
		for (PlayRobotPackLog data : datas) {
			if (data.getStatus().intValue() == 0 || data.getStatus().intValue() == -1) {
				return false;
			}
		}
		return true;
	}

	public static PlayRobotPackLog findError(List<PlayRobotPackLog> datas) {
		for (PlayRobotPackLog data : datas) {
			if (data.getStatus().intValue() == 2) {
				return data;
			}
		}
		return null;
	}

	public PlayRobotPackLog wrapOpt() {
		if (StringUtils.isEmpty(getOpt())) {
			setOpt("my_" + Ids.getId());
		}
		return this;
	}

	public PlayRobotPackLog wrapRadio(String radioId) {
		if (StringUtils.isEmpty(getRadioId())) {
			setRadioId(radioId);
		}
		return this;
	}

	public PlayRobotPackLog wrapPushDetailId(Integer pushDetailId) {
		setPushDetailId(pushDetailId);
		return this;
	}
}
