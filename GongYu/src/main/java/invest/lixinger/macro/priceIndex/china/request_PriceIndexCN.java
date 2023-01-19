package invest.lixinger.macro.priceIndex.china;

import invest.lixinger.macro.moneySupply.VO.moneySupplyCNParam_DataVO;
import invest.lixinger.macro.moneySupply.VO.moneySupplyCNResult_RootVO;
import invest.lixinger.macro.priceIndex.china.VO.PriceIndexCNResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class request_PriceIndexCN {
    public static void main(String[] args) throws ParseException, IOException {
        InputStream inputStream = request_PriceIndexCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String macroPriceIndexCNURL = (String) indexReqParam.get("macroPriceIndexCNURL");
        String paramJson = getParam_PriceIndexCN.getParamPriceIndexCNParamJson();
//        String resultJson = netRequest.jsonNetPost(macroPriceIndexCNURL, paramJson);
        String resultJson="{\"code\":1,\"message\":\"success\",\"data\":[{\"areaCode\":\"cn\",\"date\":\"2022-12-31T00:00:00+08:00\",\"type\":\"pi\",\"m\":{\"cpi\":{\"c_y2y\":0.018}}},{\"areaCode\":\"cn\",\"date\":\"2022-11-30T00:00:00+08:00\",\"type\":\"pi\",\"m\":{\"cpi\":{\"c_y2y\":0.016}}},{\"areaCode\":\"cn\",\"date\":\"2022-10-31T00:00:00+08:00\",\"type\":\"pi\",\"m\":{\"cpi\":{\"c_y2y\":0.021}}},{\"areaCode\":\"cn\",\"date\":\"2022-09-30T00:00:00+08:00\",\"type\":\"pi\",\"m\":{\"cpi\":{\"c_y2y\":0.028}}},{\"areaCode\":\"cn\",\"date\":\"2022-08-31T00:00:00+08:00\",\"type\":\"pi\",\"m\":{\"cpi\":{\"c_y2y\":0.025}}}]}";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        PriceIndexCNResult_RootVO resultObj = (PriceIndexCNResult_RootVO) getResult_PriceIndexCN.getResultPriceIndexCNObj(resultJson);
        System.out.println(resultObj.getData().get(0).getM().getCpi().getC_y2y());
        System.out.println(resultObj.getData().get(1).getM().getCpi().getC_y2y());
        System.out.println(resultObj.getData().get(2).getM().getCpi().getC_y2y());
        System.out.println(resultObj.getData().get(3).getM().getCpi().getC_y2y());
    }
}
