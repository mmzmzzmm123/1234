package invest.lixinger.index.hotCp.CN.VO;

import lombok.Data;

import java.util.List;

@Data
public class indexHotCpCNResult_DataVO {
    private String stockCode;
    private double cpc_fys;
    private double cpc;
    private double cpc_m1;
    private double cpc_m3;
    private double cpc_m6;
    private double cpc_y1;
    private double cpc_y3;
    private double cpc_y5;
    private double cpc_y10;
    private double cpc_fs;
    private String latestDate;
}
