package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.system.domain.dto.robot.*;
import com.ruoyi.system.domain.vo.robot.SelectRobotListVO;
import com.ruoyi.system.mapper.RobotMapper;
import com.ruoyi.system.service.IRobotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class RobotServiceImpl extends ServiceImpl<RobotMapper, Robot> implements IRobotService {
    @Resource
    private RobotMapper robotMapper;

    @Override
    public R<Page<SelectRobotListVO>> selectRobotPageList(SelectRobotListDTO dto) {
        Page<SelectRobotListVO> page = new Page<>(dto.getPage(),dto.getLimit());
        List<SelectRobotListVO> selectRobotListVOS = robotMapper.selectRobotPageList(page, dto);
        page.setRecords(selectRobotListVOS);
        return R.ok(page);
    }

    @Override
    public R<Void> syncRobot() {
        return null;
    }

    @Override
    public R<Void> setHeadImg(SetHeadImgDTO dto) {
        return null;
    }

    @Override
    public R<Void> setName(List<SetNameInfoDTO> dto) {
        return null;
    }

    @Override
    public R<Void> setUserName(SetUserNameDTO dto) {
        return null;
    }

    @Override
    public R<Void> setPrivatePhone(SetPrivatePhoneDTO dto) {
        return null;
    }

    @Override
    public R<Void> updateEnableType(UpdateEnableTypeDTO dto) {
        if(CollectionUtils.isEmpty(dto.getRobotSerialNos())){
            return R.fail("号编号不能为空");
        }
        if(dto.getEnableType() == null){
            return R.fail("类型不能为空");
        }
        boolean update = this.update(new LambdaUpdateWrapper<Robot>()
                .in(Robot::getEnableType, dto.getRobotSerialNos())
                .set(Robot::getEnableType, dto.getEnableType()));
        if(update)return R.ok();
        return R.fail("操作失败");
    }

    @Override
    public R<Void> clearSealData() {
        return null;
    }

    @Override
    public R<Void> removeRobot(RemoveRobotDTO dto) {
        return null;
    }

    @Override
    public R<Void> releaseOccupyRobot() {
        return null;
    }
}
