package com.ruoyi.contest.service.impl;

import java.util.Arrays;
import java.util.List;

import com.ruoyi.contest.domain.ContestInfo;
import com.ruoyi.contest.mapper.ContestInfoMapper;
import com.ruoyi.contest.service.IContestInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;

/**
 * 竞赛基础信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class ContestInfoServiceImpl extends ServiceImpl<ContestInfoMapper, ContestInfo> implements IContestInfoService
{
    /**
     * 查询竞赛基础信息列表
     *
     * @param contestInfo 竞赛基础信息
     * @return 竞赛基础信息
     */
    @Override
    public List<ContestInfo> selectContestInfoList(ContestInfo contestInfo)
    {
        return baseMapper.selectContestInfoList(contestInfo);
    }

}
