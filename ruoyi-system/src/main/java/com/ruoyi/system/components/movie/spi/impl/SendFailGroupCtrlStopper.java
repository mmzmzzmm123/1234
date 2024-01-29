package com.ruoyi.system.components.movie.spi.impl;

import java.util.List;

import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.system.service.PlayExecutionLogService;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.RedisTemplateTools;
import com.ruoyi.system.components.movie.spi.GroupCtrlStopper;
import com.ruoyi.system.mapper.PlayMessagePushDetailMapper;
import com.ruoyi.system.mapper.PlayMessagePushMapper;
import com.ruoyi.system.service.IVibeRuleService;

import lombok.extern.slf4j.Slf4j;

/**
 * title: 单个群累计发送失败次数上限
 * 
 * @author Administrator
 *
 */
@SPI("SendFailGroupCtrlStopper")
@Slf4j
public class SendFailGroupCtrlStopper implements GroupCtrlStopper {

	@Override
	public void doSetting(String groupId) {
	}

	@Override
	public boolean isStoped(String playId, String groupId) {
		int limit = SpringUtils.getBean(IVibeRuleService.class).getOne().getErrLimit().intValue();

		List<PlayMessagePush> pushs = SpringUtils.getBean(PlayMessagePushMapper.class)
				.selectList(new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getGroupId, groupId)
						.select(PlayMessagePush::getId));

		if (CollectionUtils.isEmpty(pushs)) {
			return false;
		}
		List<Integer> pushIds = ListTools.extract(pushs, f -> f.getId());
		// 查询 发送失败的 详情
		Long count = Objects.wrapNull(SpringUtils.getBean(PlayMessagePushDetailMapper.class)
				.selectCount(new QueryWrapper<PlayMessagePushDetail>().lambda()
						.in(PlayMessagePushDetail::getPlayMsgPushId, pushIds)
						.eq(PlayMessagePushDetail::getSendState, 2)),
				0L);

		if (count.intValue() > limit) {
			log.info("SendFailGroupCtrlStopper {} {} {}", groupId, limit, count);
			PlayExecutionLogService.savePackLog(PlayLogTyper.Group_Send, playId, groupId, StringUtils.format("群id{}:触发单个群累计发送失败次数上限风控(阈值:{},现在数值:{})，群任务暂停",groupId,limit,count), null);
			return true;
		}
		return false;
	}

}
