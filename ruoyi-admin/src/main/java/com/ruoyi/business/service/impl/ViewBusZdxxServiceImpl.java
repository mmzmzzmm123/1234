package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.ViewBusZdxxMapper;
import com.ruoyi.business.domain.ViewBusZdxx;
import com.ruoyi.business.service.IViewBusZdxxService;

/**
 * VIEWService业务层处理
 * 
 * @author yaowei
 * @date 2021-02-22
 */
@Service
public class ViewBusZdxxServiceImpl implements IViewBusZdxxService 
{
    @Autowired
    private ViewBusZdxxMapper viewBusZdxxMapper;

    /**
     * 查询VIEW
     * 
     * @param id VIEWID
     * @return VIEW
     */
    @Override
    public ViewBusZdxx selectViewBusZdxxById(Long id)
    {
        return viewBusZdxxMapper.selectViewBusZdxxById(id);
    }

    /**
     * 查询VIEW列表
     * 
     * @param viewBusZdxx VIEW
     * @return VIEW
     */
    @Override
    public List<ViewBusZdxx> selectViewBusZdxxList(ViewBusZdxx viewBusZdxx)
    {
        return viewBusZdxxMapper.selectViewBusZdxxList(viewBusZdxx);
    }

    /**
     * 新增VIEW
     * 
     * @param viewBusZdxx VIEW
     * @return 结果
     */
    @Override
    public int insertViewBusZdxx(ViewBusZdxx viewBusZdxx)
    {
        return viewBusZdxxMapper.insertViewBusZdxx(viewBusZdxx);
    }

    /**
     * 修改VIEW
     * 
     * @param viewBusZdxx VIEW
     * @return 结果
     */
    @Override
    public int updateViewBusZdxx(ViewBusZdxx viewBusZdxx)
    {
        return viewBusZdxxMapper.updateViewBusZdxx(viewBusZdxx);
    }

    /**
     * 批量删除VIEW
     * 
     * @param ids 需要删除的VIEWID
     * @return 结果
     */
    @Override
    public int deleteViewBusZdxxByIds(Long[] ids)
    {
        return viewBusZdxxMapper.deleteViewBusZdxxByIds(ids);
    }

    /**
     * 删除VIEW信息
     * 
     * @param id VIEWID
     * @return 结果
     */
    @Override
    public int deleteViewBusZdxxById(Long id)
    {
        return viewBusZdxxMapper.deleteViewBusZdxxById(id);
    }
}
