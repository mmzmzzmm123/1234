package com.ruoyi.gbxxgl.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbxrzwMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbxrzw;
import com.ruoyi.gbxxgl.service.ITsbzGbxrzwService;

/**
 * 干部任职情况-现任职务Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-07
 */
@Service
public class TsbzGbxrzwServiceImpl implements ITsbzGbxrzwService 
{
    @Autowired
    private TsbzGbxrzwMapper tsbzGbxrzwMapper;

    /**
     * 查询干部任职情况-现任职务
     * 
     * @param id 干部任职情况-现任职务ID
     * @return 干部任职情况-现任职务
     */
    @Override
    public TsbzGbxrzw selectTsbzGbxrzwById(Long id)
    {
        return tsbzGbxrzwMapper.selectTsbzGbxrzwById(id);
    }

    /**
     * 查询干部任职情况-现任职务列表
     * 
     * @param tsbzGbxrzw 干部任职情况-现任职务
     * @return 干部任职情况-现任职务
     */
    @Override
    public List<TsbzGbxrzw> selectTsbzGbxrzwList(TsbzGbxrzw tsbzGbxrzw)
    {
        return tsbzGbxrzwMapper.selectTsbzGbxrzwList(tsbzGbxrzw);
    }

    /**
     * 新增干部任职情况-现任职务
     * 
     * @param tsbzGbxrzw 干部任职情况-现任职务
     * @return 结果
     */
    @Override
    public int insertTsbzGbxrzw(TsbzGbxrzw tsbzGbxrzw)
    {
        return tsbzGbxrzwMapper.insertTsbzGbxrzw(tsbzGbxrzw);
    }

    /**
     * 修改干部任职情况-现任职务
     * 
     * @param tsbzGbxrzw 干部任职情况-现任职务
     * @return 结果
     */
    @Override
    public int updateTsbzGbxrzw(TsbzGbxrzw tsbzGbxrzw)
    {
        return tsbzGbxrzwMapper.updateTsbzGbxrzw(tsbzGbxrzw);
    }

    /**
     * 批量删除干部任职情况-现任职务
     * 
     * @param ids 需要删除的干部任职情况-现任职务ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbxrzwByIds(Long[] ids)
    {
        return tsbzGbxrzwMapper.deleteTsbzGbxrzwByIds(ids);
    }

    /**
     * 删除干部任职情况-现任职务信息
     * 
     * @param id 干部任职情况-现任职务ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbxrzwById(Long id)
    {
        return tsbzGbxrzwMapper.deleteTsbzGbxrzwById(id);
    }
}
