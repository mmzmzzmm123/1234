package invest.lixinger.index.fundamentalCN.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class fundamentalParam_RootVO_DateRange {
    String token;
    String startDate;
    String endDate;
    List<String> stockCodes=new ArrayList<>();
    List<String> metricsList=new ArrayList<>();
}
