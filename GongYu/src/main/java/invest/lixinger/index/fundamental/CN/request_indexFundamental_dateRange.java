package invest.lixinger.index.fundamental.CN;

import mybatisNoSpringUtils.mybatisNoSpringUtils;
import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNResult_RootVO;
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
public class request_indexFundamental_dateRange extends mybatisNoSpringUtils {
    public static void main(String[] args) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2022-05-01");
        indexFundamentalCNResult_RootVO vo = request_fundamental_dateRangeCN(startDate);
        System.out.println(vo);
    }

    public static indexFundamentalCNResult_RootVO request_fundamental_dateRangeCN(Date startDate) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        InputStream inputStream = request_indexFundamental_dateRange.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String indexFundamentalCNURL = (String) indexReqParam.get("indexFundamentalCNURL");
        String paramJson = getParam_indexFundamentalCN.getSingleIndexParam_dateRangeJsonCN(sdf.format(startDate));
        String resultJson = netRequest.jsonNetPost(indexFundamentalCNURL, paramJson);
        indexFundamentalCNResult_RootVO resultObj = (indexFundamentalCNResult_RootVO) getResult_indexFundamentalCN.getResultObjCN(resultJson);
        return resultObj;
    }

    public request_indexFundamental_dateRange() throws FileNotFoundException {
    }
}
