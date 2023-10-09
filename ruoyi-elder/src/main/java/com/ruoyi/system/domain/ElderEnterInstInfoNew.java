package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 养老机构表
 * @TableName elder_enter_inst_info_new
 */
@TableName(value ="elder_enter_inst_info_new")
public class ElderEnterInstInfoNew implements Serializable {
    /**
     * ID主键
     */
    @TableId
    private String id;

    /**
     * 统一社会信用代码
     */
    private String taxId;

    /**
     * 单位类型
     */
    private String enterInstType;

    /**
     * 单位名称
     */
    private String enterInstName;

    /**
     * 单位建立日期
     */
    private String establishDate;

    /**
     * 行政区划代码
     */
    private String areaCode;

    /**
     * 行政区划名称
     */
    private String areaName;

    /**
     * 
     */
    private String instProvince;

    /**
     * 
     */
    private String instCity;

    /**
     * 
     */
    private String instDistrict;

    /**
     * 
     */
    private String instStreet;

    /**
     * 
     */
    private String instVillage;

    /**
     * 机构地址
     */
    private String instAdd;

    /**
     * 机构等级
     */
    private String instLev;

    /**
     * 法定代表人或负责人姓名
     */
    private String principal;

    /**
     * 法定代表人或负责人联系方式
     */
    private String principalContact;

    /**
     * 法定代表人或负责人证件类型
     */
    private String principalCredType;

    /**
     * 法定代表人或负责人证件号码
     */
    private String principalCredId;

    /**
     * 法人登记机关
     */
    private String principalRegBureau;

    /**
     * 法人类型
     */
    private String principalType;

    /**
     * 全托床位数量
     */
    private Integer totalCareBeds;

    /**
     * 日托床位数量
     */
    private Integer dayCareBeds;

    /**
     * 护理型床位数量
     */
    private Integer nursingBeds;

    /**
     * 床位总数量
     */
    private Integer totalBeds;

    /**
     * 建筑面积
     */
    private Double structionArea;

    /**
     * 占地面积
     */
    private Double coveredArea;

    /**
     * 场所性质
     */
    private String placeProp;

    /**
     * 是否通过消防部门审验合格
     */
    private String fireQualified;

    /**
     * 未通过消防部门审验合格原因
     */
    private String fireUnqualifiedReason;

    /**
     * 消防监管部门审核意见
     */
    private String fireQualifiedComments;

    /**
     * 是否取得食品生产许可证
     */
    private String foodQualified;

    /**
     * 未取得食品生产许可证原因
     */
    private String foodUnqualifiedReason;

    /**
     * 食品监管部门审核意见
     */
    private String foodQualifiedComments;

    /**
     * 单位性质(一级)
     */
    private String unitPropFirst;

    /**
     * 单位性质(二级)
     */
    private String unitPropSecond;

    /**
     * 是否为特困救助供养机构
     */
    private String isPovertyRelief;

    /**
     * 是否为光荣院
     */
    private String isGloriousHomes;

    /**
     * 是否为街道（乡镇）区域性养老服务中心
     */
    private String isCommunityCareCenter;

    /**
     * 所处位置
     */
    private String areaType;

    /**
     * 兴办主体
     */
    private String establishMainbody;

    /**
     * 是否收住能力完好类型老年人
     */
    private String haveCapability;

    /**
     * 是否收住完全失能类型老年人
     */
    private String haveTotalDisability;

    /**
     * 是否收住部分失能类型老年人
     */
    private String haveHalfDisability;

    /**
     * 是否收住特困人员类型老年人
     */
    private String haveSpecialDifficult;

    /**
     * 是否收住残疾类型老年人
     */
    private String haveDeformity;

    /**
     * 是否收住优抚类型老年人
     */
    private String haveSpecialCare;

    /**
     * 是否收住其他类型老年人
     */
    private String haveOther;

    /**
     * 其它类型老年人
     */
    private String haveOtherName;

    /**
     * 是否投保责任险
     */
    private String isLiabilityInsurance;

    /**
     * 是否长护险定点机构
     */
    private String isLongCareInsurance;

    /**
     * 是否提供居家养老上门服务
     */
    private String isAidService;

    /**
     * 家庭养老床位服务
     */
    private String isBedAid;

    /**
     * 家庭养老床位服务服务人次/月
     */
    private Integer bedAidCount;

    /**
     * 上门助餐服务
     */
    private String isMealAid;

    /**
     * 上门助餐服务服务人次/月
     */
    private Integer mealAidCount;

    /**
     * 上门助洁服务
     */
    private String isCleanAid;

    /**
     * 上门助洁服务服务人次/月
     */
    private Integer cleanAidCount;

    /**
     * 上门助行服务
     */
    private String isWalkAid;

    /**
     * 上门助行服务服务人次/月
     */
    private Integer walkAidCount;

    /**
     * 上门助浴服务
     */
    private String isBathAid;

    /**
     * 上门助浴服务服务人次/月
     */
    private Integer bathAidCount;

    /**
     * 上门康复
     */
    private String isRecoverAid;

    /**
     * 上门康复服务人次/月
     */
    private Integer recoverAidCount;

    /**
     * 上门助医服务
     */
    private String isMedicalAid;

