package com.zeroone.crm.biz.mapper;

import java.util.List;
import com.zeroone.crm.biz.domain.Testdzc;

/**
 * 测试使用Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-01
 */
public interface TestdzcMapper 
{
    /**
     * 查询测试使用
     * 
     * @param id 测试使用主键
     * @return 测试使用
     */
    public Testdzc selectTestdzcById(Integer id);

    /**
     * 查询测试使用列表
     * 
     * @param testdzc 测试使用
     * @return 测试使用集合
     */
    public List<Testdzc> selectTestdzcList(Testdzc testdzc);

    /**
     * 新增测试使用
     * 
     * @param testdzc 测试使用
     * @return 结果
     */
    public int insertTestdzc(Testdzc testdzc);

    /**
     * 修改测试使用
     * 
     * @param testdzc 测试使用
     * @return 结果
     */
    public int updateTestdzc(Testdzc testdzc);

    /**
     * 删除测试使用
     * 
     * @param id 测试使用主键
     * @return 结果
     */
    public int deleteTestdzcById(Integer id);

    /**
     * 批量删除测试使用
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTestdzcByIds(Integer[] ids);
}
