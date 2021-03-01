package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByCustomer;

/**
 * 本一-客户关系管理Service接口
 *
 * @author tsbz
 * @date 2021-03-01
 */
public interface IByCustomerService {
    /**
     * 查询本一-客户关系管理
     *
     * @param id 本一-客户关系管理ID
     * @return 本一-客户关系管理
     */
    public ByCustomer selectByCustomerById(Long id);

    /**
     * 查询本一-客户关系管理列表
     *
     * @param byCustomer 本一-客户关系管理
     * @return 本一-客户关系管理集合
     */
    public List<ByCustomer> selectByCustomerList(ByCustomer byCustomer);

    /**
     * 新增本一-客户关系管理
     *
     * @param byCustomer 本一-客户关系管理
     * @return 结果
     */
    public int insertByCustomer(ByCustomer byCustomer);

    /**
     * 修改本一-客户关系管理
     *
     * @param byCustomer 本一-客户关系管理
     * @return 结果
     */
    public int updateByCustomer(ByCustomer byCustomer);

    /**
     * 批量删除本一-客户关系管理
     *
     * @param ids 需要删除的本一-客户关系管理ID
     * @return 结果
     */
    public int deleteByCustomerByIds(Long[] ids);

    /**
     * 删除本一-客户关系管理信息
     *
     * @param id 本一-客户关系管理ID
     * @return 结果
     */
    public int deleteByCustomerById(Long id);
}
