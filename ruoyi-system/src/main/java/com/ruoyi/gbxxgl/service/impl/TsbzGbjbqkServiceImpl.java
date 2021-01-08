package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbjbqkMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbjbqk;
import com.ruoyi.gbxxgl.service.ITsbzGbjbqkService;

/**
 * 干部基本情况Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-02
 */
@Service
public class TsbzGbjbqkServiceImpl implements ITsbzGbjbqkService 
{
    @Autowired
    private TsbzGbjbqkMapper tsbzGbjbqkMapper;

    /**
     * 查询干部基本情况
     * 
     * @param id 干部基本情况ID
     * @return 干部基本情况
     */
    @Override
    public TsbzGbjbqk selectTsbzGbjbqkById(Long id)
    {
        return tsbzGbjbqkMapper.selectTsbzGbjbqkById(id);
    }

    /**
     * 查询干部基本情况列表
     * 
     * @param tsbzGbjbqk 干部基本情况
     * @return 干部基本情况
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<TsbzGbjbqk> selectTsbzGbjbqkList(TsbzGbjbqk tsbzGbjbqk)
    {
        return tsbzGbjbqkMapper.selectTsbzGbjbqkList(tsbzGbjbqk);
    }

    /**
     * 新增干部基本情况
     * 
     * @param tsbzGbjbqk 干部基本情况
     * @return 结果
     */
    @Override
    public int insertTsbzGbjbqk(TsbzGbjbqk tsbzGbjbqk)
    {
        return tsbzGbjbqkMapper.insertTsbzGbjbqk(tsbzGbjbqk);
    }

    /**
     * 修改干部基本情况
     * 
     * @param tsbzGbjbqk 干部基本情况
     * @return 结果
     */
    @Override
    public int updateTsbzGbjbqk(TsbzGbjbqk tsbzGbjbqk)
    {
        return tsbzGbjbqkMapper.updateTsbzGbjbqk(tsbzGbjbqk);
    }

    /**
     * 批量删除干部基本情况
     * 
     * @param ids 需要删除的干部基本情况ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbjbqkByIds(Long[] ids)
    {
        return tsbzGbjbqkMapper.deleteTsbzGbjbqkByIds(ids);
    }

    /**
     * 删除干部基本情况信息
     * 
     * @param id 干部基本情况ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbjbqkById(Long id)
    {
        return tsbzGbjbqkMapper.deleteTsbzGbjbqkById(id);
    }
}
