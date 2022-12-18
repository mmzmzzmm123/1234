package invest.lixinger.company.hotTrDri;


import com.alibaba.fastjson.JSON;
import invest.lixinger.company.fs.VO.fsCompanyCNParam_RootVO;
import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_DataVO;
import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;
import invest.lixinger.company.fundamental.VO.companyFundamentalCNParam_RootVO;
import invest.lixinger.company.hotTrDri.VO.companyHotTrDriCNParam_RootVO;
import invest.lixinger.utils.getResult_NoHoliday;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static invest.lixinger.company.fsTypeOfCompany.request_fsTypeOfCompanyCN.requestFsTypeOfCompanyCN;

public class getParam_companyHotTrDriCN {

    public static String getParamCompanyHotTrDriCN(fsTypeOfCompanyCNResult_RootVO fsTypeOfCompanyCNResult_rootVO) throws IOException, ParseException {
        InputStream inputStream = getParam_companyHotTrDriCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        companyHotTrDriCNParam_RootVO vo = new companyHotTrDriCNParam_RootVO();
        vo.setToken(token);

        List<fsTypeOfCompanyCNResult_DataVO> listVO = fsTypeOfCompanyCNResult_rootVO.getData();
        List<String> listStockCodes = new ArrayList<>();
        for (fsTypeOfCompanyCNResult_DataVO fsTypeOfCompanyCNResult_dataVO : listVO) {
            listStockCodes.add(fsTypeOfCompanyCNResult_dataVO.getStockCode());
        }
        vo.setStockCodes(listStockCodes);

        return JSON.toJSONString(vo);
    }

    public static void main(String[] args) throws IOException, ParseException {
        fsTypeOfCompanyCNResult_RootVO vo = requestFsTypeOfCompanyCN();
        System.out.println(getParamCompanyHotTrDriCN(vo));
    }


}
