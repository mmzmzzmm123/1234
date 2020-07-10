package com.ruoyi.project.data.cases.domain;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 住宅成交案例
 */
public class OriginalResidenceSaleClosingCase {
    private static final SimpleDateFormat simplaDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static List<String> juzhu = new LinkedList<>();
    private static List<String> bangong = new LinkedList<>();
    private static List<String> shangye = new LinkedList<>();

    static {
        juzhu.add("办公楼");
        juzhu.add("商铺");
        juzhu.add("未知");
        juzhu.add("其它");
        juzhu.add("工厂");
        juzhu.add("车库");

        bangong.add("办公");
        bangong.add("办公楼");
        bangong.add("写字楼");

        shangye.add("商铺");
        shangye.add("商店");
    }

    private Integer yearMonth;

    /**
     * 唯一标识
     */
    private String caseId;
    /**
     * 小区名称
     */
    private String caseCommunityName;
    /**
     * 房屋地址
     */
    private String caseAddress;
    /**
     * 区域
     */
    private String caseCountyName;
    private String cleanCountyName;
    /**
     * 板块
     */
    private String caseBlockName;
    private String cleanBlockName;
    /**
     * 环线
     */
    private String caseLoopName;
    private String cleanLoopName;
    /**
     * 合同编号
     */
    private String caseContractNumber;
    /**
     * 房源编号
     */
    private String caseNumber;
    /**
     * 户型
     */
    private String caseApartmentLayout;
    /**
     * 面积
     */
    private BigDecimal caseArea;
    /**
     * 朝向
     */
    private String caseToward;
    /**
     * 楼层
     */
    private String caseStorey;
    /**
     * 总楼层
     */
    private String cleanTotalFloor;
    /**
     * 地上层数
     */
    private Integer cleanGroundFloor;
    /**
     * 所在层
     */
    private Integer cleanCurrentFloor;
    /**
     * 所在楼层描述
     */
    private String cleanCurrentFloorDesc;
    /**
     * 登记日期
     */
    private Date caseRegisterDate;
    /**
     * 签约日期
     */
    private Date caseSigningDate;
    /**
     * 挂牌价格
     */
    private BigDecimal caseOpeningPrice;
    /**
     * 成交总价
     */
    private BigDecimal caseTotalPrice;
    /**
     * 成交单价
     */
    private BigDecimal caseUnitPrice;
    /**
     * 抓取日期
     */
    private Date caseGetDate;
    /**
     * 成交URL
     */
    private String caseUrl;
    /**
     * 中介公司
     */
    private String agencyName;
    /**
     * 中介类型
     */
    private String agencyType;
    /**
     * 卖家类型
     */
    private String sellerType;
    /**
     * 买家类型
     */
    private String buyerType;
    /**
     * 出生年月日
     */
    private Date birthday;
    /**
     * 抓取网站名
     */
    private String caseProvider;
    /**
     * 房屋类型(公寓	口径后面提供)
     */
    private String caseHouseType;
    /**
     * 成交类型	必填		二手	取值：一手、二手
     */
    private Integer cleanCaseType;
    /**
     * 单套地址
     */
    private String cleanCondoAddress;
    /**
     * 楼栋地址
     */
    private String cleanBuildingAddress;
    /**
     * 房屋地址(一手二手)(规范)			浦城路366弄8号1803	地址匹配中间结果
     * 应该是单套地址
     */
    private String cleanAddress;
    /**
     * 单套id
     */
    private String cleanCondoId;
    /**
     * 楼栋id
     */
    private String cleanBuildingId;
    /**
     * 小区id
     */
    private String cleanCommunityId;
    /**
     * 楼栋系数
     */
    private BigDecimal buildingCoefficient;
    /**
     * 单套系数
     */
    private BigDecimal condoAreaCoefficient;
    /**
     * 竣工日期
     */
    private Integer caseYear;
    /**
     * 年份系数
     */
    private BigDecimal yearCoefficient;
    /**
     * 主力面积基价
     */
    private BigDecimal areaCoefficient;
    /**
     * 物业档次
     */
    private String cleanProjectLevel;
    /**
     * 电梯
     */
    private String caseElevator;
    private Boolean cleanElevator;
    /**
     * 电梯系数
     */
    private Integer elevatorCoefficient;
    /**
     * 没有电梯系数
     */
    private Integer noElevatorCoefficient;
    /**
     * 楼层修正系数
     */
    private BigDecimal floorCoefficient;
    /***
     * 评估单价
     */
    private BigDecimal appraiseUnitPrice;
    /**
     * 评估总价
     */
    private BigDecimal appraiseTotalPrice;

