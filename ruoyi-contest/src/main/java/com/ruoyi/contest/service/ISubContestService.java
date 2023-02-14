package com.ruoyi.contest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.contest.domain.SubContest;
import com.ruoyi.contest.domain.vo.SubContestQueryVo;

import java.util.List;

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

    /**
     *
     * @param subContest
     * @return
     */
    List<SubContest> selectSubContestListWithParent(SubContestQueryVo subContest);
}
