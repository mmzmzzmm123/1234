package com.ruoyi.contest.service.impl;

import com.ruoyi.contest.domain.ContestInfo;
import com.ruoyi.contest.mapper.ContestInfoMapper;
import com.ruoyi.contest.service.ContestInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 竞赛基础信息表 服务实现类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Service
public class ContestInfoServiceImpl extends ServiceImpl<ContestInfoMapper, ContestInfo> implements ContestInfoService {

    @Override
    public List<ContestInfo> selectContestInfoList(ContestInfo contestInfo) {
        return baseMapper.selectContestInfoList(contestInfo);
    }
}
