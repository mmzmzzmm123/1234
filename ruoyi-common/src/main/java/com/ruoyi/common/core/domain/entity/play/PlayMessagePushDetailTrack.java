package com.ruoyi.common.core.domain.entity.play;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "t_play_message_push_detail_track")
@Data
public class PlayMessagePushDetailTrack {
	/**
	 * 主键Id
	 */
	@TableId(type = IdType.AUTO)
	private Integer trackId;

	/**
	 * 机器人Id
	 */
	private String robotId;

	/**
	 * 消息序号
	 */
	private Integer messageSort;

	private String groupId;
	private String playId;
	private Date createTime;
}