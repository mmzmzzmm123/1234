package invest.lixinger.index.fundamental.US.spx.VO;

import lombok.Data;

import java.util.List;

@Data
public class indexFundamentalSPXResult_RootVO {
    private int code;
    private String message;
    private List<indexFundamentalSPXResult_DataVO> data;

}






