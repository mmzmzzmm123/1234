package invest.lixinger.company.fsTypeOfCompany.VO;

import lombok.Data;

import java.util.List;

@Data
public class fsTypeOfCompanyCNResult_RootVO {
    private String code;
    private String message;
    private List<fsTypeOfCompanyCNResult_DataVO> data;
}
