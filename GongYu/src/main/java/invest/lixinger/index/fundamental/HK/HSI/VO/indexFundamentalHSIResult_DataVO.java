package invest.lixinger.index.fundamental.HK.HSI.VO;

import lombok.Data;

@Data
public class indexFundamentalHSIResult_DataVO {
    private String date;
    private indexFundamentalHSIResult_PbVO pb;
    private indexFundamentalHSIResult_PeVO pe_ttm;
    private indexFundamentalHSIResult_PsVO ps_ttm;
    private String stockCode;
}
