package com.ruoyi.pay.mapper;

import java.util.List;
import com.ruoyi.pay.domain.SysPay;

/**
 * 支付相关Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-29
 */
public interface SysPayMapper 
{
    /**
     * 查询支付相关
     * 
     * @param id 支付相关主键
     * @return 支付相关
     */
    public SysPay selectSysPayById(Long id);

    /**
     * 查询支付相关列表
     * 
     * @param sysPay 支付相关
     * @return 支付相关集合
     */
    public List<SysPay> selectSysPayList(SysPay sysPay);

    /**
     * 新增支付相关
     * 
     * @param sysPay 支付相关
     * @return 结果
     */
    public int insertSysPay(SysPay sysPay);

    /**
     * 修改支付相关
     * 
     * @param sysPay 支付相关
     * @return 结果
     */
    public int updateSysPay(SysPay sysPay);

    /**
     * 删除支付相关
     * 
     * @param id 支付相关主键
     * @return 结果
     */
    public int deleteSysPayById(Long id);

    /**
     * 批量删除支付相关
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysPayByIds(Long[] ids);
}
