package com.ruoyi.system.components.prepare.multipack;

import java.util.List;
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
		List<PlayMessagePush> groups = messagePushMapper
				.selectList(new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getPlayId, play.getId()));
		if (CollectionUtils.isEmpty(groups)) {
			return;
		}

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

				boolean full = info.getMemberCount().intValue() - Objects.wrapNull(robotCount, 0L) >= play.getUserNum()
						.intValue();
				log.info("炒群条件判断 {} {} {} {}", full, info, robotCount, push);
				if (full) {
					// 达到了超群条件
					fullCondition(push);
				}
			}
		}
		if (play.getGroupCondition().intValue() == 1) {
			// 是否过了系统时间
			for (PlayMessagePush push : groups) {
				if (Times.now() >= play.getStartGroupDate().getTime()) {
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
