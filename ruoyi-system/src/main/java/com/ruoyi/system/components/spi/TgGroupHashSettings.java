package com.ruoyi.system.components.spi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.mapper.GroupRobotMapper;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgSqlTaskSubmitInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@SPI("TgGroupHashSettings")
@Slf4j
public class TgGroupHashSettings implements Settings {
	public static String getSql(String chatroomSerialNo, String robotSerialNo, List<String> memberSerialNolist) {
		StringBuffer sqlBuffer = new StringBuffer("select access_hash " +
				"from(select access_hash,ROW_NUMBER() " +
				"over(PARTITION BY robot_code,chatroom_code,member_code order by update_time desc) rownum " +
				"from telegram_det_group_member_info_v2 where ");
		sqlBuffer.append("robot_code = '");
		sqlBuffer.append(robotSerialNo);
		sqlBuffer.append("' ");
		sqlBuffer.append("and chatroom_code = '");
		sqlBuffer.append(chatroomSerialNo);
		sqlBuffer.append("' ");
		sqlBuffer.append("and member_code in(");
		StringBuffer memberBuffer = new StringBuffer();
		for (int i = 0; i < memberSerialNolist.size(); i++) {
			if (i == 0) {
				memberBuffer.append("'" + memberSerialNolist.get(i) + "'");
				continue;
			}
			memberBuffer.append(",'" + memberSerialNolist.get(i) + "'");
		}
		sqlBuffer.append(memberBuffer);
		sqlBuffer.append("))t where rownum=1");
		return sqlBuffer.toString();
	}

	@Override
	public PlayRobotPackLog set(Map<String, Object> param) {
		@SuppressWarnings("rawtypes")
		OpenApiResult<TgBaseOutputDTO> ret = null;
		final String groupId = SpringUtils.getBean(GroupInfoMapper.class).selectById(param.get(Settings.Key_GroupId).toString()).getGroupSerialNo();
		final GroupRobot groupOwnerRobot = SpringUtils.getBean(GroupRobotMapper.class).selectOne(new LambdaQueryWrapper<GroupRobot>().eq(GroupRobot::getGroupId, param.get(Settings.Key_GroupId).toString()).eq(GroupRobot::getMemberType, 1).last(" limit 1 "));
		if(groupOwnerRobot == null){
			ret = new OpenApiResult<>();
			String trace = Ids.getId();
			ret.setMessage("接口异常: 群主号信息未同步");
			log.error("sqlTaskSubmitByThirdKpTg_error {} {}" , trace , groupId);
		}
		final String robotId = param.get(Settings.Key_RobotId).toString();
		ThirdTgSqlTaskSubmitInputDTO dto = new ThirdTgSqlTaskSubmitInputDTO();
		dto.setDbSource("kfpt-doris-ed");
		dto.setSql(getSql(groupId, groupOwnerRobot.getRobotId(), ListTools.newArrayList(robotId)));
		try {
			ret = OpenApiClient.sqlTaskSubmitByThirdKpTg(dto);
			log.info("sqlTaskSubmitByThirdKpTg {} {}" , dto , ret);
		} catch (Exception e) {
			String trace = Ids.getId();
			ret.setMessage("接口异常: " + trace);
			log.error("sqlTaskSubmitByThirdKpTg_error {} {}" , trace , dto);
		}
		PlayRobotPackLog data = new PlayRobotPackLog();
		data.setChatroomId(param.get(Settings.Key_GroupId).toString());
		data.setCreateTime(new Date());
		data.setIsFinish(0);
		// 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员 5-获取群成员
		data.setOp(3);
		data.setPlayId(param.get(Settings.Key_PlayId).toString());
		data.setRetryCount(0);
		data.setRobotId(robotId);
		data.setIsBackup(((boolean) param.get(Settings.Key_Backup_Flag)) ? 1 : 0);

		if (ret.getData() != null && !StringUtils.isEmpty(ret.getData().getOptSerNo()) && ret.isSuccess()) {
			// 成功
			data.setStatus(0);
			data.setOpt(ret.getData().getOptSerNo());
			return data;
		}
		// 失败
		data.setStatus(2);
		data.setErrMsg(ret.getMessage());
		return data;
	}

}
