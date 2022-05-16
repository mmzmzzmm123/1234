package invest.lixinger.company.singleIndustry.VO;

import lombok.Data;

import java.util.List;

@Data
public class singleIndustryResult_RootVO {
    private String code;

    private String message;
    private List<singleIndustryResult_DataVO> data;
}
