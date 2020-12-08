package com.ruoyi.gbxxgl.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbshzwMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbshzw;
import com.ruoyi.gbxxgl.service.ITsbzGbshzwService;

/**
 * 干部任职情况-社会职务Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-08
 */
@Service
public class TsbzGbshzwServiceImpl implements ITsbzGbshzwService 
{
    @Autowired
    private TsbzGbshzwMapper tsbzGbshzwMapper;

    /**
     * 查询干部任职情况-社会职务
     * 
     * @param id 干部任职情况-社会职务ID
     * @return 干部任职情况-社会职务
     */
    @Override
    public TsbzGbshzw selectTsbzGbshzwById(Long id)
    {
        return tsbzGbshzwMapper.selectTsbzGbshzwById(id);
    }

    /**
     * 查询干部任职情况-社会职务列表
     * 
     * @param tsbzGbshzw 干部任职情况-社会职务
     * @return 干部任职情况-社会职务
     */
    @Override
    public List<TsbzGbshzw> selectTsbzGbshzwList(TsbzGbshzw tsbzGbshzw)
    {
        return tsbzGbshzwMapper.selectTsbzGbshzwList(tsbzGbshzw);
    }

    /**
     * 新增干部任职情况-社会职务
     * 
     * @param tsbzGbshzw 干部任职情况-社会职务
     * @return 结果
     */
    @Override
    public int insertTsbzGbshzw(TsbzGbshzw tsbzGbshzw)
    {
        return tsbzGbshzwMapper.insertTsbzGbshzw(tsbzGbshzw);
    }

    /**
     * 修改干部任职情况-社会职务
     * 
     * @param tsbzGbshzw 干部任职情况-社会职务
     * @return 结果
     */
    @Override
    public int updateTsbzGbshzw(TsbzGbshzw tsbzGbshzw)
    {
        return tsbzGbshzwMapper.updateTsbzGbshzw(tsbzGbshzw);
    }

    /**
     * 批量删除干部任职情况-社会职务
     * 
     * @param ids 需要删除的干部任职情况-社会职务ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbshzwByIds(Long[] ids)
    {
        return tsbzGbshzwMapper.deleteTsbzGbshzwByIds(ids);
    }

    /**
     * 删除干部任职情况-社会职务信息
     * 
     * @param id 干部任职情况-社会职务ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbshzwById(Long id)
    {
        return tsbzGbshzwMapper.deleteTsbzGbshzwById(id);
    }
}
