package com.ruoyi.project.benyi.service.impl;

import com.ruoyi.project.benyi.domain.ByDatetime;
import com.ruoyi.project.benyi.mapper.ByDatetimeMapper;
import com.ruoyi.project.benyi.service.IByDatetimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ByDatetimeServiceImpl  implements IByDatetimeService {
    @Autowired
    private ByDatetimeMapper byDatetimeMapper;

    /**
     * 查询日期列表
     *
     * @param byDatetime 日期
     * @return 日期集合
     */
    @Override
    public List<ByDatetime> selectByDatetimeList(ByDatetime byDatetime) {
        return byDatetimeMapper.selectByDatetimeList(byDatetime);
    }
}
