package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.domain.dto.play.PlayGroupInfo;
import com.ruoyi.system.domain.dto.play.PlayGroupMemberLog;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import com.ruoyi.system.mapper.PlayGroupInfoMapper;
import com.ruoyi.system.mapper.PlayGroupMemberLogMapper;
import com.ruoyi.system.mapper.PlayMapper;
import com.ruoyi.system.mapper.PlayRobotGroupRelationMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgGetGroupMemberInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.GroupInfoService;
import com.ruoyi.system.service.GroupRobotService;
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.PlayGroupInfoService;
import com.ruoyi.system.service.business.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/17/017 14:36
 * @Description :
 */
@Service
@Slf4j
public class PlayGroupInfoServiceImpl extends ServiceImpl<PlayGroupInfoMapper, PlayGroupInfo>
        implements PlayGroupInfoService {

    @Autowired
    PlayGroupInfoMapper playGroupInfoMapper;

    @Autowired
    PlayRobotGroupRelationMapper playRobotGroupRelationMapper;

    @Autowired
    PlayMapper playMapper;

    @Autowired
    PlayGroupMemberLogMapper playGroupMemberLogMapper;

    @Autowired
    GroupRobotService groupRobotService;

    @Autowired
    PlayExecutionLogService playExecutionLogService;

    @Autowired
    private GroupInfoService groupInfoService;


    @Override
    public List<PlayGroupInfo> listByPlayId(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<PlayGroupInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PlayGroupInfo::getPlayId, playId);
        lambdaQueryWrapper.eq(PlayGroupInfo::getIntoStatus, 2);
        lambdaQueryWrapper.orderByDesc(PlayGroupInfo::getCreateTime);
        return super.list(lambdaQueryWrapper);
    }

    @Override
    public R<String> forceFinishGroup(String groupId,String playId) {
        log.info("强制结束入群状态");
        //根据群链接和剧本ID查询群
        PlayGroupInfo playGroupInfo = playGroupInfoMapper.selectGroupInfoById(groupId,playId);
        if (playGroupInfo == null) {
            return R.fail("群ID不存在！");
        }
        //查询群内机器人数量
        Integer robotCount = playRobotGroupRelationMapper.selectRobotGroupCount(groupId);
        if (robotCount == null) {
            robotCount = 0;
        }
        Play play = playMapper.selectPlayById(playGroupInfo.getPlayId());
        //根据剧本计算所需的群人数
        Integer groupNum = play.getRobotNum();
        if (robotCount >= groupNum) {
            playGroupInfo.setIntoStatus(2);
            //获取群ID
            GroupInfo groupInfo = groupInfoService.getById(playGroupInfo.getGroupId());
            retryGroupMember(playGroupInfo.getGroupId(),play.getId(),groupInfo.getGroupSerialNo());
        } else {
            log.info("群入群失败！");
            playGroupInfo.setIntoStatus(3);
            playGroupInfo.setState(3);
            playGroupInfo.setTip("入群失败！群人数不足");
        }
        playGroupInfoMapper.updateById(playGroupInfo);
        return R.ok();
    }

    public  void retryGroupMember(String groupId,String playId,String tgGroupId){
        //调用开平接口获取群成员
        ThirdTgGetGroupMemberInputDTO dto = new ThirdTgGetGroupMemberInputDTO();
        dto.setChatroomSerialNo(tgGroupId);
        GroupRobot robot = groupRobotService.getAdminRobot(groupId);
        if (robot != null){
            dto.setTgRobotId(robot.getRobotId());
            @SuppressWarnings("rawtypes")
            OpenApiResult<TgBaseOutputDTO> ret = OpenApiClient.getGroupMemberByThirdKpTg(dto);
            if (ret.getCode() == 0){
                setMemberLog(groupId,playId,robot.getRobotId(),tgGroupId,1,ret.getData().getOptSerNo());
                setLog(playId, "群" + groupId + "同步开平群成员请求成功！等待回调", 0, PlayLogTyper.Group_into, null);
            }else {
                setMemberLog(groupId,playId,robot.getRobotId(),tgGroupId,4,ret.getData().getOptSerNo());
                setLog(playId, "群" + groupId + "同步开平群成员失败！原因："+ret.getMessage(), 1, PlayLogTyper.Group_into, null);
            }
        }else {
            setLog(playId, "群" + groupId + "获取群主号异常！", 1, PlayLogTyper.Group_into, null);
        }

    }

    public void  setMemberLog(String groupId,String playId,String robotId,String tgGroupId,Integer state,String opt){
        PlayGroupMemberLog playGroupMemberLog = new PlayGroupMemberLog();
        playGroupMemberLog.setGroupId(groupId);
        playGroupMemberLog.setGroupSerialNo(tgGroupId);
        playGroupMemberLog.setId(IdUtils.fastUUID());
        playGroupMemberLog.setOptSerNo(opt);
        playGroupMemberLog.setPlayId(playId);
        playGroupMemberLog.setRobotId(robotId);
        playGroupMemberLog.setCreateTime(new Date());
        playGroupMemberLogMapper.insert(playGroupMemberLog);
    }

    public void setLog(String playId, String content, Integer state, PlayLogTyper typer, String groupId) {
        PlayExecutionLog log = new PlayExecutionLog();
//        log.setId(IdUtils.fastUUID());
        log.setPlayId(playId);
        log.setCreateTime(new Date());
        log.setContent(content);
        log.setState(state);
        log.setType(typer);
        log.setGroupId(groupId);
        playExecutionLogService.saveLog(log);
    }


}
