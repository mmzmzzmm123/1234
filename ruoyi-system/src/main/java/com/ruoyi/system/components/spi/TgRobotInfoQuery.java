package com.ruoyi.system.components.spi;

import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgSelectRobotInfoListDTO;
import com.ruoyi.system.openapi.model.output.ExtTgSelectRobotInfoListVO;

@SPI("TgRobotInfoQuery")
public class TgRobotInfoQuery implements RobotInfoQuery {

	@Override
	public List<ExtTgSelectRobotInfoListVO> listById(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}

		ThirdTgSelectRobotInfoListDTO data = new ThirdTgSelectRobotInfoListDTO();
		data.setRobotSerialNos(ids);
		OpenApiResult<List<ExtTgSelectRobotInfoListVO>> ret = OpenApiClient.selectRobotInfoListByThirdUtchatTg(data);
		return ret.getData();
	}

}
