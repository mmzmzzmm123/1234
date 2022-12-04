package invest.lixinger.index.fundamentalCN.VO;

import lombok.Data;

import java.sql.Date;

@Data
public class fundamentalReulst_DataVO {
    Date date;
    fundamentalResult_PbVO pb;
    fundamentalReulst_PsVO ps_ttm;
    fundamentalReulst_PeVO pe_ttm;
    double cp;
    String stockCode;
}
