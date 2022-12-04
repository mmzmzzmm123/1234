package invest.lixinger.index.getAllIndex.HK.VO;

import lombok.Data;

import java.util.List;
@Data
public class allIndexHKResult_RootVO {
    private int code;
    private String message;
    private List<allIndexHKResult_DataVO> data;
}
