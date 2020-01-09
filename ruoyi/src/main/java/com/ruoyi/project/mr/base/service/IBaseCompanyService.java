package com.ruoyi.project.mr.base.service;

import com.ruoyi.project.mr.base.domain.BaseCompany;
import java.util.List;

/**
 * 公司管理Service接口
 *
 * @author mr
 * @date 2020-01-04
 */
public interface IBaseCompanyService
{
    /**
     * 查询公司管理
     *
     * @param id 公司管理ID
     * @return 公司管理
     */
    public BaseCompany selectBaseCompanyById(String id);

    /**
     * 查询公司管理列表
     *
     * @param baseCompany 公司管理
     * @return 公司管理集合
     */
    public List<BaseCompany> selectBaseCompanyList(BaseCompany baseCompany);

    /**
     * 新增公司管理
     *
     * @param baseCompany 公司管理
     * @return 结果
     */
    public int insertBaseCompany(BaseCompany baseCompany);

    /**
     * 修改公司管理
     *
     * @param baseCompany 公司管理
     * @return 结果
     */
    public int updateBaseCompany(BaseCompany baseCompany);

    /**
     * 批量删除公司管理
     *
     * @param ids 需要删除的公司管理ID
     * @return 结果
     */
    public int deleteBaseCompanyByIds(String[] ids);

    /**
     * 删除公司管理信息
     *
     * @param id 公司管理ID
     * @return 结果
     */
    public int deleteBaseCompanyById(String id);

    /**
     * 修改公司状态
     *
     * @param baseCompany 公司信息
     * @return 结果
     */
    public int updateBaseCompanyEnabled(BaseCompany baseCompany);
}
