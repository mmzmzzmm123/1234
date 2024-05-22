package com.onethinker.bx.mapper;

import java.util.List;

import com.onethinker.bx.domain.BxCustomerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 佰兴-客户信息表Mapper接口
 *
 * @author yangyouqi
 * @date 2024-05-22
 */
public interface BxCustomerInfoMapper extends BaseMapper<BxCustomerInfo> {
    /**
     * 查询佰兴-客户信息表
     *
     * @param id 佰兴-客户信息表主键
     * @return 佰兴-客户信息表
     */
    public BxCustomerInfo selectBxCustomerInfoById(Long id);

    /**
     * 查询佰兴-客户信息表列表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 佰兴-客户信息表集合
     */
    public List<BxCustomerInfo> selectBxCustomerInfoList(BxCustomerInfo bxCustomerInfo);

    /**
     * 新增佰兴-客户信息表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 结果
     */
    public int insertBxCustomerInfo(BxCustomerInfo bxCustomerInfo);

    /**
     * 修改佰兴-客户信息表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 结果
     */
    public int updateBxCustomerInfo(BxCustomerInfo bxCustomerInfo);

    /**
     * 删除佰兴-客户信息表
     *
     * @param id 佰兴-客户信息表主键
     * @return 结果
     */
    public int deleteBxCustomerInfoById(Long id);

    /**
     * 批量删除佰兴-客户信息表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBxCustomerInfoByIds(Long[] ids);
}
