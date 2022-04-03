package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.FarmerContractMapper;
import com.jlt.csa.domain.FarmerContract;
import com.jlt.csa.service.IFarmerContractService;

/**
 * 会员签约Service业务层处理
 * 
 * @author 郏磊涛
 * @date 2022-04-01
 */
@Service
public class FarmerContractServiceImpl implements IFarmerContractService 
{
    @Autowired
    private FarmerContractMapper farmerContractMapper;

    /**
     * 查询会员签约
     * 
     * @param contractId 会员签约主键
     * @return 会员签约
     */
    @Override
    public FarmerContract selectFarmerContractByContractId(Long contractId)
    {
        return farmerContractMapper.selectFarmerContractByContractId(contractId);
    }

    /**
     * 查询会员签约列表
     * 
     * @param farmerContract 会员签约
     * @return 会员签约
     */
    @Override
    public List<FarmerContract> selectFarmerContractList(FarmerContract farmerContract)
    {
        return farmerContractMapper.selectFarmerContractList(farmerContract);
    }

    /**
     * 新增会员签约
     * 
     * @param farmerContract 会员签约
     * @return 结果
     */
    @Override
    public int insertFarmerContract(FarmerContract farmerContract)
    {
        farmerContract.countDays();
        farmerContract.setCreateTime(DateUtils.getNowDate());
        return farmerContractMapper.insertFarmerContract(farmerContract);
    }

    /**
     * 修改会员签约
     * 
     * @param farmerContract 会员签约
     * @return 结果
     */
    @Override
    public int updateFarmerContract(FarmerContract farmerContract)
    {
        farmerContract.countDays();
        farmerContract.setUpdateTime(DateUtils.getNowDate());
        return farmerContractMapper.updateFarmerContract(farmerContract);
    }

    /**
     * 批量删除会员签约
     * 
     * @param contractIds 需要删除的会员签约主键
     * @return 结果
     */
    @Override
    public int deleteFarmerContractByContractIds(Long[] contractIds)
    {
        return farmerContractMapper.deleteFarmerContractByContractIds(contractIds);
    }

    /**
     * 删除会员签约信息
     * 
     * @param contractId 会员签约主键
     * @return 结果
     */
    @Override
    public int deleteFarmerContractByContractId(Long contractId)
    {
        return farmerContractMapper.deleteFarmerContractByContractId(contractId);
    }
}
