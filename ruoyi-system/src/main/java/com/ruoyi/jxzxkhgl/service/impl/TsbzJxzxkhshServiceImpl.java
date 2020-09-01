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
 * @date 2020-09-01
 */
@Service
public class TsbzJxzxkhshServiceImpl implements ITsbzJxzxkhshService 
{
    @Autowired
    private TsbzJxzxkhshMapper tsbzJxzxkhshMapper;

    /**
     * 查询考核审核过程
     * 
     * @param id 考核审核过程ID
     * @return 考核审核过程
     */
    @Override
    public TsbzJxzxkhsh selectTsbzJxzxkhshById(Long id)
    {
        return tsbzJxzxkhshMapper.selectTsbzJxzxkhshById(id);
    }

    /**
     * 查询考核审核过程列表
     * 
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 考核审核过程
     */
    @Override
    public List<TsbzJxzxkhsh> selectTsbzJxzxkhshList(TsbzJxzxkhsh tsbzJxzxkhsh)
    {
        return tsbzJxzxkhshMapper.selectTsbzJxzxkhshList(tsbzJxzxkhsh);
    }

    /**
     * 新增考核审核过程
     * 
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxkhsh(TsbzJxzxkhsh tsbzJxzxkhsh)
    {
        tsbzJxzxkhsh.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxkhshMapper.insertTsbzJxzxkhsh(tsbzJxzxkhsh);
    }

    /**
     * 修改考核审核过程
     * 
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxkhsh(TsbzJxzxkhsh tsbzJxzxkhsh)
    {
        return tsbzJxzxkhshMapper.updateTsbzJxzxkhsh(tsbzJxzxkhsh);
    }

    /**
     * 批量删除考核审核过程
     * 
     * @param ids 需要删除的考核审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhshByIds(Long[] ids)
    {
        return tsbzJxzxkhshMapper.deleteTsbzJxzxkhshByIds(ids);
    }

    /**
     * 删除考核审核过程信息
     * 
     * @param id 考核审核过程ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhshById(Long id)
    {
        return tsbzJxzxkhshMapper.deleteTsbzJxzxkhshById(id);
    }
}
