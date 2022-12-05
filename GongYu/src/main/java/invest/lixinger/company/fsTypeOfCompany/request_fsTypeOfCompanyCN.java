package invest.lixinger.company.fsTypeOfCompany;

import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class request_fsTypeOfCompanyCN {
    public static fsTypeOfCompanyCNResult_RootVO requestFsTypeOfCompanyCN() throws IOException {

        InputStream inputStream = request_fsTypeOfCompanyCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String allCompanyURLCN = (String) indexReqParam.get("allCompanyURLCN");
        String paramJson = getParam_fsTypeOfCompanyCN.getParamFsTypeOfCompanyCN();
        String resultJson = netRequest.jsonNetPost(allCompanyURLCN, paramJson);

        fsTypeOfCompanyCNResult_RootVO resultObj = (fsTypeOfCompanyCNResult_RootVO) getResult_fsTypeOfCompanyCN.getResultObj(resultJson);
        resultObj.getData().forEach(System.out::println);
        System.out.println(resultObj.getData().size());
        return resultObj;
    }

    public static void main(String[] args) throws IOException {
        requestFsTypeOfCompanyCN();
    }
}
