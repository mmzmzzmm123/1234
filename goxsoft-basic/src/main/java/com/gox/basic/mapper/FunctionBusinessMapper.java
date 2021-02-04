package com.gox.basic.mapper;

import com.gox.basic.domain.FunctionBusiness;

import java.util.List;

/**
 * 职能业务Mapper接口
 * 
 * @author gox
 * @date 2020-12-29
 */
public interface FunctionBusinessMapper 
{
    /**
     * 查询职能业务
     * 
     * @param id 职能业务ID
     * @return 职能业务
     */
    public FunctionBusiness selectFunctionBusinessById(Long id);

    /**
     * 查询职能业务列表
     * 
     * @param functionBusiness 职能业务
     * @return 职能业务集合
     */
    public List<FunctionBusiness> selectFunctionBusinessList(FunctionBusiness functionBusiness);

    /**
     * 新增职能业务
     * 
     * @param functionBusiness 职能业务
     * @return 结果
     */
    public int insertFunctionBusiness(FunctionBusiness functionBusiness);

    /**
     * 修改职能业务
     * 
     * @param functionBusiness 职能业务
     * @return 结果
     */
    public int updateFunctionBusiness(FunctionBusiness functionBusiness);

    /**
     * 删除职能业务
     * 
     * @param id 职能业务ID
     * @return 结果
     */
    public int deleteFunctionBusinessById(Long id);

    /**
     * 批量删除职能业务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFunctionBusinessByIds(Long[] ids);
}
