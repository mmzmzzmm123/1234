package com.ruoyi.system.components.prepare.spi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.dto.play.PlayExt;
import com.ruoyi.common.core.domain.entity.VibeRule;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.impl.PreRobotSpeakAllocator;
import com.ruoyi.system.components.prepare.ExecutionParamContext;
import com.ruoyi.system.components.prepare.ExecutionResultContext;
import com.ruoyi.system.components.spi.RobotInfoQuery;
import com.ruoyi.system.domain.dto.play.PlayRobotGroupRelation;
import com.ruoyi.system.mapper.PlayRobotGroupRelationMapper;
import com.ruoyi.system.mapper.VibeRuleMapper;
import com.ruoyi.system.openapi.model.output.ExtTgSelectRobotInfoListVO;
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.PlayMessagePushDetailService;
import lombok.extern.slf4j.Slf4j;

/**
 * 机器人预分配
 * 
 * @author Administrator
 *
 */
@SPI("RobotPreallocationTask")
@Slf4j
public class RobotPreallocationTask implements TaskExecution {

	@Override
	public ExecutionResultContext doExecute(ExecutionParamContext context) {
		final PlayMessagePush messagePush = context.getMessagePush();
		// 查询群内机器人
		final LambdaQueryWrapper<PlayRobotGroupRelation> queryWrapper = new QueryWrapper<PlayRobotGroupRelation>()
				.lambda();
		queryWrapper.eq(PlayRobotGroupRelation::getGroupId, context.getChatroomId());
		queryWrapper.eq(PlayRobotGroupRelation::getState, 1).eq(PlayRobotGroupRelation::getIsDelete, 0);
		List<PlayRobotGroupRelation> relationList = SpringUtils.getBean(PlayRobotGroupRelationMapper.class)
				.selectList(queryWrapper);

		if (CollectionUtils.isEmpty(relationList)) {
			// 群内 查询 不到 演员
			PlayExecutionLogService.robotPackLog(context.getPlay().getId(), context.getChatroomId(), null,
					"【号预分配】 群" + context.getChatroomId() + " 内无演员", "群内无演员");
			return ExecutionResultContext.buildError(context, "群内无演员");
		}
		final Map<String, ExtTgSelectRobotInfoListVO> map = new HashMap<>();
		List<String> source = ListTools.extract(relationList, f -> f.getRobotId());
		// 查询所有的detail
		List<PlayMessagePushDetail> details = context.getPushDetails();

		final RobotInfoQuery robotInfoQuery = ServiceLoader.load(RobotInfoQuery.class, "TgRobotInfoQuery");

		for (PlayMessagePushDetail detail : details) {
			ExtTgSelectRobotInfoListVO robot = map.get(detail.getSpokesmanNickname());
			if (robot != null) {
				// 这个发言人 已经分配过了 ， 直接用
				detail.setRobotId(robot.getRobotId());
				detail.setRobotImgUrl(robot.getHeadImgUrl());
				detail.setRobotNickname(robot.getUserName());
				detail.setRobotAcct(robot.getAccount());

				PreRobotSpeakAllocator.Cache.set(messagePush.getPlayId(), messagePush.getGroupId(),
						detail.getSpokesmanNickname(), detail.getRobotId());
				continue;
			}
			if (!CollectionUtils.isEmpty(source)) {
				detail.setRobotId(source.get(0));
				List<ExtTgSelectRobotInfoListVO> robots = robotInfoQuery.listById(ListTools.newArrayList(detail.getRobotId()));
				if (!CollectionUtils.isEmpty(robots)) {
					detail.setRobotImgUrl(robots.get(0).getHeadImgUrl());
					detail.setRobotNickname(robots.get(0).getUserName());
					detail.setRobotAcct(robots.get(0).getAccount());
					map.put(detail.getSpokesmanNickname(), robots.get(0));
				} else {
					ExtTgSelectRobotInfoListVO vo = new ExtTgSelectRobotInfoListVO();
					vo.setRobotId(detail.getRobotId());
					map.put(detail.getSpokesmanNickname(), vo);
				}
				source.remove(0);
				log.info("预分配到机器人 {} {}", messagePush, detail);
				PreRobotSpeakAllocator.Cache.set(messagePush.getPlayId(), messagePush.getGroupId(),
						detail.getSpokesmanNickname(), detail.getRobotId());
			}
		}
		// ----------分配备用号----------------
		final PlayExt playExt = context.getPlay().convertPlayExtStr();
		final VibeRule rule = SpringUtils.getBean(VibeRuleMapper.class).selectList(null).get(0);

		if (playExt.getStandbyState().intValue() == 1 && rule.getStandbyNum().intValue() > 0) {
			int bi = rule.getStandbyNum().intValue(); // 水军倍数
			// 开启了备用号
			List<String> excutionList = ListTools.extract(details, f -> f.getRobotId());
			// 要排除的 正式 号
			source.removeAll(excutionList);
			LinkedList<String> lk = new LinkedList<>(source);
			for (PlayMessagePushDetail detail : details) {
				// 每个 号 带 bi 个备用水军
				List<String> ids = take(lk, bi);
				if (CollectionUtils.isEmpty(ids)) {
					break;
				}
				detail.setSpareRobot(StringUtils.join(ids, ","));
				log.info("预分配设置备用号 {}", detail);
			}
		}
		// 批量更新
		SpringUtils.getBean(PlayMessagePushDetailService.class).updateBatchById(details);
		PlayExecutionLogService.robotPackLog(context.getPlay().getId(), context.getChatroomId(), null,
				"【号预分配】 群" + context.getChatroomId() + " 分配成功", null);
		return ExecutionResultContext.buildSync(context);
	}

	private List<String> take(LinkedList<String> lk, int num) {
		List<String> ret = new ArrayList<String>();
		while (true) {
			if (ret.size() == num) {
				break;
			}
			if (lk.size() == 0) {
				break;
			}
			ret.add(lk.removeFirst());
		}
		return ret;
	}

}
