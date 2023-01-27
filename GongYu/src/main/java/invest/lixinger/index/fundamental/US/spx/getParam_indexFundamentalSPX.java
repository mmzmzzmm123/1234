package invest.lixinger.index.fundamental.US.spx;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.fundamental.US.spx.VO.indexFundamentalSPXParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//{"token":"af111e8f-e2ae-47ee-a676-82d7caeeaafd","date":"2022-04-22","stockCodes":["000016"],"metricsList":["pe_ttm.y10.median","pb.y10.median","ps_ttm.y10.median"]}
//{"date":"2022-04-27","metricsList":["\"pe_ttm.y20.median\"","\"pe_ttm.y10.median\"","\"pb.y10.median\"","\"pb.y20.median\"","\"ps_ttm.y10.median\"","\"ps_ttm.y20.median\""],"stockCodes":["000016"],"token":"af111e8f-e2ae-47ee-a676-82d7caeeaafd"}
public class getParam_indexFundamentalSPX {
    public static String getSingleIndexParamJson(String startDate) throws IOException, ParseException {
        InputStream inputStream = getParam_indexFundamentalSPX.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        List<String> indexFundamentalMetricsList = (List<String>) indexReqParam.get("indexFundamentalMetricsList");
//        indexFundamentalMetricsList.removeIf(e -> e.contains("cp"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//定义日期显示格式
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -15);
        if(startDate.equals("")){
             startDate = sdf.format(cal.getTime());
        }
        String today = sdf.format(new Date());
//        String today = "2020-02-01";
        indexFundamentalSPXParam_RootVO paramvo = new indexFundamentalSPXParam_RootVO();
        paramvo.setToken(token);
        paramvo.setStartDate(startDate);
        paramvo.setEndDate(today);
        paramvo.setStockCodes(new ArrayList<>(Collections.singleton(".INX")));
        paramvo.setMetricsList(indexFundamentalMetricsList);

        return JSON.toJSONString(paramvo);
    }


    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(getSingleIndexParamJson(""));
        System.out.println("11111111111111111111111111111");
        System.out.println("22222222222222222222222222222");
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "399702", "399965", "399966");
        System.out.println("333333333333333333333333333333");
        Date nearestDataInDB = new Date();
    }
}
