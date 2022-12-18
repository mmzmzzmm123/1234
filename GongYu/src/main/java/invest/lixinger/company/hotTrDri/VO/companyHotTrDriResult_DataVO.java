package invest.lixinger.company.hotTrDri.VO;

import lombok.Data;

@Data
public class companyHotTrDriResult_DataVO {
    private String last_update_date;
    private double cagr_p_r_fs;
    private double cagr_p_r_fys;
    private double cagr_p_r_y1;
    private double cagr_p_r_y3;
    private double cagr_p_r_y5;
    private double cagr_p_r_y10;
    private double cagr_p_r_y20;
    private String stockCode;
}
