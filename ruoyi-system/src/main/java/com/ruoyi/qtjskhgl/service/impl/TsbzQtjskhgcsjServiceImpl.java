package com.ruoyi.qtjskhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjskhgl.mapper.TsbzQtjskhgcsjMapper;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhgcsj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhgcsjService;

/**
 * 群体教师考核过程数据Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-17
 */
@Service
public class TsbzQtjskhgcsjServiceImpl implements ITsbzQtjskhgcsjService {
    @Autowired
    private TsbzQtjskhgcsjMapper tsbzQtjskhgcsjMapper;

    /**
     * 查询群体教师考核过程数据
     *
     * @param id 群体教师考核过程数据ID
     * @return 群体教师考核过程数据
     */
    @Override
    public TsbzQtjskhgcsj selectTsbzQtjskhgcsjById(String id) {
        return tsbzQtjskhgcsjMapper.selectTsbzQtjskhgcsjById(id);
    }

    /**
     * 查询群体教师考核过程数据列表
     *
     * @param tsbzQtjskhgcsj 群体教师考核过程数据
     * @return 群体教师考核过程数据
     */
    @Override
    public List<TsbzQtjskhgcsj> selectTsbzQtjskhgcsjList(TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        return tsbzQtjskhgcsjMapper.selectTsbzQtjskhgcsjList(tsbzQtjskhgcsj);
    }

    /**
     * 新增群体教师考核过程数据
     *
     * @param tsbzQtjskhgcsj 群体教师考核过程数据
     * @return 结果
     */
    @Override
    public int insertTsbzQtjskhgcsj(TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        tsbzQtjskhgcsj.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjskhgcsjMapper.insertTsbzQtjskhgcsj(tsbzQtjskhgcsj);
    }

    /**
     * 修改群体教师考核过程数据
     *
     * @param tsbzQtjskhgcsj 群体教师考核过程数据
     * @return 结果
     */
    @Override
    public int updateTsbzQtjskhgcsj(TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        return tsbzQtjskhgcsjMapper.updateTsbzQtjskhgcsj(tsbzQtjskhgcsj);
    }

    /**
     * 批量删除群体教师考核过程数据
     *
     * @param ids 需要删除的群体教师考核过程数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhgcsjByIds(String[] ids) {
        return tsbzQtjskhgcsjMapper.deleteTsbzQtjskhgcsjByIds(ids);
    }

    /**
     * 删除群体教师考核过程数据信息
     *
     * @param id 群体教师考核过程数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhgcsjById(String id) {
        return tsbzQtjskhgcsjMapper.deleteTsbzQtjskhgcsjById(id);
    }
}
