package com.ruoyi.system.domain.model;

/**
 * 信贷直通车热门产品信息
 * @author renjf
 * @date 2022年05月26日 16:13
 */
public class FinanceHotProductInfo {
    // id
    private Long id;

    //金融信贷产品ID
    private String proId;

    //推荐类型（字典数据value）
    private String type;

    //排序
    private String orderNum;

    //创建用户名
    private String createUserName;

    //创建人
    private String createStaffName;

    //创建时间
    private String createTime;

    //更新用户名
    private String updateUserName;

    //更新人
    private String updateStaffName;

    //更新时间
    private String updateTime;

    //备注
    private String remark;

    // 产品说明
    private FinanceProductInfo product ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FinanceHotProductInfo{" +
                "id='" + id + '\'' +
                "proId='" + proId + '\'' +
                ", type='" + type + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", createStaffName='" + createStaffName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", updateStaffName='" + updateStaffName + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", remark='" + remark + '\'' +
                ", product=" + product +
                '}';
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateStaffName() {
        return createStaffName;
    }

    public void setCreateStaffName(String createStaffName) {
        this.createStaffName = createStaffName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateStaffName() {
        return updateStaffName;
    }

    public void setUpdateStaffName(String updateStaffName) {
        this.updateStaffName = updateStaffName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public FinanceProductInfo getProduct() {
        return product;
    }

    public void setProduct(FinanceProductInfo product) {
        this.product = product;
    }
}
