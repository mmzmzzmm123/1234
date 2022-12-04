package invest.lixinger.index.fundamental.HK.HSI.VO;

import lombok.Data;

import java.util.List;

@Data
public class fundamentalHSIResult_RootVO {
    private int code;
    private String message;
    private List<fundamentalHSIResult_DataVO> data;
}








