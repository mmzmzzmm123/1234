package com.jlt.csa.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.jlt.csa.domain.Farmer;
import com.jlt.csa.domain.Garden;
import com.jlt.csa.domain.dto.ContractClaimDto;
import com.jlt.csa.service.IFarmerService;
import com.jlt.csa.service.IGardenService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(FarmerContractServiceImpl.class);

    private FarmerContractMapper farmerContractMapper;
    private IFarmerService farmerService;
    private IGardenService gardenService;

    @Autowired
    public void setFarmerContractMapper(FarmerContractMapper farmerContractMapper) {
        this.farmerContractMapper = farmerContractMapper;
    }

    @Autowired
    public void setFarmerService(IFarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @Autowired
    public void setGardenService(IGardenService gardenService) {
        this.gardenService = gardenService;
    }

    /**
     * 会员认领菜地，更新会员姓名和电话，关联地块（不设置地块日期）
     * @param claim 认领菜地的信息
     * @return 受影响的记录数
     */
    @Override
    @Transactional
    public int claimGarden(ContractClaimDto claim) {
        // 载入合约
        FarmerContract contract = selectFarmerContractByContractId(claim.getContractId());

        // 检查合约是否存在
        if (contract == null) {
            throw new RuntimeException("该合约不存在！");
        }

        // 获取允许认地的2种状态字典值
        String valAudited = DictUtils.getDictValue("csa_contract_status", "已审");
        String valClaimed = DictUtils.getDictValue("csa_contract_status", "认领");

        // 判断当前合约状态是否允许认地
        if (!Arrays.asList(valAudited, valClaimed).contains(contract.getStatus())) {
            throw new RuntimeException("该合约的状态不允许认领菜地！");
        }

        // 载入地主信息
        Farmer farmer = farmerService.selectFarmerWithGardenById(contract.getFarmerId());

        // 检查地主是否存在
        if (farmer == null) {
            throw new RuntimeException("该合约对应的地主信息不存在，无法认领！");
        }

        Garden garden = gardenService.selectGardenById(claim.getGardenId());

        if (!garden.getIsSelled().equals("N")) {
            // 菜地为售出状态
            throw new RuntimeException("该菜地已被认领，无法再次认领！");
        } else {
            if (garden.getFarmerId() != null && garden.getFarmerId() != farmer.getFarmerId()) {
                // 菜地已经有地主信息
                throw new RuntimeException("该菜地已被他人选定，无法认领！");
            }
        }

        /** 已经绑定过菜地，先取消绑定 */
        if (contract.getStatus().equals(valClaimed)) {
            /** 只有选定菜地发生更改的时候才进行取消操作 */
            Garden claimedGarden = gardenService.selectGardenById(contract.getGardenId());
            if (claimedGarden.getGardenId().longValue() != claim.getGardenId()) {
                if (!claimedGarden.getIsSelled().equals("N")) {
                    // 菜地为售出状态，不能取消
                    throw new RuntimeException("现合约原有菜地的状态不允许解绑！");
                }

                // 准备更新菜地字段，取消地主关联和菜地名称
                claimedGarden = new Garden();
                claimedGarden.setGardenId(contract.getGardenId());
                claimedGarden.setFarmerId(0L);
                claimedGarden.setName("");

                gardenService.updateGarden(claimedGarden);
            }
        }

        /** 将合约、菜地、地主信息相关联 */
        // 准备更新的字段
        String status = contract.getStatus().equals(valAudited) ? valClaimed : null;
        contract = new FarmerContract();
        contract.setContractId(claim.getContractId());
        contract.setGardenId(claim.getGardenId());
        contract.setStatus(status);

        garden = new Garden();
        garden.setGardenId(claim.getGardenId());
        garden.setFarmerId(farmer.getFarmerId());
        garden.setName(claim.getGardenName());

        farmer = new Farmer();
        farmer.setFarmerId(garden.getFarmerId());
        farmer.setName(claim.getFarmerName());
        farmer.setMobileNumber(claim.getMobileNumber());

        this.updateFarmerContract(contract);
        gardenService.updateGarden(garden);
        farmerService.updateFarmer(farmer);

        return 4;
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

        // 检查合约是否存在
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
