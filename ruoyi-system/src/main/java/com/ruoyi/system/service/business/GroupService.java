package com.ruoyi.system.service.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.AdMonitor;
import com.ruoyi.common.core.domain.dto.play.VibeRuleDTO;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.core.thread.AsyncTask;
import com.ruoyi.common.enums.GroupAction;
import com.ruoyi.common.enums.InviteBotAction;
import com.ruoyi.common.enums.LimitingDimensions;
import com.ruoyi.common.enums.SetAdminAction;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.bot.ApiClient;
import com.ruoyi.system.bot.mode.input.AdMonitorDTO;
import com.ruoyi.system.bot.mode.output.BotInfoVO;
import com.ruoyi.system.callback.dto.Called1100860002DTO;
import com.ruoyi.system.callback.dto.Called1100910017DTO;
import com.ruoyi.system.callback.dto.Called1100910039DTO;
import com.ruoyi.system.callback.dto.Called1100910053DTO;
import com.ruoyi.system.components.spi.TgGroupHashSettings;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.GroupMemberInfoVO;
import com.ruoyi.system.domain.vo.GroupResourceVO;
import com.ruoyi.system.domain.vo.GroupRobotVO;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.*;
import com.ruoyi.system.openapi.model.output.ExtTgSelectGroupMemberVO;
import com.ruoyi.system.openapi.model.output.ExtTgSelectGroupVO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 朱少波
 * @date 2024/1/15/015 15:47
 */
@Service
@AllArgsConstructor
@Slf4j
public class GroupService {

    private final GroupInfoService groupInfoService;

    private final GroupClusterRefService groupClusterRefService;

    private final GroupClusterService groupClusterService;

    private final GroupStateService groupStateService;

    private final GroupMonitorInfoService groupMonitorInfoService;

    private final GroupRobotService groupRobotService;

    private final RedisLock redisLock;

    private final RedisCache redisCache;

    private final IVibeRuleService ibiRuleService;

    private final RobotStatisticsService robotStatisticsService;

    private final GroupActionLogService groupActionLogService;

    private final GroupBatchActionService groupBatchActionService;

    private final IRobotService iRobotService;

    private final IPlayService playbackService;

    /**
     * 选群
     *
     * @param dto 选群条件
     * @return 满足条件群
     */
    public R<List<GroupInfoVO>> queryGroup(GroupQueryDTO dto) {
        int count;
        List<String> failGroupId = new ArrayList<>();
        List<String> countryCodes = new ArrayList<>();
        List<RLock> locks = new ArrayList<>();
        List<GroupInfoVO> groupInfoList = new ArrayList<>();
        VibeRuleDTO one = ibiRuleService.getOne();
        if (one == null) {
            return R.fail("无风控规则");
        }

        try {
            while (groupInfoList.size() < dto.getGroupNum()) {
                count = dto.getGroupNum() - groupInfoList.size();
                List<GroupInfoVO> result = groupInfoService.selectGroup(dto.getRegistrationDay(), count, countryCodes, failGroupId);
                //没有满足条件的群
                if (CollUtil.isEmpty(result)) {
                    //如果有优先国家  清空优先国家再次查询 否则跳出循环
                    if (CollUtil.isNotEmpty(countryCodes)) {
                        countryCodes = new ArrayList<>();
                    } else {
                        break;
                    }
                }
                for (GroupInfoVO groupInfoVO : result) {
                    //加锁 防止并发取群
                    String key = "selectGroup:" + groupInfoVO.getGroupId();
                    RLock lock = redisLock.lock(key);
                    if (!lock.isLocked()) {
                        failGroupId.add(groupInfoVO.getGroupId());
                        log.info("选群失败 加锁失败={}", groupInfoVO);
                        continue;
                    }
                    locks.add(lock);
                    //检查是否满足风控
                    if (one.getSetManageLimit() != null && dto.getSetAdminCount() != null && !robotStatisticsService.checkAndAddLeaderCount(groupInfoVO.getLeaderId(), dto.getSetAdminCount(), one.getSetManageLimit())) {
                        failGroupId.add(groupInfoVO.getGroupId());
                        log.info("选群失败 单个群主号可设置管理员的上限={}", groupInfoVO);
                        //提前解锁
                        lock.unlock();
                        locks.remove(lock);
                        continue;
                    }
                    List<GroupRobot> adminRobots = groupRobotService.getAdminRobots(groupInfoVO.getGroupId());
                    if (CollUtil.isEmpty(adminRobots)) {
                        failGroupId.add(groupInfoVO.getGroupId());
                        continue;
                    }

                    groupInfoVO.setRobots(adminRobots.stream().map(p -> BeanUtil.copyProperties(p, GroupRobotVO.class)).collect(Collectors.toList()));

                    groupInfoList.add(groupInfoVO);
                }
            }
            //满足条件的数据
            if (groupInfoList.size() >= dto.getGroupNum()) {
                List<String> groupIds = groupInfoList.stream().map(GroupInfoVO::getGroupId).collect(Collectors.toList());
                groupStateService.markUsed(groupIds, 1);
                groupMonitorInfoService.setPlayId(groupIds, dto.getPayId());

                return R.ok(groupInfoList);
            } else {
                if (CollUtil.isNotEmpty(groupInfoList) && dto.getSetAdminCount() != null) {
                    groupInfoList.forEach(p -> robotStatisticsService.restoreQuantity(p.getLeaderId(), dto.getSetAdminCount()));
                }
                return R.fail("群资源不足");
            }
        } finally {
            if (CollUtil.isNotEmpty(locks)) {
                locks.forEach(redisLock::unlock);
            }
        }
    }


    /**
     * 取消标记群已使用
     *
     * @param groupIds 群id集合
     */
    public void cancelGroup(List<String> groupIds) {
        groupStateService.markUsed(groupIds, 0);
    }

