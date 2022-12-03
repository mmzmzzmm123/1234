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
//        String resultJson = netRequest.jsonNetPost(nationaldebtURL, paramJson);
        String resultJson = "{\"code\":1,\"message\":\"success\",\"data\":[{\"areaCode\":\"us\",\"date\":\"2022-12-01T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0404,\"mir_m3\":0.0433,\"mir_m6\":0.0465,\"mir_y1\":0.0466,\"mir_y2\":0.0425,\"mir_y3\":0.0398,\"mir_y5\":0.0368,\"mir_y7\":0.0362,\"mir_y10\":0.0353,\"mir_y20\":0.0385,\"mir_y30\":0.0364},{\"areaCode\":\"us\",\"date\":\"2022-11-30T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0407,\"mir_m3\":0.0437,\"mir_m6\":0.047,\"mir_y1\":0.0474,\"mir_y2\":0.0438,\"mir_y3\":0.0413,\"mir_y5\":0.0382,\"mir_y7\":0.0376,\"mir_y10\":0.0368,\"mir_y20\":0.04,\"mir_y30\":0.038},{\"areaCode\":\"us\",\"date\":\"2022-11-29T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0408,\"mir_m3\":0.0438,\"mir_m6\":0.0472,\"mir_y1\":0.0478,\"mir_y2\":0.0448,\"mir_y3\":0.0424,\"mir_y5\":0.0392,\"mir_y7\":0.0385,\"mir_y10\":0.0375,\"mir_y20\":0.0402,\"mir_y30\":0.0381},{\"areaCode\":\"us\",\"date\":\"2022-11-28T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0411,\"mir_m3\":0.0441,\"mir_m6\":0.0472,\"mir_y1\":0.0476,\"mir_y2\":0.0446,\"mir_y3\":0.0422,\"mir_y5\":0.0388,\"mir_y7\":0.038,\"mir_y10\":0.0369,\"mir_y20\":0.0397,\"mir_y30\":0.0374},{\"areaCode\":\"us\",\"date\":\"2022-11-25T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0416,\"mir_m3\":0.0441,\"mir_m6\":0.0467,\"mir_y1\":0.0476,\"mir_y2\":0.0442,\"mir_y3\":0.042,\"mir_y5\":0.0385,\"mir_y7\":0.0378,\"mir_y10\":0.0368,\"mir_y20\":0.0397,\"mir_y30\":0.0374},{\"areaCode\":\"us\",\"date\":\"2022-11-23T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0412,\"mir_m3\":0.044,\"mir_m6\":0.0467,\"mir_y1\":0.0475,\"mir_y2\":0.0446,\"mir_y3\":0.0423,\"mir_y5\":0.0388,\"mir_y7\":0.0381,\"mir_y10\":0.0371,\"mir_y20\":0.0397,\"mir_y30\":0.0374},{\"areaCode\":\"us\",\"date\":\"2022-11-22T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0397,\"mir_m3\":0.044,\"mir_m6\":0.0468,\"mir_y1\":0.0479,\"mir_y2\":0.0447,\"mir_y3\":0.0427,\"mir_y5\":0.0393,\"mir_y7\":0.0386,\"mir_y10\":0.0376,\"mir_y20\":0.0405,\"mir_y30\":0.0383},{\"areaCode\":\"us\",\"date\":\"2022-11-21T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0397,\"mir_m3\":0.0441,\"mir_m6\":0.0465,\"mir_y1\":0.0475,\"mir_y2\":0.0448,\"mir_y3\":0.0432,\"mir_y5\":0.0397,\"mir_y7\":0.0394,\"mir_y10\":0.0383,\"mir_y20\":0.0414,\"mir_y30\":0.0391},{\"areaCode\":\"us\",\"date\":\"2022-11-18T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0393,\"mir_m3\":0.0434,\"mir_m6\":0.0461,\"mir_y1\":0.0474,\"mir_y2\":0.0451,\"mir_y3\":0.0428,\"mir_y5\":0.0399,\"mir_y7\":0.0392,\"mir_y10\":0.0382,\"mir_y20\":0.0413,\"mir_y30\":0.0392},{\"areaCode\":\"us\",\"date\":\"2022-11-17T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0393,\"mir_m3\":0.0432,\"mir_m6\":0.0457,\"mir_y1\":0.0468,\"mir_y2\":0.0443,\"mir_y3\":0.0422,\"mir_y5\":0.0393,\"mir_y7\":0.0387,\"mir_y10\":0.0377,\"mir_y20\":0.041,\"mir_y30\":0.0389},{\"areaCode\":\"us\",\"date\":\"2022-11-16T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0381,\"mir_m3\":0.0432,\"mir_m6\":0.0454,\"mir_y1\":0.0462,\"mir_y2\":0.0435,\"mir_y3\":0.0413,\"mir_y5\":0.0383,\"mir_y7\":0.0377,\"mir_y10\":0.0367,\"mir_y20\":0.0403,\"mir_y30\":0.0385},{\"areaCode\":\"us\",\"date\":\"2022-11-15T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0377,\"mir_m3\":0.0431,\"mir_m6\":0.0454,\"mir_y1\":0.046,\"mir_y2\":0.0437,\"mir_y3\":0.0417,\"mir_y5\":0.0393,\"mir_y7\":0.0388,\"mir_y10\":0.038,\"mir_y20\":0.042,\"mir_y30\":0.0398},{\"areaCode\":\"us\",\"date\":\"2022-11-14T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0372,\"mir_m3\":0.0434,\"mir_m6\":0.0455,\"mir_y1\":0.0463,\"mir_y2\":0.044,\"mir_y3\":0.0424,\"mir_y5\":0.04,\"mir_y7\":0.0395,\"mir_y10\":0.0388,\"mir_y20\":0.0428,\"mir_y30\":0.0407},{\"areaCode\":\"us\",\"date\":\"2022-11-10T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0371,\"mir_m3\":0.0428,\"mir_m6\":0.0452,\"mir_y1\":0.0459,\"mir_y2\":0.0434,\"mir_y3\":0.0417,\"mir_y5\":0.0395,\"mir_y7\":0.0389,\"mir_y10\":0.0382,\"mir_y20\":0.0424,\"mir_y30\":0.0403},{\"areaCode\":\"us\",\"date\":\"2022-11-09T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0365,\"mir_m3\":0.0429,\"mir_m6\":0.0459,\"mir_y1\":0.0475,\"mir_y2\":0.0461,\"mir_y3\":0.0449,\"mir_y5\":0.0427,\"mir_y7\":0.042,\"mir_y10\":0.0412,\"mir_y20\":0.045,\"mir_y30\":0.0431},{\"areaCode\":\"us\",\"date\":\"2022-11-08T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0366,\"mir_m3\":0.0428,\"mir_m6\":0.046,\"mir_y1\":0.0477,\"mir_y2\":0.0467,\"mir_y3\":0.0455,\"mir_y5\":0.0431,\"mir_y7\":0.0422,\"mir_y10\":0.0414,\"mir_y20\":0.0447,\"mir_y30\":0.0428},{\"areaCode\":\"us\",\"date\":\"2022-11-07T00:00:00-05:00\",\"type\":\"nd\",\"mir_m1\":0.0378,\"mir_m3\":0.0429,\"mir_m6\":0.0462,\"mir_y1\":0.048,\"mir_y2\":0.0472,\"mir_y3\":0.0463,\"mir_y5\":0.0439,\"mir_y7\":0.0431,\"mir_y10\":0.0422,\"mir_y20\":0.0455,\"mir_y30\":0.0434},{\"areaCode\":\"us\",\"date\":\"2022-11-04T00:00:00-04:00\",\"type\":\"nd\",\"mir_m1\":0.0373,\"mir_m3\":0.0421,\"mir_m6\":0.0455,\"mir_y1\":0.0476,\"mir_y2\":0.0466,\"mir_y3\":0.0458,\"mir_y5\":0.0433,\"mir_y7\":0.0426,\"mir_y10\":0.0417,\"mir_y20\":0.0449,\"mir_y30\":0.0427}]}";
//        System.out.println(resultJson);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        nationalDebtUSResult_RootVO resultObj = (nationalDebtUSResult_RootVO) getResult_nationDebtUS.getResultObj(resultJson);
        nationalDebtUSResult_DataVO latestDayVO = resultObj.getData().get(0);
        nationalDebtUSResult_DataVO oneMonthAgoVO = resultObj.getData().get(resultObj.getData().size() - 1);
        System.out.println("统计到的美债日期为：" + sdf.format(sdf.parse(latestDayVO.getDate())));

        double latestDayy2us = latestDayVO.getMir_y2();
        double latestDayy10us = latestDayVO.getMir_y10();
        double oneMonthAgoy2us = oneMonthAgoVO.getMir_y2();
        double oneMonthAgoy10us = oneMonthAgoVO.getMir_y10();
        String latestDayDebt = String.format("%.2f", (latestDayy2us - latestDayy10us) * 100);
        String oneMonthAgoDebt = String.format("%.2f", (oneMonthAgoy2us - oneMonthAgoy10us) * 100);
        System.out.println("最近日期美债倒挂比例" + latestDayDebt);
        System.out.println("一个月钱美债倒挂比例" + oneMonthAgoDebt);
    }
}