    /**
     * 物业类型
     */
    private String cleanPropertyType;
    /**
     * 房屋性质
     */
    private String caseHouseProperty;
    /**
     * 计算总价
     */
    private BigDecimal computeTotalPrice;
    /**
     * 计算单价
     */
    private BigDecimal computeUnitPrice;
    /**
     * 参考总价
     */
    private BigDecimal refTotalPrice;
    /**
     * 参考单价
     */
    private BigDecimal refUnitPrice;

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getCaseCommunityName() {
        return caseCommunityName;
    }

    public void setCaseCommunityName(String caseCommunityName) {
        this.caseCommunityName = caseCommunityName;
    }

    public String getCaseAddress() {
        return caseAddress;
    }

    public void setCaseAddress(String caseAddress) {
        this.caseAddress = caseAddress;
    }

    public String getCaseCountyName() {
        return caseCountyName;
    }

    public void setCaseCountyName(String caseCountyName) {
        this.caseCountyName = caseCountyName;
    }

    public String getCleanCountyName() {
        return cleanCountyName;
    }

    public void setCleanCountyName(String cleanCountyName) {
        this.cleanCountyName = cleanCountyName;
    }

    public String getCaseBlockName() {
        return caseBlockName;
    }

    public void setCaseBlockName(String caseBlockName) {
        this.caseBlockName = caseBlockName;
    }

    public String getCleanBlockName() {
        return cleanBlockName;
    }

    public void setCleanBlockName(String cleanBlockName) {
        this.cleanBlockName = cleanBlockName;
    }

    public String getCaseLoopName() {
        return caseLoopName;
    }

    public void setCaseLoopName(String caseLoopName) {
        this.caseLoopName = caseLoopName;
    }

    public String getCleanLoopName() {
        return cleanLoopName;
    }

    public void setCleanLoopName(String cleanLoopName) {
        this.cleanLoopName = cleanLoopName;
    }

    public String getCaseContractNumber() {
        return caseContractNumber;
    }

