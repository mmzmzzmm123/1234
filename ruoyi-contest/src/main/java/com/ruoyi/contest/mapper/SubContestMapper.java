package com.ruoyi.contest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.SubContest;

import java.util.List;

/**
 * <p>
 * 子竞赛信息表 Mapper 接口
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface SubContestMapper extends BaseMapper<SubContest> {
    /**
     * 查询子竞赛信息列表
     *
     * @param subContest 子竞赛信息
     * @return 子竞赛信息集合
     */
    public List<SubContest> selectSubContestList(SubContest subContest);

}
