package com.ruoyi.system.components.spi;

import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyNameInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;

@SPI("TgRobotNameSettings")
public class TgRobotNameSettings implements Settings {

	@Override
	public PlayRobotPackLog set(Map<String, Object> param) {
		ThirdTgModifyNameInputDTO dto = new ThirdTgModifyNameInputDTO();
		if (param.containsKey(Settings.Key_Names1)) {
			dto.setFirstNameBase64(param.get(Settings.Key_Names1).toString());
		}
		if (param.containsKey(Settings.Key_Names2)) {
			dto.setLastNameBase64(param.get(Settings.Key_Names2).toString());
		}
		dto.setTgRobotId(param.get(Settings.Key_RobotId).toString());
		@SuppressWarnings("rawtypes")
		OpenApiResult<TgBaseOutputDTO> ret = OpenApiClient.modifyNameByThirdKpTg(dto);
		PlayRobotPackLog data = new PlayRobotPackLog();
		data.setChatroomId(param.get(Settings.Key_GroupId).toString());
		data.setCreateTime(new Date());
		data.setIsFinish(0);
		data.setOp(1);
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
