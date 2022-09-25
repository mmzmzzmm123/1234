package invest.lixinger.company.singleIndustry.VO;

import lombok.Data;

import java.util.List;
@Data
public class singleIndustryResult_DataVO {
    private String stockCode;
    private String market;
    private String ipoDate;
    private String areaCode;
    private List<String> mutualMarkets;
    private String name;
    private String fsType;
}
