package com.ruoyi.system.components.spi;

import java.util.List;
import com.ruoyi.system.openapi.model.output.ExtTgSelectRobotInfoListVO;

/**
 * title: 机器人 信息查询
 * 
 * @author Administrator
 *
 */
public interface RobotInfoQuery {

	List<ExtTgSelectRobotInfoListVO> listById(List<String> ids);

}
