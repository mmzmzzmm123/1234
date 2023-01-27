package invest.lixinger.macro.nationalDebt;

import invest.lixinger.macro.nationalDebt.VO.nationalDebtUSResult_DataVO;
import invest.lixinger.macro.nationalDebt.VO.nationalDebtUSResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class request_nationDebt {

    // 计算美国国债收益率
    @Test
    public void calculteUSDebt() throws IOException, ParseException {
        InputStream inputStream = request_nationDebt.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String macroNationalDebtURL = (String) indexReqParam.get("macroNationalDebtURL");
        String paramJson = getParam_nationDebtUS.getNationDebtUSParamJson();
        String resultJson = netRequest.jsonNetPost(macroNationalDebtURL, paramJson);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        nationalDebtUSResult_RootVO resultObj = (nationalDebtUSResult_RootVO) getResult_nationDebtUS.getResultObj(resultJson);
        nationalDebtUSResult_DataVO latestDayVO = resultObj.getData().get(0);
        nationalDebtUSResult_DataVO oneMonthAgoVO = resultObj.getData().get(resultObj.getData().size() - 1);
        System.out.println("统计的美债日期为：" + sdf.format(sdf.parse(latestDayVO.getDate())));

        double latestDayy2us = latestDayVO.getMir_y2();
        double latestDayy10us = latestDayVO.getMir_y10();
        double oneMonthAgoy2us = oneMonthAgoVO.getMir_y2();
        double oneMonthAgoy10us = oneMonthAgoVO.getMir_y10();

        Map<String,String>map=new HashMap<>();
        if ((latestDayy2us - latestDayy10us) > 0 || (oneMonthAgoy2us - oneMonthAgoy10us) > 0) {
            String latestDayDebtInverted = String.format("%.2f", (latestDayy2us - latestDayy10us) * 100);
            String oneMonthAgoDebtInverted = String.format("%.2f", (oneMonthAgoy2us - oneMonthAgoy10us) * 100);
            System.out.println("最近日期国债倒挂比例：" + latestDayDebtInverted + "，一个月前国债倒挂比例：" + oneMonthAgoDebtInverted);
            map.put("latestDayDebtInverted",latestDayDebtInverted);
            map.put("oneMonthAgoDebtInverted",oneMonthAgoDebtInverted);
        } else {
            System.out.println("最近日期、一个月前，国债没有倒挂");
        }
    }

    // 计算中国国债收益率
    @Test
    public void calculteCNDebt() throws Exception {
        InputStream inputStream = request_nationDebt.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String macroNationalDebtURL = (String) indexReqParam.get("macroNationalDebtURL");
        String paramJson = getParam_nationDebtCN.getNationDebtCNParamJson();
        String resultJson = netRequest.jsonNetPost(macroNationalDebtURL, paramJson);
        nationalDebtUSResult_RootVO resultObj = (nationalDebtUSResult_RootVO) getResult_nationDebtUS.getResultObj(resultJson);
        List<nationalDebtUSResult_DataVO> resultData = resultObj.getData();
        nationalDebtUSResult_DataVO latestDayVO = resultData.get(0);
        nationalDebtUSResult_DataVO oneMonthAgoVO = resultData.get(30);
        // 计算国债---------------------------------
        Map<String, String> map = calcultePos(resultObj);
        //----------------------
        double latestDayy2cn = latestDayVO.getMir_y2();
        double latestDayy10cn = latestDayVO.getMir_y10();
        double oneMonthAgoy2cn = oneMonthAgoVO.getMir_y2();
        double oneMonthAgoy10cn = oneMonthAgoVO.getMir_y10();
        if ((latestDayy2cn - latestDayy10cn) > 0 || (oneMonthAgoy2cn - oneMonthAgoy10cn) > 0) {
            String latestDayDebtInverted = String.format("%.2f", (latestDayy2cn - latestDayy10cn) * 100);
            String oneMonthAgoDebtInverted = String.format("%.2f", (oneMonthAgoy2cn - oneMonthAgoy10cn) * 100);
            System.out.println("最近日期美债倒挂比例：" + latestDayDebtInverted + "，一个月前美债倒挂比例：" + oneMonthAgoDebtInverted);
            map.put("latestDayDebtInverted",latestDayDebtInverted);
            map.put("oneMonthAgoDebtInverted",oneMonthAgoDebtInverted);
        } else {
            System.out.println("最近日期、一个月前，美债没有倒挂");
        }
    }

    /**
     * 计算百分位位置
     * 由于可能出现倒挂等极端情况，所以同时统计了2、3、5、10四种期限的国债，用平均更准确
     * 20、30年的百分位太低不适合
     * 7年期没有数据所以没有使用
     */
    public static Map<String, String> calcultePos(nationalDebtUSResult_RootVO resultObj) throws Exception {

        List<nationalDebtUSResult_DataVO> resultData = resultObj.getData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        nationalDebtUSResult_DataVO latestDayVO = resultData.get(0);
        Map<String, String> map = new HashMap<>();
        // 当期日期
        map.put("date", sdf.format(sdf.parse(latestDayVO.getDate())));
        // -----------------------------------------计算2年期--------------------------------------------
        List<Double> list2 = new ArrayList<>();
        for (nationalDebtUSResult_DataVO resultDatum : resultData) {
            list2.add(resultDatum.getMir_y2());
        }
        Collections.sort(list2);
        // 2年期国债，最新日期数值
        map.put("2debtValue", String.valueOf(latestDayVO.getMir_y2()));
        // 2年期国债，最新值百分位
        map.put("2debtPos", String.valueOf(list2.indexOf(latestDayVO.getMir_y2()) / (double) list2.size()));
        // -----------------------------------------计算3年期--------------------------------------------
        List<Double> list3 = new ArrayList<>();
        for (nationalDebtUSResult_DataVO resultDatum : resultData) {
            list3.add(resultDatum.getMir_y3());
        }
        Collections.sort(list3);
        // 3年期国债，最新日期数值
        map.put("3debtValue", String.valueOf(latestDayVO.getMir_y3()));
        // 3年期国债，最新值百分位
        map.put("3debtPos", String.valueOf(list3.indexOf(latestDayVO.getMir_y3()) / (double) list3.size()));

        // -----------------------------------------计算5年期--------------------------------------------
        List<Double> list5 = new ArrayList<>();
        for (nationalDebtUSResult_DataVO resultDatum : resultData) {
            list5.add(resultDatum.getMir_y5());
        }
        Collections.sort(list5);
        // 5年期国债，最新日期数值
        map.put("5debtValue", String.valueOf(latestDayVO.getMir_y5()));
        // 5年期国债，最新值百分位
        map.put("5debtPos", String.valueOf(list5.indexOf(latestDayVO.getMir_y5()) / (double) list5.size()));
        // -----------------------------------------计算10年期--------------------------------------------
        List<Double> list10 = new ArrayList<>();
        for (nationalDebtUSResult_DataVO datum : resultData) {
            list10.add(datum.getMir_y10());
        }
        Collections.sort(list10);

        // 10年期国债，最新日期数值
        map.put("10debtValue", String.valueOf(latestDayVO.getMir_y10()));
        // 10年期国债，最新值百分位
        map.put("10debtPos", String.valueOf(list10.indexOf(latestDayVO.getMir_y10()) / (double) list10.size()));
        // ------------------------------------------计算2、3、5、10期限的平均值----------------------------------
        double totalPos = Double.parseDouble(map.get("10debtPos")) + Double.parseDouble(map.get("2debtPos")) + Double.parseDouble(map.get("3debtPos")) + Double.parseDouble(map.get("5debtPos"));
        double averagePos = totalPos / 4;
        map.put("averagePos", String.valueOf(averagePos));
        // ----------------------------
//        System.out.println("2debtValue：" + map.get("2debtValue"));
//        System.out.println("2debtPos：" + map.get("2debtPos"));
//        System.out.println("3debtValue：" + map.get("3debtValue"));
//        System.out.println("3debtPos：" + map.get("3debtPos"));
//        System.out.println("5debtValue：" + map.get("5debtValue"));
//        System.out.println("5debtPos：" + map.get("5debtPos"));
        System.out.println("10年期国债数值：" + new DecimalFormat("0.00%").format(Double.valueOf(map.get("10debtValue"))));
        System.out.println("10年期国债百分位：" + new DecimalFormat("0.00%").format(Double.valueOf(map.get("10debtPos"))));
        System.out.println("平均后的国债百分位为：" + new DecimalFormat("0.00%").format(Double.valueOf(map.get("averagePos"))));

        return map;
    }
}
