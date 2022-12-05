package invest.lixinger.index.fundamental.CN.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class indexFundamentalCNResult_RootVO {
    int code;
    String message;
    List<indexFundamentalCNReulst_DataVO> data=new ArrayList<>();
}
