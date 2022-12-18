package invest.lixinger.company.hotTrDri.VO;

import lombok.Data;

import java.util.List;

@Data
public class companyHotTrDriResult_RootVO {
    private int code;
    private String message;
    private List<companyHotTrDriResult_DataVO> data;
}
