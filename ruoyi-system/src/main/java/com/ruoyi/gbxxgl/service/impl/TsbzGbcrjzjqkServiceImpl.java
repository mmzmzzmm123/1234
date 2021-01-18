package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbcrjzjqkMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbcrjzjqk;
import com.ruoyi.gbxxgl.service.ITsbzGbcrjzjqkService;

/**
 * 干部出入境证件情况Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-21
 */
@Service
public class TsbzGbcrjzjqkServiceImpl implements ITsbzGbcrjzjqkService 
{
    @Autowired
    private TsbzGbcrjzjqkMapper tsbzGbcrjzjqkMapper;

    /**
     * 查询干部出入境证件情况
     * 
     * @param id 干部出入境证件情况ID
     * @return 干部出入境证件情况
     */
    @Override
    public TsbzGbcrjzjqk selectTsbzGbcrjzjqkById(Long id)
    {
        return tsbzGbcrjzjqkMapper.selectTsbzGbcrjzjqkById(id);
    }

    /**
     * 查询干部出入境证件情况列表
     * 
     * @param tsbzGbcrjzjqk 干部出入境证件情况
     * @return 干部出入境证件情况
     */
    @Override
    @DataScope(deptAlias = "b", userAlias = "b")
    public List<TsbzGbcrjzjqk> selectTsbzGbcrjzjqkList(TsbzGbcrjzjqk tsbzGbcrjzjqk)
    {
        return tsbzGbcrjzjqkMapper.selectTsbzGbcrjzjqkList(tsbzGbcrjzjqk);
    }

    /**
     * 新增干部出入境证件情况
     * 
     * @param tsbzGbcrjzjqk 干部出入境证件情况
     * @return 结果
     */
    @Override
    public int insertTsbzGbcrjzjqk(TsbzGbcrjzjqk tsbzGbcrjzjqk)
    {
        tsbzGbcrjzjqk.setCreateTime(DateUtils.getNowDate());
        return tsbzGbcrjzjqkMapper.insertTsbzGbcrjzjqk(tsbzGbcrjzjqk);
    }

    /**
     * 修改干部出入境证件情况
     * 
     * @param tsbzGbcrjzjqk 干部出入境证件情况
     * @return 结果
     */
    @Override
    public int updateTsbzGbcrjzjqk(TsbzGbcrjzjqk tsbzGbcrjzjqk)
    {
        return tsbzGbcrjzjqkMapper.updateTsbzGbcrjzjqk(tsbzGbcrjzjqk);
    }

    /**
     * 批量删除干部出入境证件情况
     * 
     * @param ids 需要删除的干部出入境证件情况ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbcrjzjqkByIds(Long[] ids)
    {
        return tsbzGbcrjzjqkMapper.deleteTsbzGbcrjzjqkByIds(ids);
    }

    /**
     * 删除干部出入境证件情况信息
     * 
     * @param id 干部出入境证件情况ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbcrjzjqkById(Long id)
    {
        return tsbzGbcrjzjqkMapper.deleteTsbzGbcrjzjqkById(id);
    }
}
