package com.ruoyi.jxzxkhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxzxkhgl.mapper.TsbzJxzxkhshMapper;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhsh;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhshService;

/**
 * 考核审核过程Service业务层处理
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@Service
public class TsbzJxzxkhshServiceImpl implements ITsbzJxzxkhshService {
    @Autowired
    private TsbzJxzxkhshMapper tsbzJxzxkhshMapper;

    /**
     * 查询考核审核过程
     *
     * @param id 考核审核过程ID
     * @return 考核审核过程
     */
    @Override
    public TsbzJxzxkhsh selectTsbzJzxzkhshById(Long id) {
        return tsbzJxzxkhshMapper.selectTsbzJzxzkhshById(id);
    }

    /**
     * 查询考核审核过程列表
     *
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 考核审核过程
     */
    @Override
    public List<TsbzJxzxkhsh> selectTsbzJzxzkhshList(TsbzJxzxkhsh tsbzJxzxkhsh) {
        return tsbzJxzxkhshMapper.selectTsbzJzxzkhshList(tsbzJxzxkhsh);
    }

    /**
     * 新增考核审核过程
     *
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 结果
     */
    @Override
    public int insertTsbzJzxzkhsh(TsbzJxzxkhsh tsbzJxzxkhsh) {
        tsbzJxzxkhsh.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxkhshMapper.insertTsbzJzxzkhsh(tsbzJxzxkhsh);
    }

    /**
     * 修改考核审核过程
     *
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 结果
     */
    @Override
    public int updateTsbzJzxzkhsh(TsbzJxzxkhsh tsbzJxzxkhsh) {
        return tsbzJxzxkhshMapper.updateTsbzJzxzkhsh(tsbzJxzxkhsh);
    }

    /**
     * 批量删除考核审核过程
     *
     * @param ids 需要删除的考核审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJzxzkhshByIds(Long[] ids) {
        return tsbzJxzxkhshMapper.deleteTsbzJzxzkhshByIds(ids);
    }

    /**
     * 删除考核审核过程信息
     *
     * @param id 考核审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJzxzkhshById(Long id) {
        return tsbzJxzxkhshMapper.deleteTsbzJzxzkhshById(id);
    }
}
