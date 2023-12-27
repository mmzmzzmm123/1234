package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.InstallmentpaymentMapper;
import com.ruoyi.system.domain.Installmentpayment;
import com.ruoyi.system.service.IInstallmentpaymentService;

/**
 * 查询Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
@Service
public class InstallmentpaymentServiceImpl implements IInstallmentpaymentService 
{
    @Autowired
    private InstallmentpaymentMapper installmentpaymentMapper;

    /**
     * 查询查询
     * 
     * @param id 查询主键
     * @return 查询
     */
    @Override
    public Installmentpayment selectInstallmentpaymentById(Long id)
    {
        return installmentpaymentMapper.selectInstallmentpaymentById(id);
    }

    /**
     * 查询查询列表
     * 
     * @param installmentpayment 查询
     * @return 查询
     */
    @Override
    public List<Installmentpayment> selectInstallmentpaymentList(Installmentpayment installmentpayment)
    {
        System.out.println(installmentpayment);
        return installmentpaymentMapper.selectInstallmentpaymentList(installmentpayment);
    }

    /**
     * 新增查询
     * 
     * @param installmentpayment 查询
     * @return 结果
     */
    @Override
    public int insertInstallmentpayment(Installmentpayment installmentpayment)
    {
        return installmentpaymentMapper.insertInstallmentpayment(installmentpayment);
    }

    /**
     * 修改查询
     * 
     * @param installmentpayment 查询
     * @return 结果
     */
    @Override
    public int updateInstallmentpayment(Installmentpayment installmentpayment)
    {
        return installmentpaymentMapper.updateInstallmentpayment(installmentpayment);
    }

    /**
     * 批量删除查询
     * 
     * @param ids 需要删除的查询主键
     * @return 结果
     */
    @Override
    public int deleteInstallmentpaymentByIds(Long[] ids)
    {
        return installmentpaymentMapper.deleteInstallmentpaymentByIds(ids);
    }

    /**
     * 删除查询信息
     * 
     * @param id 查询主键
     * @return 结果
     */
    @Override
    public int deleteInstallmentpaymentById(Long id)
    {
        return installmentpaymentMapper.deleteInstallmentpaymentById(id);
    }

    @Override
    public List<Installmentpayment> selectsrchecksheetId(Long id) {
        return installmentpaymentMapper.selectsrchecksheetId(id);
    }
}
