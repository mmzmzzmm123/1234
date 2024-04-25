package com.baoli.sysmanage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 提成对象 baoli_biz_bonus
 * 
 * @author niujs
 * @date 2024-04-25
 */
public class BaoliBizBonus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户Id */
    @Excel(name = "用户Id")
    private Long userId;

    /** 岗位id */
    @Excel(name = "岗位id")
    private Long positionId;

    /** 省份 */
    @Excel(name = "省份")
    private String provinceId;

    /** 城市 */
    @Excel(name = "城市")
    private String cityId;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 月份 */
    @Excel(name = "月份")
    private String month;

    /** 出单笔数 */
    @Excel(name = "出单笔数")
    private Long genOrderCount;

    /** 出单金额 */
    @Excel(name = "出单金额")
    private Float genOrderAmount;

    /** 放款笔数 */
    @Excel(name = "放款笔数")
    private Long releaseMoneyCount;

    /** 放款金额 */
    @Excel(name = "放款金额")
    private Float releaseMoneyAmount;

    /** 提成系数 */
    @Excel(name = "提成系数")
    private Float bonusRatio;

    /** 提成金额 */
    @Excel(name = "提成金额")
    private Float bonusAmount;

    /** 调整金额 */
    @Excel(name = "调整金额")
    private Float adjustAmount;

    /** 扣款 */
    @Excel(name = "扣款")
    private Float deductAmount;
    private Float totalAmount;

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setPositionId(Long positionId) 
    {
        this.positionId = positionId;
    }

    public Long getPositionId() 
    {
        return positionId;
    }
    public void setProvinceId(String provinceId) 
    {
        this.provinceId = provinceId;
    }

    public String getProvinceId() 
    {
        return provinceId;
    }
    public void setCityId(String cityId) 
    {
        this.cityId = cityId;
    }

    public String getCityId() 
    {
        return cityId;
    }
    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }
    public void setMonth(String month) 
    {
        this.month = month;
    }

    public String getMonth() 
    {
        return month;
    }
    public void setGenOrderCount(Long genOrderCount) 
    {
        this.genOrderCount = genOrderCount;
    }

    public Long getGenOrderCount() {
        return genOrderCount;
    }

    public Float getGenOrderAmount() {
        return genOrderAmount;
    }

    public void setGenOrderAmount(Float genOrderAmount) {
        this.genOrderAmount = genOrderAmount;
    }

    public Long getReleaseMoneyCount() {
        return releaseMoneyCount;
    }

    public void setReleaseMoneyCount(Long releaseMoneyCount) {
        this.releaseMoneyCount = releaseMoneyCount;
    }

    public Float getReleaseMoneyAmount() {
        return releaseMoneyAmount;
    }

    public void setReleaseMoneyAmount(Float releaseMoneyAmount) {
        this.releaseMoneyAmount = releaseMoneyAmount;
    }

    public Float getBonusRatio() {
        return bonusRatio;
    }

    public void setBonusRatio(Float bonusRatio) {
        this.bonusRatio = bonusRatio;
    }

    public Float getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Float bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Float getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(Float adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    public Float getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(Float deductAmount) {
        this.deductAmount = deductAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("positionId", getPositionId())
            .append("provinceId", getProvinceId())
            .append("cityId", getCityId())
            .append("year", getYear())
            .append("month", getMonth())
            .append("genOrderCount", getGenOrderCount())
            .append("genOrderAmount", getGenOrderAmount())
            .append("releaseMoneyCount", getReleaseMoneyCount())
            .append("releaseMoneyAmount", getReleaseMoneyAmount())
            .append("bonusRatio", getBonusRatio())
            .append("bonusAmount", getBonusAmount())
            .append("adjustAmount", getAdjustAmount())
            .append("deductAmount", getDeductAmount())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
