package invest.lixinger.macro.nationalDebt.us.VO;

import lombok.Data;

import java.util.List;
import java.sql.Date;

@Data
public class nationalDebtUSParam_RootVO {
    private String token;
    private String areaCode;
    private String startDate;
    private String endDate;
    private List<String> metricsList;
}
