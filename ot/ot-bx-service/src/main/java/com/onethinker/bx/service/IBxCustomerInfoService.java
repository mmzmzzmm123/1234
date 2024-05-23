package com.onethinker.bx.service;

import com.onethinker.bx.domain.BxCustomerInfo;

import java.util.List;

/**
 * 佰兴-客户信息表Service接口
 *
 * @author yangyouqi
 * @date 2024-05-22
 */
public interface IBxCustomerInfoService {
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
     * 批量删除佰兴-客户信息表
     *
     * @param ids 需要删除的佰兴-客户信息表主键集合
     * @return 结果
     */
    public int deleteBxCustomerInfoByIds(Long[] ids);

    /**
     * 删除佰兴-客户信息表信息
     *
     * @param id 佰兴-客户信息表主键
     * @return 结果
     */
    public int deleteBxCustomerInfoById(Long id);

    /**
     * 批量保存
     * @param bxCustomerInfos
     */
    void saveEntry(List<BxCustomerInfo> bxCustomerInfos);

    /**
     * 导出数据
     * @param bxCustomerInfo
     * @return
     */
    List<BxCustomerInfo> export(BxCustomerInfo bxCustomerInfo);
}
