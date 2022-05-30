package invest.lixinger.index.fundamental;

import GYProject.mybatisTest.mybatisNoSpringUtils.mybatisNoSpringUtils;
import invest.lixinger.index.fundamental.VO.fundamentalResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class request_fundamental_dateRange extends mybatisNoSpringUtils {
    public static void main(String[] args) throws IOException, ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2022-05-01");
        fundamentalResult_RootVO vo=getRequest_fundamental_dateRange(startDate);
        System.out.println(vo);
    }

    public static fundamentalResult_RootVO getRequest_fundamental_dateRange(Date startDate) throws IOException, ParseException {
        String filePath = request_fundamental_dateRange.class.getClassLoader().getResource("indexReqParam.yml").getPath();
        Map indexReqParam = new Yaml().load(new FileInputStream(filePath));
        String fundamentalURL = (String) indexReqParam.get("fundamentalURL");
        String paramJson = getParam_fundamental.getSingleIndexParam_dateRangeJson(startDate);
        String resultJson = netRequest.jsonNetPost(fundamentalURL, paramJson);
        fundamentalResult_RootVO resultObj = (fundamentalResult_RootVO) getResult_fundamental.getResultObj(resultJson);
        return resultObj;
    }

    public request_fundamental_dateRange() throws FileNotFoundException {
    }
}
