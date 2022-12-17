package invest.lixinger.index.hotCp.CN.VO;

import lombok.Data;

import java.util.List;

@Data
public class indexHotCpCNResult_RootVO {
    private int code;
    private String message;
    private List<indexHotCpCNResult_DataVO> data;
}
