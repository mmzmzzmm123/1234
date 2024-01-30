package com.ruoyi.system.callback.processor;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.callback.Type;
import com.ruoyi.system.callback.dto.*;
import com.ruoyi.system.components.movie.PlayDirector;
import com.ruoyi.system.components.movie.spi.GroupCtrlStopper;
import com.ruoyi.system.components.prepare.multipack.MultipackLogContainer;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.vo.robot.SetNameResourceVO;
import com.ruoyi.system.openapi.model.input.ThirdTgModifyChatroomNameInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgSetChatroomAdminInputDTO;
import com.ruoyi.system.service.*;
import com.ruoyi.system.service.business.AutoReplyService;
import com.ruoyi.system.service.business.GroupService;
import com.ruoyi.system.service.impl.IntoGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TgRobotProcessor {

    @Autowired
    IntoGroupService intoGroupService;

    @Resource
    private PlayMessageConfoundLogService playMessageConfoundLogService;

    @Autowired
    GroupService groupService;

    @Autowired
    GroupInfoService groupInfoService;

    @Autowired
    private IRobotService robotService;

    @Autowired
    private PlayMessagePushService playMessagePushService;

    @Autowired
    private GroupStateService groupStateService;

    /***
     *
     * TG(资源被动)号回收
     */
    @Type(value = 50005004, parameterClass = Called50005004DTO.class)
    public void called50005004(Called50005004DTO source) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
//        log.info("NQ50005004 called:{}",root);
        if (!CollectionUtils.isEmpty(source.getRobotSerialNos())) {
            robotService.recycleRobot(source.getRobotSerialNos());
        }
    }

    /***
     *
     * TG(资源被动)号变更商家
     */
    @Type(value = 50005005, parameterClass = Called50005005DTO.class)
    public void called50005005(List<Called50005005DTO> sourceList) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
