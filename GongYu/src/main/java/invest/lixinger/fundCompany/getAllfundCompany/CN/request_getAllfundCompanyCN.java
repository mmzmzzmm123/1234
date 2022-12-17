package invest.lixinger.fundCompany.getAllfundCompany.CN;

import invest.lixinger.fundCompany.getAllfundCompany.CN.VO.allfundCompanyCNResult_DataVO;
import invest.lixinger.fundCompany.getAllfundCompany.CN.VO.allfundCompanyCNResult_RootVO;
import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNResult_RootVO;
import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNReulst_DataVO;
import invest.lixinger.index.getAllIndex.CN.VO.allIndexCNResult_DataVO;
import invest.lixinger.index.getAllIndex.CN.VO.allIndexCNResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

import static invest.lixinger.index.fundamental.CN.getParam_indexFundamentalCN.getAllIndexParamJsonCN;
import static invest.lixinger.index.fundamental.CN.getResult_indexFundamentalCN.getResultObjCN;

public class request_getAllfundCompanyCN {
    public static void main(String[] args) throws IOException, ParseException {
        requestGetAllIndexCN();
    }

    public static void requestGetAllIndexCN() throws IOException, ParseException {
        InputStream inputStream = request_getAllfundCompanyCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String allfundCompanyURLCN = (String) indexReqParam.get("allfundCompanyURLCN");
        String paramJson = getParam_getAllfundCompanyCN.getParamGetAllfundCompanyCN();
        String resultAllFundCompanyJson = netRequest.jsonNetPost(allfundCompanyURLCN, paramJson);
        allfundCompanyCNResult_RootVO resultresultAllFundCompanyJsonObj = (allfundCompanyCNResult_RootVO) getResult_getAllfundCompanyCN.getResultGetAllfundCompanyCN(resultAllFundCompanyJson);
        List<allfundCompanyCNResult_DataVO> top100fundcompanyList = top100fundCompany(resultresultAllFundCompanyJsonObj.getData());
        top100fundcompanyList.forEach(System.out::println);
    }

    /**
     * 只需要前100家公司
     */
    private static List<allfundCompanyCNResult_DataVO> top100fundCompany(List<allfundCompanyCNResult_DataVO> dataVOList) {
        // 把为null的值全部剔除
        List<allfundCompanyCNResult_DataVO> tempList1 = new ArrayList<>();
        for (allfundCompanyCNResult_DataVO dataVO : dataVOList) {
            if (dataVO.getAssetScale() != null) {
                tempList1.add(dataVO);
            }
        }
        // 降序排序
        tempList1.sort((a, b) -> {
            Double aDouble = Double.valueOf(a.getAssetScale());
            Double bDouble = Double.valueOf(b.getAssetScale());
            return bDouble.compareTo(aDouble);
        });
        // 只添加排名前100的公司
        List<allfundCompanyCNResult_DataVO> tempList2 = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            tempList2.add(tempList1.get(i));
        }
        return tempList2;
    }

}
