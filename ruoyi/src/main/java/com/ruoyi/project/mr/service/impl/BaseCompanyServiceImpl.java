package com.ruoyi.project.mr.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.mr.domain.BaseCompany;
import com.ruoyi.project.mr.mapper.BaseCompanyMapper;
import com.ruoyi.project.mr.service.IBaseCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公司管理Service业务层处理
 *
 * @author mr
 * @date 2020-01-04
 */
@Service
public class BaseCompanyServiceImpl implements IBaseCompanyService
{
    @Autowired
    private BaseCompanyMapper baseCompanyMapper;

    /**
     * 查询公司管理
     *
     * @param id 公司管理ID
     * @return 公司管理
     */
    @Override
    public BaseCompany selectBaseCompanyById(String id)
    {
        return baseCompanyMapper.selectBaseCompanyById(id);
    }

    /**
     * 查询公司管理列表
     *
     * @param baseCompany 公司管理
     * @return 公司管理
     */
    @Override
    public List<BaseCompany> selectBaseCompanyList(BaseCompany baseCompany)
    {
        return baseCompanyMapper.selectBaseCompanyList(baseCompany);
    }

    /**
     * 新增公司管理
     *
     * @param baseCompany 公司管理
     * @return 结果
     */
    @Override
    public int insertBaseCompany(BaseCompany baseCompany)
    {
        baseCompany.setCreateTime(DateUtils.getNowDate());
        return baseCompanyMapper.insertBaseCompany(baseCompany);
    }

    /**
     * 修改公司管理
     *
     * @param baseCompany 公司管理
     * @return 结果
     */
    @Override
    public int updateBaseCompany(BaseCompany baseCompany)
    {
        baseCompany.setUpdateTime(DateUtils.getNowDate());
        return baseCompanyMapper.updateBaseCompany(baseCompany);
    }

    /**
     * 批量删除公司管理
     *
     * @param ids 需要删除的公司管理ID
     * @return 结果
     */
    @Override
    public int deleteBaseCompanyByIds(String[] ids)
    {
        return baseCompanyMapper.deleteBaseCompanyByIds(ids);
    }

    /**
     * 删除公司管理信息
     *
     * @param id 公司管理ID
     * @return 结果
     */
    @Override
    public int deleteBaseCompanyById(String id)
    {
        return baseCompanyMapper.deleteBaseCompanyById(id);
    }

    /**
     * 修改用户状态
     *
     * @param baseCompany 用户信息
     * @return 结果
     */
    @Override
    public int updateBaseCompanyEnabled(BaseCompany baseCompany)
    {
        return baseCompanyMapper.updateBaseCompany(baseCompany);
    }

}
