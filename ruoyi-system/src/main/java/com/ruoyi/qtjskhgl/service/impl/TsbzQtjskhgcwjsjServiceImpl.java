package com.ruoyi.qtjskhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjskhgl.mapper.TsbzQtjskhgcwjsjMapper;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhgcwjsj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhgcwjsjService;

/**
 * 群体教师考核过程文件数据Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-17
 */
@Service
public class TsbzQtjskhgcwjsjServiceImpl implements ITsbzQtjskhgcwjsjService {
    @Autowired
    private TsbzQtjskhgcwjsjMapper tsbzQtjskhgcwjsjMapper;

    /**
     * 查询群体教师考核过程文件数据
     *
     * @param id 群体教师考核过程文件数据ID
     * @return 群体教师考核过程文件数据
     */
    @Override
    public TsbzQtjskhgcwjsj selectTsbzQtjskhgcwjsjById(Long id) {
        return tsbzQtjskhgcwjsjMapper.selectTsbzQtjskhgcwjsjById(id);
    }

    /**
     * 查询群体教师考核过程文件数据列表
     *
     * @param tsbzQtjskhgcwjsj 群体教师考核过程文件数据
     * @return 群体教师考核过程文件数据
     */
    @Override
    public List<TsbzQtjskhgcwjsj> selectTsbzQtjskhgcwjsjList(TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj) {
        return tsbzQtjskhgcwjsjMapper.selectTsbzQtjskhgcwjsjList(tsbzQtjskhgcwjsj);
    }

    /**
     * 新增群体教师考核过程文件数据
     *
     * @param tsbzQtjskhgcwjsj 群体教师考核过程文件数据
     * @return 结果
     */
    @Override
    public int insertTsbzQtjskhgcwjsj(TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj) {
        tsbzQtjskhgcwjsj.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjskhgcwjsjMapper.insertTsbzQtjskhgcwjsj(tsbzQtjskhgcwjsj);
    }

    /**
     * 修改群体教师考核过程文件数据
     *
     * @param tsbzQtjskhgcwjsj 群体教师考核过程文件数据
     * @return 结果
     */
    @Override
    public int updateTsbzQtjskhgcwjsj(TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj) {
        return tsbzQtjskhgcwjsjMapper.updateTsbzQtjskhgcwjsj(tsbzQtjskhgcwjsj);
    }

    /**
     * 批量删除群体教师考核过程文件数据
     *
     * @param ids 需要删除的群体教师考核过程文件数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhgcwjsjByIds(Long[] ids) {
        return tsbzQtjskhgcwjsjMapper.deleteTsbzQtjskhgcwjsjByIds(ids);
    }

    /**
     * 删除群体教师考核过程文件数据信息
     *
     * @param id 群体教师考核过程文件数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhgcwjsjById(Long id) {
        return tsbzQtjskhgcwjsjMapper.deleteTsbzQtjskhgcwjsjById(id);
    }
}
