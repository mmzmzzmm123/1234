package com.ruoyi.system.components.prepare.spi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.play.PlayGroupPack;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPack;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.system.components.prepare.ExecutionParamContext;
import com.ruoyi.system.components.prepare.ExecutionResultContext;
import com.ruoyi.system.components.spi.Settings;
import lombok.extern.slf4j.Slf4j;

@SPI("ManualPackingTask")
@Slf4j
public class ManualPackingTask implements TaskExecution {

	@Override
	public ExecutionResultContext doExecute(ExecutionParamContext context) {
		// 群包装
		R<String> ret = packGroup(context);
		if (StringUtils.isEmpty(ret.getData())) {
			// 直接请求失败
			context.log(context, PlayLogTyper.Group_Settings, false,
					"[设置群人设] 群(" + context.getChatroomId() + ")同步请求失败, 原因:" + ret.getMsg());
			return ExecutionResultContext.buildError(context, ret.getMsg());
		}
		context.log(context, PlayLogTyper.Group_Settings, false,
				"[设置群人设] 群(" + context.getChatroomId() + ")请求成功，等待回调，操作码:" + ret.getData());
		// 发言人包装
		ret = packRobot(context);
		if (StringUtils.isEmpty(ret.getData())) {
			// 有机器人包装错误
			return ExecutionResultContext.buildError(context, ret.getMsg());
		}
		return ExecutionResultContext.buildAsync(context);
	}

	private R<String> packRobot(ExecutionParamContext context) {
		if (CollectionUtils.isEmpty(context.getPlayRobotPackList())) {
			return R.ok("设置无需包装");
		}
		final Settings settings = ServiceLoader.load(Settings.class, "TgRobotSettings");

		List<String> optList = new ArrayList<String>();
		for (PlayRobotPack robotPck : context.getPlayRobotPackList()) {
			// 查找 对应的sort 机器人数据
			PlayMessagePushDetail detail = getBySort(context.getPushDetails(), robotPck.getMessageSort().intValue());
			if (detail == null || StringUtils.isEmpty(detail.getRobotId())) {
				log.error("数据错误 {} {}", robotPck.getMessageSort().intValue(), context.getPushDetails());
				continue;
			}
			// 请求 设置 机器人头像 ，昵称等
			Map<String, Object> param = new HashMap<>();
			param.put(Settings.Key_Id, detail.getRobotId());
			// 姓名
			param.put(Settings.Key_Names1, Objects.wrapNull(robotPck.getName(), ""));
			// 姓氏
			param.put(Settings.Key_Names2, Objects.wrapNull(robotPck.getSurname(), ""));
			// 管理员
			param.put(Settings.Key_Admin_Flag, robotPck.getIsAdmin().intValue() == 0 ? false : true);
			// 头像
			param.put(Settings.Key_Img, Objects.wrapNull(robotPck.getPic(), ""));
			R<String> ret = settings.set(param);
			if (StringUtils.isEmpty(ret.getData())) {
				context.log(context, PlayLogTyper.Group_Settings, false, "[设置号人设] 群(" + context.getChatroomId() + ") 号("
						+ detail.getRobotId() + ")同步请求失败, 原因:" + ret.getMsg());
				return ret;
			}
			context.log(context, PlayLogTyper.Group_Settings, false, "[设置号人设] 群(" + context.getChatroomId() + ") 号("
					+ detail.getRobotId() + ")请求成功，等待回调，操作码:" + ret.getMsg());
			optList.add(ret.getData());
		}
		if (CollectionUtils.isEmpty(optList)) {
			return R.fail("数据错误");
		}
		return R.ok("请求成功 " + StringUtils.join(optList, ","));
	}

	private PlayMessagePushDetail getBySort(List<PlayMessagePushDetail> datas, int sort) {
		for (PlayMessagePushDetail detail : datas) {
			if (detail.getMessageSort().intValue() == sort) {
				return detail;
			}
		}
		return null;
	}

	private R<String> packGroup(ExecutionParamContext context) {
		if (CollectionUtils.isEmpty(context.getPlayGroupPackList())) {
			return R.ok("设置无需包装");
		}
		// 随机一个群
		PlayGroupPack groupPack = ListTools.randomOne(context.getPlayGroupPackList());
		// 设置 群名称 / 头像
		Map<String, Object> param = new HashMap<>();
		param.put(Settings.Key_Img, Objects.wrapNull(groupPack.getPic(), ""));
		param.put(Settings.Key_Names, Objects.wrapNull(groupPack.getName(), ""));
		return ServiceLoader.load(Settings.class, "TgGroupSettings").set(param);
	}

}
