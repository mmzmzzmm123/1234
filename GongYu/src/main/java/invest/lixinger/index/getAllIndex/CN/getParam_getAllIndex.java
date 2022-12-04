package invest.lixinger.index.getAllIndex.CN;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.getAllIndex.CN.VO.allIndexParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class getParam_getAllIndex {
    public static String getParamJson() throws IOException {
        InputStream inputStream = getParam_getAllIndex.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map conf = new Yaml().load(inputStream);
        String token = (String) conf.get("token");
        allIndexParam_RootVO paramvo = new allIndexParam_RootVO();
        paramvo.setToken(token);

        return JSON.toJSONString(paramvo);
    }

    public static Object getParamObj(String json) {
        String temp = "{\"token\":\"9e9b88fa-9d9d-462f-9d78-7be52e975550\"}";
        return JSON.parseObject(temp, allIndexParam_RootVO.class);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getParamJson());
        System.out.println(getParamObj(""));
    }
}
