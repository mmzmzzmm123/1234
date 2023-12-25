package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Installmentpayment;

/**
 * 查询Service接口
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
public interface IInstallmentpaymentService 
{
    /**
     * 查询查询
     * 
     * @param id 查询主键
     * @return 查询
     */
    public Installmentpayment selectInstallmentpaymentById(Long id);

    /**
     * 查询查询列表
     * 
     * @param installmentpayment 查询
     * @return 查询集合
     */
    public List<Installmentpayment> selectInstallmentpaymentList(Installmentpayment installmentpayment);

    /**
     * 新增查询
     * 
     * @param installmentpayment 查询
     * @return 结果
     */
    public int insertInstallmentpayment(Installmentpayment installmentpayment);

    /**
     * 修改查询
     * 
     * @param installmentpayment 查询
     * @return 结果
     */
    public int updateInstallmentpayment(Installmentpayment installmentpayment);

    /**
     * 批量删除查询
     * 
     * @param ids 需要删除的查询主键集合
     * @return 结果
     */
    public int deleteInstallmentpaymentByIds(Long[] ids);

    /**
     * 删除查询信息
     * 
     * @param id 查询主键
     * @return 结果
     */
    public int deleteInstallmentpaymentById(Long id);

    public List<Installmentpayment> selectsrchecksheetId(Long id);
}