    public void setCaseContractNumber(String caseContractNumber) {
        this.caseContractNumber = caseContractNumber;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCaseApartmentLayout() {
        return caseApartmentLayout;
    }

    public void setCaseApartmentLayout(String caseApartmentLayout) {
        this.caseApartmentLayout = caseApartmentLayout;
    }

    public BigDecimal getCaseArea() {
        return caseArea;
    }

    public void setCaseArea(BigDecimal caseArea) {
        this.caseArea = caseArea;
    }

    public String getCaseToward() {
        return caseToward;
    }

    public void setCaseToward(String caseToward) {
        this.caseToward = caseToward;
    }

    public String getCaseStorey() {
        return caseStorey;
    }

    public void setCaseStorey(String caseStorey) {
        this.caseStorey = caseStorey;
    }

    public String getCleanTotalFloor() {
        return cleanTotalFloor;
    }

    public void setCleanTotalFloor(String cleanTotalFloor) {
        this.cleanTotalFloor = cleanTotalFloor;
    }

    public Integer getCleanGroundFloor() {
        return cleanGroundFloor;
    }

    public void setCleanGroundFloor(Integer cleanGroundFloor) {
        this.cleanGroundFloor = cleanGroundFloor;
    }

    public Integer getCleanCurrentFloor() {
        return cleanCurrentFloor;
    }

    public void setCleanCurrentFloor(Integer cleanCurrentFloor) {
        this.cleanCurrentFloor = cleanCurrentFloor;
    }

    public Date getCaseRegisterDate() {
        return caseRegisterDate;
    }

    public void setCaseRegisterDate(Date caseRegisterDate) {
        this.caseRegisterDate = caseRegisterDate;
    }

    public Date getCaseSigningDate() {
        return caseSigningDate;
    }

    public void setCaseSigningDate(Date caseSigningDate) {
        this.caseSigningDate = caseSigningDate;
    }

    public BigDecimal getCaseOpeningPrice() {
        return caseOpeningPrice;
    }

    public void setCaseOpeningPrice(BigDecimal caseOpeningPrice) {
        this.caseOpeningPrice = caseOpeningPrice;
    }

    public BigDecimal getCaseTotalPrice() {
        return caseTotalPrice;
    }

    public void setCaseTotalPrice(BigDecimal caseTotalPrice) {
        this.caseTotalPrice = caseTotalPrice;
    }

    public BigDecimal getCaseUnitPrice() {
        return caseUnitPrice;
    }

    public void setCaseUnitPrice(BigDecimal caseUnitPrice) {
        this.caseUnitPrice = caseUnitPrice;
    }

    public Date getCaseGetDate() {
        return caseGetDate;
    }

    public void setCaseGetDate(Date caseGetDate) {
        this.caseGetDate = caseGetDate;
    }

    public String getCaseUrl() {
        return caseUrl;
    }

    public void setCaseUrl(String caseUrl) {
        this.caseUrl = caseUrl;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyType() {
        return agencyType;
    }

    public void setAgencyType(String agencyType) {
        this.agencyType = agencyType;
    }

    public String getSellerType() {
        return sellerType;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCaseProvider() {
        return caseProvider;
    }

    public void setCaseProvider(String caseProvider) {
        this.caseProvider = caseProvider;
    }

    public String getCaseHouseType() {
        return caseHouseType;
    }

    public void setCaseHouseType(String caseHouseType) {
        this.caseHouseType = caseHouseType;
    }

    public Integer getCleanCaseType() {
        return cleanCaseType;
    }

    public void setCleanCaseType(Integer cleanCaseType) {
        this.cleanCaseType = cleanCaseType;
    }

    public String getCleanCondoAddress() {
        return cleanCondoAddress;
    }

    public void setCleanCondoAddress(String cleanCondoAddress) {
        this.cleanCondoAddress = cleanCondoAddress;
    }

    public String getCleanBuildingAddress() {
        return cleanBuildingAddress;
    }

    public void setCleanBuildingAddress(String cleanBuildingAddress) {
        this.cleanBuildingAddress = cleanBuildingAddress;
    }

    public String getCleanAddress() {
        return cleanAddress;
    }

    public void setCleanAddress(String cleanAddress) {
        this.cleanAddress = cleanAddress;
    }

    public String getCleanCondoId() {
        return cleanCondoId;
    }

    public void setCleanCondoId(String cleanCondoId) {
        this.cleanCondoId = cleanCondoId;
    }

    public String getCleanBuildingId() {
        return cleanBuildingId;
    }

    public void setCleanBuildingId(String cleanBuildingId) {
        this.cleanBuildingId = cleanBuildingId;
    }

    public String getCleanCommunityId() {
        return cleanCommunityId;
    }

    public void setCleanCommunityId(String cleanCommunityId) {
        this.cleanCommunityId = cleanCommunityId;
    }

    public BigDecimal getBuildingCoefficient() {
        return buildingCoefficient;
    }

    public void setBuildingCoefficient(BigDecimal buildingCoefficient) {
        this.buildingCoefficient = buildingCoefficient;
    }

    public BigDecimal getCondoAreaCoefficient() {
        return condoAreaCoefficient;
    }

    public void setCondoAreaCoefficient(BigDecimal condoAreaCoefficient) {
        this.condoAreaCoefficient = condoAreaCoefficient;
    }

    public Integer getCaseYear() {
        return caseYear;
    }

    public void setCaseYear(Integer caseYear) {
        this.caseYear = caseYear;
    }

    public BigDecimal getYearCoefficient() {
        return yearCoefficient;
    }

    public void setYearCoefficient(BigDecimal yearCoefficient) {
        this.yearCoefficient = yearCoefficient;
    }

    public BigDecimal getAreaCoefficient() {
        return areaCoefficient;
    }

    public void setAreaCoefficient(BigDecimal areaCoefficient) {
        this.areaCoefficient = areaCoefficient;
    }

    public String getCleanProjectLevel() {
        return cleanProjectLevel;
    }

    public void setCleanProjectLevel(String cleanProjectLevel) {
        this.cleanProjectLevel = cleanProjectLevel;
    }

    public String getCaseElevator() {
        return caseElevator;
    }

    public void setCaseElevator(String caseElevator) {
        this.caseElevator = caseElevator;
    }

    public Boolean getCleanElevator() {
        return cleanElevator;
    }

    public void setCleanElevator(Boolean cleanElevator) {
        this.cleanElevator = cleanElevator;
    }

    public Integer getElevatorCoefficient() {
        return elevatorCoefficient;
    }

    public void setElevatorCoefficient(Integer elevatorCoefficient) {
        this.elevatorCoefficient = elevatorCoefficient;
    }

    public Integer getNoElevatorCoefficient() {
        return noElevatorCoefficient;
    }

    public void setNoElevatorCoefficient(Integer noElevatorCoefficient) {
        this.noElevatorCoefficient = noElevatorCoefficient;
    }

    public BigDecimal getFloorCoefficient() {
        return floorCoefficient;
    }

    public void setFloorCoefficient(BigDecimal floorCoefficient) {
        this.floorCoefficient = floorCoefficient;
    }

    public BigDecimal getAppraiseUnitPrice() {
        return appraiseUnitPrice;
    }

    public void setAppraiseUnitPrice(BigDecimal appraiseUnitPrice) {
        this.appraiseUnitPrice = appraiseUnitPrice;
    }

    public BigDecimal getAppraiseTotalPrice() {
        return appraiseTotalPrice;
    }

    public void setAppraiseTotalPrice(BigDecimal appraiseTotalPrice) {
        this.appraiseTotalPrice = appraiseTotalPrice;
    }

    public String getCleanPropertyType() {
        return cleanPropertyType;
    }

    public void setCleanPropertyType(String cleanPropertyType) {
        this.cleanPropertyType = cleanPropertyType;
    }

    public String getCaseHouseProperty() {
        return caseHouseProperty;
    }

    public void setCaseHouseProperty(String caseHouseProperty) {
        this.caseHouseProperty = caseHouseProperty;
    }

    public BigDecimal getComputeTotalPrice() {
        return computeTotalPrice;
    }

    public void setComputeTotalPrice(BigDecimal computeTotalPrice) {
        this.computeTotalPrice = computeTotalPrice;
    }

    public BigDecimal getComputeUnitPrice() {
        return computeUnitPrice;
    }

    public void setComputeUnitPrice(BigDecimal computeUnitPrice) {
        this.computeUnitPrice = computeUnitPrice;
    }

    public BigDecimal getRefTotalPrice() {
        return refTotalPrice;
    }

    public void setRefTotalPrice(BigDecimal refTotalPrice) {
        this.refTotalPrice = refTotalPrice;
    }

    public BigDecimal getRefUnitPrice() {
        return refUnitPrice;
    }

    public void setRefUnitPrice(BigDecimal refUnitPrice) {
        this.refUnitPrice = refUnitPrice;
    }

    public String getCleanCurrentFloorDesc() {
        return cleanCurrentFloorDesc;
    }

    public void setCleanCurrentFloorDesc(String cleanCurrentFloorDesc) {
        this.cleanCurrentFloorDesc = cleanCurrentFloorDesc;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    /**
     * 构造caseId
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String generateCaseId() {
        String text = getCaseCountyName() + getCaseAddress();
        if (null != getCaseSigningDate()) {
            text += simplaDateFormat.format(getCaseSigningDate());
        }

        return DigestUtils.md5DigestAsHex(text.getBytes());
    }


    public String refinePropertyType() {
        String houseType = getCaseHouseType();
        if (StringUtils.isEmpty(houseType)) return "其他";

        // 不包含
        boolean exists = juzhu.stream().filter(x -> x.equals(houseType)).findAny().isPresent();
        if (!exists)
            return "居住";

        exists = bangong.stream().filter(x -> x.equals(houseType)).findAny().isPresent();
        if (!exists)
            return "办公";

        exists = shangye.stream().filter(x -> x.equals(houseType)).findAny().isPresent();
        if (!exists)
            return "商业";

        return "其他";
    }

    /**
     * 从室号中提取所在层
     *
     * @return
     */
    public Integer refineCurrentFloor() {
        if (StringUtils.isEmpty(getCaseAddress())) {
            return null;
        }

        Pattern pattern = Pattern.compile("(\\d+)(室)$");

        Matcher matcher = pattern.matcher(getCaseAddress());
        if (!matcher.find()) {
            return null;
        }

        String value = matcher.group(1);
        Integer num = new Integer(value);
        if (num > 1000 || num > 100) {
            return num / 100;
        }


        return null;
    }

    /**
     * 获取楼栋地址
     * 1. 去掉abc室
     * 2. 去掉末尾是数字的
     *
     * @return
     */
    public String refineBuildingAddress() {
        if (StringUtils.isEmpty(getCaseAddress())) {
            return null;
        }

        Pattern pattern = Pattern.compile("(\\d+)室$");
        Matcher matcher = pattern.matcher(getCaseAddress());
        if (matcher.find()) {
            return getCaseAddress().replace(matcher.group(0), "");
        }

        pattern = Pattern.compile("(\\d+)$");
        matcher = pattern.matcher(getCaseAddress());
        if (matcher.find()) {
            return getCaseAddress().replace(matcher.group(0), "");
        }

        return null;
    }
}
