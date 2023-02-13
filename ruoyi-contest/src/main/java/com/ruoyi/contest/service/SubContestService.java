package com.ruoyi.contest.service;

import com.ruoyi.contest.domain.SubContest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 子竞赛信息表 服务类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface SubContestService extends IService<SubContest> {

    /**
     *
     * @param subContest
     * @return
     */
    List<SubContest> selectSubContestList(SubContest subContest);
}
