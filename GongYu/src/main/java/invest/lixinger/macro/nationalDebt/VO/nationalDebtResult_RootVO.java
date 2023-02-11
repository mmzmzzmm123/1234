package invest.lixinger.macro.nationalDebt.VO;
import lombok.Data;

import java.util.List;
@Data
public class nationalDebtResult_RootVO {
    private int code;
    private String message;
    private List<nationalDebtResult_DataVO> data;
}
