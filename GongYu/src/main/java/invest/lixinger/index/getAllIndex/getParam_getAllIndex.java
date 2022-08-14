package invest.lixinger.index.getAllIndex;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.getAllIndex.VO.allIndexParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class getParam_getAllIndex {
    public static String getParamJson() throws IOException {
        String filePath = getParam_getAllIndex.class.getClassLoader().getResource("indexReqParam.yml").getPath();
        Map conf = new Yaml().load(new FileInputStream(filePath));
        String token = (String) conf.get("token");
        allIndexParam_RootVO paramvo = new allIndexParam_RootVO();
        paramvo.setToken(token);
        String json = JSON.toJSONString(paramvo);

        return json;
    }

    public static Object getParamObj(String json) {
        String temp = "{\"token\":\"9e9b88fa-9d9d-462f-9d78-7be52e975550\"}";
        return JSON.parseObject(temp, allIndexParam_RootVO.class);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getParamJson());
        System.out.println(getParamObj(new String()));
    }
}
