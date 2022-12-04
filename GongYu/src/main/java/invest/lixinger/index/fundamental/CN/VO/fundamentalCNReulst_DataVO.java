package invest.lixinger.index.fundamental.CN.VO;

import lombok.Data;

import java.sql.Date;

@Data
public class fundamentalCNReulst_DataVO {
    Date date;
    fundamentalCNResult_PbVO pb;
    fundamentalCNReulst_PsVO ps_ttm;
    fundamentalCNReulst_PeVO pe_ttm;
    double cp;
    String stockCode;
}
