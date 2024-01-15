package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.Performer;
import com.ruoyi.common.core.domain.dto.play.PlayDTO;
import com.ruoyi.common.core.domain.dto.play.VibeRuleDTO;
import com.ruoyi.common.core.domain.entity.VibeRule;
import com.ruoyi.common.openapi.OpenApiClient;
import com.ruoyi.common.openapi.OpenApiResult;
import com.ruoyi.common.openapi.model.input.ThirdTgJoinChatroomByUrlInputDTO;
import com.ruoyi.common.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.components.RandomListPicker;
import com.ruoyi.system.domain.dto.GroupQueryDTO;
import com.ruoyi.system.domain.dto.play.PlayIntoGroupTask;
import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;
import com.ruoyi.system.mapper.PlayIntoGroupTaskMapper;
import com.ruoyi.system.service.IVibeRuleService;
import com.ruoyi.system.service.RobotStatisticsService;
import com.ruoyi.system.service.business.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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



    public void  into(PlayDTO playDTO){
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
        VibeRuleDTO vibeRule = vibeRuleService.getOne();
        //备用号逻辑
        if (playDTO.getPlayExt().getStandbyState() == 1){
            //获取备用号规则
            adminNum = adminNum+(adminNum*vibeRule.getStandbyNum());
        }
        //是否设置目标国
        if (StringUtils.isNotEmpty(playDTO.getTargetCountyCode())){

        }
        //获取需要入群的群数
        adminNum = adminNum * playDTO.getGroupNum();
        robotNum = robotNum * playDTO.getRobotNum();
        GetRobotDTO adminDTO = new GetRobotDTO();
        //获取可以被设置管理员的机器人
        adminDTO.setCount(adminNum);
        /**------未完成-------**/
        adminDTO.setCountryCode(new ArrayList<>());
        //调用获取机器人接口
        List<GetRobotVO> robotAdminVOS =  robotStatisticsService.getRobot(adminDTO);
        //获取可以不需要设置成管理员的机器人
        GetRobotDTO robotDTO = new GetRobotDTO();
        robotDTO.setCount(robotNum);
        /**------未完成-------**/
        robotDTO.setCountryCode(new ArrayList<>());
        List<GetRobotVO> robotVOS = robotStatisticsService.getRobot(robotDTO);
        //判定是否是平台提供群
        if (playDTO.getGroupSource() == 0){
            GroupQueryDTO groupQueryDTO = new GroupQueryDTO();
            groupQueryDTO.setGroupNum(playDTO.getGroupNum());
            groupQueryDTO.setRegistrationDay(playDTO.getGroupDay());
            groupQueryDTO.setCountryCode(new ArrayList<>());
            //从波少那边获取足够的群
            List<GroupInfoVO> groupList = groupService.queryGroup(groupQueryDTO);
            //拆分入群任务
            for (int i=0;i< playDTO.getRobotNum();i++){
                PlayIntoGroupTask playIntoGroupTask = new PlayIntoGroupTask();
//                // 插入计划表
//                playIntoGroupTask.setId(IdUtils.fastUUID());
//                playIntoGroupTask.setGroupUrl(null);
//                playIntoGroupTask.setCreateTime(new Date());
//                playIntoGroupTask.setModifyTime(new Date());
//                playIntoGroupTask.setMerchantId("");
//                playIntoGroupTask.setIntoType(1);
//                if (robotList.size() == 0) {
//                    playIntoGroupTask.setPersonId("");
//                    playIntoGroupTask.setTaskState(4);
//                    playIntoGroupTask.setFailCause("无可用的号");
//                } else {
//                    int index = RandomListPicker.pickRandom(robotList);
//                    playIntoGroupTask.setPersonId(robotList.get(index));
//                    robotList.remove(index);
//                    playIntoGroupTask.setTaskState(1);
//                }
//                personIntoGroupTasks.add(playIntoGroupTask);
            }

        }else {
            //群链接入群
            List<String> groupUrls = playDTO.getGroupUrls();
            List<PlayIntoGroupTask> personIntoGroupTasks = new ArrayList<>();
            for (String group : groupUrls) {
                // 循环插入计划表
                for (int i = 0; i < playDTO.getRobotNum(); i++) {
                    // 插入计划表
                    PlayIntoGroupTask personIntoGroupTask = new PlayIntoGroupTask();
                    personIntoGroupTask.setId(IdUtils.fastUUID());
                    personIntoGroupTask.setGroupUrl(group);
                    personIntoGroupTask.setCreateTime(new Date());
                    personIntoGroupTask.setModifyTime(new Date());
                    personIntoGroupTask.setMerchantId(playDTO.getMerchantId());
                    personIntoGroupTask.setIntoType(1);
//                    int index = RandomListPicker.pickRandom(robotList);
//                    personIntoGroupTask.setPersonId(robotList.get(index));
//                    robotList.remove(index);
                    personIntoGroupTask.setTaskState(1);
                    personIntoGroupTasks.add(personIntoGroupTask);
                }
                playIntoGroupTaskMapper.batchInsert(personIntoGroupTasks);
                //插入机器人调用记录


            }
        }
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
    public void intoGroupCallback(){

    }


    //退群处理
    public void outGroup(){

    }

}