    /**
     * 上门助医服务服务人次/月
     */
    private Integer medicalAidCount;

    /**
     * 巡访关爱服务
     */
    private String isVisitAid;

    /**
     * 巡访关爱服务服务人次/月
     */
    private Integer visitAidCount;

    /**
     * 其他上门服务
     */
    private String isOtherAid;

    /**
     * 其他上门服务服务人次/月
     */
    private Integer otherAidCount;

    /**
     * 是否提供社区养老服务
     */
    private String isCommunityCare;

    /**
     * 日托(日间照料)
     */
    private String isDayCare;

    /**
     * 日托(日间照料)服务人次/月
     */
    private Integer dayCareCount;

    /**
     * 老年餐桌(食堂)
     */
    private String isElderDinning;

    /**
     * 老年餐桌(食堂)服务人次/月
     */
    private Integer elderDinningCount;

    /**
     * 社区文化娱乐
     */
    private String isEntertainment;

    /**
     * 社区文化娱乐服务人次/月
     */
    private Integer entertainmentCount;

    /**
     * 时间银行(志愿服务)
     */
    private String isTimeBank;

    /**
     * 时间银行(志愿服务)服务人次/月
     */
    private Integer timeBankCount;

    /**
     * 社区康复服务
     */
    private String isCommunityRecover;

    /**
     * 社区康复服务服务人次/月
     */
    private Integer communityRecoverCount;

    /**
     * 其他社区养老服务
     */
    private String isOtherService;

    /**
     * 其它服务名称
     */
    private String otherServiceName;

    /**
     * 其它服务服务人次/月
     */
    private Integer otherServiceCount;

    /**
     * 是否开展医疗服务
     */
    private String isMedicalService;

    /**
     * 是否内设医疗机构
     */
    private String isMedicalInst;

    /**
     * 自办医院
     */
    private String isPrivateHospital;

    /**
     * 护理站
     */
    private String isNusringStation;

    /**
     * 医务室
     */
    private String isClinic;

    /**
     * 是否与医疗机构签约合作（协议医疗保障）
     */
    private String isMedicalContracted;

    /**
     * 医院
     */
    private String isHospital;

    /**
     * 乡镇卫生院
     */
    private String isTownHospital;

    /**
     * 村(社区)卫生服务站(中心)
     */
    private String isCommunityHospital;

    /**
     * 是否取得医疗机构执业许可证书
     */
    private String isMedicalCert;

    /**
     * 是否取得医疗保险定点医疗机构资格证书
     */
    private String isMedicalInsuranceUnit;

    /**
     * 是否享受政府扶持优惠政策内容
     */
    private String isGovernAssist;

    /**
     * 税收优惠政策
     */
    private String isTaxBreak;

    /**
     * 建设补贴优惠政策
     */
    private String isConstructBreak;

    /**
     * 运营补贴优惠政策
     */
    private String isOperaBreak;

    /**
     * 土地划拨优惠政策
     */
    private String isLandBreak;

    /**
     * 行政事业收费减免优惠政策
     */
    private String isAdminBreak;

    /**
     * 居民用水用电价格优惠政策
     */
    private String isWaterElecBreak;

    /**
     * 收费方式
     */
    private String chargeType;

    /**
     * 是否采用会员制方式收费
     */
    private String isMembership;

    /**
     * 护理费（最高）
     */
    private Double nursingUp;

    /**
     * 护理费（最低）
     */
    private Double nursingLow;

    /**
     * 床位费（最高）
     */
    private Double bedUp;

    /**
     * 床位费（最低）
     */
    private Double bedLow;

    /**
     * 膳食费（最高）
     */
    private Double mealUp;

    /**
     * 膳食费（最低）
     */
    private Double mealLow;

    /**
     * 押金/备用金（最高）
     */
    private Double depositUp;

    /**
     * 押金/备用金（最低）
     */
    private Double depositLow;

    /**
     * 是否其他收费
     */
    private String isOtherFee;

    /**
     * 其他收费名称
     */
    private String otherFeeName;

    /**
     * 其他收费（最低）
     */
    private Double otherFeeLow;

    /**
     * 其他收费（最高）
     */
    private Double otherFeeUp;

    /**
     * 月末收住老年人数
     */
    private Integer totalElders;

    /**
     * 机构生存状态
     */
    private String currentStatus;

    /**
     * 是否备案
     */
    private String isRecord;

    /**
     * 备案时间
     */
    private String recordDate;

    /**
     * 经度
     */
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 
     */
    private String star;

    /**
     * 查询关键字
     */
    private String searchValue;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志
     */
    private String delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * ID主键
     */
    public String getId() {
        return id;
    }

    /**
     * ID主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 统一社会信用代码
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * 统一社会信用代码
     */
    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    /**
     * 单位类型
     */
    public String getEnterInstType() {
        return enterInstType;
    }

    /**
     * 单位类型
     */
    public void setEnterInstType(String enterInstType) {
        this.enterInstType = enterInstType;
    }

    /**
     * 单位名称
     */
    public String getEnterInstName() {
        return enterInstName;
    }

    /**
     * 单位名称
     */
    public void setEnterInstName(String enterInstName) {
        this.enterInstName = enterInstName;
    }

    /**
     * 单位建立日期
     */
    public String getEstablishDate() {
        return establishDate;
    }

