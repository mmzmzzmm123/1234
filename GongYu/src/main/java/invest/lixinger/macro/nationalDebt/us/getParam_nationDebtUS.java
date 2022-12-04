package invest.lixinger.macro.nationalDebt.us;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.fundamental.CN.getParam_fundamental;
import invest.lixinger.macro.nationalDebt.us.VO.nationalDebtUSParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class getParam_nationDebtUS {
    public  static String getNationDebtUSParamJson() throws IOException, ParseException {
        InputStream inputStream = getParam_fundamental.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//定义日期显示格式
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        String lastMonthToday = sdf.format(cal.getTime());
        String today = sdf.format(new Date());

        nationalDebtUSParam_RootVO paramvo = new nationalDebtUSParam_RootVO();
        paramvo.setToken(token);
        paramvo.setStartDate(lastMonthToday);
        paramvo.setEndDate(today);
        paramvo.setAreaCode("us");
        String[] metricsArray = {"mir_m1", "mir_m3", "mir_m6", "mir_y1", "mir_y2", "mir_y3", "mir_y5", "mir_y7", "mir_y10", "mir_y20", "mir_y30"};
        ArrayList<String> metricsList = new ArrayList<>(Arrays.asList(metricsArray));
        paramvo.setMetricsList(metricsList);

        return JSON.toJSONString(paramvo);
    }
}
