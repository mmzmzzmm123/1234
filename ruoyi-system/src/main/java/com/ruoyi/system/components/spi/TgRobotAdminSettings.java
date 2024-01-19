package com.ruoyi.system.components.spi;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.mapper.GroupRobotMapper;
import com.ruoyi.system.mapper.RobotMapper;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@SPI("TgRobotAdminSettings")
@Slf4j
public class TgRobotAdminSettings implements Settings {

	@Data
	@Accessors(chain = true)
	public static class Req {
		final String botUserId;
		final String chatId;
		final String userId;
		boolean canChangeInfo = true;
		boolean canDeleteMessages = true;
		boolean canDeleteStories = true;
		boolean canEditMessages = true;
		boolean canEditStories = true;
		boolean canInviteUsers = true;
		boolean canManageChat = true;
		boolean canManageVideoChats = true;
		boolean canPinMessages = true;
		boolean canPostMessages = true;
		boolean canPromoteMembers = true;
		boolean canRestrictMembers = true;
		boolean isAnonymous = true;
	}

	@Override
	public PlayRobotPackLog set(Map<String, Object> param) {
		// 要设置管理员的id
		final String robotId = param.get(Settings.Key_RobotId).toString();
		final String groupId = param.get(Settings.Key_GroupId).toString();
		//

		PlayRobotPackLog data = new PlayRobotPackLog();
		data.setChatroomId(groupId);
		data.setCreateTime(new Date());
		data.setIsFinish(0);
		// 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员
		data.setOp(3);
		data.setPlayId(param.get(Settings.Key_PlayId).toString());
		data.setRetryCount(0);
		data.setRobotId(robotId);
		data.setIsBackup(((boolean) param.get(Settings.Key_Backup_Flag)) ? 1 : 0);
		GroupInfo groupInfo = SpringUtils.getBean(GroupInfoMapper.class)
				.selectById(param.get(Settings.Key_GroupId).toString());
		if (groupInfo == null) {
			// 群信息查询不到
			data.setStatus(2);
			data.setErrMsg("群信息不存在");
			return data;
		}
		// 查询群主
		GroupRobot mainRobot = SpringUtils.getBean(GroupRobotMapper.class).selectOne(new QueryWrapper<GroupRobot>()
				.lambda().eq(GroupRobot::getGroupId, groupId).eq(GroupRobot::getMemberType, 1).last(" limit 1 "));
		if (mainRobot == null) {
			data.setStatus(2);
			data.setErrMsg("群主不存在");
			return data;
		}
		// 查找 user id
		List<Robot> rs = SpringUtils.getBean(RobotMapper.class).selectList(new QueryWrapper<Robot>().lambda()
				.in(Robot::getRobotSerialNo, Arrays.asList(robotId, mainRobot.getRobotId())));
		if (CollectionUtils.isEmpty(rs) || rs.size() != 2) {
			data.setStatus(2);
			data.setErrMsg("基础号不存在");
			return data;
		}
		Map<String, List<Robot>> map = ListTools.group(rs, f -> f.getRobotSerialNo());
		String jsonBody = JSON.toJSONString(
				new Req(map.get(mainRobot.getRobotId()).get(0).getId(), groupId, map.get(robotId).get(0).getId()));

		// http://30q623450x.vicp.fun:80/api/v1/bot/setAdminUser
		try {
			HttpRequest request = HttpUtil.createPost("http://30q623450x.vicp.fun:80/api/v1/bot/setAdminUser")
					.body(jsonBody, ContentType.JSON.toString());
			HttpResponse execute = request.execute();
			String response = execute.body();

			log.info("30q623450x_setAdminUser {} {}", jsonBody, response);

			JSONObject jsonObject = JSON.parseObject(response);
			if (jsonObject.containsKey("code")) {
				int code = jsonObject.getIntValue("code");
				if (code == 0) {
					data.setStatus(1);
					return data;
				}
				data.setStatus(2);
				data.setErrMsg(jsonObject.getString("msg"));
				return data;
			}
		} catch (Exception e) {
			log.error("请求设置管理员未知异常 {}", jsonBody, e);
		}
		data.setStatus(2);
		data.setErrMsg("请求设置管理员未知异常");
		return data;
//		final String groupId = SpringUtils.getBean(GroupInfoMapper.class).selectById(param.get(Settings.Key_GroupId).toString()).getGroupSerialNo();
//		
//		ThirdTgSetChatroomAdminInputDTO dto = new ThirdTgSetChatroomAdminInputDTO();
//		// 先获取成员hash值
//		dto.setMemberUserAccessHash(Objects.wrapNull(param.get(Settings.Key_AttachContent), "").toString());
//		dto.setTgRobotId(param.get(Settings.Key_RobotId).toString());
//		dto.setChatroomSerialNo(groupId);
//		dto.setAddAdmins(true);
//		// 成员编号（需要设置成管理员的成员编号）
//		dto.setMemberSerialNo(dto.getTgRobotId());
//		@SuppressWarnings("rawtypes")
//		OpenApiResult<TgBaseOutputDTO> ret = OpenApiClient.setChatroomAdminByThirdKpTg(dto);
//		PlayRobotPackLog data = new PlayRobotPackLog();
//		data.setChatroomId(param.get(Settings.Key_GroupId).toString());
//		data.setCreateTime(new Date());
//		data.setIsFinish(0);
//		// 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群管理员
//		data.setOp(2);
//		data.setPlayId(param.get(Settings.Key_PlayId).toString());
//		data.setRetryCount(0);
//		data.setRobotId(dto.getTgRobotId());
//		data.setIsBackup(((boolean) param.get(Settings.Key_Backup_Flag)) ? 1 : 0);
//
//		if (ret.getData() != null && !StringUtils.isEmpty(ret.getData().getOptSerNo())) {
//			// 成功
//			data.setStatus(0);
//			data.setOpt(ret.getData().getOptSerNo());
//			return data;
//		}
//		// 失败
//		data.setStatus(2);
//		data.setErrMsg(ret.getMessage());
//		return data;
	}

	public static void main(String[] args) {

		String jsonBody = JSON.toJSONString(new Req("11", "11", "11"));

		HttpRequest request = HttpUtil.createPost("http://30q623450x.vicp.fun/api/v1/bot/setAdminUser").body(jsonBody,
				ContentType.JSON.toString());
		HttpResponse execute = request.execute();
		String response = execute.body();

		log.info("30q623450x_setAdminUser {} {}", jsonBody, response);

		JSONObject jsonObject = JSON.parseObject(response);
		if (jsonObject.containsKey("code")) {
			int code = jsonObject.getIntValue("code");
			if (code == 0) {
			}
		}
	}

}
