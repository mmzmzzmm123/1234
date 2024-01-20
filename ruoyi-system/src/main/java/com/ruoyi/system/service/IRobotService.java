package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.system.callback.dto.Called1100910101DTO;
import com.ruoyi.system.domain.dto.robot.*;
import com.ruoyi.system.domain.vo.play.RobotStatisticsVO;
import com.ruoyi.system.domain.vo.robot.SelectRobotListVO;
import com.ruoyi.system.domain.vo.robot.SetNameResourceVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRobotService extends IService<Robot> {
    /**
     * 查询号列表
     * @param dto
     * @return
     */
    R<Page<SelectRobotListVO>> selectRobotPageList(SelectRobotListDTO dto);

    /**
     * 同步账号
     * @return
     */
    R<Void> syncRobot();

    /**
     * 设置头像
     * @param dto
     * @return
     */
    R<Void> setHeadImg(SetHeadImgDTO dto);
    R<Void> setName(SetNameInfoDTO dto);
    List<SetNameResourceVO> analysisExcelInfo(MultipartFile file);

    /**
     * 设置用户名
     * @param dto
     * @return
     */
    R<Void> setUserName(SetUserNameDTO dto);
    R<Void> setPrivatePhone(SetPrivatePhoneDTO dto);

    /**
     * 修改禁用启用状态
     * @param dto
     * @return
     */
    R<Void> updateEnableType(UpdateEnableTypeDTO dto);

    /**
     * 一键清除封号数据
     * @return
     */
    R<Void> clearSealData(ClearSealDataDTO dto);
    R<Void> removeRobot(RemoveRobotDTO dto);

    /**
     * 一键释放占用水军
     * @param dto
     * @return
     */
    R<Void> releaseOccupyRobot(ReleaseOccupyRobotDTO dto);

    /**
     * 设置群主号
     * @param robotSerialNos
     * @return
     */
    R<Void> setGroupOwner(List<String> robotSerialNos);

    /**
     * 统计这些机器人的状态
     * @param robotIds
     * @return
     */
    RobotStatisticsVO getRobotStatisticsVO(List<String> robotIds);

    void updateUsername(String robotSerialNo,String userName);

    void updateHeadImgUrl(String robotSerialNo,String headImgUrl);

    void updateName(String robotSerialNo,SetNameResourceVO vo);

    /**
     * 号回收
     * @param robotSerialNos
     */
    void recycleRobot(List<String> robotSerialNos);

    /**
     * 号商家变更
     */
    void updateRobotMerchant(List<String> robotSerialNos);

    void updateBidirectional(Called1100910101DTO dto);

}
