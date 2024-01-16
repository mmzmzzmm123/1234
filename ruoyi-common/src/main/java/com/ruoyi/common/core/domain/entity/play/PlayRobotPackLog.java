package com.ruoyi.common.core.domain.entity.play;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

	private String isAdmin;

	private Date createTime;
}
