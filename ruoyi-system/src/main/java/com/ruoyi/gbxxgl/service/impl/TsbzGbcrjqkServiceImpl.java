package com.ruoyi.gbxxgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbcrjqkMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbcrjqk;
import com.ruoyi.gbxxgl.service.ITsbzGbcrjqkService;

/**
 * 干部出入境情况Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-08
 */
@Service
public class TsbzGbcrjqkServiceImpl implements ITsbzGbcrjqkService 
{
    @Autowired
    private TsbzGbcrjqkMapper tsbzGbcrjqkMapper;

    /**
     * 查询干部出入境情况
     * 
     * @param id 干部出入境情况ID
     * @return 干部出入境情况
     */
    @Override
    public TsbzGbcrjqk selectTsbzGbcrjqkById(Long id)
    {
        return tsbzGbcrjqkMapper.selectTsbzGbcrjqkById(id);
    }

    /**
     * 查询干部出入境情况列表
     * 
     * @param tsbzGbcrjqk 干部出入境情况
     * @return 干部出入境情况
     */
    @Override
    public List<TsbzGbcrjqk> selectTsbzGbcrjqkList(TsbzGbcrjqk tsbzGbcrjqk)
    {
        return tsbzGbcrjqkMapper.selectTsbzGbcrjqkList(tsbzGbcrjqk);
    }

    /**
     * 新增干部出入境情况
     * 
     * @param tsbzGbcrjqk 干部出入境情况
     * @return 结果
     */
    @Override
    public int insertTsbzGbcrjqk(TsbzGbcrjqk tsbzGbcrjqk)
    {
        tsbzGbcrjqk.setCreateTime(DateUtils.getNowDate());
        return tsbzGbcrjqkMapper.insertTsbzGbcrjqk(tsbzGbcrjqk);
    }

    /**
     * 修改干部出入境情况
     * 
     * @param tsbzGbcrjqk 干部出入境情况
     * @return 结果
     */
    @Override
    public int updateTsbzGbcrjqk(TsbzGbcrjqk tsbzGbcrjqk)
    {
        return tsbzGbcrjqkMapper.updateTsbzGbcrjqk(tsbzGbcrjqk);
    }

    /**
     * 批量删除干部出入境情况
     * 
     * @param ids 需要删除的干部出入境情况ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbcrjqkByIds(Long[] ids)
    {
        return tsbzGbcrjqkMapper.deleteTsbzGbcrjqkByIds(ids);
    }

    /**
     * 删除干部出入境情况信息
     * 
     * @param id 干部出入境情况ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbcrjqkById(Long id)
    {
        return tsbzGbcrjqkMapper.deleteTsbzGbcrjqkById(id);
    }
}
