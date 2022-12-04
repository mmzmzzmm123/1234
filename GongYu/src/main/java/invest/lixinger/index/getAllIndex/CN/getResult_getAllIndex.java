package invest.lixinger.index.getAllIndex.CN;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.getAllIndex.CN.VO.allIndexResult_RootVO;

public class getResult_getAllIndex {
    public static Object getResultObj(String jsonString){
        return JSON.parseObject(jsonString, allIndexResult_RootVO.class);
    }
    public static void main(String[] args) {

        String temp="{\"code\":1,\"message\":\"success\",\"data\":[{\"areaCode\":\"cn\",\"market\":\"a\",\"stockCode\":\"930598\",\"source\":\"csi\",\"currency\":\"CNY\",\"name\":\"稀土产业\",\"series\":\"thematic\",\"launchDate\":\"2015-03-01T16:00:00.000Z\",\"rebalancingFrequency\":\"semi-annually\"}]}";
        allIndexResult_RootVO resultvo = JSON.parseObject(temp, allIndexResult_RootVO.class);
        System.out.println(resultvo);
    }
}
