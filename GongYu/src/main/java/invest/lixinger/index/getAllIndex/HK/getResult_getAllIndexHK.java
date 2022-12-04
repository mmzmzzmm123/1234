package invest.lixinger.index.getAllIndex.HK;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.fundamental.HK.HSI.VO.fundamentalHSIResult_RootVO;
import invest.lixinger.index.getAllIndex.HK.VO.allIndexHKResult_RootVO;

public class getResult_getAllIndexHK {
    public static Object getResultObjHK(String jsonString){
        return JSON.parseObject(jsonString, allIndexHKResult_RootVO.class);
    }
    public static void main(String[] args) {

        String temp="{\"code\":1,\"message\":\"success\",\"data\":[{\"areaCode\":\"cn\",\"market\":\"a\",\"stockCode\":\"930598\",\"source\":\"csi\",\"currency\":\"CNY\",\"name\":\"稀土产业\",\"series\":\"thematic\",\"launchDate\":\"2015-03-01T16:00:00.000Z\",\"rebalancingFrequency\":\"semi-annually\"}]}";
        fundamentalHSIResult_RootVO resultvo = JSON.parseObject(temp, fundamentalHSIResult_RootVO.class);
        System.out.println(resultvo);
    }
}
