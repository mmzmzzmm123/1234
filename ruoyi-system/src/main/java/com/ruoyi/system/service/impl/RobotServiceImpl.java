package com.ruoyi.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.dto.robot.*;
import com.ruoyi.system.domain.vo.robot.SelectRobotListVO;
import com.ruoyi.system.mapper.RobotMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgBatchRobotSimpInfoInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgSelectRobotListByRadioDTO;
import com.ruoyi.system.openapi.model.output.ExtTgBatchRobotSimpInfoData;
import com.ruoyi.system.openapi.model.output.ExtTgSelectRobotByMerchantVO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.IRobotService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
        ThirdTgSelectRobotListByRadioDTO robotDTO = new ThirdTgSelectRobotListByRadioDTO();
        robotDTO.setLimit(1000);
        robotDTO.setPage(1);
        OpenApiResult<Page<ExtTgSelectRobotByMerchantVO>> robotListResult = OpenApiClient.selectRobotListByRadioByThirdUtchatTg(robotDTO);
        log.info("pullApiRobotDataList robotListResult:{}",JSON.toJSONString(robotListResult));
        if(robotListResult.isSuccess()){
            Page<ExtTgSelectRobotByMerchantVO> data = robotListResult.getData();
            this.pullRobotDataList(data.getRecords());
            long count = data.getTotal()/robotDTO.getLimit() - 1;
            if(count > 0){
                for (long i = 0; i < count; i++) {
                    robotDTO.setPage(robotDTO.getPage()+1);
                    OpenApiResult<Page<ExtTgSelectRobotByMerchantVO>> pageOpenApiResult = OpenApiClient.selectRobotListByRadioByThirdUtchatTg(robotDTO);
                    log.info("pullApiRobotDataList pageOpenApiResult:{}",JSON.toJSONString(pageOpenApiResult));
                    if(pageOpenApiResult.isSuccess()){
                        Page<ExtTgSelectRobotByMerchantVO> pageData = pageOpenApiResult.getData();
                        this.pullRobotDataList(pageData.getRecords());
                    }

                }
            }
        }
        return R.ok();
    }

    private void pullRobotDataList(List<ExtTgSelectRobotByMerchantVO> dataList){
        if(CollectionUtils.isEmpty(dataList)){
            log.info("List ExtTgSelectRobotByMerchantVO 等于空");
            return;
        }
        List<String> robotSerialNos = dataList.stream().map(ExtTgSelectRobotByMerchantVO::getRobotSerialNo).collect(Collectors.toList());
        Map<String, ExtTgBatchRobotSimpInfoData> simpMap = this.pullApiRobotSimpMap(robotSerialNos);
        log.info("pullRobotDataList robotSerialNos:{},simpMap:{},",robotSerialNos,simpMap);
        List<Robot> robotList = Lists.newArrayList();
        for (ExtTgSelectRobotByMerchantVO vo : dataList) {
            Robot robot = JSON.parseObject(JSON.toJSONString(vo), Robot.class);
            robot.setPhone(vo.getAccount());
            robot.setId(vo.getRobotId());
            if(simpMap.containsKey(robot.getRobotSerialNo())){
                ExtTgBatchRobotSimpInfoData mapData = simpMap.get(robot.getRobotSerialNo());
                robot.setTgAppVersion(mapData.getTgAppVersion());
                robot.setProtocolType(mapData.getProtocolType());
                robot.setProxyType(mapData.getProxyType());
                robot.setVpnIp(mapData.getSenderProxyIp());
                if(StringUtils.isNotEmpty(mapData.getSenderProxyIp())){
                    String[] split = mapData.getSenderProxyIp().split(".");
                    robot.setVpnIpB(split[0]+"."+split[1]);
                    robot.setVpnIpB(split[0]+"."+split[1]+"."+split[2]);
                }
            }
            robotList.add(robot);
        }
        this.storyRobot(robotList);
    }

    private void storyRobot(List<Robot> newRobotList){
        List<String> newRobotSerialNos = newRobotList.stream().map(Robot::getRobotSerialNo).collect(Collectors.toList());
        List<Robot> oldRobotList = robotMapper.selectList(new LambdaUpdateWrapper<Robot>().in(Robot::getRobotSerialNo, newRobotSerialNos));
        if(CollectionUtils.isEmpty(oldRobotList)){
            this.saveBatch(newRobotList);
            return;
        }
        List<Robot> saveRobotList = Lists.newArrayList();
        List<Robot> updateRobotList = Lists.newArrayList();
        Map<String,Robot> oldMap = new HashMap<>();
        oldRobotList.forEach(item->oldMap.put(item.getRobotSerialNo(),item));
        for (Robot newRobot : newRobotList) {
            if(!oldMap.containsKey(newRobot.getRobotSerialNo())){
                newRobot.setUpdateTime(new Date());
                newRobot.setCreateTime(new Date());
                saveRobotList.add(newRobot);
                continue;
            }
            Boolean flag = false;
            Robot oldRobot = oldMap.get(newRobot.getRobotSerialNo());
            if(modify(oldRobot.getUserName(),newRobot.getUserName())){
                oldRobot.setUserName(newRobot.getUserName());
                flag = true;
            }
            if(modify(oldRobot.getFirstName(),newRobot.getFirstName())){
                oldRobot.setFirstName(newRobot.getFirstName());
                flag = true;
            }
            if(modify(oldRobot.getLastName(),newRobot.getLastName())){
                oldRobot.setLastName(newRobot.getLastName());
                flag = true;
            }
            if(modify(oldRobot.getPhone(),newRobot.getPhone())){
                oldRobot.setPhone(newRobot.getPhone());
                flag = true;
            }
            if(modify(oldRobot.getHeadImgUrl(),newRobot.getHeadImgUrl())){
                oldRobot.setHeadImgUrl(newRobot.getHeadImgUrl());
                flag = true;
            }
            if(modify(oldRobot.getCountry(),newRobot.getCountry())){
                oldRobot.setCountry(newRobot.getCountry());
                flag = true;
            }
            if(modify(oldRobot.getCountryCode(),newRobot.getCountryCode())){
                oldRobot.setCountryCode(newRobot.getCountryCode());
                flag = true;
            }
            if(modify(oldRobot.getType(),newRobot.getType())){
                oldRobot.setType(newRobot.getType());
                flag = true;
            }
            if(modify(oldRobot.getHeartbeatStatus(),newRobot.getHeartbeatStatus())){
                oldRobot.setHeartbeatStatus(newRobot.getHeartbeatStatus());
                flag = true;
            }
            if(modify(oldRobot.getSealStatus(),newRobot.getSealStatus())){
                oldRobot.setSealStatus(newRobot.getSealStatus());
                flag = true;
            }
            if(modify(oldRobot.getProtocolType(),newRobot.getProtocolType())){
                oldRobot.setProtocolType(newRobot.getProtocolType());
                flag = true;
            }
            if(modify(oldRobot.getProxyType(),newRobot.getProxyType())){
                oldRobot.setProxyType(newRobot.getProxyType());
                flag = true;
            }
            if(modify(oldRobot.getTgAppVersion(),newRobot.getTgAppVersion())){
                oldRobot.setTgAppVersion(newRobot.getTgAppVersion());
                flag = true;
            }
            if(modify(oldRobot.getVpnIp(),newRobot.getVpnIp())){
                oldRobot.setVpnIp(newRobot.getVpnIp());
                flag = true;
            }
            if(modify(oldRobot.getVpnIpB(),newRobot.getVpnIpB())){
                oldRobot.setVpnIpB(newRobot.getVpnIpB());
                flag = true;
            }
            if(modify(oldRobot.getVpnIpC(),newRobot.getVpnIpC())){
                oldRobot.setVpnIpC(newRobot.getVpnIpC());
                flag = true;
            }
            if(flag){
                oldRobot.setUpdateTime(new Date());
                updateRobotList.add(oldRobot);
            }
        }
        if(!CollectionUtils.isEmpty(saveRobotList)){
            this.saveBatch(saveRobotList);
        }
        if(!CollectionUtils.isEmpty(updateRobotList)){
            this.updateBatchById(updateRobotList);
        }
    }

    private Boolean modify(Object oldData,Object newData){
        if(ObjectUtil.isEmpty(oldData) && ObjectUtil.isEmpty(newData)){
            return false;
        }
        if(ObjectUtil.isEmpty(oldData) && !ObjectUtil.isEmpty(newData)){
            return true;
        }
        if(!ObjectUtil.isEmpty(oldData) && ObjectUtil.isEmpty(newData)){
            return true;
        }
        if(!oldData.equals(newData)){
            return true;
        }
        return false;
    }

    private Map<String,ExtTgBatchRobotSimpInfoData> pullApiRobotSimpMap(List<String> robotSerialNos){
        ThirdTgBatchRobotSimpInfoInputDTO dto = new ThirdTgBatchRobotSimpInfoInputDTO();
        dto.setRobotSerialNos(robotSerialNos);
        OpenApiResult<TgBaseOutputDTO<List<ExtTgBatchRobotSimpInfoData>>> result = OpenApiClient.batchRobotSimpInfoByThirdKpTg(dto);
        Map<String,ExtTgBatchRobotSimpInfoData> map = new HashMap<>();
        if(result.isSuccess()){
            List<ExtTgBatchRobotSimpInfoData> data = result.getData().getData();
            data.forEach(item->map.put(item.getRobotSerialNo(),item));
            return map;
        }
        return map;
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

    @Override
    public R<Void> setGroupOwner(List<String> robotSerialNos) {
        if(CollectionUtils.isEmpty(robotSerialNos)){
            return R.ok();
        }
        this.update(new LambdaUpdateWrapper<Robot>().in(Robot::getRobotSerialNo,robotSerialNos).set(Robot::getGroupOwner,1))
        return R.ok();
    }
}
