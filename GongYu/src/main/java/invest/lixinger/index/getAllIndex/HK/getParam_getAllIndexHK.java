package invest.lixinger.index.getAllIndex.HK;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.getAllIndex.HK.VO.allIndexHKParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class getParam_getAllIndexHK {
    public static String getParamJsonHK() throws IOException {
        InputStream inputStream = getParam_getAllIndexHK.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map conf = new Yaml().load(inputStream);
        String token = (String) conf.get("token");
        allIndexHKParam_RootVO paramvo = new allIndexHKParam_RootVO();
        paramvo.setToken(token);

        return JSON.toJSONString(paramvo);
    }

    public static Object getParamObjHK(String json) {
        String temp = "{\"token\":\"9e9b88fa-9d9d-462f-9d78-7be52e975550\"}";
        return JSON.parseObject(temp, allIndexHKParam_RootVO.class);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getParamJsonHK());
        System.out.println(getParamObjHK(""));
    }
}
