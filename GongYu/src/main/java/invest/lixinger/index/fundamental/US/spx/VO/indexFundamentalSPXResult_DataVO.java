package invest.lixinger.index.fundamental.US.spx.VO;

import lombok.Data;

import java.util.Date;

@Data
public class indexFundamentalSPXResult_DataVO {
    private Date date;
    private indexFundamentalSPXResult_PbVO pb;
    private indexFundamentalSPXResult_PeVO pe_ttm;
    private indexFundamentalSPXResult_PsVO ps_ttm;
    double cp;
    private String stockCode;
}