    /**
     * 单位建立日期
     */
    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    /**
     * 行政区划代码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 行政区划代码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 行政区划名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 行政区划名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 
     */
    public String getInstProvince() {
        return instProvince;
    }

    /**
     * 
     */
    public void setInstProvince(String instProvince) {
        this.instProvince = instProvince;
    }

    /**
     * 
     */
    public String getInstCity() {
        return instCity;
    }

    /**
     * 
     */
    public void setInstCity(String instCity) {
        this.instCity = instCity;
    }

    /**
     * 
     */
    public String getInstDistrict() {
        return instDistrict;
    }

    /**
     * 
     */
    public void setInstDistrict(String instDistrict) {
        this.instDistrict = instDistrict;
    }

    /**
     * 
     */
    public String getInstStreet() {
        return instStreet;
    }

    /**
     * 
     */
    public void setInstStreet(String instStreet) {
        this.instStreet = instStreet;
    }

    /**
     * 
     */
    public String getInstVillage() {
        return instVillage;
    }

    /**
     * 
     */
    public void setInstVillage(String instVillage) {
        this.instVillage = instVillage;
    }

    /**
     * 机构地址
     */
    public String getInstAdd() {
        return instAdd;
    }

    /**
     * 机构地址
     */
    public void setInstAdd(String instAdd) {
        this.instAdd = instAdd;
    }

    /**
     * 机构等级
     */
    public String getInstLev() {
        return instLev;
    }

    /**
     * 机构等级
     */
    public void setInstLev(String instLev) {
        this.instLev = instLev;
    }

    /**
     * 法定代表人或负责人姓名
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * 法定代表人或负责人姓名
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     * 法定代表人或负责人联系方式
     */
    public String getPrincipalContact() {
        return principalContact;
    }

    /**
     * 法定代表人或负责人联系方式
     */
    public void setPrincipalContact(String principalContact) {
        this.principalContact = principalContact;
    }

    /**
     * 法定代表人或负责人证件类型
     */
    public String getPrincipalCredType() {
        return principalCredType;
    }

    /**
     * 法定代表人或负责人证件类型
     */
    public void setPrincipalCredType(String principalCredType) {
        this.principalCredType = principalCredType;
    }

    /**
     * 法定代表人或负责人证件号码
     */
    public String getPrincipalCredId() {
        return principalCredId;
    }

    /**
     * 法定代表人或负责人证件号码
     */
    public void setPrincipalCredId(String principalCredId) {
        this.principalCredId = principalCredId;
    }

    /**
     * 法人登记机关
     */
    public String getPrincipalRegBureau() {
        return principalRegBureau;
    }

    /**
     * 法人登记机关
     */
    public void setPrincipalRegBureau(String principalRegBureau) {
        this.principalRegBureau = principalRegBureau;
    }

    /**
     * 法人类型
     */
    public String getPrincipalType() {
        return principalType;
    }

    /**
     * 法人类型
     */
    public void setPrincipalType(String principalType) {
        this.principalType = principalType;
    }

    /**
     * 全托床位数量
     */
    public Integer getTotalCareBeds() {
        return totalCareBeds;
    }

    /**
     * 全托床位数量
     */
    public void setTotalCareBeds(Integer totalCareBeds) {
        this.totalCareBeds = totalCareBeds;
    }

    /**
     * 日托床位数量
     */
    public Integer getDayCareBeds() {
        return dayCareBeds;
    }

    /**
     * 日托床位数量
     */
    public void setDayCareBeds(Integer dayCareBeds) {
        this.dayCareBeds = dayCareBeds;
    }

    /**
     * 护理型床位数量
     */
    public Integer getNursingBeds() {
        return nursingBeds;
    }

    /**
     * 护理型床位数量
     */
    public void setNursingBeds(Integer nursingBeds) {
        this.nursingBeds = nursingBeds;
    }

    /**
     * 床位总数量
     */
    public Integer getTotalBeds() {
        return totalBeds;
    }

    /**
     * 床位总数量
     */
    public void setTotalBeds(Integer totalBeds) {
        this.totalBeds = totalBeds;
    }

    /**
     * 建筑面积
     */
    public Double getStructionArea() {
        return structionArea;
    }

    /**
     * 建筑面积
     */
    public void setStructionArea(Double structionArea) {
        this.structionArea = structionArea;
    }

    /**
     * 占地面积
     */
    public Double getCoveredArea() {
        return coveredArea;
    }

