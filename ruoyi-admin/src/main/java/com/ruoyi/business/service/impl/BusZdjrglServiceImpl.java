package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.BusZdjrglMapper;
import com.ruoyi.business.domain.BusZdjrgl;
import com.ruoyi.business.service.IBusZdjrglService;

/**
 * 重大节日管理Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-24
 */
@Service
public class BusZdjrglServiceImpl implements IBusZdjrglService 
{
    @Autowired
    private BusZdjrglMapper busZdjrglMapper;

    /**
     * 查询重大节日管理
     * 
     * @param id 重大节日管理ID
     * @return 重大节日管理
     */
    @Override
    public BusZdjrgl selectBusZdjrglById(String id)
    {
        return busZdjrglMapper.selectBusZdjrglById(id);
    }

    /**
     * 查询重大节日管理列表
     * 
     * @param busZdjrgl 重大节日管理
     * @return 重大节日管理
     */
    @Override
    public List<BusZdjrgl> selectBusZdjrglList(BusZdjrgl busZdjrgl)
    {
        return busZdjrglMapper.selectBusZdjrglList(busZdjrgl);
    }

    /**
     * 新增重大节日管理
     * 
     * @param busZdjrgl 重大节日管理
     * @return 结果
     */
    @Override
    public int insertBusZdjrgl(BusZdjrgl busZdjrgl)
    {
        return busZdjrglMapper.insertBusZdjrgl(busZdjrgl);
    }

    /**
     * 修改重大节日管理
     * 
     * @param busZdjrgl 重大节日管理
     * @return 结果
     */
    @Override
    public int updateBusZdjrgl(BusZdjrgl busZdjrgl)
    {
        return busZdjrglMapper.updateBusZdjrgl(busZdjrgl);
    }

    /**
     * 批量删除重大节日管理
     * 
     * @param ids 需要删除的重大节日管理ID
     * @return 结果
     */
    @Override
    public int deleteBusZdjrglByIds(String[] ids)
    {
        return busZdjrglMapper.deleteBusZdjrglByIds(ids);
    }

    /**
     * 删除重大节日管理信息
     * 
     * @param id 重大节日管理ID
     * @return 结果
     */
    @Override
    public int deleteBusZdjrglById(String id)
    {
        return busZdjrglMapper.deleteBusZdjrglById(id);
    }
}