    @Transactional(rollbackFor = Exception.class)
    public void importResource(List<GroupResourceVO> resourceList, String newClusterId) {
        if (CollUtil.isEmpty(resourceList)) {
            return;
        }
        //1.保存群机器信息
        List<GroupInfo> groupInfos = groupInfoService.saveImportGroup(resourceList);
        //保存群组关联
        List<String> groupIds = groupClusterRefService.add(groupInfos.stream().map(GroupInfo::getGroupId).collect(Collectors.toList()), newClusterId);
        if (CollUtil.isEmpty(groupIds)) {
            return;
        }
        groupInfos = groupInfos.stream().filter(p -> groupIds.contains(p.getGroupId())).collect(Collectors.toList());
        //默认保存群主信息
        Map<String, GroupRobot> robotMap = groupRobotService.addGroupLeader(groupIds, getRobotMap(groupInfos, resourceList));
        //设置号为群主号
        iRobotService.setGroupOwner(robotMap.values().stream().map(GroupRobot::getRobotId).collect(Collectors.toList()));
        //保存群状态
        groupStateService.addImportGroup(groupIds);
        //新增bot监控表
        groupMonitorInfoService.add(groupIds);
        //同步群信息
        List<GroupInfo> finalGroupInfos = groupInfos;
        AsyncTask.execute(() -> syncInfo(finalGroupInfos, robotMap));
        //执行邀请bot进群
        AsyncTask.execute(() -> invitingBotJoin(finalGroupInfos, robotMap::get, true));
    }


    public Map<String, String> getRobotMap(List<GroupInfo> groupInfos, List<GroupResourceVO> resourceList) {
        Map<String, String> robotMap = resourceList.stream().collect(Collectors.toMap(GroupResourceVO::getGroupSerialNo, GroupResourceVO::getRobotId, (a, b) -> b));
        return groupInfos.stream().collect(Collectors.toMap(GroupInfo::getGroupId, p -> robotMap.get(p.getGroupSerialNo()), (a, b) -> b));

    }


    public void syncInfo(List<GroupInfo> groupInfos, Map<String, GroupRobot> robotMap) {
        for (GroupInfo groupInfo : groupInfos) {
            try {
                GroupRobot robot = robotMap == null ? groupRobotService.getAdminRobot(groupInfo.getGroupId()) : robotMap.get(groupInfo.getGroupId());
                if (robot == null) {
                    continue;
                }

                ThirdTgGetChatroomInfoInputDTO data = new ThirdTgGetChatroomInfoInputDTO();
                data.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                data.setTgRobotId(robot.getRobotId());
                OpenApiResult<TgBaseOutputDTO> pageOpenApiResult = OpenApiClient.getChatroomInfoByThirdKpTg(data);
                log.info("getChatroomInfoByThirdKpTg={},{}", JSON.toJSONString(data), JSON.toJSONString(pageOpenApiResult));
            } catch (Exception e) {
                log.info("getChatroomInfoByThirdKpTg.error={}", groupInfos, e);
            }
        }

        try {
            ThirdTgSelectGroupListDTO data = new ThirdTgSelectGroupListDTO();

            data.setChatroomSerialNos(groupInfos.stream().map(GroupInfo::getGroupSerialNo).collect(Collectors.toList()));
            OpenApiResult<List<ExtTgSelectGroupVO>> pageOpenApiResult = OpenApiClient.selectGroupListByThirdUtchatTg(data);
            log.info("selectGroupListByThirdUtchatTg={},{}", JSON.toJSONString(data), JSON.toJSONString(pageOpenApiResult));
            if (pageOpenApiResult.isSuccess()) {
                groupInfoService.syncGroupInfo(groupInfos, pageOpenApiResult.getData());
            }
        } catch (Exception e) {
            log.info("syncInfo.error={}", groupInfos, e);
        }

    }

    public int invitingBotJoin(List<String> groupIds) {
        return invitingBotJoin(groupInfoService.listByIds(groupIds), groupRobotService::getAdminRobot, false);
    }

    public int setBotAdmin(List<String> groupIds) {
        int count = 0;
        List<GroupInfo> groupInfos = groupInfoService.listByIds(groupIds);
        Assert.notEmpty(groupInfos, "群不存在");
        for (GroupInfo groupInfo : groupInfos) {
            if (setBotAdmin(groupInfo)) {
                count++;
            }
        }
        return count;
    }

    public boolean setBotAdmin(GroupInfo groupInfo) {
        log.info("设置bot管理员={}", JSON.toJSONString(groupInfo));

        GroupMonitorInfo groupMonitorInfo = groupMonitorInfoService.getById(groupInfo.getGroupId());
        if (groupMonitorInfo == null
                || ObjectUtil.notEqual(1, groupMonitorInfo.getBotCheck())
                || ObjectUtil.equal(1, groupMonitorInfo.getBotAdmin())) {
            log.info("无法设置bot管理员={}", JSON.toJSONString(groupMonitorInfo));
            return false;
        }

        GroupRobot adminRobot = groupRobotService.getRobot(groupInfo.getGroupId(), groupMonitorInfo.getRobotId());
        if (adminRobot == null) {
            log.info("无法设置bot管理员={}", JSON.toJSONString(groupMonitorInfo));
            return false;
        }

        String key = "setBotAdmin:" + groupInfo.getGroupId();
        RLock lock = redisLock.lock(key);
        if (!lock.isLocked()) {
            log.info("设置管理员 加锁失败={}", JSON.toJSONString(groupInfo));
            return false;
        }
        try {
            //当前是否有批次任务执行邀请bot进群检测
            GroupBatchAction batchAction = groupBatchActionService.getBatchAction(groupInfo.getGroupId(), 0);
            if (batchAction != null) {
                //上次邀请bot进群动作 已超过10分钟没结果  认为是失败
                if (Duration.between(batchAction.getLastActionTime(), LocalDateTime.now()).toMinutes() > 10) {
                    groupBatchActionService.updateStatus(batchAction.getBatchId(), 1);
                } else {
                    return false;
                }
            }
            InviteBotAction inviteBotAction = InviteBotAction.of(3);

            //初始化批量任务动作
            GroupBatchAction action = new GroupBatchAction();
            action.setBatchId(IdWorker.getIdStr());
            action.setSetType(0);
            action.setActionProgress(inviteBotAction.getCode());
            action.setRetryCount(0);
            action.setGroupId(groupInfo.getGroupId());
            action.setLastActionTime(LocalDateTime.now());
            action.setCreateTime(LocalDateTime.now());
            action.setBatchStatus(0);
            groupBatchActionService.save(action);

            AsyncTask.execute(() -> runAction(action.getBatchId(), inviteBotAction.getAction(), groupInfo, adminRobot, groupMonitorInfo.getBotSerialNo(), ""));

        } catch (Exception e) {
            log.error("邀请bot进群异常={}", groupInfo, e);
            return false;
        } finally {
            lock.unlock();
        }
        return true;
    }

