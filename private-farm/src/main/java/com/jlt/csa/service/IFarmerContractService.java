package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.FarmerContract;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 会员签约Service接口
 * 
 * @author 郏磊涛
 * @date 2022-04-01
 */
public interface IFarmerContractService 
{
    /**
     * 查询会员签约
     * 
     * @param contractId 会员签约主键
     * @return 会员签约
     */
    public FarmerContract selectFarmerContractByContractId(Long contractId);

    /**
     * 查询会员签约列表
     * 
     * @param farmerContract 会员签约
     * @return 会员签约集合
     */
    public List<FarmerContract> selectFarmerContractList(FarmerContract farmerContract);

    /**
     * 查询入驻会员合约的列表
     * @param farmerContract 会员合约
     * @return 会员合约集合
     */
    public List<FarmerContract> selectEnterContractList(FarmerContract farmerContract);

    /**
     * 新增会员签约
     * 
     * @param farmerContract 会员签约
     * @return 结果
     */
    public int insertFarmerContract(FarmerContract farmerContract);

    /**
     * 修改会员签约
     * 
     * @param farmerContract 会员签约
     * @return 结果
     */
    public int updateFarmerContract(FarmerContract farmerContract);

    /**
     * 批量删除会员签约
     * 
     * @param contractIds 需要删除的会员签约主键集合
     * @return 结果
     */
    public int deleteFarmerContractByContractIds(Long[] contractIds);

    /**
     * 删除会员签约信息
     * 
     * @param contractId 会员签约主键
     * @return 结果
     */
    public int deleteFarmerContractByContractId(Long contractId);

    /**
     * 审核会员签约信息
     * @param contractId 合约id
     * @return 受影响的记录数
     */
    public int auditContract(@PathVariable Long contractId);
}
