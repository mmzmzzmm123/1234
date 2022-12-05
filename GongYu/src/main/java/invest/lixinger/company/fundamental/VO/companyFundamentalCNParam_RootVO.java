package invest.lixinger.company.fundamental.VO;

import lombok.Data;

import java.util.List;

@Data
public class companyFundamentalCNParam_RootVO {
    private String token;
    private String date;
    private List<String> stockCodes;
    private List<String> metricsList;
}
