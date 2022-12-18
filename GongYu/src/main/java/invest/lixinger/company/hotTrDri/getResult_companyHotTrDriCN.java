package invest.lixinger.company.hotTrDri;



import com.alibaba.fastjson.JSON;
import invest.lixinger.company.fundamental.VO.companyFundamentalCNResult_RootVO;
import invest.lixinger.company.hotTrDri.VO.companyHotTrDriResult_RootVO;


public class getResult_companyHotTrDriCN {
    public static Object getResultCompanyHotTrDriCN(String jsonString){
        return JSON.parseObject(jsonString, companyHotTrDriResult_RootVO.class);
    }
    public static void main(String[] args) {

    }
}
