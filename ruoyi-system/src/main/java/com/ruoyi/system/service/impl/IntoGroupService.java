package com.ruoyi.system.service.impl;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.VibeRuleTargetParam;
import com.ruoyi.common.core.domain.dto.play.Performer;
import com.ruoyi.common.core.domain.dto.play.PlayDTO;
import com.ruoyi.common.core.domain.dto.play.PlayExt;
import com.ruoyi.common.core.domain.dto.play.VibeRuleDTO;
import com.ruoyi.common.core.domain.entity.VibeRule;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayGroupPack;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPack;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.callback.dto.Called1100910039DTO;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.callback.dto.CalledEmptyDTO;
import com.ruoyi.system.components.RandomListPicker;
import com.ruoyi.system.domain.dto.GroupQueryDTO;
import com.ruoyi.system.domain.dto.play.PlayGroupInfo;
import com.ruoyi.system.domain.dto.play.PlayIntoGroupTask;
import com.ruoyi.system.domain.dto.play.PlayModifierGroupLog;
import com.ruoyi.system.domain.dto.play.PlayRobotGroupRelation;
import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.GroupRobotVO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgJoinChatroomByUrlInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyChatroomHeadImageInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyChatroomNameInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.IVibeRuleService;
import com.ruoyi.system.service.RobotStatisticsService;
import com.ruoyi.system.service.business.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class IntoGroupService {

    @Autowired
    private PlayIntoGroupTaskMapper playIntoGroupTaskMapper;

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

    @Autowired
    PlayMapper playMapper;

    @Autowired
    PlayRobotPackMapper playRobotPackMapper;

    @Autowired
    PlayGroupPackMapper playGroupPackMapper;

    @Autowired
    PlayModifierGroupLogMapper playModifierGroupLogMapper;

    public void optionGroup(){
        //扫描剧本状态为调用中 未开始的剧本
        List<Play> playList = playMapper.selectIntoGroupList(1,0);
        if (playList == null || playList.size() == 0){
            return;
        }
        for (Play play:playList){
            //判断剧本是否是外部
            if (play.getGroupSource() == 1){
                //修改剧本状态为入群中
                play.setScanProgress(2);
                playMapper.updateById(play);
                continue;
            }
            //判断剧本是否需要设置群头像跟群名称
            PlayGroupPack playGroupPack =  playGroupPackMapper.selectGroupPackByPlayId(play.getId());
            //设置群头像和群名称
            if (playGroupPack == null){
                play.setScanProgress(2);
                playMapper.updateById(play);
                continue;
            }
            //修改剧本状态为入群中
            play.setScanProgress(1);
            playMapper.updateById(play);
            GroupQueryDTO groupQueryDTO = new GroupQueryDTO();
            groupQueryDTO.setGroupNum(play.getGroupNum());
            groupQueryDTO.setRegistrationDay(play.getGroupDay());
            groupQueryDTO.setCountryCode(getCountys(play));
            //从波少那边获取足够的群
            R<List<GroupInfoVO>> groupList = groupService.queryGroup(groupQueryDTO);
            if (groupList.getCode() != 0){
                play.setState(3);
                play.setFailReason("无剧本所需足够的群！");
                playMapper.updateById(play);
            }

            List<String> imgs = Arrays.asList(playGroupPack.getPic().split(","));
            List<String> names =  Arrays.asList(playGroupPack.getName().split(","));
            //循环群信息
            List<GroupInfoVO> groupInfoVOS = groupList.getData();
            for (GroupInfoVO groupInfoVO:groupInfoVOS){
                ThirdTgModifyChatroomHeadImageInputDTO dto = new ThirdTgModifyChatroomHeadImageInputDTO();
                dto.setChatroomSerialNo(groupInfoVO.getGroupSerialNo());
                //获取群主号
                String tgRobotId = "";
                for (GroupRobotVO robotVO:groupInfoVO.getRobots()){
                    if (robotVO.getMemberType() == 1){
                        tgRobotId = robotVO.getRobotId();
                    }
                }
                dto.setTgRobotId(tgRobotId);
                int index = RandomListPicker.pickRandom(imgs);
                dto.setUrl(imgs.get(index));
                //修改群名称
                ThirdTgModifyChatroomNameInputDTO inputDTO = new ThirdTgModifyChatroomNameInputDTO();
                inputDTO.setChatroomNameBase64(Base64.encode(names.get(index).getBytes()));
                inputDTO.setChatroomSerialNo(groupInfoVO.getGroupSerialNo());
                inputDTO.setTgRobotId(tgRobotId);
                OpenApiResult<TgBaseOutputDTO>  openApiResult =  OpenApiClient.modifyChatroomNameByThirdKpTg(inputDTO);
                if (openApiResult.getCode() != 0){
                    String optSerNo = null;
                    if (openApiResult.getData() != null){
                        optSerNo = openApiResult.getData().getOptSerNo();
                    }
                    //修改群名称失败 需要换群
                    getLog(groupInfoVO,play,tgRobotId,optSerNo,3);
                    continue;
                }
                OpenApiResult<TgBaseOutputDTO> dtoOpenApiResult =  OpenApiClient.modifyChatroomHeadImageByThirdKpTg(dto);
                String optSerNo = null;
                if (dtoOpenApiResult.getData() != null){
                    optSerNo = dtoOpenApiResult.getData().getOptSerNo();
                }
                if (dtoOpenApiResult.getCode() != 0){
                    //修改群名称失败 需要换群
                    getLog(groupInfoVO,play,tgRobotId,optSerNo,3);
                    continue;
                }
                getLog(groupInfoVO,play,tgRobotId,optSerNo,1);
            }
        }
    }

    public void getLog(GroupInfoVO groupInfoVO,Play play,String tgRobotId,String optSerNo,Integer state){
        PlayModifierGroupLog playModifierGroupLog = new PlayModifierGroupLog();
        playModifierGroupLog.setId(IdUtils.fastUUID());
        playModifierGroupLog.setGroupId(groupInfoVO.getGroupId());
        playModifierGroupLog.setGroupSerialNo(groupInfoVO.getGroupSerialNo());
        playModifierGroupLog.setCreateTime(new Date());
        playModifierGroupLog.setOptSerNo(optSerNo);
        playModifierGroupLog.setPlayId(play.getId());
        playModifierGroupLog.setGroupInviteLink(groupInfoVO.getGroupInviteLink());
        playModifierGroupLog.setRobotId(tgRobotId);
        playModifierGroupLog.setState(state);
        playModifierGroupLogMapper.insert(playModifierGroupLog);
    }

    public List<String> getCountys(Play play){
        VibeRuleDTO vibeRule = vibeRuleService.getOne();
        List<String> countys = new ArrayList<>();
        //是否设置目标国
        if (StringUtils.isNotEmpty(play.getTargetCountyCode())&& vibeRule.getTargetParams() != null){
            List<String> allList = new ArrayList<>();
            List<String>  preferenceCodes = new ArrayList<>();
            for (VibeRuleTargetParam param:vibeRule.getTargetParams()){
                if (param.getAllState() == 1){
                    allList = param.getPreferenceCodes();
                    continue;
                }
                if (play.getTargetCountyCode().equals(param.getCountryCode())){
                    preferenceCodes = param.getPreferenceCodes();
                }
            }
            if (preferenceCodes.size() != 0){
                countys = preferenceCodes;
            }else {
                countys = allList;
            }
        }
        return countys;
    }

    public void  modifierGroup(){
        //查询剧本状态为调度中 修改群信息状态的剧本
        List<Play> playList = playMapper.selectIntoGroupList(1,1);
        if (playList == null || playList.size() == 0){
            return;
        }
        for (Play play:playList){
            if (play.getState() != 1 || play.getIsDelete() == 1){
                return;
            }
            //获取剧本中所需要的群数
            Integer groupNum = play.getGroupNum();
            //扫描群已设置成功的数据
           List<PlayModifierGroupLog> playModifierGroupLogs = playModifierGroupLogMapper.selectGroupLogByPlayId(play.getId());
           if (playModifierGroupLogs == null){
               continue;
           }
           //获取到足够的群资源修改剧本状态
           if (playModifierGroupLogs.size() >= groupNum){
               play.setScanProgress(2);
               playMapper.updateById(play);
               return;
           }
            //查询剧本对应的所有记录
            List<PlayModifierGroupLog> logs = playModifierGroupLogMapper.selectGroupLogByPlayIdAll(play.getId());
            //获取发送群数
             groupNum = play.getGroupNum() + 10;
            //插入重试
            if (logs.size() < groupNum) {
                //重新获取群
                GroupQueryDTO groupQueryDTO = new GroupQueryDTO();
                groupQueryDTO.setGroupNum(1);
                groupQueryDTO.setRegistrationDay(play.getGroupDay());
                groupQueryDTO.setCountryCode(getCountys(play));
                //从波少那边获取足够的群
                R<List<GroupInfoVO>> groupList = groupService.queryGroup(groupQueryDTO);
                if (groupList.getCode() != 0) {
                    play.setState(3);
                    play.setFailReason("无剧本所需足够的群！");
                    playMapper.updateById(play);
                    return;
                }
                PlayGroupPack playGroupPack = playGroupPackMapper.selectGroupPackByPlayId(play.getId());
                List<String> imgs = Arrays.asList(playGroupPack.getPic().split(","));
                List<String> names = Arrays.asList(playGroupPack.getName().split(","));
                GroupInfoVO groupInfoVO = groupList.getData().get(0);
                ThirdTgModifyChatroomHeadImageInputDTO dto = new ThirdTgModifyChatroomHeadImageInputDTO();
                dto.setChatroomSerialNo(groupInfoVO.getGroupSerialNo());
                //获取群主号
                String tgRobotId = "";
                for (GroupRobotVO robotVO : groupInfoVO.getRobots()) {
                    if (robotVO.getMemberType() == 1) {
                        tgRobotId = robotVO.getRobotId();
                    }
                }
                dto.setTgRobotId(tgRobotId);
                int index = RandomListPicker.pickRandom(imgs);
                dto.setUrl(imgs.get(index));
                //修改群名称
                ThirdTgModifyChatroomNameInputDTO inputDTO = new ThirdTgModifyChatroomNameInputDTO();
                inputDTO.setChatroomNameBase64(Base64.encode(names.get(index).getBytes()));
                inputDTO.setChatroomSerialNo(groupInfoVO.getGroupSerialNo());
                inputDTO.setTgRobotId(tgRobotId);
                OpenApiResult<TgBaseOutputDTO> openApiResult = OpenApiClient.modifyChatroomNameByThirdKpTg(inputDTO);
                if (openApiResult.getCode() != 0) {
                    String optSerNo = null;
                    if (openApiResult.getData() != null) {
                        optSerNo = openApiResult.getData().getOptSerNo();
                    }
                    //修改群名称失败 需要换群
                    getLog(groupInfoVO, play, tgRobotId, optSerNo, 3);
                    return;
                }
                OpenApiResult<TgBaseOutputDTO> dtoOpenApiResult = OpenApiClient.modifyChatroomHeadImageByThirdKpTg(dto);
                String optSerNo = null;
                if (dtoOpenApiResult.getData() != null) {
                    optSerNo = dtoOpenApiResult.getData().getOptSerNo();
                }
                if (dtoOpenApiResult.getCode() != 0) {
                    //修改群名称失败 需要换群
                    getLog(groupInfoVO, play, tgRobotId, optSerNo, 3);
                    return;
                }
                getLog(groupInfoVO, play, tgRobotId, optSerNo, 1);
            }
        }
    }


    public void updateImageCallBack(CalledDTO calledDTO){
        //根据操作编码去表里查询数据
        PlayModifierGroupLog log = playModifierGroupLogMapper.selectGroupLogByCode(calledDTO.getOptSerNo());
        if (log == null){
            return;
        }
        //失败
        if (calledDTO.getResultCode() != 0){
            log.setState(3);
            playModifierGroupLogMapper.updateById(log);
        }else {
            log.setState(2);
            playModifierGroupLogMapper.updateById(log);
        }
    }


    public void  into(){
        //扫描剧本状态为调用中 未开始的剧本
        List<Play> playList = playMapper.selectIntoGroupList(1,2);
        if (playList == null || playList.size() == 0){
            return;
        }
        Boolean isTrue = false;
        VibeRuleDTO vibeRule = vibeRuleService.getOne();
        for (Play playDTO:playList){
            //修改剧本状态为入群等待中
            playDTO.setScanProgress(3);
            playMapper.updateById(playDTO);
            List<PlayIntoGroupTask> playIntoGroupTasks = new ArrayList<>();
            List<PlayRobotPack> performers = playRobotPackMapper.selectPackByPlayId(playDTO.getId());
            //判定是否是平台提供群
            if (playDTO.getGroupSource() == 0){
                PlayGroupPack playGroupPack =  playGroupPackMapper.selectGroupPackByPlayId(playDTO.getId());
                List<GroupInfoVO> infoVOS = new ArrayList<>();
                if (playGroupPack == null){
                    GroupQueryDTO groupQueryDTO = new GroupQueryDTO();
                    groupQueryDTO.setGroupNum(playDTO.getGroupNum());
                    groupQueryDTO.setRegistrationDay(playDTO.getGroupDay());
                    groupQueryDTO.setCountryCode(getCountys(playDTO));
                    //从波少那边获取足够的群
                    R<List<GroupInfoVO>> groupList = groupService.queryGroup(groupQueryDTO);
                    if (groupList.getCode() != 0){
                        //设置错误
                        playDTO.setState(3);
                        playDTO.setFailReason("无剧本所需足够的群！");
                        continue;
                    }
                    infoVOS.addAll(groupList.getData());
                }else {
                    List<PlayModifierGroupLog> playModifierGroupLogs = playModifierGroupLogMapper.selectGroupLogByPlayId(playDTO.getId());
                    for (PlayModifierGroupLog log:playModifierGroupLogs){
                        GroupInfoVO groupInfoVO = new GroupInfoVO();
                        groupInfoVO.setGroupId(log.getGroupId());
                        groupInfoVO.setGroupInviteLink(log.getGroupInviteLink());
                        groupInfoVO.setGroupSerialNo(log.getGroupSerialNo());
                        infoVOS.add(groupInfoVO);
                    }
                }
                for (GroupInfoVO groupInfoVO:infoVOS){
                    List<GetRobotVO> robotVOS= getRobot(playDTO,vibeRule,performers);
                    if (robotVOS == null){
                        playDTO.setState(3);
                        playDTO.setFailReason("无剧本所需足够的机器人！");
                        continue;
                    }
                    //拆分机器人列表
                    List<GetRobotVO> adminList = new ArrayList<>();
                    List<GetRobotVO> robotList = new ArrayList<>();
                    for (GetRobotVO getRobotVO:robotVOS){
                        if (getRobotVO.getIsSetAdmin() == 1){
                            adminList.add(getRobotVO);
                        }else {
                            robotList.add(getRobotVO);
                        }
                    }
                    //拆分入群任务
                    for (PlayRobotPack performer:performers){
                        PlayIntoGroupTask playIntoGroupTask = new PlayIntoGroupTask();
                        // 插入计划表
                        playIntoGroupTask.setId(IdUtils.fastUUID());
                        playIntoGroupTask.setGroupUrl(groupInfoVO.getGroupInviteLink());
                        playIntoGroupTask.setCreateTime(new Date());
                        playIntoGroupTask.setModifyTime(new Date());
                        playIntoGroupTask.setPlayId(playDTO.getId());
                        playIntoGroupTask.setMerchantId(playDTO.getMerchantId());
                        if (performer.getIsAdmin() == 1){
                            int index = RandomListPicker.pickRandom(adminList);
                            playIntoGroupTask.setPersonId(adminList.get(index).getRobotSerialNo());
                            robotVOS.remove(index);
                            playIntoGroupTask.setTaskState(1);
                        }else {
                            int index = RandomListPicker.pickRandom(robotList);
                            playIntoGroupTask.setPersonId(robotList.get(index).getRobotSerialNo());
                            robotVOS.remove(index);
                            playIntoGroupTask.setTaskState(1);
                        }
                        playIntoGroupTasks.add(playIntoGroupTask);
                    }
                }
            }else {
                //群链接入群
                List<String> groupUrls =Arrays.asList(playDTO.getGroupUrls().split(","));
                for (String group : groupUrls) {
                    List<GetRobotVO> robotVOS= getRobot(playDTO,vibeRule,performers);
                    //拆分机器人列表
                    List<GetRobotVO> adminList = new ArrayList<>();
                    List<GetRobotVO> robotList = new ArrayList<>();
                    for (GetRobotVO getRobotVO:robotVOS){
                        if (getRobotVO.getIsSetAdmin() == 1){
                            adminList.add(getRobotVO);
                        }else {
                            robotList.add(getRobotVO);
                        }
                    }
                    if (robotVOS == null){
                        playDTO.setState(3);
                        playDTO.setFailReason("无剧本所需足够的机器人！");
                        continue;
                    }
                    // 循环插入计划表
                    for (PlayRobotPack performer:performers){
                        // 插入计划表
                        PlayIntoGroupTask playIntoGroupTask = new PlayIntoGroupTask();
                        playIntoGroupTask.setId(IdUtils.fastUUID());
                        playIntoGroupTask.setGroupUrl(group);
                        playIntoGroupTask.setCreateTime(new Date());
                        playIntoGroupTask.setModifyTime(new Date());
                        playIntoGroupTask.setPlayId(playDTO.getId());
                        playIntoGroupTask.setMerchantId(playDTO.getMerchantId());
                        if (performer.getIsAdmin() == 1){
                            int index = RandomListPicker.pickRandom(adminList);
                            playIntoGroupTask.setPersonId(adminList.get(index).getRobotSerialNo());
                            robotVOS.remove(index);
                            playIntoGroupTask.setTaskState(1);
                        }else {
                            int index = RandomListPicker.pickRandom(robotList);
                            playIntoGroupTask.setPersonId(robotList.get(index).getRobotSerialNo());
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
    }

    public List<GetRobotVO> getRobot(Play playDTO,VibeRuleDTO vibeRule,List<PlayRobotPack> performers){
        //获取需要的机器人数量
        //统计需要管理员权限的机器人
        Integer adminNum = 0,robotNum = 0;
        for (PlayRobotPack performer:performers){
            if (performer.getIsAdmin() == 1){
                adminNum++;
            }else {
                robotNum++;
            }
        }
        PlayExt PlayDTO =  JSONObject.parseObject(playDTO.getPlayExt(), PlayExt.class);
        //备用号逻辑
        if (PlayDTO.getStandbyState() == 1){
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
        R<List<GetRobotVO>> robotAdminVOS =  robotStatisticsService.getRobot(adminDTO);
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
                    OpenApiResult<TgBaseOutputDTO> resultBody = OpenApiClient.joinChatroomByUrlByThirdKpTg(dto);
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
            //入群失败要做重试逻辑

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
        groupInfo.setIsDelete(0);
        return groupInfo;
    }


    //退群处理
    public void outGroup(){

    }

}