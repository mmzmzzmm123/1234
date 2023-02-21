package invest.lixinger.macro.nationalDebt.VO;

import lombok.Data;

@Data
public class nationalDebtResult_DataVO {
    private String areaCode;
    private String date;
    private String type;
    private double tcm_m1;
    private double tcm_m3;
    private double tcm_m6;
    private double tcm_y1;
    private double tcm_y2;
    private double tcm_y3;
    private double tcm_y5;
    // 这个数据在理杏仁里面为0
    private double tcm_y7;
    private double tcm_y10;
    // 计算百分位数值很低
    private double tcm_y20;
    // 计算百分位数值很低
    private double tcm_y30;
}