    public int invitingBotJoin(List<GroupInfo> groupInfos, Function<String, GroupRobot> getGroupRobotFuc, boolean newGroup) {
        int count = 0;
        if (CollUtil.isEmpty(groupInfos)) {
            return count;
        }

        for (GroupInfo groupInfo : groupInfos) {
            GroupRobot groupRobot = getGroupRobotFuc.apply(groupInfo.getGroupId());
            if (groupRobot == null) {
                continue;
            }
            boolean invited = newGroup;
            if (!invited) {
                GroupMonitorInfo gm = groupMonitorInfoService.getById(groupInfo.getGroupId());
                if (gm != null && ObjectUtil.equal(0, gm.getBotCheck())) {
                    invited = true;
                }
            }
            if (invited && invitingBotJoin(groupInfo, getGroupRobotFuc.apply(groupInfo.getGroupId()))) {
                count++;
            }
        }
        return count;
    }


    public void handleActionResult(String id, String optNo, boolean success, String msg, Object data) {
        try {
            if (StrUtil.isBlank(id)) {
                id = redisCache.getCacheObject("ruoyi-admin:action:" + optNo);
                log.info("getActionId={},{}", optNo, id);
            }
            if (StrUtil.isBlank(id)) {
                return;
            }
            handleActionResult(groupActionLogService.getById(id), optNo, success, msg, data);
        } catch (Exception e) {
            log.info("handleActionResult.error={},{}", id, optNo);
        }
    }

    public void handleActionResult(GroupActionLog groupActionLog, String optNo, boolean success, String msg, Object data) {
        if (groupActionLog == null) {
            return;
        }
        groupActionLogService.handleActionResult(groupActionLog.getId(), optNo, success, msg);
        //无操作动作记录 或者无批量id 直接返回
        if (StrUtil.isBlank(groupActionLog.getBatchId())) {
            return;
        }

        GroupBatchAction groupBatch = groupBatchActionService.getById(groupActionLog.getBatchId());
        if (ObjectUtil.equal(0, groupBatch.getSetType())) {
            doNextInvitingBotJoinAction(groupBatch, groupActionLog, success, data);
        } else if (ObjectUtil.equal(1, groupBatch.getSetType())) {
            doNextSetAdmin(groupBatch, groupActionLog, success, data);
        } else {
            groupBatchActionService.updateStatus(groupActionLog.getBatchId(), success ? 2 : 1);
        }

    }


    public void doNextInvitingBotJoinAction(GroupBatchAction groupBatch, GroupActionLog groupActionLog, boolean success, Object data) {
        //当前bot动作
        InviteBotAction botAction = InviteBotAction.of(groupBatch.getActionProgress());
        if (botAction == null) {
            groupBatchActionService.updateStatus(groupBatch.getBatchId(), success ? 2 : 1);
            return;
        }
        try {
            if (success) {
                if (botAction == InviteBotAction.INVITE_BOT_JOIN_GROUP) {
                    groupRobotService.addBot(groupBatch.getGroupId(), groupActionLog.getChangeValue());
                }
                String value = "";
                if (botAction == InviteBotAction.SET_BOT_ADMIN) {
                    groupMonitorInfoService.setBotAdmin(groupBatch.getGroupId());
                }
                if (botAction == InviteBotAction.INVITE_BOT_JOIN_GROUP) {
                    groupMonitorInfoService.robotCheck(groupBatch.getGroupId());
                }
                if (botAction == InviteBotAction.QUERY_HASH) {
                    value = ((List<Called1100860002DTO>) data).get(0).getAccessHash();
                }
                if (botAction == InviteBotAction.SEARCH_BOT) {
                    List<Called1100910017DTO.Called1100910017UserDTO> userList = (List<Called1100910017DTO.Called1100910017UserDTO>) data;
                    Assert.notEmpty(userList, "搜索bot失败");
                    value = userList.stream().filter(p -> ObjectUtil.equal(p.getUserName(), groupActionLog.getChangeValue()))
                            .findFirst().map(Called1100910017DTO.Called1100910017UserDTO::getUserSerialNo).orElse("");
                    Assert.notBlank(value, "搜索bot失败");
                    //更新bot的用户编号
                    groupMonitorInfoService.updateRobotSerialNo(groupBatch.getGroupId(), value, groupActionLog.getRobotId());
                }

                InviteBotAction nextAction = botAction.getNextAction();
                if (nextAction == null) {
                    //所有动作完成 标记bot已进群检查
                    groupMonitorInfoService.robotCheck(groupBatch.getGroupId());
                    //批次动作完成
                    groupBatchActionService.updateStatus(groupBatch.getBatchId(), 2);
                    return;
                }
                GroupInfo groupInfo = groupInfoService.getById(groupBatch.getGroupId());

                //当前动作是搜索bot  对比username 获取是bot的数据
                if (botAction == InviteBotAction.INVITE_BOT_JOIN_GROUP && ObjectUtil.equal(groupInfo.getCreateType(), 20)) {
                    //批次动作完成
                    groupBatchActionService.updateStatus(groupBatch.getBatchId(), 2);
                    //设置剧本广告监控
                    setBotAdMonitor(groupInfo.getGroupId());
                    return;
                }
                groupBatchActionService.doNextAction(groupBatch.getBatchId(), nextAction.getCode(), 0);

                //执行动作
                runAction(groupBatch.getBatchId(), nextAction.getAction(), groupInfo,
                        groupRobotService.getRobot(groupBatch.getGroupId(), groupActionLog.getRobotId()), groupActionLog.getChangeValue(), value);
            } else {
                //如果需要重试 则当前动作重复操作
                if (groupBatch.getRetryCount() < botAction.getRetryCount()) {
                    groupBatchActionService.doNextAction(groupBatch.getBatchId(), botAction.getCode(), groupBatch.getRetryCount() + 1);
                    runAction(groupBatch.getBatchId(), botAction.getAction(), groupInfoService.getById(groupBatch.getGroupId()),
                            groupRobotService.getRobot(groupBatch.getGroupId(), groupActionLog.getRobotId()), groupActionLog.getChangeValue(), "");
                } else {
                    //不满足重试条件  直接标记失败
                    groupBatchActionService.updateStatus(groupBatch.getBatchId(), 1);
                }
            }
        } catch (Exception e) {
            log.info("doNextInvitingBotJoinAction.error={}", groupActionLog, e);
            groupBatchActionService.updateStatus(groupBatch.getBatchId(), 1);
        }
    }


