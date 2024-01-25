package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.PlayConstants;
import com.ruoyi.common.core.domain.dto.play.ContentJson;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.enums.play.PushStateEnum;
import com.ruoyi.system.components.RandomListPicker;
import com.ruoyi.system.domain.PlayMessageConfoundLog;
import com.ruoyi.system.domain.dto.play.PlayGroupInfo;
import com.ruoyi.system.domain.dto.play.PushOperationDTO;
import com.ruoyi.system.domain.dto.play.QueryRobotDetailDTO;
import com.ruoyi.system.domain.vo.play.QueryRobotDetailVO;
import com.ruoyi.system.domain.vo.play.RobotStatisticsVO;
import com.ruoyi.system.mapper.PlayMessageConfoundLogMapper;
import com.ruoyi.system.mapper.PlayMessageMapper;
import com.ruoyi.system.mapper.PlayMessagePushMapper;
import com.ruoyi.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
public class PlayMessagePushServiceImpl extends ServiceImpl<PlayMessagePushMapper, PlayMessagePush>
        implements PlayMessagePushService {

    @Resource
    private IPlayService playInfoService;

    @Resource
    private PlayGroupInfoService playGroupInfoService;

    @Resource
    private PlayMessageMapper playMessageMapper;

    @Resource
    private PlayMessagePushDetailService playMessagePushDetailService;
    @Resource
    private PlayMessageConfoundLogMapper playMessageConfoundLogMapper;

    @Resource
    private PlayMessageConfoundService playMessageConfoundService;


    @Override
    public List<PlayMessagePush> listByPlayId(Integer playId) {
        if (playId == null) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<PlayMessagePush> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PlayMessagePush::getPlayId, playId);
        return super.list(lambdaQueryWrapper);
    }

    @Override
    public void operation(PushOperationDTO dto) {
        if (dto == null) {
            return;
        }
        for (Integer pushId : dto.getPushIds()) {
            //操作类型 0-暂停 1-继续 2-取消 3-强制开炒
            switch (dto.getOp()) {
                case 0:
                    this.pauseGroupPush(pushId);
                    break;
                case 1:
                    this.resumeGroupPush(pushId);
                    break;
                case 2:
                    this.cancelGroupPush(pushId);
                    break;
                case 3:
                    this.forceStartGroupPush(pushId);
                    break;
            }
        }

    }

    /**
     * 取消
     *
     * @param pushId
     */
    public void cancelGroupPush(Integer pushId) {
        PlayMessagePush playMessagePush = super.getById(pushId);
        this.cancelGroupPush(playMessagePush);
    }

    public void cancelGroupPush(PlayMessagePush playMessagePush) {
        if (playMessagePush == null) {
            return;
        }
        Integer pushState = playMessagePush.getPushState();
        boolean isTrue = pushState.equals(PushStateEnum.WAIT_SEND.getKey())
                || pushState.equals(PushStateEnum.ING.getKey())
                || pushState.equals(PushStateEnum.FINISH.getKey());
        Assert.isTrue(isTrue,"当前状态无法取消");
        playMessagePush.setPushState(PushStateEnum.CANCEL.getKey());
        super.updateById(playMessagePush);
    }

    /**
     * 强制开炒
     *
     * @param pushId
     */
    public void forceStartGroupPush(Integer pushId) {
        PlayMessagePush playMessagePush = super.getById(pushId);
        this.forceStartGroupPush(playMessagePush);
    }

    public void forceStartGroupPush(PlayMessagePush playMessagePush) {
        if (playMessagePush == null) {
            return;
        }
        // 状态校验
        Assert.isTrue(playMessagePush.getRobotAllocationFlag().equals(1), "号未分配无法强制开炒");
        Assert.isTrue(playMessagePush.getRobotPackFlag().equals(1), "人设未包装无法强制开炒");
        Assert.isTrue(playMessagePush.getPushState().equals(PushStateEnum.WAIT_SEND.getKey()), "非待执行状态无法强制开炒");
        playMessagePush.setSendFlag(1);
        super.updateById(playMessagePush);
    }

    /**
     * 暂停群推送
     *
     * @param pushId
     */
    @Override
    public void pauseGroupPush(Integer pushId) {
        PlayMessagePush playMessagePush = super.getById(pushId);
        this.resumeGroupPush(playMessagePush);
    }

    @Override
    public void pauseGroupPush(PlayMessagePush playMessagePush) {
        if (playMessagePush == null) {
            return;
        }
        Integer pushState = playMessagePush.getPushState();
        boolean isTrue = pushState.equals(PushStateEnum.ING.getKey());
        Assert.isTrue(isTrue,"当前状态无法暂停");
//        PlayDirector.getInstanceOfLK().pause(playMessagePush.getPlayId(), playMessagePush.getGroupId());
        //修改推送记录表
        playMessagePush.setPushState(PushStateEnum.USER_STOP.getKey());
        super.updateById(playMessagePush);
    }

    /**
     * 继续推送
     *
     * @param pushId
     */
    @Override
    public void resumeGroupPush(Integer pushId) {
        PlayMessagePush playMessagePush = super.getById(pushId);
        this.resumeGroupPush(playMessagePush);
    }

    @Override
    public void resumeGroupPush(PlayMessagePush playMessagePush) {
        if (playMessagePush == null ) {
            return;
        }
        Integer pushState = playMessagePush.getPushState();
        boolean isTrue = pushState.equals(PushStateEnum.USER_STOP.getKey());
        Assert.isTrue(isTrue,"当前状态无法开启");
        //继续推送
//        PlayDirector.getInstanceOfLK().resume(playMessagePush.getPlayId(), playMessagePush.getGroupId());
//        //修改推送记录表
        playMessagePush.setPushState(PushStateEnum.ING.getKey());
        super.updateById(playMessagePush);
    }

    @Override
    public void createPush(String playId) {
        Play playInfo = playInfoService.getById(playId);
        Assert.notNull(playInfo, "没有找到对应的剧本信息");
        try {
            log.info("createPush start {}", JSON.toJSONString(playInfo));

            List<PlayGroupInfo> playGroupInfoList = playGroupInfoService.listByPlayId(playId);
            Assert.isTrue(CollectionUtils.isNotEmpty(playGroupInfoList), "剧本对应群未找到");

            //需要推送的群集合构造
            List<PlayMessagePush> playMessagePushList = new ArrayList<>();

            for (PlayGroupInfo playGroupInfo : playGroupInfoList) {
                PlayMessagePush playMessagePush = new PlayMessagePush();
                playMessagePush.setPlayId(playId);
                playMessagePush.setGroupId(playGroupInfo.getTgGroupId());
                playMessagePush.setGroupName(playGroupInfo.getTgGroupName());

                playMessagePush.setRobotNum(playInfo.getRobotNum());
                // 剧本上未存储 消息数量
                playMessagePush.setContentNum(0);

                playMessagePushList.add(playMessagePush);
            }
            // 插入前先情况老关联数据
            this.removeByPlayId(playInfo.getId());
            //推送任务插入
            super.saveBatch(playMessagePushList);
            //插入推送详情信息
            if (playInfo.getIsConfound() == 1) {
                // 混淆逻辑
                playMessageConfoundService.clearAndCreateConfound(playInfo.getId());
            } else {
                insertPushDetail(playInfo, playMessagePushList.stream().map(e -> e.getId()).collect(Collectors.toList()));
            }
        } catch (Exception e) {
            log.info("createPush 程序内部错误", e);
        }
    }

    /**
     * 按剧本删除 MessagePush
     *
     * @param playId
     */
    private void removeByPlayId(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return;
        }
        // 需要先清空 修改前的Push数据
        LambdaQueryWrapper<PlayMessagePush> removeWrapper = new LambdaQueryWrapper<>();
        removeWrapper.eq(PlayMessagePush::getPlayId, playId);
        super.remove(removeWrapper);
    }

    //插入推送详情信息
    private void insertPushDetail(Play playInfo, List<Integer> pushIds) {
        String playId = playInfo.getId();
        //查询剧本对应的消息
        List<PlayMessage> playMessageList = playMessageMapper.selectListByPlayId(playId);
        Assert.isTrue(CollectionUtils.isNotEmpty(playMessageList), "找不到对应的剧本消息,playId:" + playId);
        log.info("insertPushDetail {} {} {}", playId, pushIds, playMessageList.size());
        List<PlayMessagePushDetail> pushDetailList = new ArrayList<>();
        for (Integer pId : pushIds) {
            for (PlayMessage playMessage : playMessageList) {
                PlayMessagePushDetail playMessagePushDetail = new PlayMessagePushDetail();
                playMessagePushDetail.setPlayMsgPushId(pId);
                playMessagePushDetail.setSpokesmanNickname(playMessage.getRobotNickname());
                playMessagePushDetail.setCallAll(0);
                playMessagePushDetail.setIntervalTime(playMessage.getIntervalTime());
                playMessagePushDetail.setPlayErrorType(playMessage.getPlayErrorType());
                playMessagePushDetail.setMessageContent(playMessage.getMessageContent());
                playMessagePushDetail.setMessageSort(playMessage.getMessageSort());
                playMessagePushDetail.setSendState(0);
                pushDetailList.add(playMessagePushDetail);
            }
        }
        playMessagePushDetailService.saveBatch(pushDetailList);
        // 不要混淆的剧本 标记到 5-号分配
        playInfo.setScanProgress(5);
        playInfo.setConfoundState(1);
        playInfoService.updateById(playInfo);

    }

    //插入推送详情信息
    private void insertPushDetailConfound(Play playInfo, List<Integer> pushIds) {
        String playId = playInfo.getId();
        //查询剧本对应的消息
        List<PlayMessage> playMessageList = playMessageMapper.selectListByPlayId(playId);
        Assert.isTrue(CollectionUtils.isNotEmpty(playMessageList), "找不到对应的剧本消息,playId:" + playId);

        log.info("insertPushDetailConfound {} {} {}", playId, pushIds, playMessageList.size());
        List<PlayMessagePushDetail> pushDetailList = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        String splitRegex = PlayConstants.splitRegex;

        for (Integer pId : pushIds) {
            for (PlayMessage playMessage : playMessageList) {
                PlayMessagePushDetail playMessagePushDetail = new PlayMessagePushDetail();
                playMessagePushDetail.setPlayMsgPushId(pId);
                playMessagePushDetail.setSpokesmanNickname(playMessage.getRobotNickname());
                playMessagePushDetail.setCallAll(0);
                playMessagePushDetail.setIntervalTime(playMessage.getIntervalTime());
                playMessagePushDetail.setPlayErrorType(playMessage.getPlayErrorType());
                List<ContentJson> contentJsons = JSON.parseArray(playMessage.getMessageContent(), ContentJson.class);
                for (ContentJson contentJson : contentJsons) {
                    //先根据UUid查询是否有结果
                    List<String> strings = map.getOrDefault(contentJson.getUuid(), new ArrayList<>());
                    if (strings == null || strings.size() == 0) {
                        if (!PlayConstants.confoundMomentTypeIds.contains(contentJson.getMomentTypeId())) {
                            continue;
                        }
                        //根据uuid查询混淆结果
                        List<PlayMessageConfoundLog> logs = playMessageConfoundLogMapper.selectConfoundList(contentJson.getUuid());
                        if (logs == null) {
                            continue;
                        }
                        for (PlayMessageConfoundLog log : logs) {
                            String[] s = log.getResultContent().split(splitRegex);
                            strings.addAll(Arrays.asList(s));
                        }
                    }
                    int index = RandomListPicker.pickRandom(strings);
                    if (contentJson.getMomentTypeId().intValue() == 2002) {
                        contentJson.setSMateImgUrl(strings.get(index));
                    } else {
                        contentJson.setSMateContent(strings.get(index));
                    }
                    strings.remove(index);
                    map.put(contentJson.getUuid(), strings);
                }
                playMessagePushDetail.setMessageContent(JSON.toJSONString(contentJsons));
                playMessagePushDetail.setMessageSort(playMessage.getMessageSort());
                playMessagePushDetail.setSendState(0);
                pushDetailList.add(playMessagePushDetail);
            }
        }
        playMessagePushDetailService.saveBatch(pushDetailList);
        // 混淆完成 标记到 5-号分配
        playInfo.setScanProgress(5);
        playInfoService.updateById(playInfo);

        // 记录混淆日志
        PlayExecutionLogService.playConfoundLog(playInfo.getId(), "混淆剧本完成", null);

    }


    @Override
    public void createPushDetail(String playId) {
        Play playInfo = playInfoService.getById(playId);
        Assert.notNull(playInfo, "剧本未找到");
        LambdaQueryWrapper<PlayMessagePush> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PlayMessagePush::getPlayId, playId);
        lambdaQueryWrapper.orderByAsc(PlayMessagePush::getId);
        lambdaQueryWrapper.select(PlayMessagePush::getId);
        List<PlayMessagePush> list = list(lambdaQueryWrapper);
        List<Integer> pushIds = list.stream().map(PlayMessagePush::getId).collect(Collectors.toList());
        this.insertPushDetailConfound(playInfo, pushIds);
    }

    @Override
    public RobotStatisticsVO getRobotStatisticsVO(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return null;
        }
        return baseMapper.getRobotStatisticsVO(playId);
    }

    @Override
    public List<QueryRobotDetailVO> robotDetails(QueryRobotDetailDTO dto) {
        return baseMapper.robotDetails(dto);
    }

    @Override
    public List<PlayMessagePush> selectByPlayId(String playId) {
        if (playId == null) {
            return new ArrayList<>();
        }

        return super.list(new LambdaQueryWrapper<PlayMessagePush>()
                .eq(PlayMessagePush::getPlayId, playId)
                .eq(PlayMessagePush::getIsDelete, 0));
    }

    @Override
    public List<PlayMessagePush> selectByPlayIdAndState(String playId, Integer state) {
        if (playId == null) {
            return new ArrayList<>();
        }

        return super.list(new LambdaQueryWrapper<PlayMessagePush>()
                .eq(PlayMessagePush::getPlayId, playId)
                .eq(PlayMessagePush::getPushState, state)
                .eq(PlayMessagePush::getIsDelete, 0));
    }

    @Override
    public PlayMessagePush getOneByPlayIdAndGroupId(String playId, String groupId) {
        return super.getOne(new LambdaQueryWrapper<PlayMessagePush>()
                .eq(PlayMessagePush::getPlayId, playId)
                .eq(PlayMessagePush::getGroupId, groupId)
                .eq(PlayMessagePush::getIsDelete, 0)
                .last(" limit 1 ")
        );
    }
}




