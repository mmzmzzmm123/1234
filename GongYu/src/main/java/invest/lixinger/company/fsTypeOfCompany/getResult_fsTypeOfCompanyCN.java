package invest.lixinger.company.fsTypeOfCompany;



import com.alibaba.fastjson.JSON;
import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;


public class getResult_fsTypeOfCompanyCN {
    public static Object getResultObj(String jsonString){
        return JSON.parseObject(jsonString, fsTypeOfCompanyCNResult_RootVO.class);
    }
    public static void main(String[] args) {

    }
}
