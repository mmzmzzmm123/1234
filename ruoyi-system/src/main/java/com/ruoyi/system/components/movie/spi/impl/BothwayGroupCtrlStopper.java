package com.ruoyi.system.components.movie.spi.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.RedisTemplateTools;
import com.ruoyi.system.components.movie.spi.GroupCtrlStopper;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.mapper.GroupRobotMapper;
import com.ruoyi.system.service.IVibeRuleService;

import lombok.extern.slf4j.Slf4j;

/**
 * title: 单个群累计双向数上限
 * 
 * @author Administrator
 *
 */
@SPI("BothwayGroupCtrlStopper")
@Slf4j
public class BothwayGroupCtrlStopper implements GroupCtrlStopper {

	@Override
	public void doSetting(String robotId) {
		RedisTemplateTools.get().opsForValue().set("ruoyi:BothwayGroupCtrlStopper:" + robotId, "1");
		log.info("BothwayGroupCtrlStopper_set {} ", robotId);
	}

	@Override
	public boolean isStoped(String groupId) {
		// 查询 群成员
		// 查询 群内的号， 然后在查询 号状态
		List<GroupRobot> robots = SpringUtils.getBean(GroupRobotMapper.class).selectList(new QueryWrapper<GroupRobot>()
				.lambda().eq(GroupRobot::getGroupId, groupId).select(GroupRobot::getRobotId));

		if (CollectionUtils.isEmpty(robots)) {
			return false;
		}
		int limit = SpringUtils.getBean(IVibeRuleService.class).getOne().getBothwayLimit().intValue();

		List<String> bothwayIds = new ArrayList<String>();
		for (String mId : ListTools.extract(robots, f -> f.getRobotId())) {
			if (RedisTemplateTools.get().opsForValue().get("ruoyi:BothwayGroupCtrlStopper:" + mId) != null) {
				bothwayIds.add(mId);
			}
		}
		if (bothwayIds.size() > limit) {
			log.info("SendFailGroupCtrlStopper {} {} {}", groupId, limit, bothwayIds.size());
			return true;
		}
		return false;
	}

}
