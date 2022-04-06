package com.jlt.csa.domain.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 合约认领菜地的信息的DTO
 */
public class ContractClaimDto {
    // 合约Id
    private Long contractId;

    // 会员姓名
    private String farmerName;

    // 会员电话
    private String mobileNumber;

    // 菜地Id
    private Long gardenId;

    // 菜地名称
    private String gardenName;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Long getGardenId() {
        return gardenId;
    }

    public void setGardenId(Long gardenId) {
        this.gardenId = gardenId;
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("contractId", getContractId())
                .append("farmerName", getFarmerName())
                .append("mobileNumber", getMobileNumber())
                .append("gardenId", getGardenId())
                .append("gardenName", getGardenName())
                .toString();
    }
}
