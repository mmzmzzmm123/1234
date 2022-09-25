package invest.lixinger.company.singleIndustry;



import com.alibaba.fastjson.JSON;
import invest.lixinger.company.singleIndustry.VO.singleIndustryResult_RootVO;


public class getResult_singleIndustry {
    public static Object getResultObj(String jsonString){
        return JSON.parseObject(jsonString, singleIndustryResult_RootVO.class);
    }
    public static void main(String[] args) {

    }
}
