package invest.lixinger.index.fundamental.CN.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class fundamentalCNParam_RootVO {
    String token;
    String date;
    List<String> stockCodes = new ArrayList<>();
    List<String> metricsList = new ArrayList<>();
}
