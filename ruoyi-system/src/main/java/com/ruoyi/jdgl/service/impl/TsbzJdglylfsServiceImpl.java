package com.ruoyi.jdgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jdgl.mapper.TsbzJdglylfsMapper;
import com.ruoyi.jdgl.domain.TsbzJdglylfs;
import com.ruoyi.jdgl.service.ITsbzJdglylfsService;

/**
 * 基地管理引领辐射Service业务层处理
 * 
 * @author tsbz
 * @date 2021-05-17
 */
@Service
public class TsbzJdglylfsServiceImpl implements ITsbzJdglylfsService 
{
    @Autowired
    private TsbzJdglylfsMapper tsbzJdglylfsMapper;

    /**
     * 查询基地管理引领辐射
     * 
     * @param id 基地管理引领辐射ID
     * @return 基地管理引领辐射
     */
    @Override
    public TsbzJdglylfs selectTsbzJdglylfsById(Long id)
    {
        return tsbzJdglylfsMapper.selectTsbzJdglylfsById(id);
    }

    /**
     * 查询基地管理引领辐射列表
     * 
     * @param tsbzJdglylfs 基地管理引领辐射
     * @return 基地管理引领辐射
     */
    @Override
    public List<TsbzJdglylfs> selectTsbzJdglylfsList(TsbzJdglylfs tsbzJdglylfs)
    {
        return tsbzJdglylfsMapper.selectTsbzJdglylfsList(tsbzJdglylfs);
    }

    /**
     * 新增基地管理引领辐射
     * 
     * @param tsbzJdglylfs 基地管理引领辐射
     * @return 结果
     */
    @Override
    public int insertTsbzJdglylfs(TsbzJdglylfs tsbzJdglylfs)
    {
        tsbzJdglylfs.setCreateTime(DateUtils.getNowDate());
        return tsbzJdglylfsMapper.insertTsbzJdglylfs(tsbzJdglylfs);
    }

    /**
     * 修改基地管理引领辐射
     * 
     * @param tsbzJdglylfs 基地管理引领辐射
     * @return 结果
     */
    @Override
    public int updateTsbzJdglylfs(TsbzJdglylfs tsbzJdglylfs)
    {
        return tsbzJdglylfsMapper.updateTsbzJdglylfs(tsbzJdglylfs);
    }

    /**
     * 批量删除基地管理引领辐射
     * 
     * @param ids 需要删除的基地管理引领辐射ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdglylfsByIds(Long[] ids)
    {
        return tsbzJdglylfsMapper.deleteTsbzJdglylfsByIds(ids);
    }

    /**
     * 删除基地管理引领辐射信息
     * 
     * @param id 基地管理引领辐射ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdglylfsById(Long id)
    {
        return tsbzJdglylfsMapper.deleteTsbzJdglylfsById(id);
    }
}
