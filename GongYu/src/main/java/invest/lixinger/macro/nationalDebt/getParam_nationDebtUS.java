package invest.lixinger.macro.nationalDebt;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.fundamental.CN.getParam_indexFundamentalCN;
import invest.lixinger.macro.nationalDebt.VO.nationalDebtParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class getParam_nationDebtUS {
    public  static String getNationDebtUSParamJson(String startDate) throws IOException, ParseException {
        InputStream inputStream = getParam_indexFundamentalCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//定义日期显示格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        cal.add(Calendar.YEAR, 10);
        String TenYearLater = sdf.format(cal.getTime());

        nationalDebtParam_RootVO paramvo = new nationalDebtParam_RootVO();
        paramvo.setToken(token);
        paramvo.setStartDate(startDate);
        paramvo.setEndDate(TenYearLater);
        paramvo.setAreaCode("us");
        String[] metricsArray = {"tcm_m1", "tcm_m3", "tcm_m6", "tcm_y1", "tcm_y2", "tcm_y3", "tcm_y5", "tcm_y7", "tcm_y10", "tcm_y20", "tcm_y30"};
        ArrayList<String> metricsList = new ArrayList<>(Arrays.asList(metricsArray));
        paramvo.setMetricsList(metricsList);


        return JSON.toJSONString(paramvo);
    }
}
