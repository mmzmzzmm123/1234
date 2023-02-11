package invest.lixinger.macro.nationalDebt.VO;

import lombok.Data;

@Data
public class nationalDebtResult_DataVO {
    private String areaCode;
    private String date;
    private String type;
    private double mir_m1;
    private double mir_m3;
    private double mir_m6;
    private double mir_y1;
    private double mir_y2;
    private double mir_y3;
    private double mir_y5;
    // 这个数据在理杏仁里面为0
    private double mir_y7;
    private double mir_y10;
    // 计算百分位数值很低
    private double mir_y20;
    // 计算百分位数值很低
    private double mir_y30;
}
