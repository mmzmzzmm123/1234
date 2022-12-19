package invest.lixinger.macro.moneySupply;

import invest.lixinger.macro.moneySupply.VO.moneySupplyCNParam_DataVO;
import invest.lixinger.macro.moneySupply.VO.moneySupplyCNResult_RootVO;
import invest.lixinger.macro.nationalDebt.us.getResult_nationDebtUS;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class request_moneySupplyCN {
    public static void main(String[] args) throws ParseException, IOException {
        InputStream inputStream = request_moneySupplyCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String macroMoneySupplyURL = (String) indexReqParam.get("macroMoneySupplyURL");
        String paramJson = getParam_moneySupplyCN.getParamMoneySupplyCNParamJson();
        String resultJson = netRequest.jsonNetPost(macroMoneySupplyURL, paramJson);
//        String resultJson="{\"code\":1,\"message\":\"success\",\"data\":[{\"date\":\"2022-10-31T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.118465},\"m1\":{\"t_y2y\":0.057594}}},{\"date\":\"2022-09-30T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.121123},\"m1\":{\"t_y2y\":0.063859}}},{\"date\":\"2022-08-31T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.122304},\"m1\":{\"t_y2y\":0.060553}}},{\"date\":\"2022-07-31T00:00:00+08:00\",\"type\":\"ms\",\"areaCode\":\"cn\",\"m\":{\"m2\":{\"t_y2y\":0.119855},\"m1\":{\"t_y2y\":0.06684}}}]}";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        moneySupplyCNResult_RootVO resultObj = (moneySupplyCNResult_RootVO) getResult_moneySupplyCN.getResultObj(resultJson);
        moneySupplyCNParam_DataVO latestMonthData = resultObj.getData().get(0);
        moneySupplyCNParam_DataVO Month2Age = resultObj.getData().get(1);
        System.out.println("统计到的货币供应日期为：" + sdf.format(sdf.parse(latestMonthData.getDate())));
        System.out.println("" + latestMonthData);
        System.out.println("" + latestMonthData.getM().getM1().getT_y2y());
        System.out.println("" + latestMonthData.getM().getM2().getT_y2y());

    }
}
