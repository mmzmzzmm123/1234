package com.ruoyi.system.components.spi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.ruoyi.common.utils.Env;
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
		if (Env.isLocal()) {
			List<ExtTgSelectRobotInfoListVO> data = new ArrayList<ExtTgSelectRobotInfoListVO>();
			for (String id : ids) {
				ExtTgSelectRobotInfoListVO v = new ExtTgSelectRobotInfoListVO();
				v.setRobotId(id);
				v.setHeadImgUrl(
						"https://axhub.im/ax9/03d5e6a9d22b1107/#id=jv175v&p=%E5%88%9B%E5%BB%BA%E7%82%92%E7%BE%A4%E4%BB%BB%E5%8A%A1&g=1");
				v.setUserName("xxx");
				v.setAccount("aaa");
				data.add(v);
			}
			return data;
		}

		ThirdTgSelectRobotInfoListDTO data = new ThirdTgSelectRobotInfoListDTO();
		data.setRobotSerialNos(ids);
		OpenApiResult<List<ExtTgSelectRobotInfoListVO>> ret = OpenApiClient.selectRobotInfoListByThirdUtchatTg(data);
		return ret.getData();
	}

}
