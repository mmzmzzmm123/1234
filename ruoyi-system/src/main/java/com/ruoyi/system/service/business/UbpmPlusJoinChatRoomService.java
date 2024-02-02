package com.ruoyi.system.service.business;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.ruoyi.common.core.domain.entity.order.Order;
import com.ruoyi.common.enums.Platform;
import com.ruoyi.common.enums.TaskType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.components.MultipartFileUtil;
import com.ruoyi.system.components.PageConvertUtil;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.domain.vo.AnalysisUploadPhoneResultVO;
import com.ruoyi.system.domain.vo.UbpmCountryVO;
import com.ruoyi.system.extend.UtTouchJoinRoomClient;
import com.ruoyi.system.extend.UtTouchProperties;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.*;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.service.CountryService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.MerchantInfoService;
import com.ruoyi.system.service.TaskService;
import com.ruoyi.system.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jing.Zhang
 */
@Slf4j
@Service
@AllArgsConstructor
public class UbpmPlusJoinChatRoomService {

    private final UtTouchProperties utTouchProperties;

    private final CountryService countryService;

    private final TaskService taskService;

    private final OrderMapper orderMapper;

    private final MerchantInfoService merchantInfoService;

    @Resource
    private OrderServiceImpl orderService;

    private final ISysConfigService configService;



    @Transactional(rollbackFor = Exception.class)
    public void saveChatRoomJoinTask(SaveChatRoomJoinTaskDTO input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        final Order order = orderMapper.selectById(input.getOrderId());
        input.setExtendKey(order.getMerchantId());
        if(StrUtil.isBlank(input.getName())){
            //任务名称为空时，用订单ID做名称
            input.setName(input.getOrderId());
        }

        // 调用UT-TOUCH接口创建任务
        UtTouchResult<String> result = UtTouchJoinRoomClient.saveChatRoomJoinTask(input);
        String taskId = result.getDataOrThrow();

        String merchantUserId = SecurityUtils.getUserId().toString();
        // 保存任务数据至自己数据库
        Task task = new Task();
        task.setTaskId(taskId);
        task.setOrderId(input.getOrderId());
        task.setTaskName(input.getName());
        task.setMerchantId(order.getMerchantId());
        task.setTaskType(TaskType.JOIN_CHAT_ROOM.name());
        task.setPlatform(Platform.TELEGRAM.getPlatformType());
        task.setCreateBy(merchantUserId);
        task.setStatus(0);
        task.setFreezeBalance(ObjectUtil.isNotEmpty(order)?order.getPrice().intValue():0);
        taskService.save(task);

        orderService.updateDataByTask(input.getOrderId(), input.getName(), input.getNIsRobotExit());
    }

    public Page<GetChatRoomJoinTaskPageOutput> getChatRoomJoinTaskPage(GetChatRoomJoinTaskPageInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        final Integer merchantType = SecurityUtils.getLoginUser().getUser().getMerchantType();
        String merchantId = Optional.ofNullable(SecurityUtils.getLoginUser().getMerchantInfo()).map(MerchantInfo::getMerchantId).orElse(null);

        // 商家查自己的
        if(ObjectUtil.isNotEmpty(merchantType) && merchantType.compareTo(0) == 0) {
            input.setExtendKeys(Collections.singletonList(merchantId));
        }

        // 代理查所有子商家的
        if (ObjectUtil.isNotEmpty(merchantType) && merchantType.compareTo(1) == 0) {
            List<String> childMerchantIds = merchantInfoService.selectChildMerchantIds(merchantId);
            input.setExtendKeys(childMerchantIds);
        }

        UtTouchResult<UtTouchPage<GetChatRoomJoinTaskPageOutput>> touchResult =
                UtTouchJoinRoomClient.getChatRoomJoinTaskPage(input);
        return Optional.of(touchResult)
                .map(UtTouchResult::getData)
                .map(PageConvertUtil::convertUtTouchPage)
                .orElse(PageConvertUtil.defaultPage());
    }

