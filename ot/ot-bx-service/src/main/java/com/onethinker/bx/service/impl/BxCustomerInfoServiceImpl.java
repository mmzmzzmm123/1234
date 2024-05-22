package com.onethinker.bx.service.impl;

import java.util.List;

import com.onethinker.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onethinker.bx.mapper.BxCustomerInfoMapper;
import com.onethinker.bx.domain.BxCustomerInfo;
import com.onethinker.bx.service.IBxCustomerInfoService;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 佰兴-客户信息表Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-05-22
 */
@Service
@Log4j2
public class BxCustomerInfoServiceImpl extends ServiceImpl<BxCustomerInfoMapper, BxCustomerInfo> implements IBxCustomerInfoService {
    @Resource
    private BxCustomerInfoMapper bxCustomerInfoMapper;

    /**
     * 查询佰兴-客户信息表
     *
     * @param id 佰兴-客户信息表主键
     * @return 佰兴-客户信息表
     */
    @Override
    public BxCustomerInfo selectBxCustomerInfoById(Long id) {
        return bxCustomerInfoMapper.selectBxCustomerInfoById(id);
    }

    /**
     * 查询佰兴-客户信息表列表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 佰兴-客户信息表
     */
    @Override
    public List<BxCustomerInfo> selectBxCustomerInfoList(BxCustomerInfo bxCustomerInfo) {
        return bxCustomerInfoMapper.selectBxCustomerInfoList(bxCustomerInfo);
    }

    /**
     * 新增佰兴-客户信息表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 结果
     */
    @Override
    public int insertBxCustomerInfo(BxCustomerInfo bxCustomerInfo) {
        bxCustomerInfo.setCreateTime(DateUtils.getNowDate());
        return bxCustomerInfoMapper.insertBxCustomerInfo(bxCustomerInfo);
    }

    /**
     * 修改佰兴-客户信息表
     *
     * @param bxCustomerInfo 佰兴-客户信息表
     * @return 结果
     */
    @Override
    public int updateBxCustomerInfo(BxCustomerInfo bxCustomerInfo) {
        bxCustomerInfo.setUpdateTime(DateUtils.getNowDate());
        return bxCustomerInfoMapper.updateBxCustomerInfo(bxCustomerInfo);
    }

    /**
     * 批量删除佰兴-客户信息表
     *
     * @param ids 需要删除的佰兴-客户信息表主键
     * @return 结果
     */
    @Override
    public int deleteBxCustomerInfoByIds(Long[] ids) {
        return bxCustomerInfoMapper.deleteBxCustomerInfoByIds(ids);
    }

    /**
     * 删除佰兴-客户信息表信息
     *
     * @param id 佰兴-客户信息表主键
     * @return 结果
     */
    @Override
    public int deleteBxCustomerInfoById(Long id) {
        return bxCustomerInfoMapper.deleteBxCustomerInfoById(id);
    }
}
