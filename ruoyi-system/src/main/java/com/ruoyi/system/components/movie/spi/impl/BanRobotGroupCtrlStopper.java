package com.ruoyi.system.components.movie.spi.impl;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.GroupCtrlStopper;
import com.ruoyi.system.components.spi.RobotInfoQuery;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.mapper.GroupRobotMapper;
import com.ruoyi.system.mapper.RobotMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * title: 单个群累计封号数上限
 * 
 * @author Administrator
 *
 */
@SPI("BanRobotGroupCtrlStopper")
@Slf4j
public class BanRobotGroupCtrlStopper implements GroupCtrlStopper {

	@Override
	public void doSetting(String groupId, String robotId) {
	}

	@Override
	public boolean isStoped(String groupId, String robotId) {
		
		// 查询 群内的号， 然后在查询 号状态
		List<GroupRobot> robots = SpringUtils.getBean(GroupRobotMapper.class).selectList(new QueryWrapper<GroupRobot>().lambda()
				.eq(GroupRobot::getGroupId, groupId).select(GroupRobot::getRobotId));
		
		if(CollectionUtils.isEmpty(robots)) {
			return false;
		}
		
		SpringUtils.getBean(RobotMapper.class).selectList(new QueryWrapper<Robot>());
		
		// 查询这个号 的 状态
		
		
		return true;
	}

}
