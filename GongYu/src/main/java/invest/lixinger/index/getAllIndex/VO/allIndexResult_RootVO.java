package invest.lixinger.index.getAllIndex.VO;

import lombok.Data;

import java.util.List;
@Data
public class allIndexResult_RootVO {
    private int code;
    private String message;
    private List<allIndexResult_DataVO> data;

}
