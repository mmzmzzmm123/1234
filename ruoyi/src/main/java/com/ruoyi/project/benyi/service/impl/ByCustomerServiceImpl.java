package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByCustomerMapper;
import com.ruoyi.project.benyi.domain.ByCustomer;
import com.ruoyi.project.benyi.service.IByCustomerService;

/**
 * 本一-客户关系管理Service业务层处理
 *
 * @author tsbz
 * @date 2021-03-01
 */
@Service
public class ByCustomerServiceImpl implements IByCustomerService {
    @Autowired
    private ByCustomerMapper byCustomerMapper;

    /**
     * 查询本一-客户关系管理
     *
     * @param id 本一-客户关系管理ID
     * @return 本一-客户关系管理
     */
    @Override
    public ByCustomer selectByCustomerById(Long id) {
        return byCustomerMapper.selectByCustomerById(id);
    }

    /**
     * 查询本一-客户关系管理列表
     *
     * @param byCustomer 本一-客户关系管理
     * @return 本一-客户关系管理
     */
    @Override
    public List<ByCustomer> selectByCustomerList(ByCustomer byCustomer) {
        return byCustomerMapper.selectByCustomerList(byCustomer);
    }

    /**
     * 新增本一-客户关系管理
     *
     * @param byCustomer 本一-客户关系管理
     * @return 结果
     */
    @Override
    public int insertByCustomer(ByCustomer byCustomer) {
        byCustomer.setCreateTime(DateUtils.getNowDate());
        return byCustomerMapper.insertByCustomer(byCustomer);
    }

    /**
     * 修改本一-客户关系管理
     *
     * @param byCustomer 本一-客户关系管理
     * @return 结果
     */
    @Override
    public int updateByCustomer(ByCustomer byCustomer) {
        return byCustomerMapper.updateByCustomer(byCustomer);
    }

    /**
     * 批量删除本一-客户关系管理
     *
     * @param ids 需要删除的本一-客户关系管理ID
     * @return 结果
     */
    @Override
    public int deleteByCustomerByIds(Long[] ids) {
        return byCustomerMapper.deleteByCustomerByIds(ids);
    }

    /**
     * 删除本一-客户关系管理信息
     *
     * @param id 本一-客户关系管理ID
     * @return 结果
     */
    @Override
    public int deleteByCustomerById(Long id) {
        return byCustomerMapper.deleteByCustomerById(id);
    }
}
