package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.BusZdjrzbry;

/**
 * 重大节日值班人员Mapper接口
 * 
 * @author yaowei
 * @date 2021-02-24
 */
public interface BusZdjrzbryMapper 
{
    /**
     * 查询重大节日值班人员
     * 
     * @param id 重大节日值班人员ID
     * @return 重大节日值班人员
     */
    public BusZdjrzbry selectBusZdjrzbryById(Long id);

    /**
     * 查询重大节日值班人员列表
     * 
     * @param busZdjrzbry 重大节日值班人员
     * @return 重大节日值班人员集合
     */
    public List<BusZdjrzbry> selectBusZdjrzbryList(BusZdjrzbry busZdjrzbry);

    /**
     * 新增重大节日值班人员
     * 
     * @param busZdjrzbry 重大节日值班人员
     * @return 结果
     */
    public int insertBusZdjrzbry(BusZdjrzbry busZdjrzbry);

    /**
     * 修改重大节日值班人员
     * 
     * @param busZdjrzbry 重大节日值班人员
     * @return 结果
     */
    public int updateBusZdjrzbry(BusZdjrzbry busZdjrzbry);

    /**
     * 删除重大节日值班人员
     * 
     * @param id 重大节日值班人员ID
     * @return 结果
     */
    public int deleteBusZdjrzbryById(Long id);

    /**
     * 批量删除重大节日值班人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusZdjrzbryByIds(Long[] ids);
}
