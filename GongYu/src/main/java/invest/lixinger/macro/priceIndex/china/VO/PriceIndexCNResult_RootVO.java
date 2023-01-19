package invest.lixinger.macro.priceIndex.china.VO;

import lombok.Data;

import java.util.List;

@Data
public class PriceIndexCNResult_RootVO {
    private int code;
    private String message;
    private List<PriceIndexCNResult_DataVO> data;
}
