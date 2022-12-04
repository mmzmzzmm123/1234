package invest.lixinger.index.fundamental.HK.HSI.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class fundamentalHSIParam_RootVO {
    private String token;
    private String startDate;
    private String endDate;
    private List<String> stockCodes;
    private List<String> metricsList;
}
