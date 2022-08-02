package com.ruoyi.quartz.service;

import com.ruoyi.quartz.domain.ReadResult;

import java.util.List;

/**
 * @author wjp
 * @date 2022/8/2 15:31
 * @description
 */
public interface IReadResultService {

    void saveOrUpdateReadResult(List<ReadResult> readResults);
}
