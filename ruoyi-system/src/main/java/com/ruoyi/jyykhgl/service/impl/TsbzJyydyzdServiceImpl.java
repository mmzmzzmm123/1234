package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyydyzdMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyydyzd;
import com.ruoyi.jyykhgl.service.ITsbzJyydyzdService;

/**
 * 调研指导Service业务层处理
 *
 * @author tsbz
 * @date 2021-01-15
 */
@Service
public class TsbzJyydyzdServiceImpl implements ITsbzJyydyzdService {
    @Autowired
    private TsbzJyydyzdMapper tsbzJyydyzdMapper;

    /**
     * 查询调研指导
     *
     * @param id 调研指导ID
     * @return 调研指导
     */
    @Override
    public TsbzJyydyzd selectTsbzJyydyzdById(Long id) {
        return tsbzJyydyzdMapper.selectTsbzJyydyzdById(id);
    }

    /**
     * 查询调研指导列表
     *
     * @param tsbzJyydyzd 调研指导
     * @return 调研指导
     */
    @Override
    public List<TsbzJyydyzd> selectTsbzJyydyzdList(TsbzJyydyzd tsbzJyydyzd) {
        return tsbzJyydyzdMapper.selectTsbzJyydyzdList(tsbzJyydyzd);
    }

    /**
     * 新增调研指导
     *
     * @param tsbzJyydyzd 调研指导
     * @return 结果
     */
    @Override
    public int insertTsbzJyydyzd(TsbzJyydyzd tsbzJyydyzd) {
        tsbzJyydyzd.setCreateTime(DateUtils.getNowDate());
        return tsbzJyydyzdMapper.insertTsbzJyydyzd(tsbzJyydyzd);
    }

    /**
     * 修改调研指导
     *
     * @param tsbzJyydyzd 调研指导
     * @return 结果
     */
    @Override
    public int updateTsbzJyydyzd(TsbzJyydyzd tsbzJyydyzd) {
        return tsbzJyydyzdMapper.updateTsbzJyydyzd(tsbzJyydyzd);
    }

    /**
     * 批量删除调研指导
     *
     * @param ids 需要删除的调研指导ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyydyzdByIds(Long[] ids) {
        return tsbzJyydyzdMapper.deleteTsbzJyydyzdByIds(ids);
    }

    /**
     * 删除调研指导信息
     *
     * @param id 调研指导ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyydyzdById(Long id) {
        return tsbzJyydyzdMapper.deleteTsbzJyydyzdById(id);
    }
}
