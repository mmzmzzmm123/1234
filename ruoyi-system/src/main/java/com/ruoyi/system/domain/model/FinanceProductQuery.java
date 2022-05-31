package com.ruoyi.system.domain.model;

import java.math.BigDecimal;

/**
 * @author renjf
 * @date 2022年05月26日 16:13
 */
public class FinanceProductQuery {
    // 页码
    private  Integer pageNum;

    // 每页条数
    private  Integer pageSize;

    // 主键
    private Long id;

    // 金融机构id
    private Long capOrgId;

    // 贷款金额最大额度
    private String limitEnd;

    // 贷款金额最小额度
    private String limitStart;

    // 贷款最大期限
    private String periodEnd;

    // 贷款最小期限
    private String periodStart;

    // 贷款最高利率
    private String rateEnd;

    // 贷款最低利率
    private String rateStart;

    // 产品状态：0-未发布，1-已发布，99-撤销发布
    private String state;

    // 担保方式（字典数据value，复数值用英文逗号分隔）
    private String guaranteeMode;

// 以下为热门产品查询使用
    // 热门推荐类型（字典数据value）
    private  String type;

    // 金融产品的状态：0-未发布，1-已发布，99-撤销发布
    private  String proState;

    // 金融产品的删除状态：0-未删除，1-删除
    private  String proDeleteFlag;

    // 金融产品名称
    private  String proName;

    @Override
    public String toString() {
        return "FinanceProductQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", capOrgId=" + capOrgId +
                ", limitEnd='" + limitEnd + '\'' +
                ", limitStart='" + limitStart + '\'' +
                ", periodEnd='" + periodEnd + '\'' +
                ", periodStart='" + periodStart + '\'' +
                ", rateEnd='" + rateEnd + '\'' +
                ", rateStart='" + rateStart + '\'' +
                ", state='" + state + '\'' +
                ", guaranteeMode='" + guaranteeMode + '\'' +
                ", type='" + type + '\'' +
                ", proState='" + proState + '\'' +
                ", proDeleteFlag='" + proDeleteFlag + '\'' +
                ", proName='" + proName + '\'' +
                '}';
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCapOrgId() {
        return capOrgId;
    }

    public void setCapOrgId(Long capOrgId) {
        this.capOrgId = capOrgId;
    }

    public String getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(String limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(String limitStart) {
        this.limitStart = limitStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getRateEnd() {
        return rateEnd;
    }

    public void setRateEnd(String rateEnd) {
        this.rateEnd = rateEnd;
    }

    public String getRateStart() {
        return rateStart;
    }

    public void setRateStart(String rateStart) {
        this.rateStart = rateStart;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGuaranteeMode() {
        return guaranteeMode;
    }

    public void setGuaranteeMode(String guaranteeMode) {
        this.guaranteeMode = guaranteeMode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProState() {
        return proState;
    }

    public void setProState(String proState) {
        this.proState = proState;
    }

    public String getProDeleteFlag() {
        return proDeleteFlag;
    }

    public void setProDeleteFlag(String proDeleteFlag) {
        this.proDeleteFlag = proDeleteFlag;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }
}
