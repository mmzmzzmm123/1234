package invest.lixinger.macro.moneySupply.VO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class moneySupplyCNResult_RootVO {
    private int code;
    private String message;
    private List<moneySupplyCNParam_DataVO> data;
}
