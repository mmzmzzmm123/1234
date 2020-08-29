package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJzxzkhshMapper;
import com.ruoyi.jxjs.domain.TsbzJzxzkhsh;
import com.ruoyi.jxjs.service.ITsbzJzxzkhshService;

/**
 * 考核审核过程Service业务层处理
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@Service
public class TsbzJzxzkhshServiceImpl implements ITsbzJzxzkhshService {
    @Autowired
    private TsbzJzxzkhshMapper tsbzJzxzkhshMapper;

    /**
     * 查询考核审核过程
     *
     * @param id 考核审核过程ID
     * @return 考核审核过程
     */
    @Override
    public TsbzJzxzkhsh selectTsbzJzxzkhshById(Long id) {
        return tsbzJzxzkhshMapper.selectTsbzJzxzkhshById(id);
    }

    /**
     * 查询考核审核过程列表
     *
     * @param tsbzJzxzkhsh 考核审核过程
     * @return 考核审核过程
     */
    @Override
    public List<TsbzJzxzkhsh> selectTsbzJzxzkhshList(TsbzJzxzkhsh tsbzJzxzkhsh) {
        return tsbzJzxzkhshMapper.selectTsbzJzxzkhshList(tsbzJzxzkhsh);
    }

    /**
     * 新增考核审核过程
     *
     * @param tsbzJzxzkhsh 考核审核过程
     * @return 结果
     */
    @Override
    public int insertTsbzJzxzkhsh(TsbzJzxzkhsh tsbzJzxzkhsh) {
        tsbzJzxzkhsh.setCreateTime(DateUtils.getNowDate());
        return tsbzJzxzkhshMapper.insertTsbzJzxzkhsh(tsbzJzxzkhsh);
    }

    /**
     * 修改考核审核过程
     *
     * @param tsbzJzxzkhsh 考核审核过程
     * @return 结果
     */
    @Override
    public int updateTsbzJzxzkhsh(TsbzJzxzkhsh tsbzJzxzkhsh) {
        return tsbzJzxzkhshMapper.updateTsbzJzxzkhsh(tsbzJzxzkhsh);
    }

    /**
     * 批量删除考核审核过程
     *
     * @param ids 需要删除的考核审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJzxzkhshByIds(Long[] ids) {
        return tsbzJzxzkhshMapper.deleteTsbzJzxzkhshByIds(ids);
    }

    /**
     * 删除考核审核过程信息
     *
     * @param id 考核审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJzxzkhshById(Long id) {
        return tsbzJzxzkhshMapper.deleteTsbzJzxzkhshById(id);
    }
}
