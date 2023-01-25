package invest.lixinger.macro.nationalDebt.VO;

import lombok.Data;

import java.util.List;

@Data
public class nationalDebtUSParam_RootVO {
    private String token;
    private String areaCode;
    private String startDate;
    private String endDate;
    private List<String> metricsList;
}
