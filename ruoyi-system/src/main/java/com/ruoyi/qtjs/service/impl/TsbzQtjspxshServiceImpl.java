package com.ruoyi.qtjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjs.mapper.TsbzQtjspxshMapper;
import com.ruoyi.qtjs.domain.TsbzQtjspxsh;
import com.ruoyi.qtjs.service.ITsbzQtjspxshService;

/**
 * 群体教师评选审核过程Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-16
 */
@Service
public class TsbzQtjspxshServiceImpl implements ITsbzQtjspxshService {
    @Autowired
    private TsbzQtjspxshMapper tsbzQtjspxshMapper;

    /**
     * 查询群体教师评选审核过程
     *
     * @param id 群体教师评选审核过程ID
     * @return 群体教师评选审核过程
     */
    @Override
    public TsbzQtjspxsh selectTsbzQtjspxshById(Long id) {
        return tsbzQtjspxshMapper.selectTsbzQtjspxshById(id);
    }

    /**
     * 查询群体教师评选审核过程列表
     *
     * @param tsbzQtjspxsh 群体教师评选审核过程
     * @return 群体教师评选审核过程
     */
    @Override
    public List<TsbzQtjspxsh> selectTsbzQtjspxshList(TsbzQtjspxsh tsbzQtjspxsh) {
        return tsbzQtjspxshMapper.selectTsbzQtjspxshList(tsbzQtjspxsh);
    }

    /**
     * 新增群体教师评选审核过程
     *
     * @param tsbzQtjspxsh 群体教师评选审核过程
     * @return 结果
     */
    @Override
    public int insertTsbzQtjspxsh(TsbzQtjspxsh tsbzQtjspxsh) {
        tsbzQtjspxsh.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjspxshMapper.insertTsbzQtjspxsh(tsbzQtjspxsh);
    }

    /**
     * 修改群体教师评选审核过程
     *
     * @param tsbzQtjspxsh 群体教师评选审核过程
     * @return 结果
     */
    @Override
    public int updateTsbzQtjspxsh(TsbzQtjspxsh tsbzQtjspxsh) {
        return tsbzQtjspxshMapper.updateTsbzQtjspxsh(tsbzQtjspxsh);
    }

    /**
     * 批量删除群体教师评选审核过程
     *
     * @param ids 需要删除的群体教师评选审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxshByIds(Long[] ids) {
        return tsbzQtjspxshMapper.deleteTsbzQtjspxshByIds(ids);
    }

    /**
     * 删除群体教师评选审核过程信息
     *
     * @param id 群体教师评选审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxshById(Long id) {
        return tsbzQtjspxshMapper.deleteTsbzQtjspxshById(id);
    }
}
