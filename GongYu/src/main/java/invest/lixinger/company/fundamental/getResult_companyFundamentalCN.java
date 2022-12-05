package invest.lixinger.company.fundamental;



import com.alibaba.fastjson.JSON;
import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;
import invest.lixinger.company.fundamental.VO.companyFundamentalCNResult_RootVO;


public class getResult_companyFundamentalCN {
    public static Object getResultCompanyFundamentalCN(String jsonString){
        return JSON.parseObject(jsonString, companyFundamentalCNResult_RootVO.class);
    }
    public static void main(String[] args) {

    }
}
