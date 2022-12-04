package invest.lixinger.index.getAllIndex.CN.VO;

import lombok.Data;

import java.util.List;
@Data
public class allIndexCNResult_RootVO {
    private int code;
    private String message;
    private List<allIndexCNResult_DataVO> data;

}
