package invest.lixinger.company.fs;

import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;

public class request_fsCompanyCN {
    public static fsTypeOfCompanyCNResult_RootVO requestFsTypeOfCompanyCN() throws IOException, ParseException {

        InputStream inputStream = request_fsCompanyCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String companyFsBankURLCN = (String) indexReqParam.get("companyFsBankURLCN");
        String paramJson = getParam_fsCompanyCN.getParamFsTypeOfCompanyCN();
        String resultJson = netRequest.jsonNetPost(companyFsBankURLCN, paramJson);

//        fsTypeOfCompanyCNResult_RootVO resultObj = (fsTypeOfCompanyCNResult_RootVO) getResult_fsCompanyCN.getResultObj(resultJson);
//        resultObj.getData().forEach(System.out::println);
//        System.out.println(resultObj.getData().size());
        return resultObj;
    }

    public static void main(String[] args) throws IOException, ParseException {
        requestFsTypeOfCompanyCN();
    }
}
