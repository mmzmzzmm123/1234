package invest.lixinger.index.fundamentalCN;

import mybatisNoSpringUtils.mybatisNoSpringUtils;
import invest.lixinger.index.fundamentalCN.VO.fundamentalResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 获取时间范围的 沪深A股 信号
 */
public class request_fundamental_dateRange extends mybatisNoSpringUtils {
    public static void main(String[] args) throws IOException, ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2022-05-01");
        fundamentalResult_RootVO vo=getRequest_fundamental_dateRange(startDate);
        System.out.println(vo);
    }

    public static fundamentalResult_RootVO getRequest_fundamental_dateRange(Date startDate) throws IOException, ParseException {
        InputStream inputStream = request_fundamental_dateRange.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String fundamentalCNURL = (String) indexReqParam.get("fundamentalCNURL");
        String paramJson = getParam_fundamental.getSingleIndexParam_dateRangeJson(startDate);
        String resultJson = netRequest.jsonNetPost(fundamentalCNURL, paramJson);
        fundamentalResult_RootVO resultObj = (fundamentalResult_RootVO) getResult_fundamental.getResultObj(resultJson);
        return resultObj;
    }

    public request_fundamental_dateRange() throws FileNotFoundException {
    }
}
