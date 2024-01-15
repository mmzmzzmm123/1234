package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.robot.*;
import com.ruoyi.system.domain.vo.robot.SelectRobotListVO;

import java.util.List;

public interface IRobotService {
    R<Page<SelectRobotListVO>> selectRobotPageList(SelectRobotListDTO dto);
    R<Void> syncRobot();
    R<Void> setHeadImg(SetHeadImgDTO dto);
    R<Void> setName(List<SetNameInfoDTO> dto);
    R<Void> setUserName(SetUserNameDTO dto);
    R<Void> setPrivatePhone(SetPrivatePhoneDTO dto);
    R<Void> updateEnableType(UpdateEnableTypeDTO dto);
    R<Void> clearSealData();
    R<Void> removeRobot(RemoveRobotDTO dto);
    R<Void> releaseOccupyRobot();
}
