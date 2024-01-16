package com.ruoyi.system.service.business;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.VibeRuleDTO;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.GroupActionLog;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupMonitorInfo;
import com.ruoyi.system.domain.GroupRobot;
import com.ruoyi.system.domain.dto.GroupAdminSetDTO;
import com.ruoyi.system.domain.dto.GroupQueryDTO;
import com.ruoyi.system.domain.dto.GroupUpdateInfoDTO;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.GroupResourceVO;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyChatroomHeadImageInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyChatroomNameInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgSetChatroomAdminInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgSetChatroomTypeInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
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
        syncInfo(groupInfos);
        //执行邀请bot进群
        invitingBotJoin(groupInfos, robotMap::get, true);
    }


    public Map<String, String> getRobotMap(List<GroupInfo> groupInfos, List<GroupResourceVO> resourceList) {
        Map<String, String> robotMap = resourceList.stream().collect(Collectors.toMap(GroupResourceVO::getGroupSerialNo, GroupResourceVO::getRobotId, (a, b) -> b));
        return groupInfos.stream().collect(Collectors.toMap(GroupInfo::getGroupId, p -> robotMap.get(p.getGroupSerialNo()), (a, b) -> b));

    }


    public void syncInfo(List<GroupInfo> groupInfos) {
        //todo openApi接口 获取ut群信息
    }

    public int invitingBotJoin(List<String> groupIds) {
        return invitingBotJoin(groupInfoService.listByIds(groupIds), groupRobotService::getRobot, false);
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

    public boolean invitingBotJoin(GroupInfo groupInfo, GroupRobot groupRobot) {
        //todo 邀请bot进群逻辑


        return false;
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
        Assert.isTrue(CollUtil.isEmpty(dto.getImageUrls()) && StrUtil.isEmpty(dto.getGroupName()), "头像或者群名不能都为空");
        int imageIndex = 0;
        int nameIndex = 0;
        List<String> groupNames = new ArrayList<>();
        if (StrUtil.isNotEmpty(dto.getGroupName())) {
            if ((dto.getNameStart() == null || dto.getNameEnd() == null)) {
                groupNames = Collections.singletonList(dto.getGroupName());
            } else {
                Assert.isTrue(dto.getNameStart() > dto.getNameEnd(), "开始编号不能大于结束编号");
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
                setAction(1, groupInfo, robot, "", name, () -> {
                            ThirdTgModifyChatroomNameInputDTO input = new ThirdTgModifyChatroomNameInputDTO();
                            input.setTgRobotId(robot.getRobotId());
                            input.setChatroomSerialNo("");
                            input.setChatroomNameBase64(Base64.encode(name));
                            return input;
                        },
                        OpenApiClient::modifyChatroomNameByThirdKpTg);
            }

            if (CollUtil.isNotEmpty(dto.getImageUrls())) {
                if (imageIndex >= dto.getImageUrls().size()) {
                    imageIndex = 0;
                }
                String url = dto.getImageUrls().get(imageIndex++);
                setAction(0, groupInfo, robot, "", url, () -> {
                            ThirdTgModifyChatroomHeadImageInputDTO input = new ThirdTgModifyChatroomHeadImageInputDTO();
                            input.setTgRobotId(robot.getRobotId());
                            input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                            input.setUrl(url);
                            return input;
                        },
                        OpenApiClient::modifyChatroomHeadImageByThirdKpTg);
            }
        }

    }

    public void setType(List<String> groupIds, Integer groupType) {
        Assert.notEmpty(groupIds, "群不能为空");
        Assert.notNull(groupType, "群类型不能为空");
        List<GroupInfo> groupInfos = groupInfoService.listByIds(groupIds);
        Assert.notEmpty(groupInfos, "群不存在");
        groupInfos = groupInfos.stream().filter(p -> ObjectUtil.equal(p.getGroupType(), groupType)).collect(Collectors.toList());
        Assert.notEmpty(groupInfos, "群类型无法更改");
        for (GroupInfo groupInfo : groupInfos) {
            GroupRobot robot = groupRobotService.getRobot(groupInfo.getGroupId());
            setAction(2, groupInfo, robot, "", groupType + "", () -> {
                        ThirdTgSetChatroomTypeInputDTO input = new ThirdTgSetChatroomTypeInputDTO();
                        input.setTgRobotId(robot.getRobotId());
                        input.setChatroomSerialNo(groupInfo.getGroupSerialNo());
                        input.setUsername(RandomUtil.randomString(32));
                        input.setChatroomType(groupType);
                        return input;
                    },
                    OpenApiClient::setChatroomTypeByThirdKpTg);
        }
    }


    public <T> void setAction(Integer type,
                              GroupInfo groupInfo,
                              GroupRobot robot,
                              String batchId,
                              String value,
                              Supplier<T> buildInput,
                              Function<T, OpenApiResult<TgBaseOutputDTO>> requestFuc) {


        GroupActionLog log = new GroupActionLog();
        log.setId(IdWorker.getIdStr());
        log.setSetType(type);
        log.setGroupId(groupInfo.getGroupId());
        log.setBatchId(batchId);
        log.setChangeValue(value);
        if (robot == null) {
            log.setPara(null);
            log.setSetStatus(1);
            log.setFailMsg("群内无机器人");
            log.setId(IdWorker.getIdStr());
        } else {
            T input = buildInput.get();
            log.setPara(JSON.toJSONString(input));
            OpenApiResult<TgBaseOutputDTO> apply = requestFuc.apply(input);
            if (apply.isSuccess()) {
                log.setSetStatus(0);
                log.setId(IdWorker.getIdStr());
            } else {
                log.setSetStatus(1);
                log.setFailMsg(apply.getMessage());
                log.setId(apply.getData().getOptSerNo());
            }
        }
        log.setCreateTime(LocalDateTime.now());
        groupActionLogService.save(log);
    }

    public void setAdmin(GroupAdminSetDTO dto) {
        Assert.notEmpty(dto.getMemberIds(), "设置的成员id不能为空");
        Assert.notBlank(dto.getGroupId(), "群id不能为空");
        GroupInfo groupInfo = groupInfoService.getById(dto.getGroupId());
        Assert.notNull(groupInfo, "群不存在");
        for (String memberId : dto.getMemberIds()) {
            GroupRobot robot = groupRobotService.getRobot(dto.getGroupId());
            setAction(2, groupInfo, robot, "", "memberId", () -> {
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
                        return input;
                    },
                    OpenApiClient::setChatroomAdminByThirdKpTg);
        }
    }
}
