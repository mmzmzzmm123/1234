package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.FarmerContract;
import org.apache.ibatis.annotations.Param;

/**
 * 会员签约Mapper接口
 * 
 * @author 郏磊涛
 * @date 2022-04-01
 */
public interface FarmerContractMapper 
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
     * 删除会员签约
     * 
     * @param contractId 会员签约主键
     * @return 结果
     */
    public int deleteFarmerContractByContractId(@Param("contractId") Long contractId, @Param("status") String status);

    /**
     * 批量删除会员签约
     * 
     * @param contractIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFarmerContractByContractIds(@Param("contractIds") Long[] contractIds, @Param("status") String status);
}
