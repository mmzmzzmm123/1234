package invest.lixinger.index.fundamental.CN.VO;

import lombok.Data;

import java.sql.Date;

@Data
public class indexFundamentalCNReulst_DataVO {
    Date date;
    indexFundamentalCNResult_PbVO pb;
    indexFundamentalCNReulst_PsVO ps_ttm;
    indexFundamentalCNReulst_PeVO pe_ttm;
    double cp;
    String stockCode;
}