    public boolean invitingBotJoin(GroupInfo groupInfo, GroupRobot groupRobot) {
        if (groupRobot == null) {
            return false;
        }
        log.info("邀请bot入群检查={},{}", JSON.toJSONString(groupInfo), JSON.toJSONString(groupRobot));

        String key = "invitingBotJoin:" + groupInfo.getGroupId();
        RLock lock = redisLock.lock(key);
        if (!lock.isLocked()) {
            log.info("邀请bot入群检查 加锁失败={}", JSON.toJSONString(groupInfo));
            return false;
        }
        try {
            //当前是否有批次任务执行邀请bot进群检测
            GroupBatchAction batchAction = groupBatchActionService.getBatchAction(groupInfo.getGroupId(), 0);
            if (batchAction != null) {
                //上次邀请bot进群动作 已超过10分钟没结果  认为是失败
                if (Duration.between(batchAction.getLastActionTime(), LocalDateTime.now()).toMinutes() > 10) {
                    groupBatchActionService.updateStatus(batchAction.getBatchId(), 1);
                } else {
                    return false;
                }
            }
            InviteBotAction inviteBotAction = InviteBotAction.of(0);
            List<BotInfoVO> botList = ApiClient.getBot(10);
            if (CollUtil.isEmpty(botList)) {
                return false;
            }
            BotInfoVO bo = botList.get(RandomUtil.randomInt(0, botList.size()));
            groupMonitorInfoService.setBotInfo(groupInfo.getGroupId(), bo);
            String botUserName = bo.getBotUsername();

            //初始化批量任务动作
            GroupBatchAction action = new GroupBatchAction();
            action.setBatchId(IdWorker.getIdStr());
            action.setSetType(0);
            action.setActionProgress(inviteBotAction.getCode());
            action.setRetryCount(0);
            action.setGroupId(groupInfo.getGroupId());
            action.setLastActionTime(LocalDateTime.now());
            action.setCreateTime(LocalDateTime.now());
            action.setBatchStatus(0);
            groupBatchActionService.save(action);

            AsyncTask.execute(() -> runAction(action.getBatchId(), inviteBotAction.getAction(), groupInfo, groupRobot, "", botUserName));

        } catch (Exception e) {
            log.error("邀请bot进群异常={}", groupInfo, e);
            return false;
        } finally {
            lock.unlock();
        }
        return true;
    }


    public void runAction(String batchId, GroupAction action, GroupInfo groupInfo, GroupRobot groupRobot, String lastValue, String value) {
        switch (action) {
            case SEARCH_BOT:
                setAction(action, groupInfo, groupRobot, batchId, value, actionId -> {
                            ThirdTgSearchKeywordInputDTO input = new ThirdTgSearchKeywordInputDTO();
                            input.setKeyword(value);
                            input.setTgRobotId(groupRobot.getRobotId());
                            input.setExtend(actionId);
                            return input;
                        },
                        OpenApiClient::searchKeywordByThirdKpTg);
                break;
            case ADD_BOT:
                setAction(action, groupInfo, groupRobot, batchId, value, actionId -> {
                            ThirdTgJoinUserInputDTO input = new ThirdTgJoinUserInputDTO();
                            input.setFriendSerialNo(value);
                            input.setTgRobotId(groupRobot.getRobotId());
                            input.setExtend(actionId);
                            return input;
                        },
                        OpenApiClient::joinUserByThirdKpTg);
                break;
            case INVITE_BOT_JOIN_GROUP:
                setAction(action, groupInfo, groupRobot, batchId, lastValue, actionId -> {
                            ThirdTgInviteJoinChatroomInputDTO input = new ThirdTgInviteJoinChatroomInputDTO();
                            input.setMemberSerialNo(lastValue);
                            input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                            input.setTgRobotId(groupRobot.getRobotId());
                            input.setExtend(actionId);
                            return input;
                        },
                        OpenApiClient::inviteJoinChatroomByThirdKpTg);
                break;
            case SET_GROUP_ADMIN:
                setAction(action, groupInfo, groupRobot, batchId, value, actionId -> {
                            ThirdTgSetChatroomAdminInputDTO input = new ThirdTgSetChatroomAdminInputDTO();
                            input.setTgRobotId(groupRobot.getRobotId());
                            input.setExtend(actionId);
                            input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                            input.setGroupId(groupInfo.getGroupId());
                            input.setMemberSerialNo(lastValue);
                            input.setIsAll(true);
                            input.setChangeInfo(true);
                            input.setDeleteMessages(true);
                            input.setBanUsers(true);
                            input.setInviteUsers(true);
                            input.setPinMessages(true);
                            input.setManageCall(true);
                            input.setAnonymous(true);
                            input.setAddAdmins(true);
                            input.setMemberUserAccessHash(value);
                            return input;
                        },
                        OpenApiClient::setChatroomAdminByThirdKpTg);
                break;
            case QUERY_HASH:
                setAction(action, groupInfo, groupRobot, batchId, lastValue, actionId -> {
                            ThirdTgSqlTaskSubmitInputDTO input = new ThirdTgSqlTaskSubmitInputDTO();
                            input.setDbSource("kfpt-doris-ed");
                            input.setSql(TgGroupHashSettings.getSql(groupInfo.getGroupSerialNo(), groupRobot.getRobotId(),
                                    ListTools.newArrayList(lastValue)));
                            return input;
                        },
                        OpenApiClient::sqlTaskSubmitByThirdKpTg, true);
                break;
            case QUERY_GROUP_MEMBER:
                setAction(action, groupInfo, groupRobot, batchId, lastValue, actionId -> {
                            ThirdTgGetGroupMemberInputDTO input = new ThirdTgGetGroupMemberInputDTO();
                            input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                            input.setTgRobotId(groupRobot.getRobotId());
                            input.setExtend(actionId);
                            return input;
                        },
                        OpenApiClient::getGroupMemberByThirdKpTg);
                break;
        }
    }

    public List<GroupResourceVO> analysisExcelInfo(MultipartFile file) {
        List<GroupResourceVO> excel = new ArrayList<>();
        try {
            ExcelUtil<GroupResourceVO> util = new ExcelUtil<>(GroupResourceVO.class);
            excel = util.importExcel(file.getInputStream()).stream().filter(p -> StrUtil.isNotBlank(p.getGroupSerialNo()) && StrUtil.isNotBlank(p.getRobotId())).collect(Collectors.toList());
        } catch (Exception e) {
            log.info("analysisExcelInfo.error={}", file.getOriginalFilename(), e);
        }
        Assert.notEmpty(excel, "文件解析失败");
        return excel;
    }


