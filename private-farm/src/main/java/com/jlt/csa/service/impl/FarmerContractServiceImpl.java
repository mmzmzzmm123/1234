package com.jlt.csa.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.jlt.csa.domain.Farmer;
import com.jlt.csa.service.IFarmerService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.FarmerContractMapper;
import com.jlt.csa.domain.FarmerContract;
import com.jlt.csa.service.IFarmerContractService;
import org.springframework.transaction.annotation.Transactional;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

/**
 * 会员签约Service业务层处理
 * 
 * @author 郏磊涛
 * @date 2022-04-01
 */
@Service
public class FarmerContractServiceImpl implements IFarmerContractService 
{
    private FarmerContractMapper farmerContractMapper;
    private IFarmerService farmerService;

    @Autowired
    public void setFarmerContractMapper(FarmerContractMapper farmerContractMapper) {
        this.farmerContractMapper = farmerContractMapper;
    }

    @Autowired
    public void setFarmerService(IFarmerService farmerService) {
        this.farmerService = farmerService;
    }

    /**
     * 审核会员签约信息
     * @param contractId 合约id
     * @return 受影响的记录数
     */
    @Override
    @Transactional
    public int auditContract(Long contractId) {
        // 载入合约
        FarmerContract contract =  selectFarmerContractByContractId(contractId);

        // 检查合约是否存在，
        if (contract == null) {
            throw new RuntimeException("合约不存在，无法审核！");
        }

        // 合约不符合审核条件，返回错误提示
        if (!contract.getStatus().equals(DictUtils.getDictValue("csa_contract_status", "待审"))) {
            throw new RuntimeException("该合约不符合审核条件！");
        }

        // 是否是新地主
        boolean isNewFarmer = contract.getFarmerId() == null;
        Farmer farmer = null;

        if (isNewFarmer) {
            // 如果是新签，新建地主信息
            farmer = new Farmer();
            farmer.setName(contract.getContractor());
            farmer.setCoins(0L);
            farmer.setBalance(new BigDecimal(0));
            farmer.setWeight(new BigDecimal(0));
            farmer.setArchedYears(0L);
            farmer.setJoinDate(contract.getJoinDate());
            farmer.setIsExpire("N");
        } else {
            // 老地主，载入地主信息
            farmer = farmerService.selectFarmerByFarmerId(contract.getFarmerId());

            if (farmer == null)
                throw new RuntimeException("该合约指向了不存在的会员信息，无法审核！");
        }

        // 累计合约信息至地主，菜地有效期需要在绑定地块时处理
        farmer.setCoins(farmer.getCoins() + contract.getCoins());
        farmer.setBalance(farmer.getBalance().add(contract.getBalance()));
        farmer.setWeight(farmer.getWeight().add(contract.getWeight()));
        farmer.setArchedYears(farmer.getArchedYears() + contract.getArchedYears());

        // 构建审核的合约更新字段
        contract = new FarmerContract();
        contract.setContractId(contractId);
        contract.setStatus(DictUtils.getDictValue("csa_contract_status", "已审"));
        contract.setUpdateBy(getUsername());

        if (isNewFarmer) {
            // 新地主新签
            farmerService.insertFarmer(farmer);
            contract.setFarmerId(farmer.getFarmerId());
        } else {
            // 老地主续签
            farmerService.updateFarmer(farmer);
        }

        return updateFarmerContract(contract);
    }

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
    public List<FarmerContract> selectFarmerContractList(FarmerContract farmerContract) {
        return farmerContractMapper.selectFarmerContractList(farmerContract);
    }

    /**
     * 查询入驻会员合约的列表
     * @param farmerContract 会员合约
     * @return 会员合约集合
     */
    @Override
    public List<FarmerContract> selectEnterContractList(FarmerContract farmerContract) {
        return farmerContractMapper.selectEnterContractList(farmerContract);
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
        farmerContract.setUpdateTime(DateUtils.getNowDate());
        return farmerContractMapper.updateFarmerContract(farmerContract);
    }

    /**
     * 批量逻辑删除会员签约
     * 
     * @param contractIds 需要删除的会员签约主键
     * @return 结果
     */
    @Override
    public int deleteFarmerContractByContractIds(Long[] contractIds)
    {
        return farmerContractMapper.deleteFarmerContractByContractIds(contractIds, "3");
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
        return farmerContractMapper.deleteFarmerContractByContractId(contractId, "3");
    }
}
