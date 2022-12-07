package invest.lixinger.company.calculateBestCompany;

import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;
import invest.lixinger.ruoyi.entity.codeAndNameVO;
import invest.lixinger.ruoyi.mapper.codeAndNameMapper;
import mybatisNoSpringUtils.mybatisNoSpringUtils;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static invest.lixinger.company.fs.request_fsCompanyCN.requestFsCompanyCN;
import static invest.lixinger.company.fsTypeOfCompany.request_fsTypeOfCompanyCN.requestFsTypeOfCompanyCN;
import static invest.lixinger.company.fundamental.request_companyFundamentalCN.requestCompanyFundamentalCN;

public class calculateBestCompany extends mybatisNoSpringUtils {

    @Test
    public void calculate() throws IOException, ParseException {
        codeAndNameMapper codeAndNameMapper = session.getMapper(codeAndNameMapper.class);
        List<codeAndNameVO> codeAndNameList = codeAndNameMapper.findAll();
        // 主要是为了得到某一行业的股票代码
        fsTypeOfCompanyCNResult_RootVO fsTypeOfCompanyCNResult_rootVO = requestFsTypeOfCompanyCN();
        // 计算公司的财务报表，主要取roe、收入、利润
        Map<String, Map<String, String>> doubleFsMap = requestFsCompanyCN(fsTypeOfCompanyCNResult_rootVO,codeAndNameList);
        // 计算pe、pb、ps
        Map<String, Map<String, String>> doubleFundMap = requestCompanyFundamentalCN(fsTypeOfCompanyCNResult_rootVO, doubleFsMap);
        List<Map.Entry<String, Map<String, String>>> listDoubleFundMap = calculateFundFs(doubleFundMap);

        listDoubleFundMap.forEach(System.out::println);
        System.out.println(doubleFsMap.get("600000"));


    }

    public static List<Map.Entry<String, Map<String, String>>> calculateFundFs(Map<String, Map<String, String>> doubleFundMap) {
        for (String key : doubleFundMap.keySet()) {
            Map<String, String> mapTemp = doubleFundMap.get(key);
            String rankFs = mapTemp.get("rankFs");
            String rankfund = mapTemp.get("rankfund");
            double rankWeight = Double.parseDouble(rankFs) + Double.parseDouble(rankfund) / 3;
            mapTemp.put("rankWeight", String.valueOf(new DecimalFormat("0.0000").format(rankWeight)));
            doubleFundMap.put(key, mapTemp);
        }
        return doubleMapSort(doubleFundMap);
    }

    public static List<Map.Entry<String, Map<String, String>>> doubleMapSort(Map<String, Map<String, String>> doubleFundMap) {
        List<Map.Entry<String, Map<String, String>>> list = new ArrayList<>(doubleFundMap.entrySet());
        Collections.sort(list, (o1, o2) -> {
            if (Double.parseDouble(o1.getValue().get("rankWeight")) > Double.parseDouble(o2.getValue().get("rankWeight"))) {
                return 1;
            }
            return -1;
        });
        return list;
    }

    public calculateBestCompany() throws FileNotFoundException {
    }


}