//        log.info("NQ50005005 called:{}",root);
        if (!CollectionUtils.isEmpty(sourceList)) {
            robotService.updateRobotMerchant(sourceList);
        }
    }

    /***
     *
     * TG(资源被动)号资料信息变更
     */
    @Type(value = 50005006, parameterClass = Called50005006DTO.class)
    public void called50005006(List<Called50005006DTO> sourceList) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("NQ50005006 called:{}", root);
        robotService.updateRobotInfo(sourceList);
    }


    /**
     * TG修改群头像
     *
     * @param dto
     */
    @Type(value = 1100910049, parameterClass = CalledEmptyDTO.class)
    public void called1100910049(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        intoGroupService.updateImageCallBack(root);
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(), null);
    }

    /**
     * TG设置群管理员
     *
     * @param dto
     */
    @Type(value = 1100910053, parameterClass = Called1100910053DTO.class)
    public void called1100910053(Called1100910053DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("1100910053-设置群管理员-{}", JSON.toJSONString(root));
        //处理tg设置管理员
        if (root.isSuccess() && !StringUtils.isEmpty(root.getRequestPara())) {
            groupService.handlerNewAdmin(JSON.parseObject(root.getRequestPara(), ThirdTgSetChatroomAdminInputDTO.class), dto);
        }
        //处理请求结果
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(), null);
        if (root.isSuccess()) {
            SpringUtils.getBean(MultipackLogContainer.class).onSucceed(root.getOptSerNo(), null, true);
            return;
        }
        SpringUtils.getBean(MultipackLogContainer.class).onfail(root.getOptSerNo(), root.getResultMsg(), true);
    }

    /**
     * TG通过群链接入群
     *
     * @param dto
     */
    @Type(value = 1100910039, parameterClass = Called1100910039DTO.class)
    public void called1100910039(Called1100910039DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        GroupInfo groupInfo = null;
        if (root.isSuccess()) {
            groupInfo = groupService.handleRobotIn(dto, root.getRobotId(),root.getRequestPara());
        }
        intoGroupService.intoGroupCallback(groupInfo, root);

    }

    /**
     * TG退出群
     *
     * @param dto
     */
    @Type(value = 1100910024, parameterClass = CalledEmptyDTO.class)
    public void called1100910024(Called1100910039DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        GroupInfo groupInfo = null;
        if (root.isSuccess()) {
            groupInfo = groupService.handleRobotOut(dto, root.getRobotId());
        }
        intoGroupService.outGroupCallback(root, dto);
    }

    /**
     * TG获取群成员列表
     *
     * @param dto
     */
    @Type(value = 1100910113, parameterClass = Called1100910113DTO.class)
    public void called1100910113(Called1100910113DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    /**
     * TG修改号头像
     *
     * @param dto
     */
    @Type(value = 1100910016, parameterClass = CalledEmptyDTO.class)
    public void called1100910016(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("1100910016-修改号头像-{}", JSON.toJSONString(root));
        if (root.isSuccess()) {
            if (StringUtils.isNotEmpty(root.getExtend())) {
                try {
                    robotService.updateHeadImgUrl(root.getRobotId(), root.getExtend());
                    return;
                } catch (Exception e) {
                }
            }
            SpringUtils.getBean(MultipackLogContainer.class).onSucceed(root.getOptSerNo(), null, true);
            return;
        }
        SpringUtils.getBean(MultipackLogContainer.class).onfail(root.getOptSerNo(), root.getResultMsg(), true);
    }

    /**
     * TG修改号姓氏和名字
     *
     * @param dto
     */
    @Type(value = 1100910033, parameterClass = CalledEmptyDTO.class)
    public void called1100910033(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("1100910033-修改号姓氏和名字-{}", JSON.toJSONString(root));
        if (root.isSuccess()) {
            if (StringUtils.isNotEmpty(root.getExtend())) {
                try {
                    SetNameResourceVO setNameResourceVO = JSON.parseObject(root.getExtend(), SetNameResourceVO.class);
                    robotService.updateName(root.getRobotId(), setNameResourceVO);
                    return;
                } catch (Exception e) {
                }
            }
            SpringUtils.getBean(MultipackLogContainer.class).onSucceed(root.getOptSerNo(), null, true);
            return;
        }
        else if("名字重复".equals(root.getResultMsg())){//名字重复当做成功
            SpringUtils.getBean(MultipackLogContainer.class).onSucceed(root.getOptSerNo(), null, true);
            return ;
        }
        SpringUtils.getBean(MultipackLogContainer.class).onfail(root.getOptSerNo(), root.getResultMsg(), true);
    }

    /**
     * TG设置用户名接口
     *
     * @param dto
     */
    @Type(value = 1100910009, parameterClass = CalledEmptyDTO.class)
    public void called1100910009(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        if (root.isSuccess()) {
            if (StringUtils.isNotEmpty(root.getExtend())) {
                robotService.updateUsername(root.getRobotId(), root.getExtend());
            }
        }
    }

    /**
     * TG获取群信息
     *
     * @param dto
     */
    @Type(value = 1100910018, parameterClass = Called1100910018DTO.class)
    public void called1100910018(Called1100910018DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        groupInfoService.updateGroupInfo(dto.getChatroomInfo());
    }

    /**
     * TG设置群类型
     *
     * @param dto
     */
    @Type(value = 1100910056, parameterClass = Called1100910056DTO.class)
    public void called1100910056(Called1100910056DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(), null);
    }

    /**
     * TG应用机器人加入通讯录
     *
     * @param dto
     */
    @Type(value = 1100910026, parameterClass = CalledEmptyDTO.class)
    public void called1100910026(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(), null);
    }

    /**
     * TG搜索群、用户、频道
     *
     * @param dto
     */
    @Type(value = 1100910017, parameterClass = Called1100910017DTO.class)
    public void called1100910017(Called1100910017DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(),
                root.isSuccess() ? dto.getUserList() : null);

    }

    /**
     * TG邀请加入群组
     *
     * @param dto
     */
    @Type(value = 1100910015, parameterClass = Called1100910015DTO.class)
    public void called1100910015(Called1100910015DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(), null);
    }

    /**
     * TG获取离散图片
     *
     * @param dto
     */
    @Type(value = 1100850508, parameterClass = Called1100850508DTO.class)
    public void called1100850508(Called1100850508DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        playMessageConfoundLogService.handleConfoundImg(root);
    }

    /**
     * TG获取离散文案
     *
     * @param dto
     */
    @Type(value = 1100850405, parameterClass = Called1100850405DTO.class)
    public void called1100850405(Called1100850405DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("1100850405-TG获取离散文案-{}", JSON.toJSONString(root));
        playMessageConfoundLogService.handleConfoundText(root);
    }

    /**
     * TG SQL执行接口
     *
     * @param dto
     */
    @Type(value = 1100860002, parameterClass = Called1100860002DTO.class)
    public void called1100860002(List<Called1100860002DTO> dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("1100860002-SQL执行接口-{}", JSON.toJSONString(root));
        //集合为空 认为失败
        if (CollUtil.isEmpty(dto)) {
            root.setResultCode(1);
        }
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(),
                root.isSuccess() ? dto : null);

        if (root.isSuccess()) {
            if (!CollectionUtils.isEmpty(dto)) {
                SpringUtils.getBean(MultipackLogContainer.class).onSucceed(root.getOptSerNo(), dto.get(0).getAccessHash(), true);
            }
            return;
        }
        SpringUtils.getBean(MultipackLogContainer.class).onfail(root.getOptSerNo(), root.getResultMsg(), true);


    }

    /**
     * TG（功能被动） 群编号变动回调
     *
     * @param dto
     */
    @Type(value = 1100910112, parameterClass = Called1100910112DTO.class)
    public void called1100910112(Called1100910112DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("收到开平群Id变更回调 {}", JSON.toJSONString(dto));
        if(root.isSuccess()){
            String oldChatroomSerialNo = dto.getOld_chatroom_serial_no();
            String newChatroomSerialNo = dto.getNew_chatroom_serial_no();

            GroupInfo groupInfo = groupInfoService.getGroupBySerialNo(oldChatroomSerialNo, newChatroomSerialNo);
            groupInfoService.changeGroupSerialNo(oldChatroomSerialNo, newChatroomSerialNo);
            if (groupInfo != null) {
                groupStateService.updateUpgradeTime(groupInfo.getGroupId());
                playMessagePushService.pauseGroupPushWhenWaitSend(groupInfo.getGroupId(), "群升级-待升级完成");
            }

        }
    }


    @Type(value = 1100910115, parameterClass = Called1100910115DTO.class)
    public void called1100910115(Called1100910115DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("收到开平群升级结果回调 {}", JSON.toJSONString(dto));
        if (root.isSuccess()) {
            GroupInfo groupInfo = groupInfoService.getGroupBySerialNo(dto.getNewChatroomSerialNo());
            if (groupInfo != null) {
                groupStateService.updateUpgradeTime(groupInfo.getGroupId());
                playMessagePushService.continuePushWhenPause(groupInfo.getGroupId(), "群升级-待升级完成");
            }
        }

    }

    /**
     * TG 发送群消息回调
     *
     * @param dto
     */
    @Type(value = 1100910011, parameterClass = Called1100910011DTO.class)
    public void called1100910011(Called1100910011DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        if (root.isSuccess()) {
            // 成功
            PlayDirector.tgInstance().onMessageSuccess(root.getOptSerNo());
            return;
        }
        PlayDirector.tgInstance().onMessageFailure(root.getOptSerNo(), root.getResultMsg());
    }

    /**
     * TG 设置隐私手机号码谁可见接口
     *
     * @param dto
     */
    @Type(value = 1100910083, parameterClass = CalledEmptyDTO.class)
    public void called1100910083(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }


    /**
     * TG 修改群名称回调
     *
     * @param dto
     */
    @Type(value = 1100910043, parameterClass = CalledEmptyDTO.class)
    public void called1100910043(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(), null);
        if (root.isSuccess() && StrUtil.isNotBlank(root.getRequestPara())) {
            ThirdTgModifyChatroomNameInputDTO input = JSON.parseObject(root.getRequestPara(), ThirdTgModifyChatroomNameInputDTO.class);
            if (input != null) {
                groupInfoService.updateNameByGroupSerialNo(input.getChatroomSerialNo(), Base64.decodeStr(input.getChatroomNameBase64()));
            }
        }

    }

    /**
     * TG 获取群成员
     *
     * @param dto
     */
    @Type(value = 1100910006, parameterClass = Called1100910006DTO.class)
    public void called1100910006(Called1100910006DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(), null);
        intoGroupService.setGroupMemberCallBack(dto, root);
    }

    /**
     * TG(号被动) 非双向解限时间回调推送
     *
     * @param dto
     */
    @Type(value = 1100910101, parameterClass = Called1100910101DTO.class)
    public void called1100910101(Called1100910101DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("NQ1100910101 called:{}", root);
        ServiceLoader.load(GroupCtrlStopper.class, "BothwayGroupCtrlStopper").doSetting(root.getRobotId());
        robotService.updateBidirectional(dto);
    }

    /***
     * TG(号被动) 机器人离线回调
     */
    @Type(value = 1100910003, parameterClass = Called1100910003DTO.class)
    public void called1100910003(Called1100910003DTO source) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
