package com.ruoyi.jdgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jdgl.mapper.TsbzJdglzwfzMapper;
import com.ruoyi.jdgl.domain.TsbzJdglzwfz;
import com.ruoyi.jdgl.service.ITsbzJdglzwfzService;

/**
 * 基地管理自我发展Service业务层处理
 * 
 * @author tsbz
 * @date 2021-05-17
 */
@Service
public class TsbzJdglzwfzServiceImpl implements ITsbzJdglzwfzService 
{
    @Autowired
    private TsbzJdglzwfzMapper tsbzJdglzwfzMapper;

    /**
     * 查询基地管理自我发展
     * 
     * @param id 基地管理自我发展ID
     * @return 基地管理自我发展
     */
    @Override
    public TsbzJdglzwfz selectTsbzJdglzwfzById(Long id)
    {
        return tsbzJdglzwfzMapper.selectTsbzJdglzwfzById(id);
    }

    /**
     * 查询基地管理自我发展列表
     * 
     * @param tsbzJdglzwfz 基地管理自我发展
     * @return 基地管理自我发展
     */
    @Override
    public List<TsbzJdglzwfz> selectTsbzJdglzwfzList(TsbzJdglzwfz tsbzJdglzwfz)
    {
        return tsbzJdglzwfzMapper.selectTsbzJdglzwfzList(tsbzJdglzwfz);
    }

    /**
     * 新增基地管理自我发展
     * 
     * @param tsbzJdglzwfz 基地管理自我发展
     * @return 结果
     */
    @Override
    public int insertTsbzJdglzwfz(TsbzJdglzwfz tsbzJdglzwfz)
    {
        tsbzJdglzwfz.setCreateTime(DateUtils.getNowDate());
        return tsbzJdglzwfzMapper.insertTsbzJdglzwfz(tsbzJdglzwfz);
    }

    /**
     * 修改基地管理自我发展
     * 
     * @param tsbzJdglzwfz 基地管理自我发展
     * @return 结果
     */
    @Override
    public int updateTsbzJdglzwfz(TsbzJdglzwfz tsbzJdglzwfz)
    {
        return tsbzJdglzwfzMapper.updateTsbzJdglzwfz(tsbzJdglzwfz);
    }

    /**
     * 批量删除基地管理自我发展
     * 
     * @param ids 需要删除的基地管理自我发展ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdglzwfzByIds(Long[] ids)
    {
        return tsbzJdglzwfzMapper.deleteTsbzJdglzwfzByIds(ids);
    }

    /**
     * 删除基地管理自我发展信息
     * 
     * @param id 基地管理自我发展ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdglzwfzById(Long id)
    {
        return tsbzJdglzwfzMapper.deleteTsbzJdglzwfzById(id);
    }
}
