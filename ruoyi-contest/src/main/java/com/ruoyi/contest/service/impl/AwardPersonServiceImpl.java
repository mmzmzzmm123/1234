package com.ruoyi.contest.service.impl;

import com.ruoyi.contest.domain.AwardPerson;
import com.ruoyi.contest.mapper.AwardPersonMapper;
import com.ruoyi.contest.service.AwardPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 参赛人员信息表 服务实现类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Service
public class AwardPersonServiceImpl extends ServiceImpl<AwardPersonMapper, AwardPerson> implements AwardPersonService {

    @Override
    public List<AwardPerson> selectAwardPersonList(AwardPerson awardPerson) {
        return baseMapper.selectAwardPersonList(awardPerson);
    }
}
