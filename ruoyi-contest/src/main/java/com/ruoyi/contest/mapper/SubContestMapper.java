package com.ruoyi.contest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.SubContest;
import com.ruoyi.contest.domain.vo.SubContestQueryVo;

import java.util.List;

/**
 * 子竞赛信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-14
 */
public interface SubContestMapper extends BaseMapper<SubContest>
{

    /**
     * 查询子竞赛信息列表
     *
     * @param subContest 子竞赛信息
     * @return 子竞赛信息集合
     */
    public List<SubContest> selectSubContestList(SubContest subContest);

    /**
     * 根据父竞赛grade、inministry查询子竞赛信息列表
     *
     * @param vo 子竞赛信息
     * @return 子竞赛信息集合
     */
    public List<SubContest> selectSubContestList2(SubContestQueryVo vo);

}
