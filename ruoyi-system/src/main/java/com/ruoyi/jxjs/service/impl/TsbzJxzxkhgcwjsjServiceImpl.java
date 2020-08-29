package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxzxkhgcwjsjMapper;
import com.ruoyi.jxjs.domain.TsbzJxzxkhgcwjsj;
import com.ruoyi.jxjs.service.ITsbzJxzxkhgcwjsjService;

/**
 * 考核过程文件数据Service业务层处理
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@Service
public class TsbzJxzxkhgcwjsjServiceImpl implements ITsbzJxzxkhgcwjsjService {
    @Autowired
    private TsbzJxzxkhgcwjsjMapper tsbzJxzxkhgcwjsjMapper;

    /**
     * 查询考核过程文件数据
     *
     * @param id 考核过程文件数据ID
     * @return 考核过程文件数据
     */
    @Override
    public TsbzJxzxkhgcwjsj selectTsbzJxzxkhgcwjsjById(Long id) {
        return tsbzJxzxkhgcwjsjMapper.selectTsbzJxzxkhgcwjsjById(id);
    }

    /**
     * 查询考核过程文件数据列表
     *
     * @param tsbzJxzxkhgcwjsj 考核过程文件数据
     * @return 考核过程文件数据
     */
    @Override
    public List<TsbzJxzxkhgcwjsj> selectTsbzJxzxkhgcwjsjList(TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj) {
        return tsbzJxzxkhgcwjsjMapper.selectTsbzJxzxkhgcwjsjList(tsbzJxzxkhgcwjsj);
    }

    /**
     * 新增考核过程文件数据
     *
     * @param tsbzJxzxkhgcwjsj 考核过程文件数据
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxkhgcwjsj(TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj) {
        tsbzJxzxkhgcwjsj.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxkhgcwjsjMapper.insertTsbzJxzxkhgcwjsj(tsbzJxzxkhgcwjsj);
    }

    /**
     * 修改考核过程文件数据
     *
     * @param tsbzJxzxkhgcwjsj 考核过程文件数据
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxkhgcwjsj(TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj) {
        return tsbzJxzxkhgcwjsjMapper.updateTsbzJxzxkhgcwjsj(tsbzJxzxkhgcwjsj);
    }

    /**
     * 批量删除考核过程文件数据
     *
     * @param ids 需要删除的考核过程文件数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhgcwjsjByIds(Long[] ids) {
        return tsbzJxzxkhgcwjsjMapper.deleteTsbzJxzxkhgcwjsjByIds(ids);
    }

    /**
     * 删除考核过程文件数据信息
     *
     * @param id 考核过程文件数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhgcwjsjById(Long id) {
        return tsbzJxzxkhgcwjsjMapper.deleteTsbzJxzxkhgcwjsjById(id);
    }
}
