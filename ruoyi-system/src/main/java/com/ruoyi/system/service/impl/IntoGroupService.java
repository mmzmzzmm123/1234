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
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.redis.RedisHandler;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.callback.dto.Called1100910039DTO;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.callback.dto.CalledEmptyDTO;
import com.ruoyi.system.components.Beans;
import com.ruoyi.system.components.RandomListPicker;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.dto.GroupQueryDTO;
import com.ruoyi.system.domain.dto.play.PlayGroupInfo;
import com.ruoyi.system.domain.dto.play.PlayIntoGroupTask;
import com.ruoyi.system.domain.dto.play.PlayModifierGroupLog;
import com.ruoyi.system.domain.dto.play.PlayRobotGroupRelation;
import com.ruoyi.system.domain.dto.robot.GetRobotDTO;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.GroupRobotVO;
import com.ruoyi.system.domain.vo.robot.GetRobotVO;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgExitChatroomInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgJoinChatroomByUrlInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyChatroomHeadImageInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyChatroomNameInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.IVibeRuleService;
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.RobotStatisticsService;
import com.ruoyi.system.service.business.GroupService;
import com.ruoyi.system.service.limit.WarningRobotLimitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("intoGroupService")
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

    @Autowired
    PlayExecutionLogService playExecutionLogService;

    @Autowired
    WarningRobotLimitService warningRobotLimitService;

    @Autowired
    GroupInfoMapper groupInfoMapper;

    String errorMessageListRedisKey = "error_message_list_redis_key:";

    @Scheduled(cron = "0/20 * * * * ?")
    public void optionGroup() {
        log.info("扫描未开始的剧本");
        //扫描剧本状态为调用中 未开始的剧本
        RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:optionGroup");
        if (lock.isLocked()) {
            return;
        }
        try {
            lock.lock(60 * 60, TimeUnit.SECONDS);
            List<Play> playList = playMapper.selectIntoGroupList(1, 0);
            log.info("未开始的剧本打印："+JSONObject.toJSONString(playList));
            if (playList == null || playList.size() == 0) {
                return;
            }
            for (Play play : playList) {
                //判断剧本是否是外部
                if (play.getGroupSource() == 1) {
                    //修改剧本状态为入群中
                    play.setScanProgress(2);
                    playMapper.updateById(play);
                    log.info("外部群不修改群人设："+JSONObject.toJSONString(play));
                    setLog(play.getId(), "外部群不需要修改群人设", 0, PlayLogTyper.Group_img_name, null);
                    continue;
                }
                //判断剧本是否需要设置群头像跟群名称
                PlayGroupPack playGroupPack = playGroupPackMapper.selectGroupPackByPlayId(play.getId());
                //设置群头像和群名称
                if (playGroupPack == null || (StringUtils.isEmpty(playGroupPack.getName()) && StringUtils.isEmpty(playGroupPack.getPic()))) {
                    play.setScanProgress(2);
                    playMapper.updateById(play);
                    log.info("内部群没有设置群人设："+JSONObject.toJSONString(play));
                    setLog(play.getId(), "内部群没有设置修改群人设", 0, PlayLogTyper.Group_img_name, null);
                    continue;
                }
                //修改剧本状态为入群中
                play.setScanProgress(1);
                playMapper.updateById(play);
                GroupQueryDTO groupQueryDTO = new GroupQueryDTO();
                groupQueryDTO.setGroupNum(play.getGroupNum());
                groupQueryDTO.setRegistrationDay(play.getGroupDay());
                groupQueryDTO.setCountryCode(getCountys(play));
                groupQueryDTO.setPayId(play.getId());
                //从波少那边获取足够的群
                log.info("获取群入参："+JSONObject.toJSONString(groupQueryDTO));
                R<List<GroupInfoVO>> groupList = groupService.queryGroup(groupQueryDTO);
                log.info("获取群返回："+JSONObject.toJSONString(groupList));
                if (groupList.getCode() != 200) {
                    play.setState(4);
                    play.setFailReason("无剧本所需足够的群！");
                    setLog(play.getId(), "无剧本所需足够的群数", 1, PlayLogTyper.Group_out, null);
                    playMapper.updateById(play);
                    continue;
                }
                List<String> imgs = Arrays.asList(playGroupPack.getPic().split(","));
                List<String> names = Arrays.asList(playGroupPack.getName().split(","));
                //循环群信息
                List<GroupInfoVO> groupInfoVOS = groupList.getData();
                for (GroupInfoVO groupInfoVO : groupInfoVOS) {
                    setLog(play.getId(), "群" + groupInfoVO.getGroupName() + ",ID为：" + groupInfoVO.getGroupSerialNo() + "已出库", 0, PlayLogTyper.Group_out, groupInfoVO.getGroupId());
                    //获取群主号
                    String tgRobotId = "";
                    for (GroupRobotVO robotVO : groupInfoVO.getRobots()) {
                        if (robotVO.getMemberType() == 1) {
                            tgRobotId = robotVO.getRobotId();
                        }
                    }
                    if (names != null && names.size() > 0){
                        //修改群名称
                        ThirdTgModifyChatroomNameInputDTO inputDTO = new ThirdTgModifyChatroomNameInputDTO();
                        int index = RandomListPicker.pickRandom(names);
                        inputDTO.setChatroomNameBase64(Base64.encode(names.get(index).getBytes()));
                        inputDTO.setChatroomSerialNo(groupInfoVO.getGroupSerialNo());
                        inputDTO.setTgRobotId(tgRobotId);
                        log.info("修改群名称入参："+JSONObject.toJSONString(inputDTO));
                        OpenApiResult<TgBaseOutputDTO> openApiResult = OpenApiClient.modifyChatroomNameByThirdKpTg(inputDTO);
                        log.info("修改群名称返回："+JSONObject.toJSONString(openApiResult));
                        String optSerNo = null;
                        if (openApiResult.getCode() != 0) {
                            if (openApiResult.getData() != null) {
                                optSerNo = openApiResult.getData().getOptSerNo();
                            }
                            setLog(play.getId(), "群" + groupInfoVO.getGroupName() + ",ID为：" + groupInfoVO.getGroupSerialNo() + "修改群名称为:" + names.get(index) + "失败！需要换群", 1, PlayLogTyper.Group_img_name, groupInfoVO.getGroupId());
                            //修改群名称失败 需要换群
                            getLog(groupInfoVO, play, tgRobotId, optSerNo, 3);
                            continue;
                        }
                        setLog(play.getId(), "群" + groupInfoVO.getGroupName() + ",ID为：" + groupInfoVO.getGroupSerialNo() + "修改群名称为:" + names.get(index) + "成功！", 0, PlayLogTyper.Group_img_name, groupInfoVO.getGroupId());
                    }
                    if (imgs != null && imgs.size() > 0){
                        ThirdTgModifyChatroomHeadImageInputDTO dto = new ThirdTgModifyChatroomHeadImageInputDTO();
                        dto.setChatroomSerialNo(groupInfoVO.getGroupSerialNo());
                        dto.setTgRobotId(tgRobotId);
                        int index = RandomListPicker.pickRandom(imgs);
                        dto.setUrl(imgs.get(index));
                        log.info("修改群头像入参："+JSONObject.toJSONString(dto));
                        OpenApiResult<TgBaseOutputDTO> dtoOpenApiResult = OpenApiClient.modifyChatroomHeadImageByThirdKpTg(dto);
                        log.info("修改群头像返回："+JSONObject.toJSONString(dtoOpenApiResult));
                        String optSerNo = null;
                        if (dtoOpenApiResult.getData() != null) {
                            optSerNo = dtoOpenApiResult.getData().getOptSerNo();
                        }
                        if (dtoOpenApiResult.getCode() != 0) {
                            //修改群头像失败 需要换群
                            getLog(groupInfoVO, play, tgRobotId, optSerNo, 3);
                            setLog(play.getId(), "群" + groupInfoVO.getGroupName() + ",ID为：" + groupInfoVO.getGroupSerialNo() + "修改群头像为:" + imgs.get(index) + "失败！需要换群", 1, PlayLogTyper.Group_img_name, groupInfoVO.getGroupId());
                            continue;
                        }
                        setLog(play.getId(), "群" + groupInfoVO.getGroupName() + ",ID为：" + groupInfoVO.getGroupSerialNo() + "修改群头像为:" + imgs.get(index) + "调用接口成功！", 0, PlayLogTyper.Group_img_name, groupInfoVO.getGroupId());
                        getLog(groupInfoVO, play, tgRobotId, optSerNo, 1);
                    }else {
                        getLog(groupInfoVO, play, tgRobotId, "", 2);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void getLog(GroupInfoVO groupInfoVO, Play play, String tgRobotId, String optSerNo, Integer state) {
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

    public List<String> getCountys(Play play) {
        VibeRuleDTO vibeRule = vibeRuleService.getOne();
        List<String> countys = new ArrayList<>();
        //是否设置目标国
        if (StringUtils.isNotEmpty(play.getTargetCountyCode()) && vibeRule.getTargetParams() != null) {
            List<String> allList = new ArrayList<>();
            List<String> preferenceCodes = new ArrayList<>();
            for (VibeRuleTargetParam param : vibeRule.getTargetParams()) {
                if (param.getAllState() == 1) {
                    allList = param.getPreferenceCodes();
                    continue;
                }
                if (play.getTargetCountyCode().equals(param.getCountryCode())) {
                    preferenceCodes = param.getPreferenceCodes();
                }
            }
            if (preferenceCodes.size() != 0) {
                countys = preferenceCodes;
            } else {
                countys = allList;
            }
        }
        return countys;
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void modifierGroup() {
        log.info("扫描修改群人设的剧本！");
        //扫描剧本状态为调用中 未开始的剧本
        RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:modifierGroup");
        if (lock.isLocked()) {
            return;
        }
        try {
            lock.lock(60 * 60, TimeUnit.SECONDS);
            //查询剧本状态为调度中 修改群信息状态的剧本
            List<Play> playList = playMapper.selectIntoGroupList(1, 1);
            log.info("获取修改群人设的剧本信息:"+JSONObject.toJSONString(playList));
            if (playList == null || playList.size() == 0) {
                return;
            }
            for (Play play : playList) {
                if (play.getState() != 1 || play.getIsDelete() == 1) {
                    return;
                }
                //获取剧本中所需要的群数
                Integer groupNum = play.getGroupNum();
                //扫描群已设置成功的数据
                List<PlayModifierGroupLog> playModifierGroupLogs = playModifierGroupLogMapper.selectGroupLogByPlayId(play.getId());
                log.info("获取已设置成功的群信息:"+JSONObject.toJSONString(playModifierGroupLogs));
                if (playModifierGroupLogs == null) {
                    continue;
                }
                //获取到足够的群资源修改剧本状态
                if (playModifierGroupLogs.size() >= groupNum) {
                    play.setScanProgress(2);
                    playMapper.updateById(play);
                    setLog(play.getId(), "已获得足够修改群头像昵称的群", 0, PlayLogTyper.Group_img_name, null);
                    continue;
                }
                //查询剧本对应的所有记录
                List<PlayModifierGroupLog> logs = playModifierGroupLogMapper.selectGroupLogByPlayIdAll(play.getId());
                log.info("获取所有修改人设的群信息:"+JSONObject.toJSONString(logs));
                Boolean isError = true;
                //判断是否有失败的群
                for (PlayModifierGroupLog log : logs) {
                    if (log.getState() == 3) {
                        isError = false;
                    }
                }
                if (isError) {
                    continue;
                }
                //获取发送群数
                groupNum = play.getGroupNum() + 10;
                //插入重试
                if (logs.size() < groupNum) {
                    replaceGroup(play);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void replaceGroup(Play play){
        //重新获取群
        GroupQueryDTO groupQueryDTO = new GroupQueryDTO();
        groupQueryDTO.setGroupNum(1);
        groupQueryDTO.setRegistrationDay(play.getGroupDay());
        groupQueryDTO.setCountryCode(getCountys(play));
        groupQueryDTO.setPayId(play.getId());
        log.info("重试获取群信息入参:"+JSONObject.toJSONString(groupQueryDTO));
        //从波少那边获取足够的群
        R<List<GroupInfoVO>> groupList = groupService.queryGroup(groupQueryDTO);
        log.info("重试获取群信息返回:"+JSONObject.toJSONString(groupList));
        if (groupList.getCode() != 200) {
            play.setState(4);
            play.setFailReason("无剧本所需足够的群！");
            setLog(play.getId(), "无剧本所需足够的群数", 1, PlayLogTyper.Group_out, null);
            playMapper.updateById(play);
            return;
        }
        PlayGroupPack playGroupPack = playGroupPackMapper.selectGroupPackByPlayId(play.getId());
        List<String> imgs = Arrays.asList(playGroupPack.getPic().split(","));
        List<String> names = Arrays.asList(playGroupPack.getName().split(","));
        GroupInfoVO groupInfoVO = groupList.getData().get(0);
        setLog(play.getId(), "重新分配群出库", 0, PlayLogTyper.Group_out, groupInfoVO.getGroupSerialNo());
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
        log.info("重试修改群名称入参:"+JSONObject.toJSONString(inputDTO));
        OpenApiResult<TgBaseOutputDTO> openApiResult = OpenApiClient.modifyChatroomNameByThirdKpTg(inputDTO);
        log.info("重试修改群名称返回:"+JSONObject.toJSONString(openApiResult));
        if (openApiResult.getCode() != 0) {
            String optSerNo = null;
            if (openApiResult.getData() != null) {
                optSerNo = openApiResult.getData().getOptSerNo();
            }
            //修改群名称失败 需要换群
            getLog(groupInfoVO, play, tgRobotId, optSerNo, 3);
            setLog(play.getId(), "群" + groupInfoVO.getGroupName() + ",ID为：" + groupInfoVO.getGroupSerialNo() + "修改群名称为:" + names.get(index) + "失败！需要换群", 1, PlayLogTyper.Group_img_name, groupInfoVO.getGroupId());
            return;
        }
        setLog(play.getId(), "群" + groupInfoVO.getGroupName() + ",ID为：" + groupInfoVO.getGroupSerialNo() + "修改群名称为:" + names.get(index) + "成功！", 0, PlayLogTyper.Group_img_name, groupInfoVO.getGroupId());
        log.info("重试修改群头像入参:"+JSONObject.toJSONString(inputDTO));
        OpenApiResult<TgBaseOutputDTO> dtoOpenApiResult = OpenApiClient.modifyChatroomHeadImageByThirdKpTg(dto);
        log.info("重试修改群头像返回:"+JSONObject.toJSONString(inputDTO));
        String optSerNo = null;
        if (dtoOpenApiResult.getData() != null) {
            optSerNo = dtoOpenApiResult.getData().getOptSerNo();
        }
        if (dtoOpenApiResult.getCode() != 0) {
            //修改群名称失败 需要换群
            getLog(groupInfoVO, play, tgRobotId, optSerNo, 3);
            setLog(play.getId(), "群" + groupInfoVO.getGroupName() + ",ID为：" + groupInfoVO.getGroupSerialNo() + "修改群头像为:" + imgs.get(index) + "失败！需要换群", 1, PlayLogTyper.Group_img_name, groupInfoVO.getGroupId());
            return;
        }
        getLog(groupInfoVO, play, tgRobotId, optSerNo, 1);
        setLog(play.getId(), "群" + groupInfoVO.getGroupName() + ",ID为：" + groupInfoVO.getGroupSerialNo() + "修改群头像为:" + imgs.get(index) + "调用接口成功！",0, PlayLogTyper.Group_img_name, groupInfoVO.getGroupId());
    }


    public void updateImageCallBack(CalledDTO calledDTO) {
        log.info("修改群头像回调:"+JSONObject.toJSONString(calledDTO));
        //根据操作编码去表里查询数据
        PlayModifierGroupLog log = playModifierGroupLogMapper.selectGroupLogByCode(calledDTO.getOptSerNo());
        if (log == null) {
            return;
        }
        //失败
        if (calledDTO.getResultCode() != 0) {
            log.setState(3);
            setLog(log.getPlayId(), "群" + log.getGroupSerialNo() + "修改群头像失败！", 1, PlayLogTyper.Group_img_name, log.getGroupId());
            playModifierGroupLogMapper.updateById(log);
        } else {
            log.setState(2);
            setLog(log.getPlayId(), "群" + log.getGroupSerialNo() + "修改群头像成功！", 0, PlayLogTyper.Group_img_name, log.getGroupId());
            playModifierGroupLogMapper.updateById(log);
        }
    }


    @Scheduled(cron = "0/20 * * * * ?")
    public void into() {
        log.info("扫描已修改好人设的剧本任务");
        RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:into");
        if (lock.isLocked()) {
            return;
        }
        try {
            lock.lock(60 * 60, TimeUnit.SECONDS);
            //扫描剧本状态为调用中 未开始的剧本
            List<Play> playList = playMapper.selectIntoGroupList(1, 2);
            log.info("扫描已修改好人设的剧本任务信息："+JSONObject.toJSONString(playList));
            if (playList == null || playList.size() == 0) {
                return;
            }
            Boolean isTrue = false;
            VibeRuleDTO vibeRule = vibeRuleService.getOne();
            for (Play playDTO : playList) {
                //修改剧本状态为入群等待中
                playDTO.setScanProgress(3);
                playMapper.updateById(playDTO);
                setLog(playDTO.getId(), "机器人安排入群中！", 0, PlayLogTyper.Group_into, null);
                List<PlayIntoGroupTask> playIntoGroupTasks = new ArrayList<>();
                List<PlayRobotPack> performers = playRobotPackMapper.selectPackByPlayId(playDTO.getId());
                //判定是否是平台提供群
                if (playDTO.getGroupSource() == 0) {
                    PlayGroupPack playGroupPack = playGroupPackMapper.selectGroupPackByPlayId(playDTO.getId());
                    List<GroupInfoVO> infoVOS = new ArrayList<>();
                    if (playGroupPack == null || (StringUtils.isEmpty(playGroupPack.getName()) && StringUtils.isEmpty(playGroupPack.getPic()))) {
                        GroupQueryDTO groupQueryDTO = new GroupQueryDTO();
                        groupQueryDTO.setGroupNum(playDTO.getGroupNum());
                        groupQueryDTO.setRegistrationDay(playDTO.getGroupDay());
                        groupQueryDTO.setCountryCode(getCountys(playDTO));
                        groupQueryDTO.setPayId(playDTO.getId());
                        log.info("不需要修改人设的群获取入参："+JSONObject.toJSONString(groupQueryDTO));
                        //从波少那边获取足够的群
                        R<List<GroupInfoVO>> groupList = groupService.queryGroup(groupQueryDTO);
                        log.info("不需要修改人设的群获取返回："+JSONObject.toJSONString(groupList));
                        if (groupList.getCode() != 200) {
                            //设置错误
                            playDTO.setState(4);
                            playDTO.setFailReason("无剧本所需足够的群！");
                            continue;
                        }
                        infoVOS.addAll(groupList.getData());
                    } else {
                        List<PlayModifierGroupLog> playModifierGroupLogs = playModifierGroupLogMapper.selectGroupLogByPlayId(playDTO.getId());
                        log.info("修改人设的群获取返回："+JSONObject.toJSONString(playModifierGroupLogs));
                        for (PlayModifierGroupLog log : playModifierGroupLogs) {
                            GroupInfoVO groupInfoVO = new GroupInfoVO();
                            groupInfoVO.setGroupId(log.getGroupId());
                            groupInfoVO.setGroupInviteLink(log.getGroupInviteLink());
                            groupInfoVO.setGroupSerialNo(log.getGroupSerialNo());
                            infoVOS.add(groupInfoVO);
                        }
                    }
                    for (GroupInfoVO groupInfoVO : infoVOS) {
                        List<GetRobotVO> robotVOS = getRobot(playDTO, vibeRule, performers);
                        if (robotVOS == null) {
                            playDTO.setState(4);
                            playDTO.setFailReason("无剧本所需足够的机器人！");
                            setLog(playDTO.getId(), "群" + groupInfoVO.getGroupName() + "机器人出库失败，无足够的机器人", 1, PlayLogTyper.Group_into, groupInfoVO.getGroupId());
                            continue;
                        }
                        setLog(playDTO.getId(), "群" + groupInfoVO.getGroupName() + "机器人出库成功！", 0, PlayLogTyper.Group_into, groupInfoVO.getGroupId());
                        //拆分机器人列表
                        List<GetRobotVO> adminList = new ArrayList<>();
                        List<GetRobotVO> robotList = new ArrayList<>();
                        for (GetRobotVO getRobotVO : robotVOS) {
                            if (getRobotVO.getIsSetAdmin() == 1) {
                                adminList.add(getRobotVO);
                            } else {
                                robotList.add(getRobotVO);
                            }
                        }
                        PlayExt playExt = JSONObject.parseObject(playDTO.getPlayExt(), PlayExt.class);
                        Integer i= 1;
                        if (playExt.getStandbyState() == 1) {
                            i = 1+vibeRule.getStandbyNum();
                        }
                        for (int j =0;j<i;j++){
                            //拆分入群任务
                            for (PlayRobotPack performer : performers) {
                                PlayIntoGroupTask playIntoGroupTask = new PlayIntoGroupTask();
                                // 插入计划表
                                playIntoGroupTask.setId(IdUtils.fastUUID());
                                playIntoGroupTask.setGroupUrl(groupInfoVO.getGroupInviteLink());
                                playIntoGroupTask.setCreateTime(new Date());
                                playIntoGroupTask.setModifyTime(new Date());
                                playIntoGroupTask.setPlayId(playDTO.getId());
                                playIntoGroupTask.setMerchantId(playDTO.getMerchantId());
                                if (performer.getIsAdmin() == 1) {
                                    int index = RandomListPicker.pickRandom(adminList);
                                    playIntoGroupTask.setPersonId(adminList.get(index).getRobotSerialNo());
                                    playIntoGroupTask.setIsAdmin(1);
                                    adminList.remove(index);
                                    playIntoGroupTask.setTaskState(1);
                                } else {
                                    int index = RandomListPicker.pickRandom(robotList);
                                    playIntoGroupTask.setPersonId(robotList.get(index).getRobotSerialNo());
                                    playIntoGroupTask.setIsAdmin(0);
                                    robotList.remove(index);
                                    playIntoGroupTask.setTaskState(1);
                                }
                                playIntoGroupTask.setIsError(0);
                                playIntoGroupTasks.add(playIntoGroupTask);
                            }
                        }

                        setLog(playDTO.getId(), "群" + groupInfoVO.getGroupName() + "机器人调度入群任务分配成功！", 0, PlayLogTyper.Group_into, groupInfoVO.getGroupId());
                    }
                } else {
                    //群链接入群
                    List<String> groupUrls = Arrays.asList(playDTO.getGroupUrls().split(","));
                    for (String group : groupUrls) {
                        List<GetRobotVO> robotVOS = getRobot(playDTO, vibeRule, performers);
                        //拆分机器人列表
                        List<GetRobotVO> adminList = new ArrayList<>();
                        List<GetRobotVO> robotList = new ArrayList<>();
                        for (GetRobotVO getRobotVO : robotVOS) {
                            if (getRobotVO.getIsSetAdmin() == 1) {
                                adminList.add(getRobotVO);
                            } else {
                                robotList.add(getRobotVO);
                            }
                        }
                        if (robotVOS == null) {
                            playDTO.setState(4);
                            playDTO.setFailReason("无剧本所需足够的机器人！");
                            setLog(playDTO.getId(), "群" + group + "机器人出库失败，无足够的机器人", 1, PlayLogTyper.Group_into, null);
                            continue;
                        }
                        PlayExt playExt = JSONObject.parseObject(playDTO.getPlayExt(), PlayExt.class);
                        Integer i= 1;
                        if (playExt.getStandbyState() == 1) {
                            i = 1+vibeRule.getStandbyNum();
                        }
                        for (int j =0;j<i;j++){
                            // 循环插入计划表
                            for (PlayRobotPack performer : performers) {
                                // 插入计划表
                                PlayIntoGroupTask playIntoGroupTask = new PlayIntoGroupTask();
                                playIntoGroupTask.setId(IdUtils.fastUUID());
                                playIntoGroupTask.setGroupUrl(group);
                                playIntoGroupTask.setCreateTime(new Date());
                                playIntoGroupTask.setModifyTime(new Date());
                                playIntoGroupTask.setPlayId(playDTO.getId());
                                playIntoGroupTask.setMerchantId(playDTO.getMerchantId());
                                if (performer.getIsAdmin() == 1) {
                                    int index = RandomListPicker.pickRandom(adminList);
                                    playIntoGroupTask.setPersonId(adminList.get(index).getRobotSerialNo());
                                    playIntoGroupTask.setIsAdmin(1);
                                    adminList.remove(index);
                                    playIntoGroupTask.setTaskState(1);
                                } else {
                                    int index = RandomListPicker.pickRandom(robotList);
                                    playIntoGroupTask.setPersonId(robotList.get(index).getRobotSerialNo());
                                    playIntoGroupTask.setIsAdmin(0);
                                    robotList.remove(index);
                                    playIntoGroupTask.setTaskState(1);
                                }
                                playIntoGroupTask.setIsError(0);
                                playIntoGroupTasks.add(playIntoGroupTask);
                            }
                        }
                        setLog(playDTO.getId(), "群" + group + "机器人调度入群任务分配成功！", 0, PlayLogTyper.Group_into, null);
                    }
                }
                if (playIntoGroupTasks.size() > 0){
                    playIntoGroupTaskMapper.batchInsert(playIntoGroupTasks);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public List<GetRobotVO> getRobot(Play playDTO, VibeRuleDTO vibeRule, List<PlayRobotPack> performers) {
        //获取需要的机器人数量
        //统计需要管理员权限的机器人
        Integer adminNum = 0, robotNum = 0;
        for (PlayRobotPack performer : performers) {
            if (performer.getIsAdmin() == 1) {
                adminNum++;
            } else {
                robotNum++;
            }
        }
        PlayExt playExt = JSONObject.parseObject(playDTO.getPlayExt(), PlayExt.class);
        //备用号逻辑
        if (playExt.getStandbyState() == 1) {
            //获取备用号规则
            adminNum = adminNum + (adminNum * vibeRule.getStandbyNum());
            robotNum = robotNum + (robotNum * vibeRule.getStandbyNum());
        }
        List<String> countys = new ArrayList<>();
        //是否设置目标国
        if (StringUtils.isNotEmpty(playDTO.getTargetCountyCode()) && vibeRule.getTargetParams() != null) {
            List<String> allList = new ArrayList<>();
            List<String> preferenceCodes = new ArrayList<>();
            for (VibeRuleTargetParam param : vibeRule.getTargetParams()) {
                if (param.getAllState() == 1) {
                    allList = param.getPreferenceCodes();
                    continue;
                }
                if (playDTO.getTargetCountyCode().equals(param.getCountryCode())) {
                    preferenceCodes = param.getPreferenceCodes();
                }
            }
            if (performers.size() != 0) {
                countys = preferenceCodes;
            } else {
                countys = allList;
            }
        }
        Integer ipType = 0;
        //是否需要做IP离散
        if (vibeRule.getPeriodByIp() == 1) {
            if (vibeRule.getPeriodByC() == 1) {
                ipType = 2;
            }
            if (vibeRule.getPeriodByB() == 1) {
                ipType = 1;
            }
        }
        GetRobotDTO adminDTO = new GetRobotDTO();
        //获取可以被设置管理员的机器人
        adminDTO.setCount(robotNum);
        adminDTO.setCountryCode(countys);
        adminDTO.setSetAdminCount(adminNum);
        adminDTO.setIpType(ipType);
        adminDTO.setIsLock(playExt.getLockState());
        log.info("获取机器人入参："+JSONObject.toJSONString(adminDTO));
        //调用获取机器人接口
        R<List<GetRobotVO>> robotAdminVOS = robotStatisticsService.getRobot(adminDTO);
        log.info("获取机器人返回："+JSONObject.toJSONString(robotAdminVOS));
        if (robotAdminVOS.getCode() != 200) {
            return null;
        }
        return robotAdminVOS.getData();
    }


    @Scheduled(cron = "0/20 * * * * ?")
    public R<Void> intoGroupTask() {
        log.info("执行入群任务{}");
        RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:intoGroupTask");
        if (lock.isLocked()) {
            return R.ok();
        }
        try {
            lock.lock(60 * 60, TimeUnit.SECONDS);
            // 查询待执行的计划
            List<PlayIntoGroupTask> personIntoGroupTasks = playIntoGroupTaskMapper.selectTaskList();
            log.info("获取执行入群执行计划"+JSONObject.toJSONString(personIntoGroupTasks));
            if (personIntoGroupTasks == null || personIntoGroupTasks.size() == 0) {
                return R.ok();
            }
            VibeRuleDTO vibeRule = vibeRuleService.getOne();
            for (PlayIntoGroupTask task : personIntoGroupTasks) {
                //获取当前剧本状态
                Play play = playMapper.selectPlayById(task.getPlayId());
                if (play.getState() == 3 || play.getState() == 4){
                    task.setTaskState(4);
                    task.setFailCause("剧本已停止，暂停入群");
                    playIntoGroupTaskMapper.updateById(task);
                    setLog(task.getPlayId(), "群链接" + task.getGroupUrl() + "机器人" + task.getPersonId() + "入群结果失败原因剧本已停止暂停入群", 1, PlayLogTyper.Group_into, null);
                    continue;
                }
                //获取风控规则
                // 判定当前机器人是否可以入群（风控）
                String limit = RedisHandler.get("intoGroup:task:limit:" + task.getGroupUrl());
                if (limit != null) {
                    log.info("当前机器人无法进群，群风控"+JSONObject.toJSONString(task));
                    //当前群已被限制入群
                    continue;
                }
                Long robotLimit = warningRobotLimitService.getWarningRobotLimit("task:limit:" + task.getGroupUrl(), 10, vibeRule.getJoinGroupLimit());
                if (robotLimit == 0) {
                    String s = RedisHandler.get("intoGroup:task:limit" + task.getGroupUrl());
                    if (s == null) {
                        RedisHandler.set("intoGroup:task:limit" + task.getGroupUrl(), "intoGroup:task:limit");
                        RedisHandler.expire("intoGroup:task:limit" + task.getGroupUrl(), RandomListPicker.getRandom(vibeRule.getDiffRobotIntervalStart().intValue(), vibeRule.getDiffRobotIntervalEnd().intValue()));
                    }
                    log.info("当前机器人无法进群，群批量风控"+JSONObject.toJSONString(task));
                    continue;
                }
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
                    setLog(task.getPlayId(), "群链接" + task.getGroupUrl() + "机器人" + task.getPersonId() + "入群失败，正在进行换号重试", 1, PlayLogTyper.Group_into, null);
                } else {
                    task.setTaskState(2);
                    TgBaseOutputDTO channelOutputDTO = resultBody.getData();
                    task.setCode(channelOutputDTO.getOptSerNo());
                    setLog(task.getPlayId(), "群链接" + task.getGroupUrl() + "机器人" + task.getPersonId() + "入群接口调用成功，等待入群结果", 0, PlayLogTyper.Group_into, null);
                }
                task.setModifyTime(new Date());
                playIntoGroupTaskMapper.updateById(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return R.ok();
    }

    //入群回调处理方法
    public void intoGroupCallback(GroupInfo group, CalledDTO calledDTO) {
        log.info("入群回调{}{}", calledDTO, group);
        if (StringUtils.isEmpty(calledDTO.getOptSerNo())) {
            return;
        }
        //根据操作编码查询入群记录
        PlayIntoGroupTask task = playIntoGroupTaskMapper.selectTaskByCode(calledDTO.getOptSerNo());
        log.info("查询操作编码："+JSONObject.toJSONString(task));
        if (task == null) {
            log.info("操作编码不存在{}", calledDTO.getOptSerNo());
            return;
        }
        Play play = playMapper.selectPlayById(task.getPlayId());
        VibeRuleDTO vibeRule = vibeRuleService.getOne();
        //返回入群失败
        if (calledDTO.getResultCode() != 0) {
            task.setTaskState(4);
            task.setFailCause(calledDTO.getResultMsg());
            playIntoGroupTaskMapper.updateById(task);
            setLog(task.getPlayId(), "群链接" + task.getGroupUrl() + "机器人" + task.getPersonId() + "入群结果失败原因为" + calledDTO.getResultMsg() + "等待换号重试", 1, PlayLogTyper.Group_into, null);
            //入群失败要做重试逻辑
            Boolean isRet = false;
            if (task.getRetryId() == null) {
                isRet = true;
            } else {
                //根据重试ID查询记录条数
                Integer retCount = playIntoGroupTaskMapper.selectTaskByRetCount(task.getRetryId());
                if (retCount < 2) {
                    isRet = true;
                }
            }
            String  errorMessageList = RedisHandler.get(errorMessageListRedisKey);
            if (errorMessageList != null){
                String [] strs = errorMessageList.split(",");
                for (String str:strs)
                //判断逻辑不做重试
                if (calledDTO.getResultMsg().equals(str)){
                    isRet = false;
                    //修改当前剧本所有的入群记录为失败
                    playIntoGroupTaskMapper.updateTaskByErrorGroupId(task.getGroupUrl());
                    //进行换群处理
                    replaceGroup(play);
                }
            }
            if (isRet) {
                //获取机器人
                Integer adminNum = 0, robotNum = 0;
                if (task.getIsAdmin() == 1) {
                    adminNum = 1;
                } else {
                    robotNum = 1;
                }
                List<String> countys = getCountys(play);
                Integer ipType = 0;
                //是否需要做IP离散
                if (vibeRule.getPeriodByIp() == 1) {
                    if (vibeRule.getPeriodByC() == 1) {
                        ipType = 2;
                    }
                    if (vibeRule.getPeriodByB() == 1) {
                        ipType = 1;
                    }
                }
                GetRobotDTO adminDTO = new GetRobotDTO();
                //获取可以被设置管理员的机器人
                adminDTO.setCount(robotNum);
                adminDTO.setCountryCode(countys);
                adminDTO.setSetAdminCount(adminNum);
                adminDTO.setIpType(ipType);
                PlayExt playExt = JSONObject.parseObject(play.getPlayExt(), PlayExt.class);
                adminDTO.setIsLock(playExt.getLockState());
                //调用获取机器人接口
                log.info("入群失败重试获取号："+JSONObject.toJSONString(adminDTO));
                R<List<GetRobotVO>> robotAdminVOS = robotStatisticsService.getRobot(adminDTO);
                log.info("入群失败重试获取号结果："+JSONObject.toJSONString(robotAdminVOS));
                PlayIntoGroupTask playIntoGroupTask = Beans.toView(task, PlayIntoGroupTask.class);
                if (robotAdminVOS.getCode() != 200) {
                    playIntoGroupTask.setPersonId(null);
                    setLog(task.getPlayId(), "群链接" + task.getGroupUrl() + "机器人出库失败，无可用号！", 1, PlayLogTyper.Group_into, null);
                    playIntoGroupTask.setTaskState(4);
                } else {
                    GetRobotVO getRobotVO = robotAdminVOS.getData().get(0);
                    playIntoGroupTask.setPersonId(getRobotVO.getRobotSerialNo());
                    playIntoGroupTask.setTaskState(1);
                    setLog(task.getPlayId(), "群链接" + task.getGroupUrl() + "机器人出库成功！号ID为：" + getRobotVO.getRobotSerialNo(), 0, PlayLogTyper.Group_into, null);
                }
                playIntoGroupTask.setId(IdUtils.fastUUID());
                playIntoGroupTask.setCreateTime(new Date());
                playIntoGroupTask.setModifyTime(new Date());
                if (task.getRetryId() != null) {
                    playIntoGroupTask.setRetryId(task.getRetryId());
                } else {
                    playIntoGroupTask.setRetryId(task.getId());
                }
                playIntoGroupTaskMapper.insert(playIntoGroupTask);
            } else {
                log.info("入群失败已满重试次数："+JSONObject.toJSONString(task));
                task.setIsError(1);
                setLog(task.getPlayId(), "群链接" + task.getGroupUrl() + "该发言人入群已失败3次，不再进行重试！", 1, PlayLogTyper.Group_into, null);
                playIntoGroupTaskMapper.updateById(task);
            }
            return;
        }
        //入群成功 查询群信息
        //添加等待锁
        SpringUtils.getBean(RedisLock.class).waitLock("ruoyi:wait:groupCallback" + group.getGroupId(), 60);
        try {
            PlayGroupInfo groupInfo = playGroupInfoMapper.selectGroupInfoById(group.getGroupId(),play.getId());
            log.info("入群成功获取群信息："+JSONObject.toJSONString(groupInfo));
            if (groupInfo == null) {
                //创建群信息
                groupInfo = getGroupInfo(group, task);
                setLog(task.getPlayId(), "群" + groupInfo.getTgGroupId() + "已绑定成功！" + "机器人" + task.getPersonId() + "成功入群！", 0, PlayLogTyper.Group_into, null);
                playGroupInfoMapper.insert(groupInfo);
                //判断是不是外部群
                if (play.getGroupSource() == 1) {
                    //添加bot
                    log.info("外部群添加BOT入群："+JSONObject.toJSONString(group));
                    groupService.saveAndInviteBot(group);
                }
            }
            //查询当前机器人是否已和群做绑定
            Integer count = playRobotGroupRelationMapper.selectRobotGroup(group.getGroupId(), task.getPersonId());
            if (count == 0) {
                //绑定机器人和群信息记录表
                PlayRobotGroupRelation robotGroupRelation = new PlayRobotGroupRelation();
                robotGroupRelation.setPlayRobotGroupRelationId(IdUtils.fastUUID());
                robotGroupRelation.setMerchantId(task.getMerchantId());
                robotGroupRelation.setRobotId(task.getPersonId());
                robotGroupRelation.setGroupId(group.getGroupId());
                robotGroupRelation.setState(1);
                robotGroupRelation.setIsDelete(0);
                robotGroupRelation.setIsAdmin(task.getIsAdmin());
                robotGroupRelation.setIncomeGroupTime(new Date());
                robotGroupRelation.setCreateTime(new Date());
                robotGroupRelation.setModifyTime(new Date());
                playRobotGroupRelationMapper.insert(robotGroupRelation);
            }
            //查询群内机器人数量
            Integer robotCount = playRobotGroupRelationMapper.selectRobotGroupCount(group.getGroupId());
            if (robotCount == null){
                robotCount = 0;
            }
            //根据剧本计算所需的群人数
            Integer groupNum = play.getRobotNum();
            PlayExt playExt = JSONObject.parseObject(play.getPlayExt(), PlayExt.class);
            Integer groupAllCount = groupNum;
            //备用号逻辑
            if (playExt.getStandbyState() == 1) {
                //获取备用号规则
                groupAllCount = groupNum + (groupNum * vibeRule.getStandbyNum());
            }
            //查询任务表无法重试的机器人数量
            Integer errorCount = playIntoGroupTaskMapper.selectIsErrorCount(task.getGroupUrl());
            if (errorCount == null){
                errorCount = 0;
            }
            setLog(task.getPlayId(), "群" + groupInfo.getTgGroupId() + "群内水军数量为：" + robotCount + ",剧本所需目标数为：" + groupAllCount + ",重试3次后失败数量" + errorCount, 0, PlayLogTyper.Group_into, null);
            log.info("群内机器人数量："+robotCount);
            log.info("剧本所需目标数为："+groupAllCount);
            log.info("重试3次后失败的数量："+errorCount);
            log.info("剧本最低发言人数："+groupNum);
            if (robotCount + errorCount >= groupAllCount) {
                if (robotCount >= groupNum) {
                    groupInfo.setIntoStatus(2);
                    //判断是不是已有群
                    if (play.getGroupSource() == 0) {
                        log.info("满足群要求后通知Bot监控规则");
                        groupService.setBotAdMonitor(groupInfo.getTgGroupId(),play.getId(), play.getAdMonitor());
                    }
                    log.info("满足群要求后入群完成");
                    setLog(task.getPlayId(), "群" + groupInfo.getTgGroupId() + "已满足剧本所需发言人数，入群完成", 0, PlayLogTyper.Group_into, null);
                } else {
                    log.info("群入群失败！");
                    groupInfo.setIntoStatus(3);
                    groupInfo.setState(3);
                    groupInfo.setTip("入群失败！");
                    setLog(task.getPlayId(), "群" + groupInfo.getTgGroupId() + "已完成调度入群，不满足发言人数量，入群失败", 1, PlayLogTyper.Group_into, null);
                }
                playGroupInfoMapper.updateById(groupInfo);
            }
            //修改入群任务状态
            task.setTaskState(3);
            task.setGroupName(group.getGroupName());
            task.setGroupId(group.getGroupId());
            playIntoGroupTaskMapper.updateById(task);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SpringUtils.getBean(RedisLock.class).unWaitLock("ruoyi:wait:groupCallback" + group.getGroupId());
        }

    }

    public PlayGroupInfo getGroupInfo(GroupInfo dto, PlayIntoGroupTask task) {
        PlayGroupInfo groupInfo = new PlayGroupInfo();
        groupInfo.setGroupId(IdUtils.fastUUID());
        groupInfo.setMerchantId(task.getMerchantId());
        groupInfo.setGroupUrl(task.getGroupUrl());
        groupInfo.setTgGroupId(dto.getGroupId());
        groupInfo.setMemberCount(0);
        groupInfo.setTgGroupName(dto.getGroupName());
        groupInfo.setPlayId(task.getPlayId());
        groupInfo.setIsDelete(0);
        groupInfo.setState(0);
        groupInfo.setIntoStatus(1);
        return groupInfo;
    }

    //扫码群状态
    @Scheduled(cron = "0/20 * * * * ?")
    public void scanGroupState() {
        log.info("扫描群状态！");
        RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:scanGroupState");
        if (lock.isLocked()) {
            return;
        }
        try {
            lock.lock(60 * 60, TimeUnit.SECONDS);
            //获取入群中状态的剧本
            //扫描剧本状态为调用中 未开始的剧本
            List<Play> playList = playMapper.selectIntoGroupList(1, 3);
            log.info("获取入群状态中的剧本:"+JSONObject.toJSONString(playList));
            if (playList == null || playList.size() == 0) {
                return;
            }
            for (Play play : playList) {
                //查询剧本已完成调度群数
                log.info("入群中的剧本:"+JSONObject.toJSONString(play));
                Integer count = playGroupInfoMapper.selectSuccessGroupCount(play.getId());
                if (count == null){
                    count = 0;
                }
                log.info("剧本已完成调度的群数："+count);
                if (count >= play.getGroupNum()) {
                    //修改剧本状态
                    play.setScanProgress(4);
                    log.info("已修改剧本状态"+JSONObject.toJSONString(count));
                    setLog(play.getId(), "所有群已完成入群调度！", 0, PlayLogTyper.Group_into, null);
                    playMapper.updateById(play);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void setLog(String playId, String content, Integer state, PlayLogTyper typer, String groupId) {
        PlayExecutionLog log = new PlayExecutionLog();
        log.setId(IdUtils.fastUUID());
        log.setPlayId(playId);
        log.setCreateTime(new Date());
        log.setContent(content);
        log.setState(state);
        log.setType(typer);
        log.setGroupId(groupId);
        playExecutionLogService.saveLog(log);
    }

    //退群处理
    public void outGroup(Play play) {
        //根据剧本查询所有的群
        List<PlayGroupInfo> playGroupInfos = playGroupInfoMapper.selectGroupInfoByPlayId(play.getId());
        PlayExt playExt = JSONObject.parseObject(play.getPlayExt(), PlayExt.class);
        if (playExt.getQuitState() == 1) {
            //将所有水军变成等待退群
            for (PlayGroupInfo playGroupInfo : playGroupInfos) {
                playRobotGroupRelationMapper.updateRobotState(playGroupInfo.getTgGroupId());
                //获取群内所有水军
                List<PlayRobotGroupRelation> robotGroupRelations = playRobotGroupRelationMapper.selectRobotByGroup(playGroupInfo.getTgGroupId());
                if (robotGroupRelations != null){
                    List<String> strings = new ArrayList<>();
                    for (PlayRobotGroupRelation robotGroupRelation:robotGroupRelations){
                        strings.add(robotGroupRelation.getRobotId());
                    }
                    robotStatisticsService.decreaseRobotGroup(strings);
                }
            }
            //将群全部设置为删除
            playGroupInfoMapper.updateGroupState(play.getId());
        }
    }

    public void outGroupJob() {
        log.info("执行退群任务{}");
        RLock lockJob = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:outGroupJob");
        if (lockJob.isLocked()) {
            return;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR,-1);
            //扫描群内等待退群的机器人记录
            List<PlayRobotGroupRelation> robotGroupRelations = playRobotGroupRelationMapper.selectWaitOutGroup(calendar.getTime());
            VibeRuleDTO vibeRule = vibeRuleService.getOne();
            for (PlayRobotGroupRelation robotGroupRelation : robotGroupRelations) {
                RLock lock = SpringUtils.getBean(RedisLock.class).lock("ruoyi:wait:outGroupJob" + robotGroupRelation.getGroupId());
                if (lock.isLocked()) {
                    continue;
                }
                RLock robotLock = SpringUtils.getBean(RedisLock.class).lock("ruoyi:wait:outGroupJob" + robotGroupRelation.getRobotId());
                if (robotLock.isLocked()) {
                    continue;
                }
                try {
                    robotLock.lock(RandomListPicker.getRandom(vibeRule.getRobotIntervalStart(), vibeRule.getRobotIntervalEnd()), TimeUnit.SECONDS);
                    lock.lock(RandomListPicker.getRandom(vibeRule.getDiffRobotIntervalStart(), vibeRule.getDiffRobotIntervalEnd()), TimeUnit.SECONDS);
                    //操作机器人退群
                    ThirdTgExitChatroomInputDTO dto = new ThirdTgExitChatroomInputDTO();
                    dto.setTgRobotId(robotGroupRelation.getRobotId());
                    GroupInfo groupInfo = groupInfoMapper.selectById(robotGroupRelation.getGroupId());
                    dto.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                    OpenApiResult<TgBaseOutputDTO> openApiResult = OpenApiClient.exitChatroomByThirdKpTg(dto);
                    if (openApiResult.getCode() != 0) {
                        log.info("退群失败" + JSONObject.toJSONString(dto));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockJob.unlock();
        }
    }

    public void outGroupCallback(CalledDTO calledDTO, Called1100910039DTO dto) {
        if (calledDTO.getResultCode() != 0) {
            log.info("退群失败：" + JSONObject.toJSONString(calledDTO));
        }
        playRobotGroupRelationMapper.updateRobotOutState(dto.getChatroomSerialNo(), calledDTO.getRobotId(), 2);
    }

    /**
     *
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void scanImgGroupCallBack(){
        log.error("定时检测修改头像是否有回调超时的任务");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -5);
        playModifierGroupLogMapper.updateTaskByOutTime(calendar.getTime(), "无回调，自动变更失败！");
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void scanPersonGroupTask() {
        log.error("定时检测入群是否有回调超时的任务");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -5);
        playIntoGroupTaskMapper.updateTaskByOutTime(calendar.getTime(), "无回调，自动变更失败！");
    }

}