    public void updateInfo(GroupUpdateInfoDTO dto) {
        Assert.notEmpty(dto.getGroupIds(), "群不能为空");
        List<GroupInfo> groupInfos = groupInfoService.listByIds(dto.getGroupIds());
        Assert.notEmpty(groupInfos, "群不存在");
        Assert.isTrue(CollUtil.isNotEmpty(dto.getImageUrls()) && StrUtil.isNotEmpty(dto.getGroupName()), "头像或者群名不能都为空");
        int imageIndex = 0;
        int nameIndex = 0;
        List<String> groupNames = new ArrayList<>();
        if (StrUtil.isNotEmpty(dto.getGroupName())) {
            if ((dto.getNameStart() == null || dto.getNameEnd() == null)) {
                groupNames = Collections.singletonList(dto.getGroupName());
            } else {
                Assert.isTrue(dto.getNameStart() <= dto.getNameEnd(), "开始编号不能大于结束编号");
                groupNames = Stream.iterate(dto.getNameStart(), d -> d + 1)
                        .limit(dto.getNameEnd() - dto.getNameStart() + 1)
                        .map(p -> dto.getGroupName() + p).collect(Collectors.toList());
            }
        }


        for (GroupInfo groupInfo : groupInfos) {
            GroupRobot robot = groupRobotService.getAdminRobot(groupInfo.getGroupId());
            if (CollUtil.isNotEmpty(groupNames)) {
                if (nameIndex >= groupNames.size()) {
                    nameIndex = 0;
                }
                String name = groupNames.get(nameIndex++);
                setAction(GroupAction.SET_GROUP_NAME, groupInfo, robot, "", name, actionId -> {
                            ThirdTgModifyChatroomNameInputDTO input = new ThirdTgModifyChatroomNameInputDTO();
                            input.setTgRobotId(robot.getRobotId());
                            input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                            input.setChatroomNameBase64(Base64.encode(name));
                            input.setExtend(actionId);
                            return input;
                        },
                        OpenApiClient::modifyChatroomNameByThirdKpTg);
            }

            if (CollUtil.isNotEmpty(dto.getImageUrls())) {
                if (imageIndex >= dto.getImageUrls().size()) {
                    imageIndex = 0;
                }
                String url = dto.getImageUrls().get(imageIndex++);
                setAction(GroupAction.SET_GROUP_IMAGE, groupInfo, robot, "", url, actionId -> {
                            ThirdTgModifyChatroomHeadImageInputDTO input = new ThirdTgModifyChatroomHeadImageInputDTO();
                            input.setTgRobotId(robot.getRobotId());
                            input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                            input.setUrl(url);
                            input.setExtend(actionId);
                            return input;
                        },
                        OpenApiClient::modifyChatroomHeadImageByThirdKpTg);
            }
        }

    }

    public void setType(GroupTypeSetDTO dto) {
        Assert.notEmpty(dto.getGroupIds(), "群不能为空");
        Assert.notNull(dto.getGroupType(), "群类型不能为空");
        List<GroupInfo> groupInfos = groupInfoService.listByIds(dto.getGroupIds());
        Assert.notEmpty(groupInfos, "群不存在");
        groupInfos = groupInfos.stream().filter(p -> ObjectUtil.notEqual(p.getGroupType(), dto.getGroupType())).collect(Collectors.toList());
        Assert.notEmpty(groupInfos, "群类型无法更改成相同类型");
        List<String> groupLinks = new ArrayList<>();
        int index = 0;

        if (ObjectUtil.equal(dto.getGroupType(), 20)) {
            Assert.notEmpty(dto.getGroupLink(), "公开群的群链接不能为空");
            if ((dto.getStart() == null || dto.getEnd() == null)) {
                groupLinks = Collections.singletonList(dto.getGroupLink());
            } else {
                Assert.isTrue(dto.getStart() <= dto.getEnd(), "开始编号不能大于结束编号");
                groupLinks = Stream.iterate(dto.getStart(), d -> d + 1)
                        .limit(dto.getEnd() - dto.getStart() + 1)
                        .map(p -> dto.getGroupLink() + p).collect(Collectors.toList());
            }

        }

        for (GroupInfo groupInfo : groupInfos) {
            if (index > 0 && index >= groupLinks.size()) {
                index = 0;
            }
            String url = ObjectUtil.equal(dto.getGroupType(), 20) ? groupLinks.get(index++) : null;
            GroupRobot robot = groupRobotService.getAdminRobot(groupInfo.getGroupId());
            setAction(GroupAction.SET_GROUP_TYPE, groupInfo, robot, "", dto.getGroupType() + "", actionId -> {
                        ThirdTgSetChatroomTypeInputDTO input = new ThirdTgSetChatroomTypeInputDTO();
                        input.setTgRobotId(robot.getRobotId());
                        input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                        input.setGroupId(groupInfo.getGroupId());
                        input.setUsername(url);
                        input.setChatroomType(dto.getGroupType());
                        input.setExtend(actionId);
                        return input;
                    },
                    OpenApiClient::setChatroomTypeByThirdKpTg);
        }
    }

    public <T> void setAction(GroupAction action,
                              GroupInfo groupInfo,
                              GroupRobot robot,
                              String batchId,
                              String value,
                              Function<String, T> buildInput,
                              Function<T, OpenApiResult<TgBaseOutputDTO>> requestFuc) {
        setAction(action, groupInfo, robot, batchId, value, buildInput, requestFuc, false);
    }

    public <T> void setAction(GroupAction action,
                              GroupInfo groupInfo,
                              GroupRobot robot,
                              String batchId,
                              String value,
                              Function<String, T> buildInput,
                              Function<T, OpenApiResult<TgBaseOutputDTO>> requestFuc, boolean saveOpt) {
        boolean success = true;
        String msg = "";
        String optNo = "";

        GroupActionLog actionLog = new GroupActionLog();
        actionLog.setId(IdWorker.getIdStr());
        actionLog.setSetType(action.getCode());
        actionLog.setGroupId(groupInfo.getGroupId());
        actionLog.setBatchId(batchId);
        actionLog.setChangeValue(value);
        actionLog.setSetStatus(0);
        actionLog.setCreateTime(LocalDateTime.now());
        if (robot == null) {
            success = false;
            msg = "群内无机器人";
            actionLog.setPara(null);
            groupActionLogService.save(actionLog);
        } else {
            T input = buildInput.apply(actionLog.getId());
            actionLog.setRobotId(robot.getRobotId());
            actionLog.setPara(JSON.toJSONString(input));
            boolean isLimit = checkLimit(action,robot.getRobotId(), actionLog.getId());
            //触发限制  状态改成等待请求
            if(isLimit){
                actionLog.setSetStatus(3);
                groupActionLogService.save(actionLog);
                return;
            }
            groupActionLogService.save(actionLog);
            try {
                OpenApiResult<TgBaseOutputDTO> apply = requestFuc.apply(input);
                log.info("groupAction={},{},{}", action.getName(), JSON.toJSONString(input), JSON.toJSONString(apply));
                optNo = apply.getData().getOptSerNo();
                if (!apply.isSuccess()) {
                    success = false;
                    msg = apply.getMessage();
                } else if (saveOpt) {
                    redisCache.setCacheObject("ruoyi-admin:action:" + optNo
                            , actionLog.getId(), 30 * 60 * 60, TimeUnit.SECONDS);
                    log.info("setActionId={},{}", optNo, actionLog.getId());
                }
            } catch (GlobalException e) {
                log.info("groupAction.error={},{},{}", action.getName(), JSON.toJSONString(input), e.getMessage());
                success = false;
                msg = e.getMessage();
            } catch (Exception e) {
                log.info("groupAction.error={},{}", action.getName(), JSON.toJSONString(input), e);
                success = false;
                msg = "未知异常";
            }
        }
        //如果失败 获取无回调 直接返回结果
        if (!success || !action.isCallBack()) {
            handleActionResult(actionLog, optNo, success, msg, null);
        }
    }

