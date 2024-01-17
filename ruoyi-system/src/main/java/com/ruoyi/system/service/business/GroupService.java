package com.ruoyi.system.service.business;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.VibeRuleDTO;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.enums.GroupAction;
import com.ruoyi.common.enums.InviteBotAction;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.callback.dto.Called1100910017DTO;
import com.ruoyi.system.callback.dto.Called1100910039DTO;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.GroupMemberInfoVO;
import com.ruoyi.system.domain.vo.GroupResourceVO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    private final GroupStateService groupStateService;

    private final GroupMonitorInfoService groupMonitorInfoService;

    private final GroupRobotService groupRobotService;

    private final RedisLock redisLock;

    private final IVibeRuleService ibiRuleService;

    private final RobotStatisticsService robotStatisticsService;

    private final GroupActionLogService groupActionLogService;

    private final GroupBatchActionService groupBatchActionService;

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
                    }
                    groupInfoList.add(groupInfoVO);
                }
            }
            //满足条件的数据
            if (groupInfoList.size() >= dto.getGroupNum()) {
                groupStateService.markUsed(groupInfoList.stream().map(GroupInfoVO::getGroupId).collect(Collectors.toList()), 1);
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
        Assert.notEmpty(groupInfos, "群已存在,请勿重复添加");
        List<String> groupIds = groupInfos.stream().map(GroupInfo::getGroupId).collect(Collectors.toList());
        //默认保存群主信息
        Map<String, GroupRobot> robotMap = groupRobotService.addGroupLeader(groupInfos, getRobotMap(groupInfos, resourceList));
        //保存群组关联
        groupClusterRefService.add(groupIds, newClusterId);
        //保存群状态
        groupStateService.addImportGroup(groupIds);
        //新增bot监控表
        groupMonitorInfoService.add(groupIds);
        //同步群信息
        syncInfo(groupInfos, robotMap);
        //执行邀请bot进群
        invitingBotJoin(groupInfos, robotMap::get, true);
    }


    public Map<String, String> getRobotMap(List<GroupInfo> groupInfos, List<GroupResourceVO> resourceList) {
        Map<String, String> robotMap = resourceList.stream().collect(Collectors.toMap(GroupResourceVO::getGroupSerialNo, GroupResourceVO::getRobotId, (a, b) -> b));
        return groupInfos.stream().collect(Collectors.toMap(GroupInfo::getGroupId, p -> robotMap.get(p.getGroupSerialNo()), (a, b) -> b));

    }


    public void syncInfo(List<GroupInfo> groupInfos, Map<String, GroupRobot> robotMap) {
        for (GroupInfo groupInfo : groupInfos) {
            try {
                GroupRobot robot = robotMap == null ? groupRobotService.getRobot(groupInfo.getGroupId()) : robotMap.get(groupInfo.getGroupId());
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
        return invitingBotJoin(groupInfoService.listByIds(groupIds), groupRobotService::getRobot, false);
    }

    public int invitingBotJoin(GroupInfo groupInfo) {
        return invitingBotJoin(Collections.singletonList(groupInfo), groupRobotService::getRobot, false);
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
        if (StrUtil.isBlank(id)) {
            return;
        }
        handleActionResult(groupActionLogService.getById(optNo), optNo, success, msg, data);
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
                InviteBotAction nextAction = botAction.getNextAction();
                if (nextAction == null) {
                    //所有动作完成 标记bot已进群检查
                    groupMonitorInfoService.robotCheck(groupBatch.getGroupId());
                    //批次动作完成
                    groupBatchActionService.updateStatus(groupBatch.getBatchId(), 2);
                    return;
                }
                String value;
                //当前动作是搜索bot  对比username 获取是bot的数据
                if (botAction == InviteBotAction.SEARCH_BOT) {
                    List<Called1100910017DTO.Called1100910017UserDTO> userList = (List<Called1100910017DTO.Called1100910017UserDTO>) data;
                    Assert.notEmpty(userList, "搜索bot失败");
                    value = userList.stream().filter(p -> ObjectUtil.equal(p.getUserName(), groupActionLog.getChangeValue()))
                            .findFirst().map(Called1100910017DTO.Called1100910017UserDTO::getUserSerialNo).orElse("");
                    Assert.notBlank(value, "搜索bot失败");
                    //更新bot的用户编号
                    groupMonitorInfoService.updateRobotSerialNo(groupBatch.getGroupId(), value);
                } else {
                    //其他的动作 都传bot的用户编号
                    value = groupActionLog.getChangeValue();
                }
                //执行动作
                runAction(groupBatch.getBatchId(), nextAction.getAction(), groupInfoService.getById(groupBatch.getGroupId()),
                        groupRobotService.getRobot(groupBatch.getGroupId()), value);
            } else {
                //如果需要重试 则当前动作重复操作
                if (groupBatch.getRetryCount() < botAction.getRetryCount()) {
                    groupBatchActionService.doNextAction(groupBatch.getBatchId(), groupBatch.getRetryCount() + 1);
                    runAction(groupBatch.getBatchId(), botAction.getAction(), groupInfoService.getById(groupBatch.getGroupId()),
                            groupRobotService.getRobot(groupBatch.getGroupId()), groupActionLog.getChangeValue());
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

        String key = "invitingBotJoin:" + groupInfo.getGroupId();
        RLock lock = redisLock.lock(key);
        if (!lock.isLocked()) {
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
            String botUserName = "";
            //todo  获取bot
            //初始化批量任务动作
            GroupBatchAction action = new GroupBatchAction();
            action.setBatchId(IdWorker.getIdStr());
            action.setSetType(inviteBotAction.getCode());
            action.setActionProgress(0);
            action.setRetryCount(0);
            action.setGroupId(groupInfo.getGroupId());
            action.setLastActionTime(LocalDateTime.now());
            action.setCreateTime(LocalDateTime.now());
            action.setBatchStatus(0);
            groupBatchActionService.save(action);

            runAction(action.getBatchId(), inviteBotAction.getAction(), groupInfo, groupRobot, botUserName);

        } finally {
            lock.unlock();
        }
        return true;
    }


    public void runAction(String batchId, GroupAction action, GroupInfo groupInfo, GroupRobot groupRobot, String value) {
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
                setAction(action, groupInfo, groupRobot, batchId, value, actionId -> {
                            ThirdTgInviteJoinChatroomInputDTO input = new ThirdTgInviteJoinChatroomInputDTO();
                            input.setMemberSerialNo(value);
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
                            input.setMemberSerialNo(value);
                            input.setIsAll(true);
                            input.setChangeInfo(true);
                            input.setDeleteMessages(true);
                            input.setBanUsers(true);
                            input.setInviteUsers(true);
                            input.setPinMessages(true);
                            input.setManageCall(true);
                            input.setAnonymous(true);
                            input.setAddAdmins(true);
                            return input;
                        },
                        OpenApiClient::setChatroomAdminByThirdKpTg);
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
            GroupRobot robot = groupRobotService.getRobot(groupInfo.getGroupId());
            if (CollUtil.isEmpty(groupNames)) {
                if (nameIndex >= groupNames.size()) {
                    nameIndex = 0;
                }
                String name = dto.getImageUrls().get(nameIndex++);
                setAction(GroupAction.SET_GROUP_NAME, groupInfo, robot, "", name, actionId -> {
                            ThirdTgModifyChatroomNameInputDTO input = new ThirdTgModifyChatroomNameInputDTO();
                            input.setTgRobotId(robot.getRobotId());
                            input.setChatroomSerialNo("");
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
        groupInfos = groupInfos.stream().filter(p -> ObjectUtil.equal(p.getGroupType(), dto.getGroupType())).collect(Collectors.toList());
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
            GroupRobot robot = groupRobotService.getRobot(groupInfo.getGroupId());
            setAction(GroupAction.SET_GROUP_TYPE, groupInfo, robot, "", dto.getGroupType() + "", actionId -> {
                        ThirdTgSetChatroomTypeInputDTO input = new ThirdTgSetChatroomTypeInputDTO();
                        input.setTgRobotId(robot.getRobotId());
                        input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
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
            actionLog.setPara(JSON.toJSONString(input));
            groupActionLogService.save(actionLog);
            try {
                OpenApiResult<TgBaseOutputDTO> apply = requestFuc.apply(input);
                log.info("groupAction={},{},{}", action.getName(), JSON.toJSONString(input), JSON.toJSONString(apply));
                optNo = apply.getData().getOptSerNo();
                if (!apply.isSuccess()) {
                    success = false;
                    msg = apply.getMessage();
                }
            } catch (Exception e) {
                log.info("groupAction.error={},{}", action.getName(), JSON.toJSONString(input), e);
            }
        }
        //如果失败 获取无回调 直接返回结果
        if (!success || !action.isCallBack()) {
            handleActionResult(actionLog, optNo, success, msg, null);
        }
    }

    public void setAdmin(GroupAdminSetDTO dto) {
        Assert.notEmpty(dto.getMemberIds(), "设置的成员id不能为空");
        Assert.notBlank(dto.getGroupId(), "群id不能为空");
        GroupInfo groupInfo = groupInfoService.getById(dto.getGroupId());
        Assert.notNull(groupInfo, "群不存在");
        for (String memberId : dto.getMemberIds()) {
            GroupRobot robot = groupRobotService.getRobot(dto.getGroupId());
            setAction(GroupAction.SET_GROUP_ADMIN, groupInfo, robot, "", memberId, actionId -> {
                        ThirdTgSetChatroomAdminInputDTO input = new ThirdTgSetChatroomAdminInputDTO();
                        input.setTgRobotId(robot.getRobotId());
                        input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                        input.setMemberSerialNo(memberId);
                        input.setIsAll(dto.getIsAll());
                        input.setChangeInfo(dto.getChangeInfo());
                        input.setDeleteMessages(dto.getDeleteMessages());
                        input.setBanUsers(dto.getBanUsers());
                        input.setInviteUsers(dto.getInviteUsers());
                        input.setPinMessages(dto.getPinMessages());
                        input.setManageCall(dto.getManageCall());
                        input.setAnonymous(dto.getAnonymous());
                        input.setAddAdmins(dto.getAddAdmins());
                        input.setExtend(actionId);
                        return input;
                    },
                    OpenApiClient::setChatroomAdminByThirdKpTg);
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

    public GroupInfo handleRobotIn(Called1100910039DTO dto) {
        GroupInfo groupInfo = groupInfoService.getGroupBySerialNo(dto.getChatroomSerialNo());
        if (groupInfo == null) {
            groupInfo = groupInfoService.saveExternalGroup(dto.getChatroomSerialNo(), dto.getChatroomSerialNo());
        }
        //todo 添加群内机器人逻辑
        return groupInfo;
    }

    public void saveAndInviteBot(GroupInfo groupInfo) {
        //todo 添加群内机器人逻辑
        //todo 执行bot检测
    }
}
