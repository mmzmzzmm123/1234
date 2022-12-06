package invest.lixinger.company.fs;



import com.alibaba.fastjson.JSON;
import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;


public class getResult_fsCompanyCN {
    public static Object getResultObj(String jsonString){
        return JSON.parseObject(jsonString, fsTypeOfCompanyCNResult_RootVO.class);
    }
    public static void main(String[] args) {

    }
}
