package invest.lixinger.company.fundamental.VO;

import lombok.Data;

@Data
public class companyFundamentalCNResult_DataVO {
    private String date;
    private double d_pe_ttm;
    private double pb_wo_gw;
    private double ps_ttm;
    private double dyr;
    private double sp;
    private double mc;
    private double d_pe_ttm_pos10;
    private double pb_wo_gw_pos10;
    private double ps_ttm_pos10;
    private String stockCode;
}
