package invest.lixinger.company.calculateBestCompany;

import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;
import invest.lixinger.company.fundamental.request_companyFundamentalCN;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import static invest.lixinger.company.fs.request_fsCompanyCN.requestFsCompanyCN;
import static invest.lixinger.company.fsTypeOfCompany.request_fsTypeOfCompanyCN.requestFsTypeOfCompanyCN;
import static invest.lixinger.company.fundamental.request_companyFundamentalCN.requestCompanyFundamentalCN;

public class calculateBestCompany {
    public static void calculate() throws IOException, ParseException {
        fsTypeOfCompanyCNResult_RootVO fsTypeOfCompanyCNResult_rootVO = requestFsTypeOfCompanyCN();
        Map<String, Map<String, String>> doubleFsMap = requestFsCompanyCN(fsTypeOfCompanyCNResult_rootVO);
        Map<String, Map<String, String>> doubleFundMap = requestCompanyFundamentalCN(fsTypeOfCompanyCNResult_rootVO,doubleFsMap);


    }

    public static void main(String[] args) throws IOException, ParseException {
        calculate();
    }
}
