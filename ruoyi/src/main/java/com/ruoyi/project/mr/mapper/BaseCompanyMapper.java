package com.ruoyi.project.mr.mapper;

import com.ruoyi.project.mr.domain.BaseCompany;
import java.util.List;

/**
 * 公司管理Mapper接口
 * 
 * @author mr
 * @date 2020-01-04
 */
public interface BaseCompanyMapper 
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
     * 删除公司管理
     * 
     * @param id 公司管理ID
     * @return 结果
     */
    public int deleteBaseCompanyById(String id);

    /**
     * 批量删除公司管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBaseCompanyByIds(String[] ids);
}
