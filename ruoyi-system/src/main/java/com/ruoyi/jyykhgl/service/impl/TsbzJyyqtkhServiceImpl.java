package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyyqtkhMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyyqtkh;
import com.ruoyi.jyykhgl.service.ITsbzJyyqtkhService;

/**
 * 其他考核（教研员）Service业务层处理
 *
 * @author tsbz
 * @date 2021-01-16
 */
@Service
public class TsbzJyyqtkhServiceImpl implements ITsbzJyyqtkhService {
    @Autowired
    private TsbzJyyqtkhMapper tsbzJyyqtkhMapper;

    /**
     * 查询其他考核（教研员）
     *
     * @param id 其他考核（教研员）ID
     * @return 其他考核（教研员）
     */
    @Override
    public TsbzJyyqtkh selectTsbzJyyqtkhById(Long id) {
        return tsbzJyyqtkhMapper.selectTsbzJyyqtkhById(id);
    }

    /**
     * 查询其他考核（教研员）列表
     *
     * @param tsbzJyyqtkh 其他考核（教研员）
     * @return 其他考核（教研员）
     */
    @Override
    public List<TsbzJyyqtkh> selectTsbzJyyqtkhList(TsbzJyyqtkh tsbzJyyqtkh) {
        return tsbzJyyqtkhMapper.selectTsbzJyyqtkhList(tsbzJyyqtkh);
    }

    /**
     * 新增其他考核（教研员）
     *
     * @param tsbzJyyqtkh 其他考核（教研员）
     * @return 结果
     */
    @Override
    public int insertTsbzJyyqtkh(TsbzJyyqtkh tsbzJyyqtkh) {
        tsbzJyyqtkh.setCreateTime(DateUtils.getNowDate());
        return tsbzJyyqtkhMapper.insertTsbzJyyqtkh(tsbzJyyqtkh);
    }

    /**
     * 修改其他考核（教研员）
     *
     * @param tsbzJyyqtkh 其他考核（教研员）
     * @return 结果
     */
    @Override
    public int updateTsbzJyyqtkh(TsbzJyyqtkh tsbzJyyqtkh) {
        return tsbzJyyqtkhMapper.updateTsbzJyyqtkh(tsbzJyyqtkh);
    }

    /**
     * 批量删除其他考核（教研员）
     *
     * @param ids 需要删除的其他考核（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyqtkhByIds(Long[] ids) {
        return tsbzJyyqtkhMapper.deleteTsbzJyyqtkhByIds(ids);
    }

    /**
     * 删除其他考核（教研员）信息
     *
     * @param id 其他考核（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyqtkhById(Long id) {
        return tsbzJyyqtkhMapper.deleteTsbzJyyqtkhById(id);
    }
}
