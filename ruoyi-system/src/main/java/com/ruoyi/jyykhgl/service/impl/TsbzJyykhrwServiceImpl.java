package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyykhrwMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyykhrw;
import com.ruoyi.jyykhgl.service.ITsbzJyykhrwService;

/**
 * 教研员考核任务Service业务层处理
 *
 * @author tsbz
 * @date 2021-01-15
 */
@Service
public class TsbzJyykhrwServiceImpl implements ITsbzJyykhrwService {
    @Autowired
    private TsbzJyykhrwMapper tsbzJyykhrwMapper;

    /**
     * 查询教研员考核任务
     *
     * @param id 教研员考核任务ID
     * @return 教研员考核任务
     */
    @Override
    public TsbzJyykhrw selectTsbzJyykhrwById(Long id) {
        return tsbzJyykhrwMapper.selectTsbzJyykhrwById(id);
    }

    /**
     * 查询教研员考核任务列表
     *
     * @param tsbzJyykhrw 教研员考核任务
     * @return 教研员考核任务
     */
    @Override
    public List<TsbzJyykhrw> selectTsbzJyykhrwList(TsbzJyykhrw tsbzJyykhrw) {
        return tsbzJyykhrwMapper.selectTsbzJyykhrwList(tsbzJyykhrw);
    }

    /**
     * 新增教研员考核任务
     *
     * @param tsbzJyykhrw 教研员考核任务
     * @return 结果
     */
    @Override
    public int insertTsbzJyykhrw(TsbzJyykhrw tsbzJyykhrw) {
        tsbzJyykhrw.setCreateTime(DateUtils.getNowDate());
        return tsbzJyykhrwMapper.insertTsbzJyykhrw(tsbzJyykhrw);
    }

    /**
     * 修改教研员考核任务
     *
     * @param tsbzJyykhrw 教研员考核任务
     * @return 结果
     */
    @Override
    public int updateTsbzJyykhrw(TsbzJyykhrw tsbzJyykhrw) {
        return tsbzJyykhrwMapper.updateTsbzJyykhrw(tsbzJyykhrw);
    }

    /**
     * 批量删除教研员考核任务
     *
     * @param ids 需要删除的教研员考核任务ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyykhrwByIds(Long[] ids) {
        return tsbzJyykhrwMapper.deleteTsbzJyykhrwByIds(ids);
    }

    /**
     * 删除教研员考核任务信息
     *
     * @param id 教研员考核任务ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyykhrwById(Long id) {
        return tsbzJyykhrwMapper.deleteTsbzJyykhrwById(id);
    }
}
