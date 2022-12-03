package invest.lixinger.macro.nationalDebt.us.VO;

import lombok.Data;

@Data
public class nationalDebtUSResult_DataVO {
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
    private double mir_y7;
    private double mir_y10;
    private double mir_y20;
    private double mir_y30;
}
