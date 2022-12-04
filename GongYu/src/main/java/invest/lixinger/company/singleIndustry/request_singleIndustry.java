package invest.lixinger.company.singleIndustry;

import invest.lixinger.company.singleIndustry.VO.singleIndustryResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class request_singleIndustry {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = request_singleIndustry.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String fundamentalURL = (String) indexReqParam.get("singleIndustryURL");
        String paramJson = getParam_singleIndustry.getSingleIndustry();
        String resultJson = netRequest.jsonNetPost(fundamentalURL, paramJson);

        singleIndustryResult_RootVO resultObj = (singleIndustryResult_RootVO) getResult_singleIndustry.getResultObj(resultJson);
        resultObj.getData().forEach(System.out::println);
        System.out.println(resultObj.getData().size());
    }


}
