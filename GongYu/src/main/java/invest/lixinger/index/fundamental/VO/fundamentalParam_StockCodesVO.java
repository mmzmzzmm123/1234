package invest.lixinger.index.fundamental.VO;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class fundamentalParam_StockCodesVO {
    List<String> stockCodes=new ArrayList<>();
}
