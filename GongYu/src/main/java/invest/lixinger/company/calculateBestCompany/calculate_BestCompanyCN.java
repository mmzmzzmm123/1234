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
import java.util.*;

import static invest.lixinger.company.fs.request_fsCompanyCN.requestFsCompanyCN;
import static invest.lixinger.company.fsTypeOfCompany.request_fsTypeOfCompanyCN.requestFsTypeOfCompanyCN;
import static invest.lixinger.company.fundamental.request_companyFundamentalCN.requestCompanyFundamentalCN;
import static invest.lixinger.company.hotTrDri.request_companyHotTrDriCN.requestCompanyHotTrDriCN;

/**
 * 计算roe+pe+pb+ps
 */
public class calculate_BestCompanyCN extends mybatisNoSpringUtils {

    @Test
    public void calculateBestCompanyCN() throws IOException, ParseException {
        codeAndNameMapper codeAndNameMapper = session.getMapper(codeAndNameMapper.class);
        List<codeAndNameVO> codeAndNameList = codeAndNameMapper.findAll();
        // 获得某一行业的股票代码
        fsTypeOfCompanyCNResult_RootVO fsTypeOfCompanyCNResult_rootVO = requestFsTypeOfCompanyCN();
        // 计算公司的财务报表，主要取 roe、收入、利润
        Map<String, Map<String, String>> doubleFsMap = requestFsCompanyCN(fsTypeOfCompanyCNResult_rootVO, codeAndNameList);
        // 计算pe、pb、ps
        Map<String, Map<String, String>> doubleFundMap = requestCompanyFundamentalCN(fsTypeOfCompanyCNResult_rootVO, doubleFsMap);
        // 计算最近涨跌幅
        Map<String, Map<String, String>> doubleTrDriMap = requestCompanyHotTrDriCN(fsTypeOfCompanyCNResult_rootVO, doubleFundMap);

        // 权重数组，总和不要超过10，分别表示roe、估值、估值百分位
        // 当经济处于低谷时，应该调大roe
        // 当经济处于中位数，应该调小roe
        double[] weight = {5, 4, 1};
        List<Map.Entry<String, Map<String, String>>> listDoubleFundMap = calculateFundFs(doubleTrDriMap, weight);
        // 按key排序
        List<Map.Entry<String, Map<String, String>>> sortedListDoubleFundMap = sortListDoubleFundMapByKey(listDoubleFundMap);
        sortedListDoubleFundMap.forEach(System.out::println);

    }

    /**
     * 对加权roe(wroe)，扣非roe(kroe)，估值指标，估值指标百分位排名
     *
     * @param doubleFundMap 返回结果
     */
    public static List<Map.Entry<String, Map<String, String>>> calculateFundFs(Map<String, Map<String, String>> doubleFundMap, double[] weight) {
        for (String key : doubleFundMap.keySet()) {
            Map<String, String> mapTemp = doubleFundMap.get(key);
            // 加权roe
            double rankWRoe = Double.parseDouble(mapTemp.get("rankWRoe"));
            // 扣非roe
            double rankKRoe = Double.parseDouble(mapTemp.get("rankKRoe"));
            // 基本面
            double rankFund = Double.parseDouble(mapTemp.get("rankFund"));
            // 估值百分位
            double rankPos = Double.parseDouble(mapTemp.get("rankPos"));
            double rankResult = 0;
            rankResult += (rankWRoe + rankKRoe) / 2 * 0.1 * weight[0];
            rankResult += rankFund / 3 * 0.1 * weight[1] + rankPos / 3 * 0.1 * weight[2];
            mapTemp.put("rankResult", String.valueOf(new DecimalFormat("0.0").format(rankResult)));
            doubleFundMap.put(key, mapTemp);
        }
        return doubleMapSort(doubleFundMap);
    }

    /**
     * 按key排序
     *
     * @param listDoubleFundMap 未排序
     * @return 按key排序的list
     */
    public static List<Map.Entry<String, Map<String, String>>> sortListDoubleFundMapByKey(List<Map.Entry<String, Map<String, String>>> listDoubleFundMap) {
        for (int i = 0; i < listDoubleFundMap.size(); i++) {
            Map.Entry<String, Map<String, String>> map = listDoubleFundMap.get(i);
            Map<String, String> sortMap = new TreeMap<String, String>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            sortMap.putAll(map.getValue());
            listDoubleFundMap.get(i).setValue(sortMap);
        }
        return listDoubleFundMap;
    }


    /**
     * 按照最终结果进行排序，方便查看最小的rankResult
     *
     * @param doubleFundMap 排序后的最终结果，双map
     */
    public static List<Map.Entry<String, Map<String, String>>> doubleMapSort(Map<String, Map<String, String>> doubleFundMap) {
        List<Map.Entry<String, Map<String, String>>> list = new ArrayList<>(doubleFundMap.entrySet());
        Collections.sort(list, (o1, o2) -> {
            if (Double.parseDouble(o1.getValue().get("rankResult")) > Double.parseDouble(o2.getValue().get("rankResult"))) {
                return 1;
            }
            return -1;
        });
        return list;
    }

    public calculate_BestCompanyCN() throws FileNotFoundException {
    }


}
