package invest.lixinger.macro.priceIndex.china.VO;

import lombok.Data;

import java.util.List;

@Data
public class PriceIndexCNResult_DataVO {
    private String areaCode;

    private String date;

    private String type;

    private PriceIndexCNResult_MVO m;
}
