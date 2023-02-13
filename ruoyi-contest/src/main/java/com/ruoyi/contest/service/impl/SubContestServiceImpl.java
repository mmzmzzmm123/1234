package com.ruoyi.contest.service.impl;

import com.ruoyi.contest.domain.SubContest;
import com.ruoyi.contest.mapper.SubContestMapper;
import com.ruoyi.contest.service.SubContestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 子竞赛信息表 服务实现类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Service
public class SubContestServiceImpl extends ServiceImpl<SubContestMapper, SubContest> implements SubContestService {

    @Override
    public List<SubContest> selectSubContestList(SubContest subContest) {
        return baseMapper.selectSubContestList(subContest);
    }
}
