package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.ViewBusZdxx;

/**
 * VIEWMapper接口
 * 
 * @author yaowei
 * @date 2021-02-22
 */
public interface ViewBusZdxxMapper 
{
    /**
     * 查询VIEW
     * 
     * @param id VIEWID
     * @return VIEW
     */
    public ViewBusZdxx selectViewBusZdxxById(Long id);

    /**
     * 查询VIEW列表
     * 
     * @param viewBusZdxx VIEW
     * @return VIEW集合
     */
    public List<ViewBusZdxx> selectViewBusZdxxList(ViewBusZdxx viewBusZdxx);

    /**
     * 新增VIEW
     * 
     * @param viewBusZdxx VIEW
     * @return 结果
     */
    public int insertViewBusZdxx(ViewBusZdxx viewBusZdxx);

    /**
     * 修改VIEW
     * 
     * @param viewBusZdxx VIEW
     * @return 结果
     */
    public int updateViewBusZdxx(ViewBusZdxx viewBusZdxx);

    /**
     * 删除VIEW
     * 
     * @param id VIEWID
     * @return 结果
     */
    public int deleteViewBusZdxxById(Long id);

    /**
     * 批量删除VIEW
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteViewBusZdxxByIds(Long[] ids);
}
