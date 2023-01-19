package invest.lixinger.macro.priceIndex.china.VO;

import lombok.Data;

import java.util.List;

@Data
public class PriceIndexCNParam_RootVO {
    private String token;
    private String areaCode;
    private String startDate;
    private String endDate;
    private List<String> metricsList;
}
