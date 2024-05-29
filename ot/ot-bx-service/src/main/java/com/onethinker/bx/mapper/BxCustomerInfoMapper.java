package com.onethinker.bx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onethinker.bx.domain.BxCustomerInfo;

import java.util.List;

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
    BxCustomerInfo selectBxCustomerInfoById(Long id);

    /**
     * 查询佰兴-客户信息表列表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 佰兴-客户信息表集合
     */
    List<BxCustomerInfo> selectBxCustomerInfoList(BxCustomerInfo bxCustomerInfo);

    /**
     * 新增佰兴-客户信息表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 结果
     */
    int insertBxCustomerInfo(BxCustomerInfo bxCustomerInfo);

    /**
     * 修改佰兴-客户信息表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 结果
     */
    int updateBxCustomerInfo(BxCustomerInfo bxCustomerInfo);

    /**
     * 删除佰兴-客户信息表
     *
     * @param id 佰兴-客户信息表主键
     * @return 结果
     */
    int deleteBxCustomerInfoById(Long id);

    /**
     * 批量删除佰兴-客户信息表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBxCustomerInfoByIds(Long[] ids);
}
