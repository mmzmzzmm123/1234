package com.ruoyi.contest.service.impl;

import java.util.Arrays;
import java.util.List;

import com.ruoyi.contest.domain.Award;
import com.ruoyi.contest.mapper.AwardMapper;
import com.ruoyi.contest.service.IAwardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;

/**
 * 获奖登记Service业务层处理
 *
 * @author lsyonlygoddes
 * @date 2023-02-14
 */
@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award> implements IAwardService
{
    /**
     * 查询获奖登记列表
     *
     * @param award 获奖登记
     * @return 获奖登记
     */
    @Override
    public List<Award> selectAwardList(Award award)
    {
        return baseMapper.selectAwardList(award);
    }

}
