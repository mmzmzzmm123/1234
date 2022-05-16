package invest.lixinger.index.fundamental;

import GYProject.mybatisTest.mybatisNoSpringUtils.mybatisNoSpringUtils;
import invest.lixinger.index.fundamental.VO.fundamentalResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class request_fundamental_dateRange extends mybatisNoSpringUtils {
    public static void main(String[] args) throws IOException {
        Date startDate=new Date();
        getRequest_fundamental_dateRange(startDate);
    }

    public static fundamentalResult_RootVO getRequest_fundamental_dateRange(Date startDate) throws IOException {
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