//        log.info("NQ1100910003 called:{}",root);
        if (root.isSuccess()) {
            robotService.offline(source.getUser_serial_no());
        }
    }

    /***
     * TG(号被动) 机器人封号回调
     */
    @Type(value = 1100910045, parameterClass = Called1100910045DTO.class)
    public void robotBanned(Called1100910045DTO source) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("NQ1100910045 called:{}", root);
        if (root.isSuccess()) {
            robotService.sealRobot(source);
        }

    }

    /***
     * 机器人登录成功回调
     */
    @Type(value = 1100910001, parameterClass = Called1100910001DTO.class)
    public void cacheLogin(Called1100910001DTO source) {
//        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
//        log.info("NQ1100910001 called:{}",root);
//        if(root.isSuccess()){
//            robotService.cacheLogin(source.getRobot_serial_no());
//        }
    }

    /**
     * 封群回调 被动
     * @param dto
     */
    @Type(value = 1100850217, parameterClass = Called1100850217DTO.class)
    public void called1100850217(Called1100850217DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("NQ1100850217 called:{} {}", root, JSON.toJSONString(dto));
        if (root.isSuccess()) {
            groupStateService.groupBaned(dto.getChatroomSerialNo(), dto.getSealTime());
        }
    }


    /**
     * 机器人私聊消息回调
     *
     * @param dto
     */
    @Type(value = 1100910027, parameterClass = Called1100910027DTO.class)
    public void called1100910027(List<Called1100910027DTO> dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("NQ1100910027 called:{} {}", root, JSON.toJSONString(dto));
        SpringUtils.getBean(AutoReplyService.class).messageCallback(root, dto);
    }

    /**
     * 发送好友消息回调
     * @param dto
     */
    @Type(value = 1100910010, parameterClass = Called1100910010DTO.class)
    public void called1100910010(Called1100910010DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        log.info("NQ1100910010 called:{} {}", root, JSON.toJSONString(dto));

        SpringUtils.getBean(AutoReplyService.class).sendMessageResult(root, dto);


    }
}
