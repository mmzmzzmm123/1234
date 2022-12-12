package invest.lixinger.fundCompany.getAllfundCompany.CN;

import com.alibaba.fastjson.JSON;
import invest.lixinger.fundCompany.getAllfundCompany.CN.VO.allfundCompanyCNParam_RootVO;
import invest.lixinger.index.getAllIndex.CN.VO.allIndexCNParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class getParam_getAllfundCompanyCN {
    public static String getParamGetAllfundCompanyCN() throws IOException {
        InputStream inputStream = getParam_getAllfundCompanyCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map conf = new Yaml().load(inputStream);
        String token = (String) conf.get("token");
        allfundCompanyCNParam_RootVO paramvo = new allfundCompanyCNParam_RootVO();
        paramvo.setToken(token);

        return JSON.toJSONString(paramvo);
    }

    public static Object getParamObjCN(String json) {
        String temp = "{\"token\":\"9e9b88fa-9d9d-462f-9d78-7be52e975550\"}";
        return JSON.parseObject(temp, allfundCompanyCNParam_RootVO.class);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getParamGetAllfundCompanyCN());
        System.out.println(getParamObjCN(""));
    }
}
