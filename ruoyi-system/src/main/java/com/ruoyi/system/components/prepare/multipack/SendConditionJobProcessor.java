package com.ruoyi.system.components.prepare.multipack;

import java.util.List;

import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.service.PlayExecutionLogService;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.common.utils.Times;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.GroupMonitorInfo;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.mapper.GroupMonitorInfoMapper;
import com.ruoyi.system.mapper.GroupRobotMapper;
import com.ruoyi.system.mapper.PlayMessagePushMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 炒群条件
 * 
 * @author Administrator
 *
 */
@SPI("SendConditionJobProcessor")
@Slf4j
public class SendConditionJobProcessor implements LogJobProcessor {

	@Override
	public void handle(Play play) {
		final PlayMessagePushMapper messagePushMapper = SpringUtils.getBean(PlayMessagePushMapper.class);
		// 查询所有的群
		List<PlayMessagePush> groups = messagePushMapper.selectList(new QueryWrapper<PlayMessagePush>().lambda()
				.eq(PlayMessagePush::getPlayId, play.getId()).eq(PlayMessagePush::getSendFlag, 0)
				.eq(PlayMessagePush::getRobotPackFlag, 1).eq(PlayMessagePush::getRobotAllocationFlag, 1));
		if (CollectionUtils.isEmpty(groups)) {
			return;
		}
		
		log.info("SendConditionJobProcessor {} {}" , play , groups.size());


		if (play.getGroupCondition().intValue() == 0) {
			// 炒群条件：0-人数 1-时间
			for (PlayMessagePush push : groups) {
				// 判断 人数是否达到， 达到后 就可以开始发剧本
				GroupMonitorInfo info = SpringUtils.getBean(GroupMonitorInfoMapper.class).selectById(push.getGroupId());
				if (info == null) {
					log.error("无法查询群信息 {}", push);
					continue;
				}
				// 排除机器人的人数
				Long robotCount = SpringUtils.getBean(GroupRobotMapper.class).selectCount(
						new QueryWrapper<GroupRobot>().lambda().eq(GroupRobot::getGroupId, push.getGroupId()));
				
				// 拉手好
				int p = Objects.wrapNull(info.getLinkJoinCount() , 0) ;
				boolean full = info.getMemberCount().intValue() - Objects.wrapNull(robotCount, 0L) - p >= play.getUserNum()
						.intValue();
				log.info("炒群条件判断 {} {} {} {} {}", full, info, robotCount, push , p);
				if (full) {
					GroupInfo groupInfo = SpringUtils.getBean(GroupInfoMapper.class).selectById(push.getGroupId());
					PlayExecutionLogService.savePackLog(PlayLogTyper.Group_Send, play.getId(), StringUtils.format("【群名称{}-{}】达到炒群条件，群人数大于{}人，开始炒群",groupInfo.getGroupName(), push.getGroupId(), play.getUserNum()), null);
					// 达到了超群条件
					fullCondition(push);
				}
			}
		}
		if (play.getGroupCondition().intValue() == 1) {
			// 是否过了系统时间
			if (Times.now() >= play.getStartGroupDate().getTime()) {
				PlayExecutionLogService.savePackLog(PlayLogTyper.Group_Send, play.getId(), StringUtils.format("达到炒群条件-{}，开始炒群", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, play.getStartGroupDate())), null);
				for (PlayMessagePush push : groups) {
					fullCondition(push);
				}
			}
		}

	}

	private void fullCondition(PlayMessagePush push) {
		PlayMessagePush update = new PlayMessagePush();
		update.setId(push.getId());
		// 炒群条件: 0-未达到 1-达到
		update.setSendFlag(1);
		SpringUtils.getBean(PlayMessagePushMapper.class).updateById(update);
		log.info("群维度达到炒群条件 {}", push);
	}

}
