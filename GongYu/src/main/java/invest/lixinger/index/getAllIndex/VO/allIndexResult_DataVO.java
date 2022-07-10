package invest.lixinger.index.getAllIndex.VO;

import lombok.Data;

@Data
public class allIndexResult_DataVO {
    private String areaCode;
    private String market;
    private String stockCode;
    private String source;
    private String currency;
    private String name;
    private String series;
    private String launchDate;
    private String rebalancingFrequency;
}

