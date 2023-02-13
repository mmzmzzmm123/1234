package com.ruoyi.contest.service;

import com.ruoyi.contest.domain.ContestInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 竞赛基础信息表 服务类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface ContestInfoService extends IService<ContestInfo> {

    /**
     *
     * @param contestInfo
     * @return
     */
    List<ContestInfo> selectContestInfoList(ContestInfo contestInfo);
}
