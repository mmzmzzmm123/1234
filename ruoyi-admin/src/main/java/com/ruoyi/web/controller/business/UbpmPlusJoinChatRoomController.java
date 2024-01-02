package com.ruoyi.web.controller.business;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.vo.AnalysisUploadPhoneResultVO;
import com.ruoyi.system.domain.vo.PhoneNumberFileUrlVO;
import com.ruoyi.system.domain.vo.PhoneNumbersVO;
import com.ruoyi.system.domain.vo.SaveAndCreateTaskVO;
import com.ruoyi.system.extend.data.*;
import com.ruoyi.system.service.business.UbpmPlusJoinChatRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jing.Zhang
 */
@Slf4j
@Api(tags = {"拉群任务"})
@RestController
@AllArgsConstructor
@RequestMapping("join-chat-room")
public class UbpmPlusJoinChatRoomController {


    private final UbpmPlusJoinChatRoomService ubpmPlusJoinChatRoomService;

    @ApiOperation("保存拉群任务")
    @PostMapping("/saveChatRoomJoinTask")
    public R<Void> saveChatRoomJoinTask(
            @RequestBody SaveChatRoomJoinTaskDTO input) {
        ubpmPlusJoinChatRoomService.saveChatRoomJoinTask(input);
        return R.ok();
    }


    @ApiOperation("解析上传文件")
    @PostMapping("analysisUploadFile")
    public R<AnalysisUploadPhoneResultVO> analysisUploadFile(MultipartFile file) {
        return R.ok(ubpmPlusJoinChatRoomService.analysisUploadFile(file));
    }

    @ApiOperation("解析手机号码")
    @PostMapping("analysisPhoneNumbers")
    public R<AnalysisUploadPhoneResultVO> analysisPhoneNumbers(@RequestBody PhoneNumbersVO input) {
        return R.ok(ubpmPlusJoinChatRoomService.analysisPhoneNumbers(input.getPhoneNumbers()));
    }

    @ApiOperation("解析上传的文件地址")
    @PostMapping("analysisUploadPhoneFileUrl")
    public R<AnalysisUploadPhoneResultVO> analysisUploadPhoneFileUrl(@RequestBody PhoneNumberFileUrlVO input) {
        return R.ok(ubpmPlusJoinChatRoomService.analysisUploadPhoneFileUrl(input.getFilePathUrl()));
    }


    @ApiOperation("获取拉群任务分页数据")
    @PostMapping("getChatRoomJoinTaskPage")
    public R<Page<GetChatRoomJoinTaskPageOutput>> getChatRoomJoinTaskPage(
            @RequestBody GetChatRoomJoinTaskPageInput input) {
        return R.ok(ubpmPlusJoinChatRoomService.getChatRoomJoinTaskPage(input));
    }

    @ApiOperation("获取任务明细")
    @PostMapping("getChatRoomJoinTaskDetail")
    public R<GetChatRoomJoinTaskDetailOutput> getChatRoomJoinTaskDetail(
            @RequestBody GetChatRoomJoinTaskDetailInput input) {

        return R.ok(ubpmPlusJoinChatRoomService.getChatRoomJoinTaskDetail(input));
    }


    @ApiOperation("取消拉群")
    @PostMapping("cancelChatRoomJoin")
    public R<Void> cancelChatRoomJoin(
            @RequestBody CancelChatRoomJoinInput input) {
        ubpmPlusJoinChatRoomService.cancelChatRoomJoin(input);
        return R.ok();
    }

    @ApiOperation("暂停开启拉群任务")
    @PostMapping("stopChatRoomJoin")
    public R<Void> stopChatRoomJoin(
            @RequestBody StopChatRoomJoinInput input) {
        ubpmPlusJoinChatRoomService.stopChatRoomJoin(input);
        return R.ok();
    }

    @ApiOperation("删除任务")
    @PostMapping("delChatRoomJoinTask")
    public R<Void> delChatRoomJoinTask(
            @RequestBody DeleteChatRoomJoinTaskInput input) {
        ubpmPlusJoinChatRoomService.delChatRoomJoinTask(input);
        return R.ok();
    }


