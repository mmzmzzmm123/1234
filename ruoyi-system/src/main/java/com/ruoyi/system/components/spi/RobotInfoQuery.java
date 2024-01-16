package com.ruoyi.system.components.spi;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * title: 机器人 信息查询
 * 
 * @author Administrator
 *
 */
public interface RobotInfoQuery {

	List<RobotInfoVO> listById(List<RobotInfoDTO> dtoList);

	@Data
	public static class RobotInfoVO {
		// 头像
		String headImg;
		// 昵称
		String nickName;
		// 账号
		String acct;
		// 机器人编号
		String robotId;
	}

	@Data
	@Accessors(chain = true)
	public static class RobotInfoDTO {
		final String robotId;
	}

}
