package com.ruoyi.system.components.movie.spi.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.PlayExecutionLogService;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.GroupCtrlStopper;
import com.ruoyi.system.components.spi.RobotInfoQuery;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.mapper.GroupRobotMapper;
import com.ruoyi.system.mapper.RobotMapper;
import com.ruoyi.system.service.IVibeRuleService;

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
	public void doSetting(String groupId) {
	}

	@Override
	public boolean isStoped(String playId, String groupId) {
		// 查询 群内的号， 然后在查询 号状态
		List<GroupRobot> robots = SpringUtils.getBean(GroupRobotMapper.class).selectList(new QueryWrapper<GroupRobot>()
				.lambda().eq(GroupRobot::getGroupId, groupId).select(GroupRobot::getRobotId));

		if (CollectionUtils.isEmpty(robots)) {
			return false;
		}
		List<Robot> robotInfo = ServiceLoader.load(RobotInfoQuery.class)
				.listById(ListTools.extract(robots, f -> f.getRobotId()));
		if (CollectionUtils.isEmpty(robotInfo)) {
			return false;
		}
		int limit = SpringUtils.getBean(IVibeRuleService.class).getOne().getBannedLimit().intValue();
		// 查询这个号 的 状态
		List<Robot> banList = new ArrayList<Robot>();
		for (Robot r : robotInfo) {
			if (r.getSealStatus().intValue() != 10) {
				// 封号
				banList.add(r);
			}
		}

		if (banList.size() > limit) {
			log.info("BanRobotGroupCtrlStopper {} {} {}", groupId, limit, banList.size());
			PlayExecutionLogService.savePackLog(PlayLogTyper.Group_Send, playId, groupId, StringUtils.format("群id{}:触发单个群累计封号数上限风控(阈值:{},现在数值:{})，群任务暂停",groupId,limit,banList.size()), null);
			return true;
		}
		return false;
	}

}
