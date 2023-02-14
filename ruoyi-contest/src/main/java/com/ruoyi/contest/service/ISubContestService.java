package com.ruoyi.contest.service;

import java.util.List;

import com.ruoyi.contest.domain.SubContest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 子竞赛信息Service接口
 *
 * @author ruoyi
 * @date 2023-02-14
 */
public interface ISubContestService extends IService<SubContest>
{
    /**
     * 查询子竞赛信息列表
     *
     * @param subContest 子竞赛信息
     * @return 子竞赛信息集合
     */
    public List<SubContest> selectSubContestList(SubContest subContest);
}
