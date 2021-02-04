package com.gox.basic.service.impl;

import com.gox.basic.domain.FunctionBusiness;
import com.gox.basic.mapper.FunctionBusinessMapper;
import com.gox.basic.service.IFunctionBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职能业务Service业务层处理
 * 
 * @author gox
 * @date 2020-12-29
 */
@Service
public class FunctionBusinessServiceImpl implements IFunctionBusinessService 
{
    @Autowired
    private FunctionBusinessMapper functionBusinessMapper;

    /**
     * 查询职能业务
     * 
     * @param id 职能业务ID
     * @return 职能业务
     */
    @Override
    public FunctionBusiness selectFunctionBusinessById(Long id)
    {
        return functionBusinessMapper.selectFunctionBusinessById(id);
    }

    /**
     * 查询职能业务列表
     * 
     * @param functionBusiness 职能业务
     * @return 职能业务
     */
    @Override
    public List<FunctionBusiness> selectFunctionBusinessList(FunctionBusiness functionBusiness)
    {
        return functionBusinessMapper.selectFunctionBusinessList(functionBusiness);
    }

    /**
     * 新增职能业务
     * 
     * @param functionBusiness 职能业务
     * @return 结果
     */
    @Override
    public int insertFunctionBusiness(FunctionBusiness functionBusiness)
    {
        return functionBusinessMapper.insertFunctionBusiness(functionBusiness);
    }

    /**
     * 修改职能业务
     * 
     * @param functionBusiness 职能业务
     * @return 结果
     */
    @Override
    public int updateFunctionBusiness(FunctionBusiness functionBusiness)
    {
        return functionBusinessMapper.updateFunctionBusiness(functionBusiness);
    }

    /**
     * 批量删除职能业务
     * 
     * @param ids 需要删除的职能业务ID
     * @return 结果
     */
    @Override
    public int deleteFunctionBusinessByIds(Long[] ids)
    {
        return functionBusinessMapper.deleteFunctionBusinessByIds(ids);
    }

    /**
     * 删除职能业务信息
     * 
     * @param id 职能业务ID
     * @return 结果
     */
    @Override
    public int deleteFunctionBusinessById(Long id)
    {
        return functionBusinessMapper.deleteFunctionBusinessById(id);
    }
}
