package invest.lixinger.macro.nationalDebt.us;

import invest.lixinger.index.fundamental.VO.fundamentalResult_RootVO;
import invest.lixinger.index.fundamental.getParam_fundamental;
import invest.lixinger.index.fundamental.getResult_fundamental;
import invest.lixinger.macro.nationalDebt.us.VO.nationalDebtUSResult_DataVO;
import invest.lixinger.macro.nationalDebt.us.VO.nationalDebtUSResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class request_nationDebtUS {
    public static void main(String[] args) throws IOException, ParseException {
        InputStream inputStream = request_nationDebtUS.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String nationaldebtURL = (String) indexReqParam.get("nationaldebtURL");
        String paramJson = getParam_nationDebtUS.getNationDebtUSParamJson();
        String resultJson = netRequest.jsonNetPost(nationaldebtURL, paramJson);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        nationalDebtUSResult_RootVO resultObj = (nationalDebtUSResult_RootVO) getResult_nationDebtUS.getResultObj(resultJson);
        nationalDebtUSResult_DataVO latestDayVO = resultObj.getData().get(0);
        nationalDebtUSResult_DataVO oneMonthAgoVO = resultObj.getData().get(resultObj.getData().size() - 1);
        System.out.println("统计的美债日期为：" + sdf.format(sdf.parse(latestDayVO.getDate())));

        double latestDayy2us = latestDayVO.getMir_y2();
        double latestDayy10us = latestDayVO.getMir_y10();
        double oneMonthAgoy2us = oneMonthAgoVO.getMir_y2();
        double oneMonthAgoy10us = oneMonthAgoVO.getMir_y10();
        String latestDayDebt = String.format("%.2f", (latestDayy2us - latestDayy10us) * 100);
        String oneMonthAgoDebt = String.format("%.2f", (oneMonthAgoy2us - oneMonthAgoy10us) * 100);
        System.out.println("最近日期美债倒挂比例：" + latestDayDebt);
        System.out.println("一个月钱美债倒挂比例：" + oneMonthAgoDebt);
    }
}
