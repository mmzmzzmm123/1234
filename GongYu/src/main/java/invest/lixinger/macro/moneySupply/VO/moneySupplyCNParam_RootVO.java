package invest.lixinger.macro.moneySupply.VO;

import lombok.Data;

import java.util.List;

@Data
public class moneySupplyCNParam_RootVO {
    private String token;
    private String areaCode;
    private String startDate;
    private String endDate;
    private List<String> metricsList;
}
