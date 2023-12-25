package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Invoice;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-12-24
 */
public interface IInvoiceService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Invoice selectInvoiceById(Integer id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param invoice 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Invoice> selectInvoiceList(Invoice invoice);

    /**
     * 新增【请填写功能名称】
     * 
     * @param invoice 【请填写功能名称】
     * @return 结果
     */
    public int insertInvoice(Invoice invoice);

    /**
     * 修改【请填写功能名称】
     * 
     * @param invoice 【请填写功能名称】
     * @return 结果
     */
    public int updateInvoice(Invoice invoice);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteInvoiceByIds(Integer[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteInvoiceById(Integer id);
}
