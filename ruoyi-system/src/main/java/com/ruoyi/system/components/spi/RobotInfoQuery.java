package com.ruoyi.system.components.spi;

import java.util.List;
import com.ruoyi.common.core.domain.entity.robot.Robot;

/**
 * title: 机器人 信息查询
 * 
 * @author Administrator
 *
 */
public interface RobotInfoQuery {

	List<Robot> listById(List<String> ids);

}
