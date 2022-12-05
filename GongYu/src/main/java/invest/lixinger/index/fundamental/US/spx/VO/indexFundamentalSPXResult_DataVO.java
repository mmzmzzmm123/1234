package invest.lixinger.index.fundamental.US.spx.VO;

import lombok.Data;

@Data
public class indexFundamentalSPXResult_DataVO {
    private String date;
    private indexFundamentalSPXResult_PbVO pb;
    private indexFundamentalSPXResult_PeVO pe_ttm;
    private indexFundamentalSPXResult_PsVO ps_ttm;
    private String stockCode;
}
