package invest.lixinger.index.fundamental.US.spx;



import com.alibaba.fastjson.JSON;
import invest.lixinger.index.fundamental.US.spx.VO.indexFundamentalSPXResult_RootVO;


public class getResult_indexFundamentalSPX {
    public static Object getResultObj(String jsonString){
        return JSON.parseObject(jsonString, indexFundamentalSPXResult_RootVO.class);
    }
    public static void main(String[] args) {
        String temp="{\"code\":1,\"message\":\"success\",\"data\":[{\"date\":\"2022-04-22T00:00:00+08:00\",\"pe_ttm\":{\"y10\":{\"median\":{\"avgv\":15.99358923975735,\"cv\":15.088674533305376,\"cvpos\":0.5152138157894737,\"maxpv\":32.57314260168165,\"maxv\":32.57314260168165,\"minv\":8.593906590193125,\"q2v\":12.547018631955986,\"q5v\":14.871018960205824,\"q8v\":18.649198279692527}}},\"stockCode\":\"000016\"}]}";
        indexFundamentalSPXResult_RootVO resultvo = JSON.parseObject(temp, indexFundamentalSPXResult_RootVO.class);
        System.out.println(resultvo);
        System.out.println(resultvo.getData().get(0).getPe_ttm().getY10().getMedian().getCvpos());
        System.out.println(resultvo.getData());
    }
}
