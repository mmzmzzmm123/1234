package invest.lixinger.macro.nationalDebt.VO;
import lombok.Data;

import java.util.List;
@Data
public class nationalDebtUSResult_RootVO {
    private int code;
    private String message;
    private List<nationalDebtUSResult_DataVO> data;
}
