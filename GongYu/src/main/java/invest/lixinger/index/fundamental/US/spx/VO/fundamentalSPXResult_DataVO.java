package invest.lixinger.index.fundamental.US.spx.VO;

import lombok.Data;

@Data
public class fundamentalSPXResult_DataVO {
    private String date;
    private fundamentalSPXResult_PbVO pb;
    private fundamentalSPXResult_PeVO pe_ttm;
    private fundamentalSPXResult_PsVO ps_ttm;
    private String stockCode;
}