    /**
     * 占地面积
     */
    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
    }

    /**
     * 场所性质
     */
    public String getPlaceProp() {
        return placeProp;
    }

    /**
     * 场所性质
     */
    public void setPlaceProp(String placeProp) {
        this.placeProp = placeProp;
    }

    /**
     * 是否通过消防部门审验合格
     */
    public String getFireQualified() {
        return fireQualified;
    }

    /**
     * 是否通过消防部门审验合格
     */
    public void setFireQualified(String fireQualified) {
        this.fireQualified = fireQualified;
    }

    /**
     * 未通过消防部门审验合格原因
     */
    public String getFireUnqualifiedReason() {
        return fireUnqualifiedReason;
    }

    /**
     * 未通过消防部门审验合格原因
     */
    public void setFireUnqualifiedReason(String fireUnqualifiedReason) {
        this.fireUnqualifiedReason = fireUnqualifiedReason;
    }

    /**
     * 消防监管部门审核意见
     */
    public String getFireQualifiedComments() {
        return fireQualifiedComments;
    }

    /**
     * 消防监管部门审核意见
     */
    public void setFireQualifiedComments(String fireQualifiedComments) {
        this.fireQualifiedComments = fireQualifiedComments;
    }

    /**
     * 是否取得食品生产许可证
     */
    public String getFoodQualified() {
        return foodQualified;
    }

    /**
     * 是否取得食品生产许可证
     */
    public void setFoodQualified(String foodQualified) {
        this.foodQualified = foodQualified;
    }

    /**
     * 未取得食品生产许可证原因
     */
    public String getFoodUnqualifiedReason() {
        return foodUnqualifiedReason;
    }

    /**
     * 未取得食品生产许可证原因
     */
    public void setFoodUnqualifiedReason(String foodUnqualifiedReason) {
        this.foodUnqualifiedReason = foodUnqualifiedReason;
    }

    /**
     * 食品监管部门审核意见
     */
    public String getFoodQualifiedComments() {
        return foodQualifiedComments;
    }

    /**
     * 食品监管部门审核意见
     */
    public void setFoodQualifiedComments(String foodQualifiedComments) {
        this.foodQualifiedComments = foodQualifiedComments;
    }

    /**
     * 单位性质(一级)
     */
    public String getUnitPropFirst() {
        return unitPropFirst;
    }

    /**
     * 单位性质(一级)
     */
    public void setUnitPropFirst(String unitPropFirst) {
        this.unitPropFirst = unitPropFirst;
    }

    /**
     * 单位性质(二级)
     */
    public String getUnitPropSecond() {
        return unitPropSecond;
    }

    /**
     * 单位性质(二级)
     */
    public void setUnitPropSecond(String unitPropSecond) {
        this.unitPropSecond = unitPropSecond;
    }

    /**
     * 是否为特困救助供养机构
     */
    public String getIsPovertyRelief() {
        return isPovertyRelief;
    }

    /**
     * 是否为特困救助供养机构
     */
    public void setIsPovertyRelief(String isPovertyRelief) {
        this.isPovertyRelief = isPovertyRelief;
    }

    /**
     * 是否为光荣院
     */
    public String getIsGloriousHomes() {
        return isGloriousHomes;
    }

    /**
     * 是否为光荣院
     */
    public void setIsGloriousHomes(String isGloriousHomes) {
        this.isGloriousHomes = isGloriousHomes;
    }

    /**
     * 是否为街道（乡镇）区域性养老服务中心
     */
    public String getIsCommunityCareCenter() {
        return isCommunityCareCenter;
    }

    /**
     * 是否为街道（乡镇）区域性养老服务中心
     */
    public void setIsCommunityCareCenter(String isCommunityCareCenter) {
        this.isCommunityCareCenter = isCommunityCareCenter;
    }

    /**
     * 所处位置
     */
    public String getAreaType() {
        return areaType;
    }

    /**
     * 所处位置
     */
    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    /**
     * 兴办主体
     */
    public String getEstablishMainbody() {
        return establishMainbody;
    }

    /**
     * 兴办主体
     */
    public void setEstablishMainbody(String establishMainbody) {
        this.establishMainbody = establishMainbody;
    }

    /**
     * 是否收住能力完好类型老年人
     */
    public String getHaveCapability() {
        return haveCapability;
    }

    /**
     * 是否收住能力完好类型老年人
     */
    public void setHaveCapability(String haveCapability) {
        this.haveCapability = haveCapability;
    }

    /**
     * 是否收住完全失能类型老年人
     */
    public String getHaveTotalDisability() {
        return haveTotalDisability;
    }

    /**
     * 是否收住完全失能类型老年人
     */
    public void setHaveTotalDisability(String haveTotalDisability) {
        this.haveTotalDisability = haveTotalDisability;
    }

    /**
     * 是否收住部分失能类型老年人
     */
    public String getHaveHalfDisability() {
        return haveHalfDisability;
    }

    /**
     * 是否收住部分失能类型老年人
     */
    public void setHaveHalfDisability(String haveHalfDisability) {
        this.haveHalfDisability = haveHalfDisability;
    }

    /**
     * 是否收住特困人员类型老年人
     */
    public String getHaveSpecialDifficult() {
        return haveSpecialDifficult;
    }

    /**
     * 是否收住特困人员类型老年人
     */
    public void setHaveSpecialDifficult(String haveSpecialDifficult) {
        this.haveSpecialDifficult = haveSpecialDifficult;
    }

    /**
     * 是否收住残疾类型老年人
     */
    public String getHaveDeformity() {
        return haveDeformity;
    }

    /**
     * 是否收住残疾类型老年人
     */
    public void setHaveDeformity(String haveDeformity) {
        this.haveDeformity = haveDeformity;
    }

    /**
     * 是否收住优抚类型老年人
     */
    public String getHaveSpecialCare() {
        return haveSpecialCare;
    }

    /**
     * 是否收住优抚类型老年人
     */
    public void setHaveSpecialCare(String haveSpecialCare) {
        this.haveSpecialCare = haveSpecialCare;
    }

    /**
     * 是否收住其他类型老年人
     */
    public String getHaveOther() {
        return haveOther;
    }

    /**
     * 是否收住其他类型老年人
     */
    public void setHaveOther(String haveOther) {
        this.haveOther = haveOther;
    }

    /**
     * 其它类型老年人
     */
    public String getHaveOtherName() {
        return haveOtherName;
    }

    /**
     * 其它类型老年人
     */
    public void setHaveOtherName(String haveOtherName) {
        this.haveOtherName = haveOtherName;
    }

    /**
     * 是否投保责任险
     */
    public String getIsLiabilityInsurance() {
        return isLiabilityInsurance;
    }

    /**
     * 是否投保责任险
     */
    public void setIsLiabilityInsurance(String isLiabilityInsurance) {
        this.isLiabilityInsurance = isLiabilityInsurance;
    }

    /**
     * 是否长护险定点机构
     */
    public String getIsLongCareInsurance() {
        return isLongCareInsurance;
    }

    /**
     * 是否长护险定点机构
     */
    public void setIsLongCareInsurance(String isLongCareInsurance) {
        this.isLongCareInsurance = isLongCareInsurance;
    }

    /**
     * 是否提供居家养老上门服务
     */
    public String getIsAidService() {
        return isAidService;
    }

    /**
     * 是否提供居家养老上门服务
     */
    public void setIsAidService(String isAidService) {
        this.isAidService = isAidService;
    }

    /**
     * 家庭养老床位服务
     */
    public String getIsBedAid() {
        return isBedAid;
    }

    /**
     * 家庭养老床位服务
     */
    public void setIsBedAid(String isBedAid) {
        this.isBedAid = isBedAid;
    }

    /**
     * 家庭养老床位服务服务人次/月
     */
    public Integer getBedAidCount() {
        return bedAidCount;
    }

    /**
     * 家庭养老床位服务服务人次/月
     */
    public void setBedAidCount(Integer bedAidCount) {
        this.bedAidCount = bedAidCount;
    }

    /**
     * 上门助餐服务
     */
    public String getIsMealAid() {
        return isMealAid;
    }

    /**
     * 上门助餐服务
     */
    public void setIsMealAid(String isMealAid) {
        this.isMealAid = isMealAid;
    }

    /**
     * 上门助餐服务服务人次/月
     */
    public Integer getMealAidCount() {
        return mealAidCount;
    }

    /**
     * 上门助餐服务服务人次/月
     */
    public void setMealAidCount(Integer mealAidCount) {
        this.mealAidCount = mealAidCount;
    }

    /**
     * 上门助洁服务
     */
    public String getIsCleanAid() {
        return isCleanAid;
    }

    /**
     * 上门助洁服务
     */
    public void setIsCleanAid(String isCleanAid) {
        this.isCleanAid = isCleanAid;
    }

    /**
     * 上门助洁服务服务人次/月
     */
    public Integer getCleanAidCount() {
        return cleanAidCount;
    }

    /**
     * 上门助洁服务服务人次/月
     */
    public void setCleanAidCount(Integer cleanAidCount) {
        this.cleanAidCount = cleanAidCount;
    }

    /**
     * 上门助行服务
     */
    public String getIsWalkAid() {
        return isWalkAid;
    }

    /**
     * 上门助行服务
     */
    public void setIsWalkAid(String isWalkAid) {
        this.isWalkAid = isWalkAid;
    }

    /**
     * 上门助行服务服务人次/月
     */
    public Integer getWalkAidCount() {
        return walkAidCount;
    }

    /**
     * 上门助行服务服务人次/月
     */
    public void setWalkAidCount(Integer walkAidCount) {
        this.walkAidCount = walkAidCount;
    }

    /**
     * 上门助浴服务
     */
    public String getIsBathAid() {
        return isBathAid;
    }

    /**
     * 上门助浴服务
     */
    public void setIsBathAid(String isBathAid) {
        this.isBathAid = isBathAid;
    }

    /**
     * 上门助浴服务服务人次/月
     */
    public Integer getBathAidCount() {
        return bathAidCount;
    }

    /**
     * 上门助浴服务服务人次/月
     */
    public void setBathAidCount(Integer bathAidCount) {
        this.bathAidCount = bathAidCount;
    }

    /**
     * 上门康复
     */
    public String getIsRecoverAid() {
        return isRecoverAid;
    }

    /**
     * 上门康复
     */
    public void setIsRecoverAid(String isRecoverAid) {
        this.isRecoverAid = isRecoverAid;
    }

    /**
     * 上门康复服务人次/月
     */
    public Integer getRecoverAidCount() {
        return recoverAidCount;
    }

    /**
     * 上门康复服务人次/月
     */
    public void setRecoverAidCount(Integer recoverAidCount) {
        this.recoverAidCount = recoverAidCount;
    }

    /**
     * 上门助医服务
     */
    public String getIsMedicalAid() {
        return isMedicalAid;
    }

    /**
     * 上门助医服务
     */
    public void setIsMedicalAid(String isMedicalAid) {
        this.isMedicalAid = isMedicalAid;
    }

    /**
     * 上门助医服务服务人次/月
     */
    public Integer getMedicalAidCount() {
        return medicalAidCount;
    }

    /**
     * 上门助医服务服务人次/月
     */
    public void setMedicalAidCount(Integer medicalAidCount) {
        this.medicalAidCount = medicalAidCount;
    }

    /**
     * 巡访关爱服务
     */
    public String getIsVisitAid() {
        return isVisitAid;
    }

    /**
     * 巡访关爱服务
     */
    public void setIsVisitAid(String isVisitAid) {
        this.isVisitAid = isVisitAid;
    }

    /**
     * 巡访关爱服务服务人次/月
     */
    public Integer getVisitAidCount() {
        return visitAidCount;
    }

    /**
     * 巡访关爱服务服务人次/月
     */
    public void setVisitAidCount(Integer visitAidCount) {
        this.visitAidCount = visitAidCount;
    }

    /**
     * 其他上门服务
     */
    public String getIsOtherAid() {
        return isOtherAid;
    }

    /**
     * 其他上门服务
     */
    public void setIsOtherAid(String isOtherAid) {
        this.isOtherAid = isOtherAid;
    }

    /**
     * 其他上门服务服务人次/月
     */
    public Integer getOtherAidCount() {
        return otherAidCount;
    }

    /**
     * 其他上门服务服务人次/月
     */
    public void setOtherAidCount(Integer otherAidCount) {
        this.otherAidCount = otherAidCount;
    }

    /**
     * 是否提供社区养老服务
     */
    public String getIsCommunityCare() {
        return isCommunityCare;
    }

    /**
     * 是否提供社区养老服务
     */
    public void setIsCommunityCare(String isCommunityCare) {
        this.isCommunityCare = isCommunityCare;
    }

    /**
     * 日托(日间照料)
     */
    public String getIsDayCare() {
        return isDayCare;
    }

    /**
     * 日托(日间照料)
     */
    public void setIsDayCare(String isDayCare) {
        this.isDayCare = isDayCare;
    }

    /**
     * 日托(日间照料)服务人次/月
     */
    public Integer getDayCareCount() {
        return dayCareCount;
    }

    /**
     * 日托(日间照料)服务人次/月
     */
    public void setDayCareCount(Integer dayCareCount) {
        this.dayCareCount = dayCareCount;
    }

    /**
     * 老年餐桌(食堂)
     */
    public String getIsElderDinning() {
        return isElderDinning;
    }

    /**
     * 老年餐桌(食堂)
     */
    public void setIsElderDinning(String isElderDinning) {
        this.isElderDinning = isElderDinning;
    }

    /**
     * 老年餐桌(食堂)服务人次/月
     */
    public Integer getElderDinningCount() {
        return elderDinningCount;
    }

    /**
     * 老年餐桌(食堂)服务人次/月
     */
    public void setElderDinningCount(Integer elderDinningCount) {
        this.elderDinningCount = elderDinningCount;
    }

    /**
     * 社区文化娱乐
     */
    public String getIsEntertainment() {
        return isEntertainment;
    }

    /**
     * 社区文化娱乐
     */
    public void setIsEntertainment(String isEntertainment) {
        this.isEntertainment = isEntertainment;
    }

    /**
     * 社区文化娱乐服务人次/月
     */
    public Integer getEntertainmentCount() {
        return entertainmentCount;
    }

    /**
     * 社区文化娱乐服务人次/月
     */
    public void setEntertainmentCount(Integer entertainmentCount) {
        this.entertainmentCount = entertainmentCount;
    }

    /**
     * 时间银行(志愿服务)
     */
    public String getIsTimeBank() {
        return isTimeBank;
    }

    /**
     * 时间银行(志愿服务)
     */
    public void setIsTimeBank(String isTimeBank) {
        this.isTimeBank = isTimeBank;
    }

    /**
     * 时间银行(志愿服务)服务人次/月
     */
    public Integer getTimeBankCount() {
        return timeBankCount;
    }

    /**
     * 时间银行(志愿服务)服务人次/月
     */
    public void setTimeBankCount(Integer timeBankCount) {
        this.timeBankCount = timeBankCount;
    }

    /**
     * 社区康复服务
     */
    public String getIsCommunityRecover() {
        return isCommunityRecover;
    }

    /**
     * 社区康复服务
     */
    public void setIsCommunityRecover(String isCommunityRecover) {
        this.isCommunityRecover = isCommunityRecover;
    }

    /**
     * 社区康复服务服务人次/月
     */
    public Integer getCommunityRecoverCount() {
        return communityRecoverCount;
    }

    /**
     * 社区康复服务服务人次/月
     */
    public void setCommunityRecoverCount(Integer communityRecoverCount) {
        this.communityRecoverCount = communityRecoverCount;
    }

    /**
     * 其他社区养老服务
     */
    public String getIsOtherService() {
        return isOtherService;
    }

    /**
     * 其他社区养老服务
     */
    public void setIsOtherService(String isOtherService) {
        this.isOtherService = isOtherService;
    }

    /**
     * 其它服务名称
     */
    public String getOtherServiceName() {
        return otherServiceName;
    }

    /**
     * 其它服务名称
     */
    public void setOtherServiceName(String otherServiceName) {
        this.otherServiceName = otherServiceName;
    }

    /**
     * 其它服务服务人次/月
     */
    public Integer getOtherServiceCount() {
        return otherServiceCount;
    }

    /**
     * 其它服务服务人次/月
     */
    public void setOtherServiceCount(Integer otherServiceCount) {
        this.otherServiceCount = otherServiceCount;
    }

    /**
     * 是否开展医疗服务
     */
    public String getIsMedicalService() {
        return isMedicalService;
    }

    /**
     * 是否开展医疗服务
     */
    public void setIsMedicalService(String isMedicalService) {
        this.isMedicalService = isMedicalService;
    }

    /**
     * 是否内设医疗机构
     */
    public String getIsMedicalInst() {
        return isMedicalInst;
    }

    /**
     * 是否内设医疗机构
     */
    public void setIsMedicalInst(String isMedicalInst) {
        this.isMedicalInst = isMedicalInst;
    }

    /**
     * 自办医院
     */
    public String getIsPrivateHospital() {
        return isPrivateHospital;
    }

    /**
     * 自办医院
     */
    public void setIsPrivateHospital(String isPrivateHospital) {
        this.isPrivateHospital = isPrivateHospital;
    }

    /**
     * 护理站
     */
    public String getIsNusringStation() {
        return isNusringStation;
    }

    /**
     * 护理站
     */
    public void setIsNusringStation(String isNusringStation) {
        this.isNusringStation = isNusringStation;
    }

    /**
     * 医务室
     */
    public String getIsClinic() {
        return isClinic;
    }

    /**
     * 医务室
     */
    public void setIsClinic(String isClinic) {
        this.isClinic = isClinic;
    }

    /**
     * 是否与医疗机构签约合作（协议医疗保障）
     */
    public String getIsMedicalContracted() {
        return isMedicalContracted;
    }

    /**
     * 是否与医疗机构签约合作（协议医疗保障）
     */
    public void setIsMedicalContracted(String isMedicalContracted) {
        this.isMedicalContracted = isMedicalContracted;
    }

    /**
     * 医院
     */
    public String getIsHospital() {
        return isHospital;
    }

    /**
     * 医院
     */
    public void setIsHospital(String isHospital) {
        this.isHospital = isHospital;
    }

    /**
     * 乡镇卫生院
     */
    public String getIsTownHospital() {
        return isTownHospital;
    }

    /**
     * 乡镇卫生院
     */
    public void setIsTownHospital(String isTownHospital) {
        this.isTownHospital = isTownHospital;
    }

    /**
     * 村(社区)卫生服务站(中心)
     */
    public String getIsCommunityHospital() {
        return isCommunityHospital;
    }

    /**
     * 村(社区)卫生服务站(中心)
     */
    public void setIsCommunityHospital(String isCommunityHospital) {
        this.isCommunityHospital = isCommunityHospital;
    }

    /**
     * 是否取得医疗机构执业许可证书
     */
    public String getIsMedicalCert() {
        return isMedicalCert;
    }

    /**
     * 是否取得医疗机构执业许可证书
     */
    public void setIsMedicalCert(String isMedicalCert) {
        this.isMedicalCert = isMedicalCert;
    }

    /**
     * 是否取得医疗保险定点医疗机构资格证书
     */
    public String getIsMedicalInsuranceUnit() {
        return isMedicalInsuranceUnit;
    }

    /**
     * 是否取得医疗保险定点医疗机构资格证书
     */
    public void setIsMedicalInsuranceUnit(String isMedicalInsuranceUnit) {
        this.isMedicalInsuranceUnit = isMedicalInsuranceUnit;
    }

    /**
     * 是否享受政府扶持优惠政策内容
     */
    public String getIsGovernAssist() {
        return isGovernAssist;
    }

    /**
     * 是否享受政府扶持优惠政策内容
     */
    public void setIsGovernAssist(String isGovernAssist) {
        this.isGovernAssist = isGovernAssist;
    }

    /**
     * 税收优惠政策
     */
    public String getIsTaxBreak() {
        return isTaxBreak;
    }

    /**
     * 税收优惠政策
     */
    public void setIsTaxBreak(String isTaxBreak) {
        this.isTaxBreak = isTaxBreak;
    }

    /**
     * 建设补贴优惠政策
     */
    public String getIsConstructBreak() {
        return isConstructBreak;
    }

    /**
     * 建设补贴优惠政策
     */
    public void setIsConstructBreak(String isConstructBreak) {
        this.isConstructBreak = isConstructBreak;
    }

    /**
     * 运营补贴优惠政策
     */
    public String getIsOperaBreak() {
        return isOperaBreak;
    }

    /**
     * 运营补贴优惠政策
     */
    public void setIsOperaBreak(String isOperaBreak) {
        this.isOperaBreak = isOperaBreak;
    }

    /**
     * 土地划拨优惠政策
     */
    public String getIsLandBreak() {
        return isLandBreak;
    }

    /**
     * 土地划拨优惠政策
     */
    public void setIsLandBreak(String isLandBreak) {
        this.isLandBreak = isLandBreak;
    }

    /**
     * 行政事业收费减免优惠政策
     */
    public String getIsAdminBreak() {
        return isAdminBreak;
    }

    /**
     * 行政事业收费减免优惠政策
     */
    public void setIsAdminBreak(String isAdminBreak) {
        this.isAdminBreak = isAdminBreak;
    }

    /**
     * 居民用水用电价格优惠政策
     */
    public String getIsWaterElecBreak() {
        return isWaterElecBreak;
    }

    /**
     * 居民用水用电价格优惠政策
     */
    public void setIsWaterElecBreak(String isWaterElecBreak) {
        this.isWaterElecBreak = isWaterElecBreak;
    }

    /**
     * 收费方式
     */
    public String getChargeType() {
        return chargeType;
    }

    /**
     * 收费方式
     */
    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    /**
     * 是否采用会员制方式收费
     */
    public String getIsMembership() {
        return isMembership;
    }

    /**
     * 是否采用会员制方式收费
     */
    public void setIsMembership(String isMembership) {
        this.isMembership = isMembership;
    }

    /**
     * 护理费（最高）
     */
    public Double getNursingUp() {
        return nursingUp;
    }

    /**
     * 护理费（最高）
     */
    public void setNursingUp(Double nursingUp) {
        this.nursingUp = nursingUp;
    }

    /**
     * 护理费（最低）
     */
    public Double getNursingLow() {
        return nursingLow;
    }

    /**
     * 护理费（最低）
     */
    public void setNursingLow(Double nursingLow) {
        this.nursingLow = nursingLow;
    }

    /**
     * 床位费（最高）
     */
    public Double getBedUp() {
        return bedUp;
    }

    /**
     * 床位费（最高）
     */
    public void setBedUp(Double bedUp) {
        this.bedUp = bedUp;
    }

    /**
     * 床位费（最低）
     */
    public Double getBedLow() {
        return bedLow;
    }

    /**
     * 床位费（最低）
     */
    public void setBedLow(Double bedLow) {
        this.bedLow = bedLow;
    }

    /**
     * 膳食费（最高）
     */
    public Double getMealUp() {
        return mealUp;
    }

    /**
     * 膳食费（最高）
     */
    public void setMealUp(Double mealUp) {
        this.mealUp = mealUp;
    }

    /**
     * 膳食费（最低）
     */
    public Double getMealLow() {
        return mealLow;
    }

    /**
     * 膳食费（最低）
     */
    public void setMealLow(Double mealLow) {
        this.mealLow = mealLow;
    }

    /**
     * 押金/备用金（最高）
     */
    public Double getDepositUp() {
        return depositUp;
    }

    /**
     * 押金/备用金（最高）
     */
    public void setDepositUp(Double depositUp) {
        this.depositUp = depositUp;
    }

    /**
     * 押金/备用金（最低）
     */
    public Double getDepositLow() {
        return depositLow;
    }

    /**
     * 押金/备用金（最低）
     */
    public void setDepositLow(Double depositLow) {
        this.depositLow = depositLow;
    }

    /**
     * 是否其他收费
     */
    public String getIsOtherFee() {
        return isOtherFee;
    }

    /**
     * 是否其他收费
     */
    public void setIsOtherFee(String isOtherFee) {
        this.isOtherFee = isOtherFee;
    }

    /**
     * 其他收费名称
     */
    public String getOtherFeeName() {
        return otherFeeName;
    }

    /**
     * 其他收费名称
     */
    public void setOtherFeeName(String otherFeeName) {
        this.otherFeeName = otherFeeName;
    }

    /**
     * 其他收费（最低）
     */
    public Double getOtherFeeLow() {
        return otherFeeLow;
    }

    /**
     * 其他收费（最低）
     */
    public void setOtherFeeLow(Double otherFeeLow) {
        this.otherFeeLow = otherFeeLow;
    }

    /**
     * 其他收费（最高）
     */
    public Double getOtherFeeUp() {
        return otherFeeUp;
    }

    /**
     * 其他收费（最高）
     */
    public void setOtherFeeUp(Double otherFeeUp) {
        this.otherFeeUp = otherFeeUp;
    }

    /**
     * 月末收住老年人数
     */
    public Integer getTotalElders() {
        return totalElders;
    }

    /**
     * 月末收住老年人数
     */
    public void setTotalElders(Integer totalElders) {
        this.totalElders = totalElders;
    }

    /**
     * 机构生存状态
     */
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * 机构生存状态
     */
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * 是否备案
     */
    public String getIsRecord() {
        return isRecord;
    }

    /**
     * 是否备案
     */
    public void setIsRecord(String isRecord) {
        this.isRecord = isRecord;
    }

    /**
     * 备案时间
     */
    public String getRecordDate() {
        return recordDate;
    }

    /**
     * 备案时间
     */
    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    /**
     * 经度
     */
    public Double getLng() {
        return lng;
    }

    /**
     * 经度
     */
    public void setLng(Double lng) {
        this.lng = lng;
    }

    /**
     * 纬度
     */
    public Double getLat() {
        return lat;
    }

    /**
     * 纬度
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * 
     */
    public String getStar() {
        return star;
    }

    /**
     * 
     */
    public void setStar(String star) {
        this.star = star;
    }

    /**
     * 查询关键字
     */
    public String getSearchValue() {
        return searchValue;
    }

    /**
     * 查询关键字
     */
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 更新者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 删除标志
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标志
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}