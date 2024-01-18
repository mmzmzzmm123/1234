package com.ruoyi.system.callback.processor;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.callback.Type;
import com.ruoyi.system.callback.dto.*;
import com.ruoyi.system.components.prepare.multipack.MultipackLogContainer;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.vo.robot.SetNameResourceVO;
import com.ruoyi.system.openapi.model.input.ThirdTgSetChatroomAdminInputDTO;
import com.ruoyi.system.service.GroupInfoService;
import com.ruoyi.system.service.IRobotService;
import com.ruoyi.system.service.PlayMessageConfoundLogService;
import com.ruoyi.system.service.business.GroupService;
import com.ruoyi.system.service.impl.IntoGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    /***
     *
     * TG号回收
     */
    @Type(value = 50005004, parameterClass = Called50005004DTO.class)
    public void called50005004(Called50005004DTO source) {

    }

    /***
     *
     * TG号变更商家
     */
    @Type(value = 50005005, parameterClass = Called50005005DTO.class)
    public void called50005005(List<Called50005005DTO> sourceList) {

    }

    /***
     *
     * TG号资料信息变更
     */
    @Type(value = 50005006, parameterClass = Called50005006DTO.class)
    public void called50005006(List<Called50005006DTO> sourceList) {

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
        //处理tg设置管理员
        if(root.isSuccess() && !StringUtils.isEmpty(root.getRequestPara())) {
            groupService.handlerNewAdmin(JSON.parseObject(root.getRequestPara(), ThirdTgSetChatroomAdminInputDTO.class), dto);
        }
        if(root.isSuccess()) {
        	SpringUtils.getBean(MultipackLogContainer.class).onSucceed(root.getOptSerNo(), null);
        	return ;
        }
    	SpringUtils.getBean(MultipackLogContainer.class).onfail(root.getOptSerNo(), root.getResultMsg());
        //处理请求结果
        groupService.handleActionResult(root.getExtend(), root.getOptSerNo(), root.isSuccess(), root.getResultMsg(), null);
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
            groupInfo = groupService.handleRobotIn(dto,root.getRobotId());
        }
        intoGroupService.intoGroupCallback(groupInfo,root);

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
            groupInfo =  groupService.handleRobotOut(dto,root.getRobotId());
        }
        intoGroupService.outGroupCallback(root,dto);
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
        if(root.isSuccess()) {
            if(StringUtils.isNotEmpty(root.getExtend())){
                try {
                    robotService.updateHeadImgUrl(root.getRobotId(), root.getExtend());
                    return;
                }catch (Exception e){}
            }
        	SpringUtils.getBean(MultipackLogContainer.class).onSucceed(root.getOptSerNo(), null);
        	return ;
        }
    	SpringUtils.getBean(MultipackLogContainer.class).onfail(root.getOptSerNo(), root.getResultMsg());
    }

    /**
     * TG修改号姓氏和名字
     *
     * @param dto
     */
    @Type(value = 1100910033, parameterClass = CalledEmptyDTO.class)
    public void called1100910033(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        if(root.isSuccess()) {
            if(StringUtils.isNotEmpty(root.getExtend())){
                try {
                    SetNameResourceVO setNameResourceVO = JSON.parseObject(root.getExtend(), SetNameResourceVO.class);
                    robotService.updateName(root.getRobotId(),setNameResourceVO);
                    return;
                }catch (Exception e){}
            }
        	SpringUtils.getBean(MultipackLogContainer.class).onSucceed(root.getOptSerNo(), null);
        	return ;
        }
    	SpringUtils.getBean(MultipackLogContainer.class).onfail(root.getOptSerNo(), root.getResultMsg());
    }

    /**
     * TG设置用户名接口
     *
     * @param dto
     */
    @Type(value = 1100910009, parameterClass = CalledEmptyDTO.class)
    public void called1100910009(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        if(root.isSuccess()){
            if(StringUtils.isNotEmpty(root.getExtend())){
                robotService.updateUsername(root.getRobotId(), root.getExtend());
            }
        }
    }

    /**
     * TG获取群信息
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
        playMessageConfoundLogService.handleConfoundText(root);
    }

    /**
     * TG SQL执行接口
     *
     * @param dto
     */
    @Type(value = 1100860002, parameterClass = Called1100860002DTO.class)
    public void called1100860002(Called1100860002DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        if(root.isSuccess()) {
        	SpringUtils.getBean(MultipackLogContainer.class).onSucceed(root.getOptSerNo(), dto.getAccessHash());
        	return ;
        }
    	SpringUtils.getBean(MultipackLogContainer.class).onfail(root.getOptSerNo(), root.getResultMsg());
    }

    /**
     * TG 群编号变动回调
     *
     * @param dto
     */
    @Type(value = 1100910112, parameterClass = Called1100910112DTO.class)
    public void called1100910112(Called1100910112DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        if(root.isSuccess()){
            groupInfoService.changeGroupSerialNo(dto.getOld_chatroom_serial_no(),dto.getNew_chatroom_serial_no());
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
    }

    /**
     * TG 设置隐私手机号码谁可见接口
     *
     * @param dto
     */
    @Type(value = 1100910083, parameterClass = CalledEmptyDTO.class)
    public void called1100910083(Called1100910011DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }



}