    public GetChatRoomJoinTaskDetailOutput getChatRoomJoinTaskDetail(GetChatRoomJoinTaskDetailInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<GetChatRoomJoinTaskDetailOutput> result = UtTouchJoinRoomClient.getChatRoomJoinTaskDetail(input);
        return result.getDataOrThrow();
    }

    public void cancelChatRoomJoin(CancelChatRoomJoinInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.cancelChatRoomJoin(input);
        result.failedAndThrow();
    }


    public void stopChatRoomJoin(StopChatRoomJoinInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.stopChatRoomJoin(input);
        result.failedAndThrow();
    }

    public void delChatRoomJoinTask(DeleteChatRoomJoinTaskInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.delChatRoomJoinTask(input);
        result.failedAndThrow();
    }


    public void cancelChatRoomJoinTask(OperateChatRoomJoinTaskInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.cancelChatRoomJoinTask(input);
        result.failedAndThrow();
    }

    public void stopChatRoomJoinTask(OperateChatRoomJoinTaskInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.stopChatRoomJoinTask(input);
        result.failedAndThrow();
    }

    public void startChatRoomJoinTask(OperateChatRoomJoinTaskInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.startChatRoomJoinTask(input);
        result.failedAndThrow();
    }

    public AnalysisUploadPhoneResultVO analysisUploadFile(MultipartFile file) {
        List<String> uploadPhones = new ArrayList<>();
        if (MultipartFileUtil.isText(file)) {
            uploadPhones = MultipartFileUtil.analyseTextFile(file);
        } else {
            throw new GlobalException("不支持的格式");
        }
        return this.analysisPhoneNumbers(uploadPhones);



    }

    public void addTaskObject(AddTaskObjectData input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<String> result = UtTouchJoinRoomClient.addTaskObject(input);
        result.failedAndThrow();
    }

    public void updateChatRoomTaskInviteRule(UpdateTaskRuleData input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.updateChatRoomTaskInviteRule(input);
        result.failedAndThrow();
    }

    public void updateChatRoomTask(UpdateChatRoomTaskInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.updateChatRoomTask(input);
        result.failedAndThrow();
    }

    public UpdateTaskRuleData getChatRoomTaskInviteRuleData(
            GetChatRoomTaskInviteRuleData input) {
        UtTouchResult<UpdateTaskRuleData> result = UtTouchJoinRoomClient.getChatRoomTaskInviteRule(input);
        return result.getDataOrThrow();
    }

    public void setChatroomName(SetChatroomNameInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.setChatroomName(input);
        result.failedAndThrow();
    }


    public void setChatroomAdmin(SetChatroomAdminInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.setChatroomAdmin(input);
        result.failedAndThrow();
    }

    public void setChatroomHeadImg(SetChatroomHeadImgInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.setChatroomHeadImg(input);
        result.failedAndThrow();
    }

    public void syncChatroomMember(SyncChatroomMembersInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.syncChatroomMember(input);
        result.failedAndThrow();
    }


    public void transferChatroomOwner(SetChatroomAdminInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.transferChatroomOwner(input);
        result.failedAndThrow();
    }

    public Page<GetChatroomMembersOutput> getChatroomMembers(GetChatroomMembersInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<UtTouchPage<GetChatroomMembersOutput>> result = UtTouchJoinRoomClient.getChatroomMembers(input);
        return PageConvertUtil.convertUtTouchPage(result.getData());
    }

    public Page<GetChatRoomJoinTaskRobotListOutput> getChatRoomJoinTaskRobotList(GetChatRoomJoinTaskRobotListInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<UtTouchPage<GetChatRoomJoinTaskRobotListOutput>> result = UtTouchJoinRoomClient.getChatRoomJoinTaskRobotList(input);
        return PageConvertUtil.convertUtTouchPage(result.getData());
    }

