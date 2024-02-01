package com.ruoyi.system.components.prepare.multipack;

import java.util.Date;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.system.components.movie.SendMsgOptTempRedis;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.utils.Times;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.PlayDirector;
import com.ruoyi.system.configure.RedisConfigure;
import com.ruoyi.system.mapper.PlayMessagePushDetailMapper;
import com.ruoyi.system.mapper.PlayMessagePushMapper;
import lombok.extern.slf4j.Slf4j;

/***
 * title: 消息超时处理
 * 
 * @author Administrator
 *
 */
@Slf4j
@SPI("MessageTimeoutJobProcessor")
public class MessageTimeoutJobProcessor implements LogJobProcessor {

	@SuppressWarnings("deprecation")
	@Override
	public void handle(Play play) {
		// 回调超时处理
		int timeout = SpringUtils.getBean(RedisConfigure.class).getTimeout();
		List<PlayMessagePush> pushList = SpringUtils.getBean(PlayMessagePushMapper.class)
				.selectList(new QueryWrapper<PlayMessagePush>().lambda()
						.eq(PlayMessagePush::getSendFlag, 1)   // 炒群条件: 0-未达到 1-达到
						.eq(PlayMessagePush::getPushState, 2)  // 推送状态:1:待发送 2:进行中 3:已结束 4:人工暂停 5:推送失败 6:取消
						.eq(PlayMessagePush::getPlayId, play.getId()));
		for(PlayMessagePush push : pushList) {
			List<PlayMessagePushDetail> datas = SpringUtils.getBean(PlayMessagePushDetailMapper.class)
					.selectList(new QueryWrapper<PlayMessagePushDetail>().lambda().eq(PlayMessagePushDetail::getPlayMsgPushId, push.getId())
							.eq(PlayMessagePushDetail::getSendState, 3)
							.le(PlayMessagePushDetail::getModifyTime, Times.getSecond(new Date(), -timeout)));
			if (CollectionUtils.isEmpty(datas)) {
				continue;
			}
			for (PlayMessagePushDetail d : datas) {
				if (StringUtils.isEmpty(d.getOptSerialNo())) {
					continue;
				}
				log.info("MessageTimeoutJobProcessor_超时回调处理 {}", d);
				SendMsgOptTempRedis.SendMsgOptTempEntry entry = new SendMsgOptTempRedis.SendMsgOptTempEntry();
				entry.setChatroomId(push.getGroupId());
				entry.setPlayId(push.getPlayId());
				entry.setMsgSort(d.getMessageSort());
				entry.setRobotNickName(d.getSpokesmanNickname());
				entry.setRobotId(d.getRobotId());
				PlayDirector.tgInstance().onMessageFailure(d.getOptSerialNo(), "回调超时", entry);
			}
			
		}
	}

}
