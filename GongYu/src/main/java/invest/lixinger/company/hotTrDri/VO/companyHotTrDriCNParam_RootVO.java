package invest.lixinger.company.hotTrDri.VO;

import lombok.Data;

import java.util.List;

@Data
public class companyHotTrDriCNParam_RootVO {
    private String token;
    private List<String> stockCodes;
}
