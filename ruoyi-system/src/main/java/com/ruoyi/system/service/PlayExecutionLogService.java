package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.dto.play.QueryExecutionLogDTO;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/13/013 15:25
 * @Description :
 */
public interface PlayExecutionLogService {

	/**
	 * 查询剧本执行日志列表
	 *
	 * @param playId
	 * @return
	 */
	List<PlayExecutionLog> listByPlayId(String playId);

	/**
	 * 分页查询剧本执行日志列表
	 *
	 * @param dto
	 * @return
	 */
	Page<PlayExecutionLog> logPage(QueryExecutionLogDTO dto);

	/**
	 * 获取对应剧本某个群的最新一条记录
	 * @param playId
	 * @param groupId
	 * @return
	 */
	PlayExecutionLog getOne(String playId, String groupId);

	/**
	 * 保存日志
	 *
	 * @param log
	 */
	void saveLog(PlayExecutionLog log);

	/**
	 * 查询剧本广告监控触发次数
	 * @param playId
	 * @return
	 */
	long getMonitorTriggerNum(String playId);

	/**
	 * 剧本混淆日志
	 * @param playId
	 * @param content
	 * @param state
	 */
	public static void playConfoundLog(String playId, String content, Integer state) {
		PlayExecutionLog log = new PlayExecutionLog();
		log.setPlayId(playId);
		if (state != null) {
			log.setState(state);
		}
		log.setType(PlayLogTyper.Play_Confound);
		log.setContent(content);
		SpringUtils.getBean(PlayExecutionLogService.class).saveLog(log);
	}

	public static void robotPackLog(String playId, String content, Integer state) {
		savePackLog(PlayLogTyper.Robot_Settings, playId, content,state);
	}
	
	
	public static void robotPackLog(String playId, String groupId, String robotId ,String conetent , String errMsg){
		PlayExecutionLog log = new PlayExecutionLog();
		log.setGroupId(groupId);
		log.setPlayId(playId);
		// 状态 0-成功（默认） 1-失败
		log.setState(StringUtils.isEmpty(errMsg) ? 0 : 1);
		log.setType(PlayLogTyper.Robot_Settings);
		log.setRobotId(robotId);
		SpringUtils.getBean(PlayExecutionLogService.class).saveLog(log);
	}

	/**
	 * title: 发言人包装日志记录
	 * 
	 * @param playId
	 * @param groupId
	 * @param robotId
	 * @param success
	 * @param funcAlias
	 */
	public static void robotPackLog(String playId, String groupId, String robotId, String errMsg, String opt,
			String funcAlias, boolean isSync) {
		PlayExecutionLog log = new PlayExecutionLog();
		log.setGroupId(groupId);
		log.setPlayId(playId);
		log.setOpt(opt);
		// 状态 0-成功（默认） 1-失败
		log.setState(StringUtils.isEmpty(errMsg) ? 0 : 1);
		log.setType(PlayLogTyper.Robot_Settings);
		log.setRobotId(robotId);
		if (isSync && !StringUtils.isEmpty(errMsg)) {
			// 同步请求 && 失败
			log.setContent(String.format("【发言人包装-%s】 群%s 号%s 同步请求失败，原因：%s", funcAlias, groupId, robotId, errMsg));
		}

		if (isSync && StringUtils.isEmpty(errMsg)) {
			// 同步请求 & 成功
			log.setContent(String.format("【发言人包装-%s】 群%s 号%s 请求成功，操作码：%s", funcAlias, groupId, robotId, opt));
		}

		if (!isSync && !StringUtils.isEmpty(errMsg)) {
			// 回调请求 && 失败
			log.setContent(
					String.format("【发言人包装-%s】 群%s 号%s 回调失败，操作码： %s，原因：%s", funcAlias, groupId, robotId, opt, errMsg));
		}

		if (!isSync && StringUtils.isEmpty(errMsg)) {
			// 回调请求 && 成功
			log.setContent(String.format("【发言人包装-%s】 群%s 号%s 回调成功，操作码： %s", funcAlias, groupId, robotId, opt));
		}
		SpringUtils.getBean(PlayExecutionLogService.class).saveLog(log);
	}
	
	
	
	public static void robotSendMassageErrLog(String playId, String groupId, String robotId, String errMsg, String opt) {
		PlayExecutionLog log = new PlayExecutionLog();
		log.setGroupId(groupId);
		log.setPlayId(playId);
		log.setOpt(opt);
		// 状态 0-成功（默认） 1-失败
		log.setState(StringUtils.isEmpty(errMsg) ? 0 : 1);
		log.setType(PlayLogTyper.Robot_Settings);
		log.setRobotId(robotId);
		if(StringUtils.isEmpty(opt)) {
			log.setContent(String.format("【剧本消息发送失败】 群%s 号%s，原因：%s，操作码:%s", groupId, robotId, errMsg , opt));
		}else {
			log.setContent(String.format("【剧本消息发送失败】 群%s 号%s，原因：%s", groupId, robotId, errMsg));
		}
		SpringUtils.getBean(PlayExecutionLogService.class).saveLog(log);
	}

	public static void savePackLog(PlayLogTyper playLogTyper, String playId, String content, Integer state) {
		PlayExecutionLog log = new PlayExecutionLog();
		log.setPlayId(playId);
		if (state != null) {
			log.setState(state);
		}
		log.setType(playLogTyper);
		log.setContent(content);
		SpringUtils.getBean(PlayExecutionLogService.class).saveLog(log);
	}


}
