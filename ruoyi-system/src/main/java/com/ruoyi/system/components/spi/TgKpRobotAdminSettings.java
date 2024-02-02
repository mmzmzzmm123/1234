package com.ruoyi.system.components.spi;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.Env;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.mapper.GroupRobotMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyNameInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgSetChatroomAdminInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgSqlTaskSubmitInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.PlayRobotPackLogService;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@SPI("TgKpRobotAdminSettings")
@Slf4j
public class TgKpRobotAdminSettings implements Settings {
	@Override
	public PlayRobotPackLog set(Map<String, Object> param) {
		@SuppressWarnings("rawtypes")
		OpenApiResult<TgBaseOutputDTO> ret = new OpenApiResult<>();
		final String groupId = SpringUtils.getBean(GroupInfoMapper.class).selectById(param.get(Settings.Key_GroupId).toString()).getGroupSerialNo();
		final GroupRobot groupOwnerRobot = SpringUtils.getBean(GroupRobotMapper.class).selectOne(new LambdaQueryWrapper<GroupRobot>().eq(GroupRobot::getGroupId, param.get(Settings.Key_GroupId).toString()).eq(GroupRobot::getMemberType, 1).last(" limit 1 "));
		if(groupOwnerRobot == null){
			String trace = Ids.getId();
			ret.setMessage("接口异常: 群主号信息未同步");
			ret.setCode(1);
			log.error("sqlTaskSubmitByThirdKpTg_error {} {}" , trace , groupId);
		}
		final String robotId = param.get(Settings.Key_RobotId).toString();
		ThirdTgSetChatroomAdminInputDTO dto = new ThirdTgSetChatroomAdminInputDTO();
		dto.setTgRobotId(groupOwnerRobot.getRobotId());
		dto.setChatroomSerialNo(groupId);
		dto.setMemberSerialNo(robotId);
		dto.setIsAll(true);
		dto.setChangeInfo(true);
		dto.setDeleteMessages(true);
		dto.setBanUsers(true);
		dto.setInviteUsers(true);
		dto.setPinMessages(true);
		dto.setManageCall(true);
		dto.setAnonymous(true);
		dto.setAddAdmins(true);
		ISysConfigService sysConfigService = SpringUtils.getBean(SysConfigServiceImpl.class);
		if(ObjectUtil.equal("1", sysConfigService.selectConfigByKey("setBotAdmin:para"))){
			dto.setAnonymous(false);
			dto.setAddAdmins(false);
			dto.setNotModifyPermissions(true);
		}
		dto.setMemberUserAccessHash(ObjectUtil.isNotEmpty(param.get(Settings.Key_AttachContent))?param.get(Settings.Key_AttachContent).toString(): null);

		PlayRobotPackLog data = new PlayRobotPackLog();
		data.setOpt(Ids.getId());
		data.setChatroomId(param.get(Settings.Key_GroupId).toString());
		data.setCreateTime(new Date());
		data.setIsFinish(0);
		//1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员 5-获取群成员
		data.setOp(4);
		data.setPlayId(param.get(Settings.Key_PlayId).toString());
		data.setRetryCount(0);
		data.setRobotId(dto.getTgRobotId());
		data.setAttchContent(dto.getMemberUserAccessHash());
		data.setIsBackup(((boolean) param.get(Settings.Key_Backup_Flag)) ? 1 : 0);
		data.setStatus(0);
		SpringUtils.getBean(PlayRobotPackLogService.class).saveOrUpdate(data);

		dto.setExtend(data.getOpt());
		if(ret.isSuccess()) {
			ret = OpenApiClient.setChatroomAdminByThirdKpTg(dto);
		}

		if (ret.getData() != null && !StringUtils.isEmpty(ret.getData().getOptSerNo()) && ret.isSuccess()) {
			// 成功
			data.setStatus(0);
			data.setKpOpt(ret.getData().getOptSerNo());
			return data;
		}
		// 失败
		data.setStatus(2);
		data.setErrMsg(ret.getMessage());
		return data;
	}

}
