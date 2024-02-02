package com.ruoyi.system.components.spi;

import java.util.Date;
import java.util.Map;

import com.ruoyi.common.utils.Ids;
import com.ruoyi.system.service.PlayRobotPackLogService;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyRobotHeadImgInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;

@SPI("TgRobotImgSettings")
public class TgRobotImgSettings implements Settings {

	@Override
	public PlayRobotPackLog set(Map<String, Object> param) {
		ThirdTgModifyRobotHeadImgInputDTO dto = new ThirdTgModifyRobotHeadImgInputDTO();
		if (param.containsKey(Settings.Key_Img)) {
			dto.setHeadimgUrl(param.get(Settings.Key_Img).toString());
		}
		dto.setTgRobotId(param.get(Settings.Key_RobotId).toString());
		PlayRobotPackLog data = new PlayRobotPackLog();
		data.setOpt(Ids.getId());
		data.setChatroomId(param.get(Settings.Key_GroupId).toString());
		data.setCreateTime(new Date());
		data.setIsFinish(0);
		// 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员  5-获取群成员
		data.setOp(2);
		data.setPlayId(param.get(Settings.Key_PlayId).toString());
		data.setRetryCount(0);
		data.setRobotId(dto.getTgRobotId());
		data.setIsBackup(((boolean) param.get(Settings.Key_Backup_Flag)) ? 1 : 0);
		data.setStatus(0);
		SpringUtils.getBean(PlayRobotPackLogService.class).saveOrUpdate(data);
		dto.setExtend(data.getOpt());
		@SuppressWarnings("rawtypes")
		OpenApiResult<TgBaseOutputDTO> ret = OpenApiClient.modifyRobotHeadImgByThirdKpTg(dto);
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
