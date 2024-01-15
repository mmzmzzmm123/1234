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

    /***
     * 缓存登录成功结果
     */
    @Type(value = 1100910001, parameterClass = Called1100910001DTO.class)
    public void called1100910001(Called1100910001DTO dto) {

    }

    /**
     * 缓存登录 失败结果
     */
    @Type(value = 1100910002, parameterClass = Called1100910002DTO.class)
    public void called1100910002(Called1100910002DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }


    @Type(value = 1100910016, parameterClass = CalledEmptyDTO.class)
    public void called1100910016(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    @Type(value = 1100910009, parameterClass = CalledEmptyDTO.class)
    public void called1100910009(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    @Type(value = 1100910033, parameterClass = CalledEmptyDTO.class)
    public void called1100910033(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    @Type(value = 1100910083, parameterClass = CalledEmptyDTO.class)
    public void called1100910083(CalledEmptyDTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }

    @Type(value = 1100910101, parameterClass = Called1100910101DTO.class)
    public void called1100910101(Called1100910101DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
    }


    @Type(value = 1100850508, parameterClass = Called1100850508DTO.class)
    public void called1100850508(Called1100850508DTO dto) {
        CalledDTO root = CalledDTOThreadLocal.getAndRemove();
        String optSerNo = root.getOptSerNo();
    }

}