    public Page<GetChatRoomJoinTaskDetailInfoListOutput> getChatRoomJoinTaskDetailInfoList(GetChatRoomJoinTaskDetailInfoListInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<UtTouchPage<GetChatRoomJoinTaskDetailInfoListOutput>> result =
                UtTouchJoinRoomClient.getChatRoomJoinTaskDetailInfoList(input);
        return PageConvertUtil.convertUtTouchPage(result.getData());
    }

    public GetChatRoomJoinTaskDetailStatisticsOutput getChatRoomJoinTaskDetailStatistics(GetChatRoomJoinTaskDetailStatisticsInput input) {
        input.setUserCode(utTouchProperties.getTouchMerchantId());
        UtTouchResult<GetChatRoomJoinTaskDetailStatisticsOutput> result =
                UtTouchJoinRoomClient.getChatRoomJoinTaskDetailStatistics(input);
        return result.getDataOrThrow();
    }

    public CountryBusinessEstimateOutput countryBusinessEstimate(CountryBusinessEstimateInput input) {
        UtTouchResult<CountryBusinessEstimateOutput> result = UtTouchJoinRoomClient.countryBusinessEstimate(input);
        return result.getDataOrThrow();
    }
    public void chatRoomChooseRobotExit(ChatRoomChooseRobotExitInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.chatRoomChooseRobotExit(input);
        result.failedAndThrow();
    }

    public void robotJoinChatRoom(RobotJoinChatRoomInput input) {
        UtTouchResult<String> result = UtTouchJoinRoomClient.robotJoinChatRoom(input);
        result.failedAndThrow();
    }

    public AnalysisUploadPhoneResultVO analysisPhoneNumbers(List<String> phoneNumbers) {

        if (CollectionUtils.isEmpty(phoneNumbers)) {
            return new AnalysisUploadPhoneResultVO();
        }

        /**
         * 1. 去重
         * 2. 获取手机号对应的国家
         * 3. 按照手机号对应国家进行分组计数
         */
        Map<String, Long> countByCountry = phoneNumbers.stream()
                .distinct()
                .map(countryService::getCountriesByPhone)
                .peek(it -> {
                    if (StringUtils.isBlank(it.getCountryName())) {
                        it.setCountryName("未知");
                    }
                })
                .collect(Collectors.groupingBy(UbpmCountryVO::getCountryName, Collectors.counting()));

        return new AnalysisUploadPhoneResultVO(countByCountry, phoneNumbers);
    }

    public AnalysisUploadPhoneResultVO analysisUploadPhoneFileUrl(String filePathUrl) {
        //            File file = FileUtils.toFile(new URL(filePathUrl));
//            List<String> uploadPhones = MultipartFileUtil.analyseTextFile(file);
        final List<String> uploadPhones = com.ruoyi.common.utils.file.FileUtils.getTextListByFilePath(filePathUrl);
        return this.analysisPhoneNumbers(uploadPhones);

    }

    public UserLoginOutput userLogin() {
        String merchantId = Optional.ofNullable(SecurityUtils.getLoginUser().getMerchantInfo()).map(MerchantInfo::getMerchantId).orElse(null);
//        if (StringUtils.isEmpty(merchantId)) {
//            merchantId = configService.selectConfigByKey("robot:merchant");
//        }
        UserLoginInputDTO input = new UserLoginInputDTO();
        input.setAccount(utTouchProperties.getUserAccount());
        input.setPwd(utTouchProperties.getUserPassword());
        UtTouchResult<UserLoginOutput> result = UtTouchJoinRoomClient.userLogin(input);
        if (result != null && result.isSuccess() && StringUtils.isNotEmpty(merchantId)) {
            UserLoginOutput data = result.getData();
            String url = data.getUrl() + "&extendKey=" + merchantId;
            data.setUrl(url);
            return data;
        }
        return result.getData();
    }
}
