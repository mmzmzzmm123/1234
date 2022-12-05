package invest.lixinger.company.fsTypeOfCompany.VO;

import lombok.Data;

import java.util.List;
@Data
public class fsTypeOfCompanyCNResult_DataVO {
    private String stockCode;
    private String market;
    private String ipoDate;
    private String areaCode;
    private List<String> mutualMarkets;
    private String name;
    private String fsType;
}
