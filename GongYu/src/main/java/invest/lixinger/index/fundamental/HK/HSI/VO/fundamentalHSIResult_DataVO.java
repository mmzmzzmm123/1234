package invest.lixinger.index.fundamental.HK.HSI.VO;

import lombok.Data;

@Data
public class fundamentalHSIResult_DataVO {
    private String date;
    private fundamentalHSIResult_PbVO pb;
    private fundamentalHSIResult_PeVO pe_ttm;
    private fundamentalHSIResult_PsVO ps_ttm;
    private String stockCode;
}
