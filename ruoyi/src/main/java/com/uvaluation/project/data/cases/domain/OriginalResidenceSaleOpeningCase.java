package com.uvaluation.project.data.cases.domain;

import com.uvaluation.common.utils.StringUtils;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OriginalResidenceSaleOpeningCase {

    // 东南,	西南,	西北,	东北,	南,	东,	西,	北
    private static String[] towardArray = new String[]{"东南", "西南", "西北", "东北", "南", "东", "西", "北"};
    private Integer yearMonth;
    /**
     * 案例ID 唯一
     */
    private String caseId;
    /**
     * 链家案例id
     */
    private String caseLianJiaId;
    /**
     * 链家小区编码
     */
    private String caseLianJiaCommunityId;
    /**
     * 房源标题
     */
    private String caseTitle;
    /**
     * 物业类型
     */
    private String cleanPropertyType;
    /**
     * 户型
     */
    private String caseApartmentLayout;
    /**
     * 房屋结构
     */
    private String caseHouseStructure;
    /**
     * 建筑面积
     */
    private BigDecimal caseArea;
    /**
     * 地下面积
     */
    private BigDecimal caseUndergroundArea;
    /**
     * 朝向
     */
    private String caseToward;
    /**
     * 朝向
     */
    private String cleanToward;
    /**
     * 楼层
     */
    private String caseStorey;
    /**
     * 总层
     */
    private Integer cleanTotalFloor;
    /**
     * 所在层
     */
    private String cleanCurrentFloorDesc;
    /**
     * 上架日期
     */
    private Date caseUpDate;
    /**
     * 下架日期
     */
    private Date caseDownDate;
    /**
     * 有无电梯
     */
    private String caseElevator;
    /**
     * 有无电梯
     */
    private Boolean cleanElevator;
    /**
     * 电梯梯户
     */
    private String caseTiHu;

    /**
     * 装修
     */
    private String caseDecoration;
    /**
     * 装修
     */
    private String cleanDecoration;
    /**
     * 竣工年
     */
    private String caseYear;
    /**
     * 竣工年
     */
    private Integer cleanYear;
    /**
     * 地址
     */
    private String caseAddress;
    /**
     *
     */
    private String vid;
    /**
     * 产权性质
     */
    private String caseChanQuanXingZhi;
    /**
     * 售价
     */
    private BigDecimal casePrice;
    /**
     * 总价
     */
    private BigDecimal cleanTotalPrice;
    /**
     * 单价
     */
    private BigDecimal cleanUnitPrice;
    /**
     * 小区名称
     */
    private String caseCommunityName;
    /**
     * 累计带看
     */
    private Integer caseVisitedNum;
    /**
     * 首次带看日
     */
    private Date caseFirstVisitTime;
    /**
     * 最近带看日
     */
    private Date caseLatestVisitTime;
    /**
     * 近15日带看
     */
    private Integer caseVisitedNum15;
    /**
     * 近30天带看
     */
    private Integer caseVisitedNum30;
    /**
     * 案例URL
     */
    private String caseUrl;
    /**
     * 小区URL
     */
    private String caseCommunityUrl;
    /**
     * 抓取日期
     */
    private Date caseGetDate;
    /**
     * 抓取网站名
     */
    private String caseProvider;
    /**
     * 小区名称-系统
     */
    private String uvCommunityName;
    /**
     * 小区地址-系统
     */
    private String uvCommunityAddress;
    /**
     * 区域
     */
    private String uvCounty;
    /**
     * 板块
     */
    private String uvBlock;
    /**
     * 环线
     */
    private String uvLoop;
    /**
     * 面积系数
     */
    private BigDecimal areaCoefficient;
    /**
     * 朝向系数
     */
    private BigDecimal towardCoefficient;
    /**
     * 楼层系数
     */
    private BigDecimal floorCoefficient;
    /**
     * 建成年代系数
     */
    private BigDecimal yearCoefficient;
    /**
     * 楼栋系数
     */
    private BigDecimal buildingCoefficient;
    /**
     * 最终成交日期
     */
    private Date caseLastDeal;
    /**
     *
     */
    private String newCaseId;

    public static String[] getTowardArray() {
        return towardArray;
    }

    public static void setTowardArray(String[] towardArray) {
        OriginalResidenceSaleOpeningCase.towardArray = towardArray;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseLianJiaId() {
        return caseLianJiaId;
    }

    public void setCaseLianJiaId(String caseLianJiaId) {
        this.caseLianJiaId = caseLianJiaId;
    }

    public String getCaseLianJiaCommunityId() {
        return caseLianJiaCommunityId;
    }

    public void setCaseLianJiaCommunityId(String caseLianJiaCommunityId) {
        this.caseLianJiaCommunityId = caseLianJiaCommunityId;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getCleanPropertyType() {
        return cleanPropertyType;
    }

    public void setCleanPropertyType(String cleanPropertyType) {
        this.cleanPropertyType = cleanPropertyType;
    }

    public String getCaseApartmentLayout() {
        return caseApartmentLayout;
    }

    public void setCaseApartmentLayout(String caseApartmentLayout) {
        this.caseApartmentLayout = caseApartmentLayout;
    }

    public String getCaseHouseStructure() {
        return caseHouseStructure;
    }

    public void setCaseHouseStructure(String caseHouseStructure) {
        this.caseHouseStructure = caseHouseStructure;
    }

    public BigDecimal getCaseArea() {
        return caseArea;
    }

    public void setCaseArea(BigDecimal caseArea) {
        this.caseArea = caseArea;
    }

    public BigDecimal getCaseUndergroundArea() {
        return caseUndergroundArea;
    }

    public void setCaseUndergroundArea(BigDecimal caseUndergroundArea) {
        this.caseUndergroundArea = caseUndergroundArea;
    }

    public String getCaseToward() {
        return caseToward;
    }

    public void setCaseToward(String caseToward) {
        this.caseToward = caseToward;
    }

    public String getCleanToward() {
        return cleanToward;
    }

    public void setCleanToward(String cleanToward) {
        this.cleanToward = cleanToward;
    }

    public String getCaseStorey() {
        return caseStorey;
    }

    public void setCaseStorey(String caseStorey) {
        this.caseStorey = caseStorey;
    }

    public Integer getCleanTotalFloor() {
        return cleanTotalFloor;
    }

    public void setCleanTotalFloor(Integer cleanTotalFloor) {
        this.cleanTotalFloor = cleanTotalFloor;
    }

    public String getCleanCurrentFloorDesc() {
        return cleanCurrentFloorDesc;
    }

    public void setCleanCurrentFloorDesc(String cleanCurrentFloorDesc) {
        this.cleanCurrentFloorDesc = cleanCurrentFloorDesc;
    }

    public Date getCaseUpDate() {
        return caseUpDate;
    }

    public void setCaseUpDate(Date caseUpDate) {
        this.caseUpDate = caseUpDate;
    }

    public Date getCaseDownDate() {
        return caseDownDate;
    }

    public void setCaseDownDate(Date caseDownDate) {
        this.caseDownDate = caseDownDate;
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

    public String getCaseTiHu() {
        return caseTiHu;
    }

    public void setCaseTiHu(String caseTiHu) {
        this.caseTiHu = caseTiHu;
    }

    public String getCaseDecoration() {
        return caseDecoration;
    }

    public void setCaseDecoration(String caseDecoration) {
        this.caseDecoration = caseDecoration;
    }

    public String getCleanDecoration() {
        return cleanDecoration;
    }

    public void setCleanDecoration(String cleanDecoration) {
        this.cleanDecoration = cleanDecoration;
    }

    public String getCaseYear() {
        return caseYear;
    }

    public void setCaseYear(String caseYear) {
        this.caseYear = caseYear;
    }

    public Integer getCleanYear() {
        return cleanYear;
    }

    public void setCleanYear(Integer cleanYear) {
        this.cleanYear = cleanYear;
    }

    public String getCaseAddress() {
        return caseAddress;
    }

    public void setCaseAddress(String caseAddress) {
        this.caseAddress = caseAddress;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCaseChanQuanXingZhi() {
        return caseChanQuanXingZhi;
    }

    public void setCaseChanQuanXingZhi(String caseChanQuanXingZhi) {
        this.caseChanQuanXingZhi = caseChanQuanXingZhi;
    }

    public BigDecimal getCasePrice() {
        return casePrice;
    }

    public void setCasePrice(BigDecimal casePrice) {
        this.casePrice = casePrice;
    }

    public BigDecimal getCleanTotalPrice() {
        return cleanTotalPrice;
    }

    public void setCleanTotalPrice(BigDecimal cleanTotalPrice) {
        this.cleanTotalPrice = cleanTotalPrice;
    }

    public BigDecimal getCleanUnitPrice() {
        return cleanUnitPrice;
    }

    public void setCleanUnitPrice(BigDecimal cleanUnitPrice) {
        this.cleanUnitPrice = cleanUnitPrice;
    }

    public String getCaseCommunityName() {
        return caseCommunityName;
    }

    public void setCaseCommunityName(String caseCommunityName) {
        this.caseCommunityName = caseCommunityName;
    }

    public Integer getCaseVisitedNum() {
        return caseVisitedNum;
    }

    public void setCaseVisitedNum(Integer caseVisitedNum) {
        this.caseVisitedNum = caseVisitedNum;
    }

    public Date getCaseFirstVisitTime() {
        return caseFirstVisitTime;
    }

    public void setCaseFirstVisitTime(Date caseFirstVisitTime) {
        this.caseFirstVisitTime = caseFirstVisitTime;
    }

    public Date getCaseLatestVisitTime() {
        return caseLatestVisitTime;
    }

    public void setCaseLatestVisitTime(Date caseLatestVisitTime) {
        this.caseLatestVisitTime = caseLatestVisitTime;
    }

    public Integer getCaseVisitedNum15() {
        return caseVisitedNum15;
    }

    public void setCaseVisitedNum15(Integer caseVisitedNum15) {
        this.caseVisitedNum15 = caseVisitedNum15;
    }

    public Integer getCaseVisitedNum30() {
        return caseVisitedNum30;
    }

    public void setCaseVisitedNum30(Integer caseVisitedNum30) {
        this.caseVisitedNum30 = caseVisitedNum30;
    }

    public String getCaseUrl() {
        return caseUrl;
    }

    public void setCaseUrl(String caseUrl) {
        this.caseUrl = caseUrl;
    }

    public String getCaseCommunityUrl() {
        return caseCommunityUrl;
    }

    public void setCaseCommunityUrl(String caseCommunityUrl) {
        this.caseCommunityUrl = caseCommunityUrl;
    }

    public Date getCaseGetDate() {
        return caseGetDate;
    }

    public void setCaseGetDate(Date caseGetDate) {
        this.caseGetDate = caseGetDate;
    }

    public String getCaseProvider() {
        return caseProvider;
    }

    public void setCaseProvider(String caseProvider) {
        this.caseProvider = caseProvider;
    }

    public String getUvCommunityName() {
        return uvCommunityName;
    }

    public void setUvCommunityName(String uvCommunityName) {
        this.uvCommunityName = uvCommunityName;
    }

    public String getUvCommunityAddress() {
        return uvCommunityAddress;
    }

    public void setUvCommunityAddress(String uvCommunityAddress) {
        this.uvCommunityAddress = uvCommunityAddress;
    }

    public String getUvCounty() {
        return uvCounty;
    }

    public void setUvCounty(String uvCounty) {
        this.uvCounty = uvCounty;
    }

    public String getUvBlock() {
        return uvBlock;
    }

    public void setUvBlock(String uvBlock) {
        this.uvBlock = uvBlock;
    }

    public String getUvLoop() {
        return uvLoop;
    }

    public void setUvLoop(String uvLoop) {
        this.uvLoop = uvLoop;
    }

    public BigDecimal getAreaCoefficient() {
        return areaCoefficient;
    }

    public void setAreaCoefficient(BigDecimal areaCoefficient) {
        this.areaCoefficient = areaCoefficient;
    }

    public BigDecimal getTowardCoefficient() {
        return towardCoefficient;
    }

    public void setTowardCoefficient(BigDecimal towardCoefficient) {
        this.towardCoefficient = towardCoefficient;
    }

    public BigDecimal getFloorCoefficient() {
        return floorCoefficient;
    }

    public void setFloorCoefficient(BigDecimal floorCoefficient) {
        this.floorCoefficient = floorCoefficient;
    }

    public BigDecimal getYearCoefficient() {
        return yearCoefficient;
    }

    public void setYearCoefficient(BigDecimal yearCoefficient) {
        this.yearCoefficient = yearCoefficient;
    }

    public BigDecimal getBuildingCoefficient() {
        return buildingCoefficient;
    }

    public void setBuildingCoefficient(BigDecimal buildingCoefficient) {
        this.buildingCoefficient = buildingCoefficient;
    }

    public Date getCaseLastDeal() {
        return caseLastDeal;
    }

    public void setCaseLastDeal(Date caseLastDeal) {
        this.caseLastDeal = caseLastDeal;
    }

    /**
     * 生成caseid，根据caseid来确定唯一案例，还有案例来源
     *
     * @return
     */
    public String getNewCaseId() {
        return DigestUtils.md5DigestAsHex(getCaseLianJiaId().getBytes());
    }
    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public void clear() {
        clearPrice();
        clearElevator();
        clearDecoration();
        clearFloor();
        clearToward();
        clearYear();
    }

    private void clearYear() {
        setCleanYear(new Integer(getCaseYear()));
    }

    private void clearPrice() {
        try {
            // 总价
            setCleanTotalPrice(getCasePrice().multiply(new BigDecimal(10 * 1000)));
            // 单价
            setCleanUnitPrice(getCleanTotalPrice().divide(getCaseArea(), 2));
        } catch (Exception e) {

        }
    }

    // 装修
    private void clearDecoration() {
        if (StringUtils.isNotNull(getCaseDecoration())) {
            if (!"其他".equals(getCaseDecoration())) {
                setCleanDecoration(getCaseDecoration());
            }
        }
    }

    // 电梯
    private void clearElevator() {
        if (StringUtils.isNotNull(getCaseElevator())) {
            if ("有".equals(getCaseElevator())) {
                setCleanElevator(true);
            } else if ("无".equals(getCaseElevator())) {
                setCleanElevator(false);
            }
        }
    }

    // 楼层
    private void clearFloor() {
        if (StringUtils.isNotNull(getCaseStorey())) {
            Pattern pattern = Pattern.compile("([高中低])楼层 \\(共(\\d+)层\\)");
            Matcher matcher = pattern.matcher(getCaseStorey());
            if (matcher.find()) {
                String currentFloor = matcher.group(1);
                String totalFloor = matcher.group(2);
                setCleanTotalFloor(new Integer(totalFloor));
                if ("高".equals(currentFloor)) {
                    setCleanCurrentFloorDesc("高区");
                } else if ("中".equals(currentFloor)) {
                    setCleanCurrentFloorDesc("中区");
                } else if ("低".equals(currentFloor)) {
                    setCleanCurrentFloorDesc("低区");
                }
            } else {
                pattern = Pattern.compile("地下室 \\(共(\\d+)层\\)");
                matcher = pattern.matcher(getCaseStorey());
                if (matcher.find()) {
                    String totalFloor = matcher.group(1);
                    setCleanTotalFloor(new Integer(totalFloor));
                }
            }

        }
    }

    private void clearToward() {
        // 排序规则
        String[] segments = this.getCaseToward().split(" ");
        String cleanTag = segments[0].trim();
        if (1 == segments.length) {
            if (!"".equals(cleanTag) && !"暂无数据".equals(cleanTag)) {
                this.setCleanToward(cleanTag);
            }
        } else {
            // 排序规则
            int index = towardArray.length;
            for (int j = 0; j < towardArray.length; j++) {
                for (String seg : segments) {
                    if (seg.equals(towardArray[j])) {
                        if (j < index)
                            index = j;
                    }
                }
            }
            this.setCleanToward(towardArray[index]);
        }
    }



    @Override
    public String toString() {
        return "OriginalResidenceOpeningCase{" +
                "yearMonth=" + yearMonth +
                ", caseId='" + caseId + '\'' +
                ", caseLianJiaId='" + caseLianJiaId + '\'' +
                ", caseLianJiaCommunityId='" + caseLianJiaCommunityId + '\'' +
                ", caseTitle='" + caseTitle + '\'' +
                ", cleanPropertyType='" + cleanPropertyType + '\'' +
                ", caseApartmentLayout='" + caseApartmentLayout + '\'' +
                ", caseHouseStructure='" + caseHouseStructure + '\'' +
                ", caseArea=" + caseArea +
                ", caseUndergroundArea=" + caseUndergroundArea +
                ", caseToward='" + caseToward + '\'' +
                ", cleanToward='" + cleanToward + '\'' +
                ", caseStorey='" + caseStorey + '\'' +
                ", cleanTotalFloor=" + cleanTotalFloor +
                ", cleanCurrentFloorDesc='" + cleanCurrentFloorDesc + '\'' +
                ", caseUpDate=" + caseUpDate +
                ", caseDownDate=" + caseDownDate +
                ", caseElevator='" + caseElevator + '\'' +
                ", cleanElevator=" + cleanElevator +
                ", caseTiHu='" + caseTiHu + '\'' +
                ", caseDecoration='" + caseDecoration + '\'' +
                ", cleanDecoration='" + cleanDecoration + '\'' +
                ", caseYear='" + caseYear + '\'' +
                ", cleanYear=" + cleanYear +
                ", caseAddress='" + caseAddress + '\'' +
                ", vid='" + vid + '\'' +
                ", caseChanQuanXingZhi='" + caseChanQuanXingZhi + '\'' +
                ", casePrice=" + casePrice +
                ", cleanTotalPrice=" + cleanTotalPrice +
                ", cleanUnitPrice=" + cleanUnitPrice +
                ", caseCommunityName='" + caseCommunityName + '\'' +
                ", caseVisitedNum=" + caseVisitedNum +
                ", caseFirstVisitTime=" + caseFirstVisitTime +
                ", caseLatestVisitTime=" + caseLatestVisitTime +
                ", caseVisitedNum15=" + caseVisitedNum15 +
                ", caseVisitedNum30=" + caseVisitedNum30 +
                ", caseUrl='" + caseUrl + '\'' +
                ", caseCommunityUrl='" + caseCommunityUrl + '\'' +
                ", caseGetDate=" + caseGetDate +
                ", caseProvider='" + caseProvider + '\'' +
                ", uvCommunityName='" + uvCommunityName + '\'' +
                ", uvCommunityAddress='" + uvCommunityAddress + '\'' +
                ", uvCounty='" + uvCounty + '\'' +
                ", uvBlock='" + uvBlock + '\'' +
                ", uvLoop='" + uvLoop + '\'' +
                ", areaCoefficient=" + areaCoefficient +
                ", towardCoefficient=" + towardCoefficient +
                ", floorCoefficient=" + floorCoefficient +
                ", yearCoefficient=" + yearCoefficient +
                ", buildingCoefficient=" + buildingCoefficient +
                ", caseLastDeal=" + caseLastDeal +
                ", newCaseId='" + newCaseId + '\'' +
                '}';
    }
}
