package invest.lixinger.company.fundamental.VO;

import lombok.Data;

import java.util.List;
@Data
public class companyFundamentalCNResult_RootVO {
    private int code;
    private String message;
    private List<companyFundamentalCNResult_DataVO> data;
}
