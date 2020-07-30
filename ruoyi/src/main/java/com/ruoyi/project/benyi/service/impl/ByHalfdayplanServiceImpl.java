package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByHalfdayplanMapper;
import com.ruoyi.project.benyi.domain.ByHalfdayplan;
import com.ruoyi.project.benyi.service.IByHalfdayplanService;

/**
 * 入园半日体验计划Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-30
 */
@Service
public class ByHalfdayplanServiceImpl implements IByHalfdayplanService {
    @Autowired
    private ByHalfdayplanMapper byHalfdayplanMapper;

    /**
     * 查询入园半日体验计划
     *
     * @param id 入园半日体验计划ID
     * @return 入园半日体验计划
     */
    @Override
    public ByHalfdayplan selectByHalfdayplanById(String id)
    {
        return byHalfdayplanMapper.selectByHalfdayplanById(id);
    }

    /**
     * 查询入园半日体验计划列表
     *
     * @param byHalfdayplan 入园半日体验计划
     * @return 入园半日体验计划
     */
    @Override
    public List<ByHalfdayplan> selectByHalfdayplanList(ByHalfdayplan byHalfdayplan)
    {
        return byHalfdayplanMapper.selectByHalfdayplanList(byHalfdayplan);
    }

    /**
     * 新增入园半日体验计划
     *
     * @param byHalfdayplan 入园半日体验计划
     * @return 结果
     */
    @Override
    public int insertByHalfdayplan(ByHalfdayplan byHalfdayplan)
    {
        byHalfdayplan.setCreateTime(DateUtils.getNowDate());
        return byHalfdayplanMapper.insertByHalfdayplan(byHalfdayplan);
    }

    /**
     * 修改入园半日体验计划
     *
     * @param byHalfdayplan 入园半日体验计划
     * @return 结果
     */
    @Override
    public int updateByHalfdayplan(ByHalfdayplan byHalfdayplan)
    {
        return byHalfdayplanMapper.updateByHalfdayplan(byHalfdayplan);
    }

    /**
     * 批量删除入园半日体验计划
     *
     * @param ids 需要删除的入园半日体验计划ID
     * @return 结果
     */
    @Override
    public int deleteByHalfdayplanByIds(String[] ids)
    {
        return byHalfdayplanMapper.deleteByHalfdayplanByIds(ids);
    }

    /**
     * 删除入园半日体验计划信息
     *
     * @param id 入园半日体验计划ID
     * @return 结果
     */
    @Override
    public int deleteByHalfdayplanById(String id)
    {
        return byHalfdayplanMapper.deleteByHalfdayplanById(id);
    }
}