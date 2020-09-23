package com.ruoyi.project.benyi.service;

import com.ruoyi.project.benyi.domain.ByDatetime;

import java.util.List;

public interface IByDatetimeService {
    /**
     * 查询日期列表
     *
     * @param byDatetime 日期
     * @return 日期集合
     */
    public List<ByDatetime> selectByDatetimeList(ByDatetime byDatetime);
}
