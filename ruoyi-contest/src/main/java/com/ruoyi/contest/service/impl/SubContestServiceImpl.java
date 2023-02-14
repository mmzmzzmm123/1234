package com.ruoyi.contest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.contest.domain.ContestInfo;
import com.ruoyi.contest.domain.SubContest;
import com.ruoyi.contest.mapper.ContestInfoMapper;
import com.ruoyi.contest.mapper.SubContestMapper;
import com.ruoyi.contest.service.ISubContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 子竞赛信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
@RequiredArgsConstructor
public class SubContestServiceImpl extends ServiceImpl<SubContestMapper, SubContest> implements ISubContestService
{

    private final ContestInfoMapper contestInfoMapper;


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

    @Override
    public List<SubContest> selectSubContestListWithParent(SubContest subContest) {
        List<SubContest> list = baseMapper.selectSubContestList(subContest);
        if (list!=null){
            for (SubContest subContest1 : list) {
                Map<String, Object> params=new HashMap<>();
                ContestInfo parentContest = contestInfoMapper.selectById(subContest1.getContestId());
                params.put("parentName", parentContest.getName());
                params.put("inMinistry",parentContest.getInMinistry());
                params.put("grade",parentContest.getGrade());
                subContest1.setParams(params);
            }
        }
        return list;
    }

}
