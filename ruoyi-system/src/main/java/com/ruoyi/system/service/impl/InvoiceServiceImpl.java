package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.InvoiceMapper;
import com.ruoyi.system.domain.Invoice;
import com.ruoyi.system.service.IInvoiceService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-24
 */
@Service
public class InvoiceServiceImpl implements IInvoiceService 
{
    @Autowired
    private InvoiceMapper invoiceMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Invoice selectInvoiceById(Integer id)
    {
        return invoiceMapper.selectInvoiceById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param invoice 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Invoice> selectInvoiceList(Invoice invoice)
    {
        Long userId = SecurityUtils.getUserId();
        if (userId!=1L&&userId!=100L){
            invoice.setDeptId(Math.toIntExact(SecurityUtils.getDeptId()));
        }
        return invoiceMapper.selectInvoiceList(invoice);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param invoice 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertInvoice(Invoice invoice)
    {
        String stoId = invoice.getStoId();
        Invoice invoice1 = invoiceMapper.selectInvoiceById(Integer.valueOf(stoId));
        Long userId = SecurityUtils.getUserId();
        invoice.setUserId(userId);
        if (userId!=1L&&userId!=100L){
            invoice.setDeptId(Math.toIntExact(SecurityUtils.getDeptId()));
        }
        invoice.setInvoiceDate(new Date());
        if (invoice1!=null){
            return invoiceMapper.updateInvoice(invoice);
        }else {
            return invoiceMapper.insertInvoice(invoice);
        }
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param invoice 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateInvoice(Invoice invoice)
    {
        return invoiceMapper.updateInvoice(invoice);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceByIds(Integer[] ids)
    {
        return invoiceMapper.deleteInvoiceByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteInvoiceById(Integer id)
    {
        return invoiceMapper.deleteInvoiceById(id);
    }
}
