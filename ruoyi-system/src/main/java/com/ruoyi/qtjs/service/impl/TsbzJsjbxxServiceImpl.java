package com.ruoyi.qtjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjs.mapper.TsbzJsjbxxMapper;
import com.ruoyi.qtjs.domain.TsbzJsjbxx;
import com.ruoyi.qtjs.service.ITsbzJsjbxxService;

/**
 * 教师基本信息Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class TsbzJsjbxxServiceImpl implements ITsbzJsjbxxService {
    @Autowired
    private TsbzJsjbxxMapper tsbzJsjbxxMapper;

    /**
     * 查询教师基本信息
     *
     * @param id 教师基本信息ID
     * @return 教师基本信息
     */
    @Override
    public TsbzJsjbxx selectTsbzJsjbxxById(String id) {
        return tsbzJsjbxxMapper.selectTsbzJsjbxxById(id);
    }

    /**
     * 查询教师基本信息列表
     *
     * @param tsbzJsjbxx 教师基本信息
     * @return 教师基本信息
     */
    @Override
    public List<TsbzJsjbxx> selectTsbzJsjbxxList(TsbzJsjbxx tsbzJsjbxx) {
        return tsbzJsjbxxMapper.selectTsbzJsjbxxList(tsbzJsjbxx);
    }

    /**
     * 新增教师基本信息
     *
     * @param tsbzJsjbxx 教师基本信息
     * @return 结果
     */
    @Override
    public int insertTsbzJsjbxx(TsbzJsjbxx tsbzJsjbxx) {
        tsbzJsjbxx.setCreateTime(DateUtils.getNowDate());
        return tsbzJsjbxxMapper.insertTsbzJsjbxx(tsbzJsjbxx);
    }

    /**
     * 修改教师基本信息
     *
     * @param tsbzJsjbxx 教师基本信息
     * @return 结果
     */
    @Override
    public int updateTsbzJsjbxx(TsbzJsjbxx tsbzJsjbxx) {
        return tsbzJsjbxxMapper.updateTsbzJsjbxx(tsbzJsjbxx);
    }

    /**
     * 批量删除教师基本信息
     *
     * @param ids 需要删除的教师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJsjbxxByIds(String[] ids) {
        return tsbzJsjbxxMapper.deleteTsbzJsjbxxByIds(ids);
    }

    /**
     * 删除教师基本信息信息
     *
     * @param id 教师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJsjbxxById(String id) {
        return tsbzJsjbxxMapper.deleteTsbzJsjbxxById(id);
    }
}
