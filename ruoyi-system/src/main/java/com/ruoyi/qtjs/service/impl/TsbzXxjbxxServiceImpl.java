package com.ruoyi.qtjs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjs.mapper.TsbzXxjbxxMapper;
import com.ruoyi.qtjs.domain.TsbzXxjbxx;
import com.ruoyi.qtjs.service.ITsbzXxjbxxService;

/**
 * 学校信息Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-14
 */
@Service
public class TsbzXxjbxxServiceImpl implements ITsbzXxjbxxService {
    @Autowired
    private TsbzXxjbxxMapper tsbzXxjbxxMapper;

    /**
     * 查询学校信息
     *
     * @param id 学校信息ID
     * @return 学校信息
     */
    @Override
    public TsbzXxjbxx selectTsbzXxjbxxById(String id) {
        return tsbzXxjbxxMapper.selectTsbzXxjbxxById(id);
    }

    /**
     * 查询学校信息列表
     *
     * @param tsbzXxjbxx 学校信息
     * @return 学校信息
     */
    @Override
    public List<TsbzXxjbxx> selectTsbzXxjbxxList(TsbzXxjbxx tsbzXxjbxx) {
        return tsbzXxjbxxMapper.selectTsbzXxjbxxList(tsbzXxjbxx);
    }

    /**
     * 新增学校信息
     *
     * @param tsbzXxjbxx 学校信息
     * @return 结果
     */
    @Override
    public int insertTsbzXxjbxx(TsbzXxjbxx tsbzXxjbxx) {
        return tsbzXxjbxxMapper.insertTsbzXxjbxx(tsbzXxjbxx);
    }

    /**
     * 修改学校信息
     *
     * @param tsbzXxjbxx 学校信息
     * @return 结果
     */
    @Override
    public int updateTsbzXxjbxx(TsbzXxjbxx tsbzXxjbxx) {
        return tsbzXxjbxxMapper.updateTsbzXxjbxx(tsbzXxjbxx);
    }

    /**
     * 批量删除学校信息
     *
     * @param ids 需要删除的学校信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzXxjbxxByIds(String[] ids) {
        return tsbzXxjbxxMapper.deleteTsbzXxjbxxByIds(ids);
    }

    /**
     * 删除学校信息信息
     *
     * @param id 学校信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzXxjbxxById(String id) {
        return tsbzXxjbxxMapper.deleteTsbzXxjbxxById(id);
    }
}
