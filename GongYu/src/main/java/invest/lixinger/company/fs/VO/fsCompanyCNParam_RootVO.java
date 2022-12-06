package invest.lixinger.company.fs.VO;

import lombok.Data;

import java.util.List;

@Data
public class fsCompanyCNParam_RootVO {
    private String token;
    private String date;
    private List<String> stockCodes;
    private List<String> metricsList;
}
