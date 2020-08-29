package com.ruoyi.jxzxkhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxzxkhgl.mapper.TsbzJxzxkhgcsjMapper;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhgcsj;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhgcsjService;

/**
 * 考核过程数据Service业务层处理
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@Service
public class TsbzJxzxkhgcsjServiceImpl implements ITsbzJxzxkhgcsjService {
    @Autowired
    private TsbzJxzxkhgcsjMapper tsbzJxzxkhgcsjMapper;

    /**
     * 查询考核过程数据
     *
     * @param id 考核过程数据ID
     * @return 考核过程数据
     */
    @Override
    public TsbzJxzxkhgcsj selectTsbzJxzxkhgcsjById(String id) {
        return tsbzJxzxkhgcsjMapper.selectTsbzJxzxkhgcsjById(id);
    }

    /**
     * 查询考核过程数据列表
     *
     * @param tsbzJxzxkhgcsj 考核过程数据
     * @return 考核过程数据
     */
    @Override
    public List<TsbzJxzxkhgcsj> selectTsbzJxzxkhgcsjList(TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        return tsbzJxzxkhgcsjMapper.selectTsbzJxzxkhgcsjList(tsbzJxzxkhgcsj);
    }

    /**
     * 新增考核过程数据
     *
     * @param tsbzJxzxkhgcsj 考核过程数据
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxkhgcsj(TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        tsbzJxzxkhgcsj.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxkhgcsjMapper.insertTsbzJxzxkhgcsj(tsbzJxzxkhgcsj);
    }

    /**
     * 修改考核过程数据
     *
     * @param tsbzJxzxkhgcsj 考核过程数据
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxkhgcsj(TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        return tsbzJxzxkhgcsjMapper.updateTsbzJxzxkhgcsj(tsbzJxzxkhgcsj);
    }

    /**
     * 批量删除考核过程数据
     *
     * @param ids 需要删除的考核过程数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhgcsjByIds(String[] ids) {
        return tsbzJxzxkhgcsjMapper.deleteTsbzJxzxkhgcsjByIds(ids);
    }

    /**
     * 删除考核过程数据信息
     *
     * @param id 考核过程数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhgcsjById(String id) {
        return tsbzJxzxkhgcsjMapper.deleteTsbzJxzxkhgcsjById(id);
    }
}
