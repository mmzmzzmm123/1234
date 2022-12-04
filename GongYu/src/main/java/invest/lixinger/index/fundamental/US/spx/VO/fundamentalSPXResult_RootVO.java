package invest.lixinger.index.fundamental.US.spx.VO;

import lombok.Data;

import java.util.List;

@Data
public class fundamentalSPXResult_RootVO {
    private int code;
    private String message;
    private List<fundamentalSPXResult_DataVO> data;

}






