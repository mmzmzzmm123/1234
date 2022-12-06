package invest.lixinger.company.fs.VO;

import lombok.Data;

import java.util.List;

@Data
public class fsCompanyCNResult_RootVO {
    private int code;
    private String message;
    private List<fsCompanyCNResult_DataVO> data;
}