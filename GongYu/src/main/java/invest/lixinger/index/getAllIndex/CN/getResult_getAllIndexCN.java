package invest.lixinger.index.getAllIndex.CN;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.getAllIndex.CN.VO.allIndexCNResult_RootVO;

public class getResult_getAllIndexCN {
    public static Object getResultObjCN(String jsonString){
        return JSON.parseObject(jsonString, allIndexCNResult_RootVO.class);
    }
    public static void main(String[] args) {

        String temp="{\"code\":1,\"message\":\"success\",\"data\":[{\"areaCode\":\"cn\",\"market\":\"a\",\"stockCode\":\"930598\",\"source\":\"csi\",\"currency\":\"CNY\",\"name\":\"稀土产业\",\"series\":\"thematic\",\"launchDate\":\"2015-03-01T16:00:00.000Z\",\"rebalancingFrequency\":\"semi-annually\"}]}";
        allIndexCNResult_RootVO resultvo = JSON.parseObject(temp, allIndexCNResult_RootVO.class);
        System.out.println(resultvo);
    }
}
