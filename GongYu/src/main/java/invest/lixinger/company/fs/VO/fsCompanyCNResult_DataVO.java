package invest.lixinger.company.fs.VO;

import lombok.Data;

@Data
public class fsCompanyCNResult_DataVO {
    private String reportType;
    private String reportDate;
    private String currency;
    private String standardDate;
    private fsCompanyCNResult_QVO q;
    private String date;
    private String stockCode;
}
