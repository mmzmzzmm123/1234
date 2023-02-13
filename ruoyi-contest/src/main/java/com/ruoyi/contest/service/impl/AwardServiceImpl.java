package com.ruoyi.contest.service.impl;

import com.ruoyi.contest.domain.Award;
import com.ruoyi.contest.mapper.AwardMapper;
import com.ruoyi.contest.service.AwardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 获奖登记表 服务实现类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award> implements AwardService {

    @Override
    public List<Award> selectAwardList(Award award) {
        return baseMapper.selectAwardList(award);
    }
}
