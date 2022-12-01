package invest.lixinger.company.singleIndustry;


import com.alibaba.fastjson.JSON;
import invest.lixinger.company.singleIndustry.VO.singleIndustryParam_RootVO;
import invest.lixinger.utils._0snakeYaml;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class getParam_singleIndustry {
    public static String getSingleIndustry() throws IOException {
        InputStream inputStream = getParam_singleIndustry.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        String fsType = (String) indexReqParam.get("fsType_bank");
        singleIndustryParam_RootVO singleIndustryParam_rootVO = new singleIndustryParam_RootVO();
        singleIndustryParam_rootVO.setToken(token);
        singleIndustryParam_rootVO.setFsType(fsType);
        String paramJson = JSON.toJSONString(singleIndustryParam_rootVO);
        return paramJson;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getSingleIndustry());
    }


}
