package com.ruoyi.system.components.spi;

import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgSetChatroomAdminInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;

@SPI("TgRobotAdminSettings")
public class TgRobotAdminSettings implements Settings {

	@Override
	public PlayRobotPackLog set(Map<String, Object> param) {
		final String groupId = SpringUtils.getBean(GroupInfoMapper.class).selectById(param.get(Settings.Key_GroupId).toString()).getGroupSerialNo();
		
		ThirdTgSetChatroomAdminInputDTO dto = new ThirdTgSetChatroomAdminInputDTO();
		// 先获取成员hash值
		dto.setMemberUserAccessHash(Objects.wrapNull(param.get(Settings.Key_AttachContent), "").toString());
		dto.setTgRobotId(param.get(Settings.Key_RobotId).toString());
		dto.setChatroomSerialNo(groupId);
		dto.setAddAdmins(true);
		// 成员编号（需要设置成管理员的成员编号）
		dto.setMemberSerialNo(dto.getTgRobotId());
		@SuppressWarnings("rawtypes")
		OpenApiResult<TgBaseOutputDTO> ret = OpenApiClient.setChatroomAdminByThirdKpTg(dto);
		PlayRobotPackLog data = new PlayRobotPackLog();
		data.setChatroomId(param.get(Settings.Key_GroupId).toString());
		data.setCreateTime(new Date());
		data.setIsFinish(0);
		// 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群管理员
		data.setOp(2);
		data.setPlayId(param.get(Settings.Key_PlayId).toString());
		data.setRetryCount(0);
		data.setRobotId(dto.getTgRobotId());
		data.setIsBackup(((boolean) param.get(Settings.Key_Backup_Flag)) ? 1 : 0);

		if (ret.getData() != null && !StringUtils.isEmpty(ret.getData().getOptSerNo())) {
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