    public boolean checkLimit(GroupAction action, String robotId,String id) {
            if(action == null || action.getLimitingDimensions() == null || action.getLimitingDimensions() == LimitingDimensions.NONE){
                return false;
            }
            String key = "actionLimit:" + action.getCode() ;
            if(action.getLimitingDimensions() == LimitingDimensions.ROBOT){
                key = key +":" + robotId;
            }
            LocalDateTime now = LocalDateTime.now();
            long currentSource = now.toEpochSecond(ZoneOffset.of("+8"));
            long expiredSource = now.plusSeconds(-action.getLimitTime()).toEpochSecond(ZoneOffset.of("+8"));
            BoundZSetOperations<String, String> zSetOperations = redisCache.redisTemplate.boundZSetOps(key);

            Long l = zSetOperations.removeRangeByScore(0, expiredSource);
            Long count = zSetOperations.count(0, currentSource);

            if (count != null && count >= action.getLimitFrequency()) {
            return true;
        }
        zSetOperations.expire(action.getLimitTime() + 30, TimeUnit.SECONDS);
        zSetOperations.addIfAbsent(id, LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        return false;
    }

    public void continueRunAction(){
        String key = "continueRunAction";
        RLock lock = redisLock.lock(key);
        if (!lock.isLocked()) {
            return ;
        }
        try {
            List<GroupActionLog> waitRunAction = groupActionLogService.getWaitRunAction();
            if(CollUtil.isEmpty(waitRunAction)){
               return;
            }
            for (GroupActionLog groupActionLog : waitRunAction) {
                boolean success = false;
                String msg = "未配置继续执行操作";
                String optNo = "";
                try {
                    GroupAction groupAction = GroupAction.of(groupActionLog.getSetType());
                    if(!checkLimit(groupAction,groupActionLog.getRobotId(),groupActionLog.getId())){
                        continue;
                    }
                    OpenApiResult<TgBaseOutputDTO> apiResult  = null;
                    switch (groupAction){

                        case INVITE_BOT_JOIN_GROUP:
                            OpenApiClient.inviteJoinChatroomByThirdKpTg(BeanUtil.copyProperties(groupActionLog.getPara(),ThirdTgInviteJoinChatroomInputDTO.class));
                            break;
                        case QUERY_HASH:
                            OpenApiClient.sqlTaskSubmitByThirdKpTg(BeanUtil.copyProperties(groupActionLog.getPara(),ThirdTgSqlTaskSubmitInputDTO.class));
                            break;

                    }
                    Assert.notNull(apiResult,"未配置继续执行操作");
                    log.info("groupAction={},{},{}", groupAction.getName(), groupActionLog.getPara(), JSON.toJSONString(apiResult));
                    optNo = apiResult.getData().getOptSerNo();
                    if (!apiResult.isSuccess()) {
                        success = false;
                        msg = apiResult.getMessage();
                    } else if (groupAction.getNeedCacheOpt()) {
                        redisCache.setCacheObject("ruoyi-admin:action:" + optNo
                                , groupActionLog.getId(), 30 * 60 * 60, TimeUnit.SECONDS);
                    }

                } catch (GlobalException e) {
                    log.info("groupAction.error={},{}", groupActionLog, e.getMessage());
                    success = false;
                    msg = e.getMessage();
                } catch (Exception e) {
                    log.info("groupAction.error={}", groupActionLog, e);
                    success = false;
                    msg = "未知异常";
                }
                if(success){
                    groupActionLogService.updateRun(groupActionLog.getId());
                }else{
                    String finalMsg = msg;
                    boolean finalResult = success;
                    String finalOptNo = optNo;
                    AsyncTask.execute(()-> handleActionResult(groupActionLog, finalOptNo, finalResult, finalMsg, null));
                }
            }

        }catch (Exception e){
            log.error("continueRunAction.error");
        }finally {
            lock.unlock();
        }
    }


    public void setAdmin(GroupAdminSetDTO dto) {
        Assert.notEmpty(dto.getMemberIds(), "设置的成员id不能为空");
        Assert.notBlank(dto.getGroupId(), "群id不能为空");
        GroupInfo groupInfo = groupInfoService.getById(dto.getGroupId());
        Assert.notNull(groupInfo, "群不存在");

        log.info("设置bot管理员={}", JSON.toJSONString(groupInfo));

        String key = "setAdmin:" + groupInfo.getGroupId();
        RLock lock = redisLock.lock(key);
        Assert.isTrue(lock.isLocked(), "群正在设置管理员中,请勿频繁操作！");
        try {
            //当前是否有批次任务执行邀请bot进群检测
            GroupBatchAction batchAction = groupBatchActionService.getBatchAction(groupInfo.getGroupId(), 1);
            if (batchAction != null) {
                if (Duration.between(batchAction.getLastActionTime(), LocalDateTime.now()).toMinutes() > 10) {
                    groupBatchActionService.updateStatus(batchAction.getBatchId(), 1);
                } else {
                    throw new IllegalArgumentException("群正在设置管理员中,请勿频繁操作！");
                }
            }
            SetAdminAction setAdminAction = SetAdminAction.of(0);

            //初始化批量任务动作
            GroupBatchAction action = new GroupBatchAction();
            action.setBatchId(IdWorker.getIdStr());
            action.setSetType(1);
            action.setActionProgress(setAdminAction.getCode());
            action.setRetryCount(0);
            action.setGroupId(groupInfo.getGroupId());
            action.setLastActionTime(LocalDateTime.now());
            action.setCreateTime(LocalDateTime.now());
            action.setBatchStatus(0);
            groupBatchActionService.save(action);


            GroupRobot robot = groupRobotService.getAdminRobot(dto.getGroupId());

            ThirdTgSetChatroomAdminInputDTO para = new ThirdTgSetChatroomAdminInputDTO();
            para.setTgRobotId(robot.getRobotId());
            para.setChatroomSerialNo(groupInfo.getGroupSerialNo());
            para.setGroupId(groupInfo.getGroupId());
            para.setMemberSerialNo(String.join(",", dto.getMemberIds()));
            para.setIsAll(dto.getIsAll());
            para.setChangeInfo(dto.getChangeInfo());
            para.setDeleteMessages(dto.getDeleteMessages());
            para.setBanUsers(dto.getBanUsers());
            para.setInviteUsers(dto.getInviteUsers());
            para.setPinMessages(dto.getPinMessages());
            para.setManageCall(dto.getManageCall());
            para.setAnonymous(dto.getAnonymous());
            para.setAddAdmins(dto.getAddAdmins());
            String value = JSON.toJSONString(para);
            setAction(setAdminAction.getAction(), groupInfo, robot, action.getBatchId(), value, actionId -> {
                        ThirdTgGetGroupMemberInputDTO input = new ThirdTgGetGroupMemberInputDTO();
                        input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                        input.setTgRobotId(robot.getRobotId());
                        input.setExtend(actionId);
                        return input;
                    },
                    OpenApiClient::getGroupMemberByThirdKpTg);
        } finally {
            lock.unlock();
        }
    }


    public void doNextSetAdmin(GroupBatchAction groupBatch, GroupActionLog groupActionLog, boolean success, Object data) {

        SetAdminAction botAction = SetAdminAction.of(groupBatch.getActionProgress());
        if (botAction == null) {
            groupBatchActionService.updateStatus(groupBatch.getBatchId(), success ? 2 : 1);
            return;
        }
        try {
            if (success) {
                GroupInfo groupInfo = groupInfoService.getById(groupBatch.getGroupId());
                GroupRobot robot = groupRobotService.getRobot(groupBatch.getGroupId(), groupActionLog.getRobotId());

                SetAdminAction nextAction = botAction.getNextAction();
                if (nextAction == SetAdminAction.SET_BOT_ADMIN) {
                    //批次动作完成
                    groupBatchActionService.updateStatus(groupBatch.getBatchId(), 2);
                    ThirdTgSetChatroomAdminInputDTO intputDTO = JSON.parseObject(groupActionLog.getChangeValue(), ThirdTgSetChatroomAdminInputDTO.class);
                    List<Called1100860002DTO> infos = (List<Called1100860002DTO>) data;

                    if (intputDTO != null && CollUtil.isNotEmpty(infos)) {
                        for (String memberId : intputDTO.getMemberSerialNo().split(",")) {
                            Optional<Called1100860002DTO> first = infos.stream().filter(p -> ObjectUtil.equal(p.getMemberCode(), memberId)).findFirst();
                            if (!first.isPresent()) {
                                continue;
                            }
                            setAction(GroupAction.SET_GROUP_ADMIN, groupInfo, robot, "", memberId, actionId -> {
                                        ThirdTgSetChatroomAdminInputDTO input = BeanUtil.copyProperties(intputDTO, ThirdTgSetChatroomAdminInputDTO.class);
                                        input.setMemberSerialNo(memberId);
                                        input.setMemberUserAccessHash(first.get().getAccessHash());
                                        input.setExtend(actionId);
                                        return input;
                                    },
                                    OpenApiClient::setChatroomAdminByThirdKpTg);
                        }
                    }
                    return;
                } else {
                    ThirdTgSetChatroomAdminInputDTO intputDTO = JSON.parseObject(groupActionLog.getChangeValue(), ThirdTgSetChatroomAdminInputDTO.class);
                    groupBatchActionService.doNextAction(groupBatch.getBatchId(), nextAction.getCode(), 0);
                    setAction(nextAction.getAction(), groupInfo, robot, groupBatch.getBatchId(), groupActionLog.getBatchId(),
                            actionId -> {
                                ThirdTgSqlTaskSubmitInputDTO input = new ThirdTgSqlTaskSubmitInputDTO();
                                input.setDbSource("kfpt-doris-ed");
                                input.setSql(TgGroupHashSettings.getSql(groupInfo.getGroupSerialNo(), robot.getRobotId(),
                                        Arrays.stream(intputDTO.getMemberSerialNo().split(",")).collect(Collectors.toList())));
                                return input;
                            },
                            OpenApiClient::sqlTaskSubmitByThirdKpTg, true);
                }
            } else {
                groupBatchActionService.updateStatus(groupBatch.getBatchId(), 1);

            }
        } catch (Exception e) {
            log.info("doNextInvitingBotJoinAction.error={}", groupActionLog, e);
            groupBatchActionService.updateStatus(groupBatch.getBatchId(), 1);
        }
    }


    public void syncMember(String groupId) {
        GroupInfo groupInfo = groupInfoService.getById(groupId);
        Assert.notNull(groupInfo, "群不存在");
        ThirdTgSyncGroupMemberDTO input = new ThirdTgSyncGroupMemberDTO();
        input.setChatroomSerialNos(Collections.singletonList(groupInfo.getGroupSerialNo()));
        OpenApiResult<Void> voidOpenApiResult = OpenApiClient.syncGroupMemberByThirdUtchatTg(input);
        log.info("syncGroupMemberByThirdUtchatTg={},{}", JSON.toJSONString(input), JSON.toJSONString(voidOpenApiResult));
    }

    public Page<GroupMemberInfoVO> queryMember(GroupMemberQueryDTO dto) {
        Assert.notBlank(dto.getGroupId(), "群id不能为空");
        GroupInfo groupInfo = groupInfoService.getById(dto.getGroupId());
        Assert.notNull(groupInfo, "群不存在");
        ThirdTgSelectGroupMemberDTO input = new ThirdTgSelectGroupMemberDTO();
        input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
        if (StrUtil.isNotBlank(dto.getMemberId())) {
            input.setMemberSerialNos(Collections.singletonList(dto.getMemberId()));
        }
        input.setStartTime(dto.getWasOnlineStart());
        input.setEndTime(dto.getWasOnlineEnd());
        input.setMemberType(dto.getMemberType());
        input.setName(dto.getFullName());
        input.setUserName(dto.getUserName());
        input.setPage(dto.getPage());
        input.setLimit(dto.getLimit());

        OpenApiResult<Page<ExtTgSelectGroupMemberVO>> result = OpenApiClient.selectGroupMemberListByThirdUtchatTg(input);
        Assert.isTrue(result.isSuccess(), "查询成员失败");
        return convertRecord(result.getData(), p -> {
            GroupMemberInfoVO vo = new GroupMemberInfoVO();
            vo.setFullName(p.getName());
            vo.setUserName(p.getUserName());
            vo.setMemberId(p.getMemberSerialNo());
            vo.setPhone(p.getPhone());
            vo.setWasOnline(p.getWasOnline());
            vo.setMemberType(p.getMemberType());
            return vo;
        });

    }

    public <T, R> Page<T> convertRecord(Page<R> page, Function<R, T> function) {
        Page<T> result = new Page<>();
        if (CollUtil.isNotEmpty(page.getRecords())) {
            result.setRecords(page.getRecords().stream().map(function).collect(Collectors.toList()));
        }
        result.setTotal(page.getTotal());
        result.setPages(page.getPages());
        result.setSize(page.getSize());
        return result;
    }

    public GroupInfo handleRobotIn(Called1100910039DTO dto, String robotId) {
        GroupInfo groupInfo = groupInfoService.getGroupBySerialNo(dto.getChatroomSerialNo());
        if (groupInfo == null) {
            groupInfo = groupInfoService.saveExternalGroup(dto.getChatroomSerialNo(), dto.getChatroomSerialNo());
        }
        groupRobotService.add(robotId, groupInfo.getGroupId());
        return groupInfo;
    }

    public GroupInfo handleRobotOut(Called1100910039DTO dto, String robotId) {
        GroupInfo groupInfo = groupInfoService.getGroupBySerialNo(dto.getChatroomSerialNo());
        if (groupInfo == null) {
            return null;
        }
        groupRobotService.del(groupInfo.getGroupId(), robotId);
        return groupInfo;
    }

    public void saveAndInviteBot(GroupInfo groupInfo) {
        //保存群组 到默认组
        groupClusterRefService.add(Collections.singletonList(groupInfo.getGroupId()), groupClusterService.getClusterDefault(""));
        //保存群状态
        groupStateService.addExternal(groupInfo.getGroupId());
        //新增bot监控表
        groupMonitorInfoService.add(Collections.singletonList(groupInfo.getGroupId()));
        //同步群信息
        //syncInfo(Collections.singletonList(groupInfo.getGroupId()), null);
        //执行bot检测
        invitingBotJoin(groupInfo, groupRobotService.getAnyRobot(groupInfo.getGroupId()));
    }

    /**
     * 设置bot广告规则
     *
     * @param groupId
     */
    public void setBotAdMonitor(String groupId) {
        GroupMonitorInfo groupMonitorInfo = groupMonitorInfoService.getById(groupId);
        if (groupMonitorInfo == null || StrUtil.isBlank(groupMonitorInfo.getBotPlayId())) {
            log.info("配置的剧本id为空={}", groupId);
            return;
        }
        Play play = playbackService.getById(groupMonitorInfo.getBotPlayId());
        if (play == null || StrUtil.isBlank(play.getAdMonitor())) {
            log.info("配置的剧本广告监控为空={}", groupId);
            return;
        }
        setBotAdMonitor(groupId, play.getId(), play.getAdMonitor());
    }

    /**
     * 设置bot广告规则
     *
     * @param groupId
     */
    public boolean setBotAdMonitor(String groupId, String playId, String adMonitor) {
        try {
            AdMonitor adMonitorInfo = JSON.parseObject(adMonitor, AdMonitor.class);
            GroupMonitorInfo groupInfo = groupMonitorInfoService.getById(groupId);
            AdMonitorDTO dto = new AdMonitorDTO();
            dto.setConfigId(playId);
            if (StrUtil.isNotBlank(adMonitorInfo.getDisposalType())) {
                dto.setDealFunction(Arrays.stream(adMonitorInfo.getDisposalType().split(",")).filter(
                                p -> Arrays.asList("1", "2").contains(p))
                        .map(p -> p.equals("1") ? "RESTRICT" : "KICK_OUT").collect(Collectors.toList()));
            }
            dto.setGroupIds(Collections.singletonList(groupInfo.getOriginalGroupId()));
            dto.setKeywords(adMonitorInfo.getKeywordRule());
            dto.setMonitorFrequency(adMonitorInfo.getSpammingNum());
            if (StrUtil.isNotBlank(adMonitorInfo.getTypes())) {
                dto.setMonitorTarget(Arrays.stream(adMonitorInfo.getTypes().split(",")).filter(
                                p -> Arrays.asList("1", "2", "3").contains(p))
                        .map(p -> p.equals("3") ? 4 : p.equals("2") ? 2 : 1).collect(Collectors.toList()));
            }
            dto.setMonitorTime(adMonitorInfo.getSpammingTime());
            dto.setRestrictMember(adMonitorInfo.getIsTabooMemberMsg());
            dto.setDeleteOtherStatement(adMonitorInfo.getIsDelMemberMsg());
            dto.setTimeUnit(ObjectUtil.equal(adMonitorInfo.getSpammingTimeUnit(), 2) ? "MINUTES" : "SECONDS");
            boolean success = ApiClient.setBotAdMonitor(dto).isSuccess();
            if (success) {
                groupMonitorInfoService.setBotAdMonitor(groupId);
            }
            return success;
        } catch (Exception e) {
            log.info("setBotAdMonitor.error={},{},{}", groupId, playId, adMonitor);
            return false;
        }

    }


    public void handlerNewAdmin(ThirdTgSetChatroomAdminInputDTO request, Called1100910053DTO dto) {
        try {
            if (request == null || StrUtil.isBlank(request.getGroupId())) {
                return;
            }
            GroupRobot groupRobot = groupRobotService.setAdmin(request.getGroupId(), request.getMemberSerialNo());
            if (ObjectUtil.equal(groupRobot.getBotType(), 1)) {
                groupMonitorInfoService.setBotAdmin(request.getGroupId());
            }
        } catch (Exception e) {
            log.info("handlerNewAdmin.error={},{}", request, dto, e);
        }

    }

    public void syncGroupInfo() {
        GroupPageQueryDTO dto = new GroupPageQueryDTO();
        int page = 1;
        int limit = 500;
        while (true) {
            try {
                dto.setPage(page++);
                dto.setLimit(limit);
                List<GroupInfo> records = groupInfoService.limitQuery(dto);
                syncInfo(records, null);
                if (records.size() < limit) {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
    }

}
