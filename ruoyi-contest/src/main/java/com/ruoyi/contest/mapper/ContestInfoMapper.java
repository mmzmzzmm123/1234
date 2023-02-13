package com.ruoyi.contest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.ContestInfo;

import java.util.List;

/**
 * <p>
 * 竞赛基础信息表 Mapper 接口
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface ContestInfoMapper extends BaseMapper<ContestInfo> {
    /**
     * 查询竞赛基础信息列表
     *
     * @param contestInfo 竞赛基础信息
     * @return 竞赛基础信息集合
     */
    public List<ContestInfo> selectContestInfoList(ContestInfo contestInfo);

}