    @ApiOperation("取消拉群任务")
    @PostMapping("cancelChatRoomJoinTask")
    public R<Void> cancelChatRoomJoinTask(
            @RequestBody OperateChatRoomJoinTaskInput input) {
        ubpmPlusJoinChatRoomService.cancelChatRoomJoinTask(input);
        return R.ok();
    }


    @ApiOperation("暂停拉群任务")
    @PostMapping("stopChatRoomJoinTask")
    public R<Void> stopChatRoomJoinTask(
            @RequestBody OperateChatRoomJoinTaskInput input) {
        ubpmPlusJoinChatRoomService.stopChatRoomJoinTask(input);
        return R.ok();
    }

    @ApiOperation("重新开启拉群任务")
    @PostMapping("startChatRoomJoinTask")
    public R<Void> startChatRoomJoinTask(
            @RequestBody OperateChatRoomJoinTaskInput input) {
        ubpmPlusJoinChatRoomService.startChatRoomJoinTask(input);
        return R.ok();
    }


    @ApiOperation("添加任务目标")
    @PostMapping("addTaskObject")
    public R<Void> addTaskObject(
            @RequestBody AddTaskObjectData input) {
        ubpmPlusJoinChatRoomService.addTaskObject(input);
        return R.ok();
    }


    @ApiOperation("修改拉人规则")
    @PostMapping("updateChatRoomTaskInviteRule")
    public R<Void> updateChatRoomTaskInviteRule(@RequestBody UpdateTaskRuleData input) {
        ubpmPlusJoinChatRoomService.updateChatRoomTaskInviteRule(input);
        return R.ok();
    }


    @ApiOperation("获取拉人规则")
    @PostMapping("getChatRoomTaskInviteRuleData")
    public R<UpdateTaskRuleData> getChatRoomTaskInviteRuleData(
            @RequestBody GetChatRoomTaskInviteRuleData input) {

        return R.ok(ubpmPlusJoinChatRoomService.getChatRoomTaskInviteRuleData(input));
    }


    @ApiOperation("任务加速与减速")
    @PostMapping("updateChatRoomTask")
    public R<Void> updateChatRoomTask(
            @RequestBody UpdateChatRoomTaskInput input) {
        ubpmPlusJoinChatRoomService.updateChatRoomTask(input);
        return R.ok();
    }

    @ApiOperation("修改群名称")
    @PostMapping("setChatroomName")
    public R<Void> setChatroomName(
            @RequestBody SetChatroomNameInput input) {
        ubpmPlusJoinChatRoomService.setChatroomName(input);
        return R.ok();
    }


    @ApiOperation("修改群管理员")
    @PostMapping("setChatroomAdmin")
    public R<Void> setChatroomAdmin(@RequestBody SetChatroomAdminInput input) {
        ubpmPlusJoinChatRoomService.setChatroomAdmin(input);
        return R.ok();
    }

    @ApiOperation("修改群头像")
    @PostMapping("setChatroomHeadImg")
    public R<Void> setChatroomHeadImg(@RequestBody SetChatroomHeadImgInput input) {
        ubpmPlusJoinChatRoomService.setChatroomHeadImg(input);
        return R.ok();
    }

    @ApiOperation("修改同步群成员")
    @PostMapping("syncChatroomMember")
    public R<Void> syncChatroomMember(@RequestBody SyncChatroomMembersInput input) {
        ubpmPlusJoinChatRoomService.syncChatroomMember(input);
        return R.ok();
    }

    @ApiOperation("转移群主")
    @PostMapping("transferChatroomOwner")
    public R<Void> transferChatroomOwner(@RequestBody SetChatroomAdminInput input) {
        ubpmPlusJoinChatRoomService.transferChatroomOwner(input);
        return R.ok();
    }


    @ApiOperation("获取群成员")
    @PostMapping("getChatroomMembers")
    public R<Page<GetChatroomMembersOutput>> getChatroomMembers(
            @RequestBody GetChatroomMembersInput input) {
        return R.ok(ubpmPlusJoinChatRoomService.getChatroomMembers(input));
    }


