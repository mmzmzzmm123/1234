package invest.lixinger.company.fsTypeOfCompany;


import com.alibaba.fastjson.JSON;
import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class getParam_fsTypeOfCompanyCN {
    public static String getParamFsTypeOfCompanyCN() throws IOException {
        InputStream inputStream = getParam_fsTypeOfCompanyCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        String fsType = (String) indexReqParam.get("fsType");
        fsTypeOfCompanyCNParam_RootVO singleIndustryParam_rootVO = new fsTypeOfCompanyCNParam_RootVO();
        singleIndustryParam_rootVO.setToken(token);
        singleIndustryParam_rootVO.setFsType(fsType);
        return JSON.toJSONString(singleIndustryParam_rootVO);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getParamFsTypeOfCompanyCN());
    }


}
