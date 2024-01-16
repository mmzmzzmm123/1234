package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.VibeRuleTargetParam;
import com.ruoyi.common.core.domain.dto.play.Performer;
import com.ruoyi.common.core.domain.dto.play.PlayDTO;
import com.ruoyi.common.core.domain.dto.play.VibeRuleDTO;
import com.ruoyi.common.core.domain.entity.VibeRule;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.callback.dto.Called1100910039DTO;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.components.RandomListPicker;
import com.ruoyi.system.domain.dto.GroupQueryDTO;
import com.ruoyi.system.domain.dto.play.PlayGroupInfo;
import com.ruoyi.system.domain.dto.play.PlayIntoGroupTask;
import com.ruoyi.system.domain.dto.play.PlayRobotGroupRelation;
import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;
import com.ruoyi.system.mapper.PlayGroupInfoMapper;
import com.ruoyi.system.mapper.PlayIntoGroupTaskMapper;
import com.ruoyi.system.mapper.PlayRobotGroupRelationMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgJoinChatroomByUrlInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.IVibeRuleService;
import com.ruoyi.system.service.RobotStatisticsService;
import com.ruoyi.system.service.business.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class IntoGroupService {

    @Autowired
    private PlayIntoGroupTaskMapper playIntoGroupTaskMapper;

    @Autowired
    private OpenApiClient openApiClient;

    @Autowired
    private IVibeRuleService vibeRuleService;

    @Autowired
    private RobotStatisticsService robotStatisticsService;

    @Autowired
    private GroupService groupService;

    @Autowired
    PlayGroupInfoMapper playGroupInfoMapper;

    @Autowired
    PlayRobotGroupRelationMapper playRobotGroupRelationMapper;


    public void  into(PlayDTO playDTO){
        List<PlayIntoGroupTask> playIntoGroupTasks = new ArrayList<>();
        VibeRuleDTO vibeRule = vibeRuleService.getOne();
        List<String> countys = new ArrayList<>();
        //是否设置目标国
        if (StringUtils.isNotEmpty(playDTO.getTargetCountyCode())&& vibeRule.getTargetParams() != null){
            List<String> allList = new ArrayList<>();
            List<String>  preferenceCodes = new ArrayList<>();
            for (VibeRuleTargetParam param:vibeRule.getTargetParams()){
                if (param.getAllState() == 1){
                    allList = param.getPreferenceCodes();
                    continue;
                }
                if (playDTO.getTargetCountyCode().equals(param.getCountryCode())){
                    preferenceCodes = param.getPreferenceCodes();
                }
            }
            if (preferenceCodes.size() != 0){
                countys = preferenceCodes;
            }else {
                countys = allList;
            }
        }
        List<Performer> performers = playDTO.getPerformerList();
        //判定是否是平台提供群
        if (playDTO.getGroupSource() == 0){
            GroupQueryDTO groupQueryDTO = new GroupQueryDTO();
            groupQueryDTO.setGroupNum(playDTO.getGroupNum());
            groupQueryDTO.setRegistrationDay(playDTO.getGroupDay());
            groupQueryDTO.setCountryCode(countys);
            //从波少那边获取足够的群
            R<List<GroupInfoVO>> groupList = groupService.queryGroup(groupQueryDTO);
            if (groupList.getCode() != 0){
                //设置错误
                return;
            }
            for (GroupInfoVO groupInfoVO:groupList.getData()){
                List<String> robotVOS= getRobot(playDTO,vibeRule);
                //拆分入群任务
                    for (Performer performer:performers){
                    PlayIntoGroupTask playIntoGroupTask = new PlayIntoGroupTask();
                    // 插入计划表
                    playIntoGroupTask.setId(IdUtils.fastUUID());
                    playIntoGroupTask.setGroupUrl(groupInfoVO.getGroupInviteLink());
                    playIntoGroupTask.setGroupName(groupInfoVO.getGroupName());
                    playIntoGroupTask.setCreateTime(new Date());
                    playIntoGroupTask.setModifyTime(new Date());
                    playIntoGroupTask.setPlayId(playDTO.getId());
                    playIntoGroupTask.setMerchantId(playDTO.getMerchantId());
                    if (performer.getIsAdmin() == 1){
                        int index = RandomListPicker.pickRandom(robotVOS);
                        playIntoGroupTask.setPersonId(robotVOS.get(index));
                        robotVOS.remove(index);
                        playIntoGroupTask.setTaskState(1);
                    }else {
                        int index = RandomListPicker.pickRandom(robotVOS);
                        playIntoGroupTask.setPersonId(robotVOS.get(index));
                        robotVOS.remove(index);
                        playIntoGroupTask.setTaskState(1);
                    }
                    playIntoGroupTasks.add(playIntoGroupTask);
                }
            }
        }else {
            //群链接入群
            List<String> groupUrls = playDTO.getGroupUrls();
            List<String> robotVOS= getRobot(playDTO,vibeRule);
            for (String group : groupUrls) {
                // 循环插入计划表
                for (Performer performer:performers){
                    // 插入计划表
                    PlayIntoGroupTask playIntoGroupTask = new PlayIntoGroupTask();
                    playIntoGroupTask.setId(IdUtils.fastUUID());
                    playIntoGroupTask.setGroupUrl(group);
                    playIntoGroupTask.setCreateTime(new Date());
                    playIntoGroupTask.setModifyTime(new Date());
                    playIntoGroupTask.setPlayId(playDTO.getId());
                    playIntoGroupTask.setMerchantId(playDTO.getMerchantId());
                    if (performer.getIsAdmin() == 1){
                        int index = RandomListPicker.pickRandom(robotVOS);
                        playIntoGroupTask.setPersonId(robotVOS.get(index));
                        robotVOS.remove(index);
                        playIntoGroupTask.setTaskState(1);
                    }else {
                        int index = RandomListPicker.pickRandom(robotVOS);
                        playIntoGroupTask.setPersonId(robotVOS.get(index));
                        robotVOS.remove(index);
                        playIntoGroupTask.setTaskState(1);
                    }
                    playIntoGroupTask.setTaskState(1);
                    playIntoGroupTasks.add(playIntoGroupTask);
                }
            }
        }
        playIntoGroupTaskMapper.batchInsert(playIntoGroupTasks);
    }

    public List<String> getRobot(PlayDTO playDTO,VibeRuleDTO vibeRule){
        //获取需要的机器人数量
        List<Performer> performers = playDTO.getPerformerList();
        //统计需要管理员权限的机器人
        Integer adminNum = 0,robotNum = 0;
        for (Performer performer:performers){
            if (performer.getIsAdmin() == 1){
                adminNum++;
            }else {
                robotNum++;
            }
        }
        //备用号逻辑
        if (playDTO.getPlayExt().getStandbyState() == 1){
            //获取备用号规则
            adminNum = adminNum+(adminNum*vibeRule.getStandbyNum());
        }
        List<String> countys = new ArrayList<>();
        //是否设置目标国
        if (StringUtils.isNotEmpty(playDTO.getTargetCountyCode())&& vibeRule.getTargetParams() != null){
            List<String> allList = new ArrayList<>();
            List<String>  preferenceCodes = new ArrayList<>();
            for (VibeRuleTargetParam param:vibeRule.getTargetParams()){
                if (param.getAllState() == 1){
                    allList = param.getPreferenceCodes();
                    continue;
                }
                if (playDTO.getTargetCountyCode().equals(param.getCountryCode())){
                    preferenceCodes = param.getPreferenceCodes();
                }
            }
            if (performers.size() != 0){
                countys = preferenceCodes;
            }else {
                countys = allList;
            }
        }
        Integer ipType = 0;
        //是否需要做IP离散
        if (vibeRule.getPeriodByIp() == 1){
            if (vibeRule.getPeriodByC() == 1){
                ipType = 2;
            }
            if (vibeRule.getPeriodByB() == 1){
                ipType = 1;
            }
        }
        GetRobotDTO adminDTO = new GetRobotDTO();
        //获取可以被设置管理员的机器人
        adminDTO.setCount(robotNum);
        adminDTO.setCountryCode(countys);
        adminDTO.setSetAdminCount(adminNum);
        adminDTO.setIpType(ipType);
        //调用获取机器人接口
        R<List<String>> robotAdminVOS =  robotStatisticsService.getRobot(adminDTO);
        if (robotAdminVOS.getCode() != 0){
            return null;
        }
        return robotAdminVOS.getData();
    }


    public R<Void> intoGroupTask() {
        log.info("执行入群任务{}");
        try {
            // 查询待执行的计划
            List<PlayIntoGroupTask> personIntoGroupTasks = playIntoGroupTaskMapper.selectTaskList();
            if (personIntoGroupTasks == null || personIntoGroupTasks.size() == 0) {
                return R.ok();
            }
            for (PlayIntoGroupTask task : personIntoGroupTasks) {
                    // 调用执行入群接口
                    ThirdTgJoinChatroomByUrlInputDTO dto = new ThirdTgJoinChatroomByUrlInputDTO();
                    dto.setUrl(task.getGroupUrl());
                    dto.setTgRobotId(task.getPersonId());
                    log.info("调用入群接口入参{}", dto);
                    OpenApiResult<TgBaseOutputDTO> resultBody = openApiClient.joinChatroomByUrlByThirdKpTg(dto);
                    log.info("调用入群接口返回{}", resultBody);
                    // 同步返回失败
                    if (resultBody.getCode() != 0 || resultBody.getData() == null) {
                        task.setTaskState(4);
                        if (resultBody.getData() != null) {
                            TgBaseOutputDTO channelOutputDTO = resultBody.getData();
                            task.setCode(channelOutputDTO.getOptSerNo());
                            task.setFailCause(resultBody.getMessage());
                        }
                    } else {
                        task.setTaskState(2);
                        TgBaseOutputDTO channelOutputDTO = resultBody.getData();
                        task.setCode(channelOutputDTO.getOptSerNo());
                    }
                    task.setModifyTime(new Date());
                    playIntoGroupTaskMapper.updateById(task);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    //入群回调处理方法
    public void intoGroupCallback(Called1100910039DTO dto,CalledDTO calledDTO){
        log.info("入群回调{}", calledDTO);
        if (StringUtils.isEmpty(calledDTO.getOptSerNo())) {
            return;
        }
        //根据操作编码查询入群记录
        PlayIntoGroupTask task = playIntoGroupTaskMapper.selectTaskByCode(calledDTO.getOptSerNo());
        if (task == null) {
            log.info("操作编码不存在{}", calledDTO.getOptSerNo());
            return;
        }
        //返回入群失败
        if (calledDTO.getResultCode() != 0) {
            task.setTaskState(4);
            task.setFailCause(calledDTO.getResultMsg());
            playIntoGroupTaskMapper.updateById(task);
            return;
        }
        //入群成功 查询群信息
        //添加等待锁
//        ClusterLock.ofAwait().lock("linkmaster:atmosphere:waitLock:intoGroupCallback" + dto.getGroupId(), 60);
        try {
            PlayGroupInfo groupInfo = playGroupInfoMapper.selectGroupInfoById(dto.getChatroomSerialNo());
            if (groupInfo == null) {
                //创建群信息
                groupInfo = getGroupInfo(dto,task);
                playGroupInfoMapper.insert(groupInfo);
            }
            //查询当前机器人是否已和群做绑定
            Integer count = playRobotGroupRelationMapper.selectRobotGroup(dto.getChatroomSerialNo(), task.getPersonId());
            if (count == 0) {
                //绑定机器人和群信息记录表
                PlayRobotGroupRelation robotGroupRelation = new PlayRobotGroupRelation();
                robotGroupRelation.setPlayRobotGroupRelationId(IdUtils.fastUUID());
                robotGroupRelation.setMerchantId(task.getMerchantId());
                robotGroupRelation.setRobotId(task.getPersonId());
                robotGroupRelation.setGroupId(dto.getChatroomSerialNo());
                robotGroupRelation.setState(1);
                robotGroupRelation.setIsDelete(0);
                playRobotGroupRelationMapper.insert(robotGroupRelation);
            }
            //查询群内机器人数量
//            Integer robotCount = playRobotGroupRelationMapper.selectRobotGroupCount(dto.getChatroomSerialNo());
//            groupInfo.set(robotCount);
//            groupInfoMapper.updateById(groupInfo);
            //修改入群任务状态
            task.setTaskState(3);
            task.setGroupName(dto.getChatroomName());
            task.setGroupId(dto.getChatroomName());
            playIntoGroupTaskMapper.updateById(task);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            ClusterLock.ofAwait().unLock("linkmaster:atmosphere:waitLock:intoGroupCallback" + dto.getGroupId());
        }

    }

    public PlayGroupInfo getGroupInfo(Called1100910039DTO dto,PlayIntoGroupTask task) {
        PlayGroupInfo groupInfo = new PlayGroupInfo();
        groupInfo.setGroupId(IdUtils.fastUUID());
        groupInfo.setMerchantId(task.getMerchantId());
        groupInfo.setGroupUrl(task.getGroupUrl());
        groupInfo.setTgGroupId(dto.getChatroomSerialNo());
        groupInfo.setTgGroupName(dto.getChatroomName());
//        groupInfo.set(1);
        groupInfo.setIsDelete(0);
        return groupInfo;
    }


    //退群处理
    public void outGroup(){

    }

}