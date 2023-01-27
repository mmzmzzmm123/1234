package invest.lixinger.macro.priceIndex.china;

import com.alibaba.fastjson.JSON;
import invest.lixinger.macro.moneySupply.VO.moneySupplyCNParam_RootVO;
import invest.lixinger.macro.priceIndex.china.VO.PriceIndexCNParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class getParam_PriceIndexCN {
    public  static String getParamPriceIndexCNParamJson() throws IOException, ParseException {
        InputStream inputStream = getParam_PriceIndexCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//定义日期显示格式
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -10);
        String fiveMonthAgo = sdf.format(cal.getTime());
        String today = sdf.format(new Date());

        PriceIndexCNParam_RootVO paramvo = new PriceIndexCNParam_RootVO();
        paramvo.setToken(token);
        paramvo.setStartDate(fiveMonthAgo);
        paramvo.setEndDate(today);
        paramvo.setAreaCode("cn");
        String[] metricsArray = {"m.cpi.c_y2y"};
        ArrayList<String> metricsList = new ArrayList<>(Arrays.asList(metricsArray));
        paramvo.setMetricsList(metricsList);

        return JSON.toJSONString(paramvo);
    }

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(getParamPriceIndexCNParamJson());
    }
}
