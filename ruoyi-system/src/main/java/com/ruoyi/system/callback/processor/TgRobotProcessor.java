package com.ruoyi.system.callback.processor;

import com.ruoyi.system.callback.Type;
import com.ruoyi.system.callback.dto.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TgRobotProcessor {


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
     * @param dto
     */
    @Type(value = 1100910049, parameterClass = CalledEmptyDTO.class)
    public void called1100910049(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    /**
     * TG设置群管理员
     * @param dto
     */
    @Type(value = 1100910053, parameterClass = Called1100910053DTO.class)
    public void called1100910053(Called1100910053DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    /**
     * TG通过群链接入群
     * @param dto
     */
    @Type(value = 1100910039, parameterClass = Called1100910039DTO.class)
    public void called1100910039(Called1100910039DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    /**
     * TG退出群
     * @param dto
     */
    @Type(value = 1100910024, parameterClass = CalledEmptyDTO.class)
    public void called1100910024(Called1100910039DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    /**
     * TG获取群成员列表
     * @param dto
     */
    @Type(value = 1100910113, parameterClass = Called1100910113DTO.class)
    public void called1100910113(Called1100910113DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    /**
     * TG修改号头像
     * @param dto
     */
    @Type(value = 1100910016, parameterClass = CalledEmptyDTO.class)
    public void called1100910016(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    /**
     * TG修改号姓氏和名字
     * @param dto
     */
    @Type(value = 1100910033, parameterClass = CalledEmptyDTO.class)
    public void called1100910033(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    /**
     * TG设置用户名接口
     * @param dto
     */
    @Type(value = 1100910009, parameterClass = CalledEmptyDTO.class)
    public void called1100910009(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

}
