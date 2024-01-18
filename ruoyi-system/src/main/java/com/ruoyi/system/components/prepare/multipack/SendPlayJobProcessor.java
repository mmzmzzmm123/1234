package com.ruoyi.system.components.prepare.multipack;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.PlayDirector;
import com.ruoyi.system.mapper.PlayMessagePushMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 炒群
 * 
 * @author Administrator
 *
 */
@SPI("SendPlayJobProcessor")
@Slf4j
public class SendPlayJobProcessor implements LogJobProcessor {

	@Override
	public void handle(Play play) {
		if (play.getState().intValue() != 1) {
			// 剧本状态：1-调度中 2-炒群中 3-已暂停 4-已取消 5-已完成
			return;
		}
		// 查询达到 炒群条件的 群
		final PlayMessagePushMapper messagePushMapper = SpringUtils.getBean(PlayMessagePushMapper.class);
		List<PlayMessagePush> datas = messagePushMapper
				.selectList(new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getPlayId, play.getId())
						.eq(PlayMessagePush::getSendFlag, 1).eq(PlayMessagePush::getPushState, 1));
		if (CollectionUtils.isEmpty(datas)) {
			return;
		}
		for (PlayMessagePush push : datas) {
			// 开始群维度发送剧本
			PlayDirector.tgInstance().getManualExecutivePlayer(play).startGroup(push.getGroupId());
			// 更新为 发送中
			PlayMessagePush update = new PlayMessagePush();
			update.setId(push.getId());
			update.setPushState(2);
			messagePushMapper.updateById(update);
			log.info("更新群维度剧本发送中 {}", push);
		}
	}

}
