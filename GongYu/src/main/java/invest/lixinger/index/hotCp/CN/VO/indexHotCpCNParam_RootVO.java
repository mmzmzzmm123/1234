package invest.lixinger.index.hotCp.CN.VO;

import lombok.Data;

import java.util.List;

@Data
public class indexHotCpCNParam_RootVO {
    private String token;
    private List<String> stockCodes;
}
