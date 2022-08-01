package com.zeroone.crm.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zeroone.crm.biz.mapper.TestdzcMapper;
import com.zeroone.crm.biz.domain.Testdzc;
import com.zeroone.crm.biz.service.ITestdzcService;

/**
 * 测试使用Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-01
 */
@Service
public class TestdzcServiceImpl implements ITestdzcService 
{
    @Autowired
    private TestdzcMapper testdzcMapper;

    /**
     * 查询测试使用
     * 
     * @param id 测试使用主键
     * @return 测试使用
     */
    @Override
    public Testdzc selectTestdzcById(Integer id)
    {
        return testdzcMapper.selectTestdzcById(id);
    }

    /**
     * 查询测试使用列表
     * 
     * @param testdzc 测试使用
     * @return 测试使用
     */
    @Override
    public List<Testdzc> selectTestdzcList(Testdzc testdzc)
    {
        return testdzcMapper.selectTestdzcList(testdzc);
    }

    /**
     * 新增测试使用
     * 
     * @param testdzc 测试使用
     * @return 结果
     */
    @Override
    public int insertTestdzc(Testdzc testdzc)
    {
        return testdzcMapper.insertTestdzc(testdzc);
    }

    /**
     * 修改测试使用
     * 
     * @param testdzc 测试使用
     * @return 结果
     */
    @Override
    public int updateTestdzc(Testdzc testdzc)
    {
        return testdzcMapper.updateTestdzc(testdzc);
    }

    /**
     * 批量删除测试使用
     * 
     * @param ids 需要删除的测试使用主键
     * @return 结果
     */
    @Override
    public int deleteTestdzcByIds(Integer[] ids)
    {
        return testdzcMapper.deleteTestdzcByIds(ids);
    }

    /**
     * 删除测试使用信息
     * 
     * @param id 测试使用主键
     * @return 结果
     */
    @Override
    public int deleteTestdzcById(Integer id)
    {
        return testdzcMapper.deleteTestdzcById(id);
    }
}
