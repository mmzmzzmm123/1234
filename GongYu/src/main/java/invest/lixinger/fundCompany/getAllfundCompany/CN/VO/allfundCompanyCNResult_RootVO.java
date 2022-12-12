package invest.lixinger.fundCompany.getAllfundCompany.CN.VO;

import lombok.Data;

import java.util.List;

@Data
public class allfundCompanyCNResult_RootVO {
    private int code;
    private String message;
    private List<allfundCompanyCNResult_DataVO> data;
}
