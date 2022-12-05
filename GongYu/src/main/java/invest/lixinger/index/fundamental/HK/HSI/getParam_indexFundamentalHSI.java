package invest.lixinger.index.fundamental.HK.HSI;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNParam_RootVO;
import invest.lixinger.index.fundamental.HK.HSI.VO.indexFundamentalHSIParam_RootVO;
import invest.lixinger.utils.getResult_NoHoliday;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class getParam_indexFundamentalHSI {
    public static String getSingleIndexParamJson() throws IOException, ParseException {
        InputStream inputStream = getParam_indexFundamentalHSI.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        List<String> indexFundamentalMetricsList = (List<String>) indexReqParam.get("indexFundamentalMetricsList");
        indexFundamentalMetricsList.removeIf(e -> e.contains("cp"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//定义日期显示格式
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -15);
        String day15Ago = sdf.format(cal.getTime());
        String today = sdf.format(new Date());
        indexFundamentalHSIParam_RootVO paramvo = new indexFundamentalHSIParam_RootVO();
        paramvo.setToken(token);
        paramvo.setStartDate(day15Ago);
        paramvo.setEndDate(today);
        paramvo.setStockCodes(new ArrayList<>(Collections.singleton("HSI")));
        paramvo.setMetricsList(indexFundamentalMetricsList);

        return JSON.toJSONString(paramvo);
    }
    public static String getAllIndexParamJsonHK(List<String> stockCodes) throws IOException, ParseException {
        InputStream inputStream = getParam_indexFundamentalHSI.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        List<String> indexFundamentalMetricsList = (List<String>) indexReqParam.get("indexFundamentalMetricsList");
        String token = (String) indexReqParam.get("token");
        String dateYml = (String) indexReqParam.get("enddate");
        String date = getResult_NoHoliday.getResult_NoHoliday(Integer.parseInt(dateYml.substring(0, 4)));

        indexFundamentalCNParam_RootVO paramvo = new indexFundamentalCNParam_RootVO();
        paramvo.setToken(token);
        paramvo.setDate(date);
        paramvo.setMetricsList(indexFundamentalMetricsList);
        paramvo.setStockCodes(stockCodes);

        return JSON.toJSONString(paramvo);
    }

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(getSingleIndexParamJson());
        System.out.println("11111111111111111111111111111");
        System.out.println("22222222222222222222222222222");
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "399702", "399965", "399966");
        System.out.println("333333333333333333333333333333");
        Date nearestDataInDB = new Date();
    }
}
