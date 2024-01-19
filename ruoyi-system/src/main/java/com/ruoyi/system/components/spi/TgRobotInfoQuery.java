package com.ruoyi.system.components.spi;

import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.RobotMapper;

@SPI("TgRobotInfoQuery")
public class TgRobotInfoQuery implements RobotInfoQuery {

	@Override
	public List<Robot> listById(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		return SpringUtils.getBean(RobotMapper.class)
				.selectList(new QueryWrapper<Robot>().lambda().in(Robot::getRobotSerialNo, ids));

	}

}
