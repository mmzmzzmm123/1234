package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.ReadResult;

import java.util.List;

/**
 * @author wjp
 * @date 2022/8/2 15:17
 * @description
 */
public interface ReadResultMapper {

    void saveReadResult(ReadResult readResult);

    void updateReadResult(ReadResult readResult);

    List<ReadResult> selectAllReadResult();
}
