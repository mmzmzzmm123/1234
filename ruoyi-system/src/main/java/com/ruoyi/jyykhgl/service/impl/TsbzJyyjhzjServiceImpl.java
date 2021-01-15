package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyyjhzjMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyyjhzj;
import com.ruoyi.jyykhgl.service.ITsbzJyyjhzjService;

/**
 * 计划总结（教研员）Service业务层处理
 *
 * @author tsbz
 * @date 2021-01-15
 */
@Service
public class TsbzJyyjhzjServiceImpl implements ITsbzJyyjhzjService {
    @Autowired
    private TsbzJyyjhzjMapper tsbzJyyjhzjMapper;

    /**
     * 查询计划总结（教研员）
     *
     * @param id 计划总结（教研员）ID
     * @return 计划总结（教研员）
     */
    @Override
    public TsbzJyyjhzj selectTsbzJyyjhzjById(Long id) {
        return tsbzJyyjhzjMapper.selectTsbzJyyjhzjById(id);
    }

    /**
     * 查询计划总结（教研员）列表
     *
     * @param tsbzJyyjhzj 计划总结（教研员）
     * @return 计划总结（教研员）
     */
    @Override
    public List<TsbzJyyjhzj> selectTsbzJyyjhzjList(TsbzJyyjhzj tsbzJyyjhzj) {
        return tsbzJyyjhzjMapper.selectTsbzJyyjhzjList(tsbzJyyjhzj);
    }

    /**
     * 新增计划总结（教研员）
     *
     * @param tsbzJyyjhzj 计划总结（教研员）
     * @return 结果
     */
    @Override
    public int insertTsbzJyyjhzj(TsbzJyyjhzj tsbzJyyjhzj) {
        tsbzJyyjhzj.setCreateTime(DateUtils.getNowDate());
        return tsbzJyyjhzjMapper.insertTsbzJyyjhzj(tsbzJyyjhzj);
    }

    /**
     * 修改计划总结（教研员）
     *
     * @param tsbzJyyjhzj 计划总结（教研员）
     * @return 结果
     */
    @Override
    public int updateTsbzJyyjhzj(TsbzJyyjhzj tsbzJyyjhzj) {
        return tsbzJyyjhzjMapper.updateTsbzJyyjhzj(tsbzJyyjhzj);
    }

    /**
     * 批量删除计划总结（教研员）
     *
     * @param ids 需要删除的计划总结（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyjhzjByIds(Long[] ids) {
        return tsbzJyyjhzjMapper.deleteTsbzJyyjhzjByIds(ids);
    }

    /**
     * 删除计划总结（教研员）信息
     *
     * @param id 计划总结（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyjhzjById(Long id) {
        return tsbzJyyjhzjMapper.deleteTsbzJyyjhzjById(id);
    }
}
