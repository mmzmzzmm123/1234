package invest.lixinger.index.hotCp.CN;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.hotCp.CN.VO.indexHotCpCNParam_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class getParam_indexHotCpCN {
    public static String getParamIndexHotCpCN(List<String> stockCodesList) throws IOException, ParseException {
        InputStream inputStream = getParam_indexHotCpCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        indexHotCpCNParam_RootVO paramvo = new indexHotCpCNParam_RootVO();
        paramvo.setToken(token);
        paramvo.setStockCodes(stockCodesList);
        return JSON.toJSONString(paramvo);
    }


    public static void main(String[] args) throws IOException, ParseException {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "000808");
        System.out.println(getParamIndexHotCpCN(list));

    }
}
