package com.ruoyi.contest.service.impl;

import java.util.Arrays;
import java.util.List;

import com.ruoyi.contest.domain.SubContest;
import com.ruoyi.contest.mapper.SubContestMapper;
import com.ruoyi.contest.service.ISubContestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;

/**
 * 子竞赛信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class SubContestServiceImpl extends ServiceImpl<SubContestMapper, SubContest> implements ISubContestService
{
    /**
     * 查询子竞赛信息列表
     *
     * @param subContest 子竞赛信息
     * @return 子竞赛信息
     */
    @Override
    public List<SubContest> selectSubContestList(SubContest subContest)
    {
        return baseMapper.selectSubContestList(subContest);
    }

}