    @ApiOperation("获取账号明细")
    @PostMapping("getChatRoomJoinTaskRobotList")
    public R<Page<GetChatRoomJoinTaskRobotListOutput>> getChatRoomJoinTaskRobotList(
            @RequestBody GetChatRoomJoinTaskRobotListInput input) {
        return R.ok(ubpmPlusJoinChatRoomService.getChatRoomJoinTaskRobotList(input));
    }


    @ApiOperation("获取拉人明细")
    @PostMapping("getChatRoomJoinTaskDetailInfoList")
    public R<Page<GetChatRoomJoinTaskDetailInfoListOutput>> getChatRoomJoinTaskDetailInfoList(
            @RequestBody GetChatRoomJoinTaskDetailInfoListInput input) {
        return R.ok(ubpmPlusJoinChatRoomService.getChatRoomJoinTaskDetailInfoList(input));
    }

    @ApiOperation("获取群统计")
    @PostMapping("getChatRoomJoinTaskDetailStatistics")
    public R<GetChatRoomJoinTaskDetailStatisticsOutput> getChatRoomJoinTaskDetailStatistics(
            @RequestBody GetChatRoomJoinTaskDetailStatisticsInput input) {
        return R.ok(ubpmPlusJoinChatRoomService.getChatRoomJoinTaskDetailStatistics(input));
    }


    @ApiOperation("获取机器人业务大盘")
    @PostMapping("countryBusinessEstimate")
    public R<CountryBusinessEstimateOutput> countryBusinessEstimate(@RequestBody CountryBusinessEstimateInput input) {
        return R.ok(ubpmPlusJoinChatRoomService.countryBusinessEstimate(input));
    }


    @ApiOperation("选择退出部分拉手号")
    @PostMapping("chatRoomChooseRobotExit")
    public R<Void> chatRoomChooseRobotExit(@RequestBody ChatRoomChooseRobotExitInput input) {
        ubpmPlusJoinChatRoomService.chatRoomChooseRobotExit(input);
        return R.ok();
    }


    @ApiOperation("新增拉手号入群")
    @PostMapping("robotJoinChatRoom")
    public R<Void> robotJoinChatRoom(@RequestBody RobotJoinChatRoomInput input) {
        ubpmPlusJoinChatRoomService.robotJoinChatRoom(input);
        return R.ok();
    }


    @ApiOperation("通过订单创建任务")
    @PostMapping("createTaskByOrder")
    public R<Void> createTaskByOrder(@RequestBody SaveAndCreateTaskVO input) {
        log.info("通过订单创建任务input {}", JSON.toJSONString(input));
        SaveChatRoomJoinTaskDTO dto = new SaveChatRoomJoinTaskDTO();
        dto.setChatRoomUrls(input.getGroupIds());
        dto.setNIsOpenChatRoomMemberLimitCount((input.getGroupPersonType()));
        dto.setChatroomUrlSource(input.getGroupSource());
        dto.setImportType(input.getInGroupType());
        dto.setNeedCount(input.getSingleGroupPerson());
        dto.setContentList(input.getTargetIds());
        dto.setName(input.getTaskName());
        dto.setOrderId(input.getOrderId());
        dto.setAutoCreateChatroomCount(0);
        dto.setNIsRobotExit(1);
        dto.setNIsCountryCode(0);
        if(StrUtil.isNotBlank(input.getCountries())){
            dto.setNIsCountryCode(1);
        }
        dto.setCountryCodes(input.getCountries());
        SaveChatRoomJoinTaskDTO.TaskRuleData taskRuleData = new SaveChatRoomJoinTaskDTO.TaskRuleData();
        if(ObjectUtil.isNotEmpty(input.getRule())) {
            BeanUtils.copyProperties(input.getRule(), taskRuleData);
        }
        dto.setTaskRuleData(taskRuleData);
        log.info("通过订单创建任务dto {}", JSON.toJSONString(dto));
        ubpmPlusJoinChatRoomService.saveChatRoomJoinTask(dto);
        return R.ok();
    }
}
