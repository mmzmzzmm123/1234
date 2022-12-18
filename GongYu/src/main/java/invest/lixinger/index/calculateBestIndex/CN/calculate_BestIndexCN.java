package invest.lixinger.index.calculateBestIndex.CN;

import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNResult_RootVO;
import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNReulst_DataVO;
import invest.lixinger.index.getAllIndex.CN.VO.allIndexCNResult_DataVO;
import invest.lixinger.index.getAllIndex.CN.VO.allIndexCNResult_RootVO;
import invest.lixinger.index.getAllIndex.CN.request_getAllIndexCN;
import invest.lixinger.index.hotCp.CN.VO.indexHotCpCNResult_RootVO;
import invest.lixinger.index.hotCp.CN.getParam_indexHotCpCN;
import invest.lixinger.index.hotCp.CN.getResult_indexHotCpCN;
import invest.lixinger.index.hotCp.CN.request_indexHotCpCN;
import invest.lixinger.utils.netRequest;
import mybatisNoSpringUtils.mybatisNoSpringUtils;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

import static invest.lixinger.index.fundamental.CN.getParam_indexFundamentalCN.getAllIndexParamJsonCN;
import static invest.lixinger.index.fundamental.CN.getResult_indexFundamentalCN.getResultObjCN;
import static invest.lixinger.index.getAllIndex.CN.request_getAllIndexCN.requestGetAllIndexCN;

public class calculate_BestIndexCN {

    @Test
    public void calculateBestIndexCN() throws IOException, ParseException {
//        codeAndNameMapper codeAndNameMapper = session.getMapper(codeAndNameMapper.class);
//        List<codeAndNameVO> codeAndNameList = codeAndNameMapper.findAll();
        InputStream inputStream = calculate_BestIndexCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        allIndexCNResult_RootVO resultresultAllIndexJsonObj = requestGetAllIndexCN();
        // 获取指数代码，名称，发布时间
        Map<String, Object> codeNameDateMap = getCodeNameDate(resultresultAllIndexJsonObj);
        // 获取codeNameDate的基本面--------------------------------------
        List<indexFundamentalCNReulst_DataVO> fundamentalDataVOList = getAllIndexFundamental(codeNameDateMap, indexReqParam);
        // 获取codeNameDateCvpos--------------------------------------
        List<Map.Entry<String, Object>> codeNameDatePosMap = getCodeNameDateCvpos(codeNameDateMap, fundamentalDataVOList);
        // 剔除不想要的stockcode---------------
        codeNameDatePosMap = removeCodeNameCvpos(codeNameDatePosMap, indexReqParam);
        // 添加时间范围内的涨跌幅年化收益率(cpc)-----------
        List<Map.Entry<String, Object>> codeNameDatePosCpcMap = getCodeNameDateCvposCpc(codeNameDatePosMap, indexReqParam);
        // 打印输出
        for (int i = 0; i < codeNameDatePosCpcMap.size(); i++) {
            System.out.println(codeNameDatePosCpcMap.get(i));
        }
    }

    /**
     * 获得代码、名称、日期、最近涨跌幅
     * @param codeNameDatePosMap 代码、名称、日期 map
     * @param indexReqParam 获取参数配置
     * @return 获得代码、名称、日期、最近涨跌幅 map
     */
    private static List<Map.Entry<String, Object>> getCodeNameDateCvposCpc(List<Map.Entry<String, Object>> codeNameDatePosMap, Map indexReqParam) throws IOException, ParseException {
        String indexHotCpCNURL = (String) indexReqParam.get("indexHotCpCNURL");
        List<String> stockCodesList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            stockCodesList.add(codeNameDatePosMap.get(i).getKey());
        }
        String paramJson = getParam_indexHotCpCN.getParamIndexHotCpCN(stockCodesList);
        String resultJson = netRequest.jsonNetPost(indexHotCpCNURL, paramJson);
//        String resultJson = "{\"code\":1,\"message\":\"success\",\"data\":[{\"stockCode\":\"000808\",\"cpc_fys\":-0.22593853735079183,\"cpc\":0.008284313169895086,\"cpc_m1\":-0.025265935591084587,\"cpc_m3\":0.1045227201711747,\"cpc_m6\":-0.01662903670050131,\"cpc_y1\":-0.22467360610779274,\"cpc_y3\":0.019234250669953212,\"cpc_y5\":0.0099643493535162,\"cpc_y10\":0.08799514336843361,\"cpc_fs\":0.10717872000871664,\"latestDate\":\"2022-12-15T16:00:00.000Z\"}]}";
        indexHotCpCNResult_RootVO resultObj = (indexHotCpCNResult_RootVO) getResult_indexHotCpCN.getResultIndexHotCpCN(resultJson);
        DecimalFormat df = new DecimalFormat("0.00%");
        for (int i = 0; i < 100; i++) {
            String resStockCode = resultObj.getData().get(i).getStockCode();
            if (codeNameDatePosMap.get(i).getKey().equals(resStockCode)) {
                List<String> list = new ArrayList<>();
                String cpcm3 = df.format(resultObj.getData().get(i).getCpc_m3());
                String cpcm6 = df.format(resultObj.getData().get(i).getCpc_m6());
                String cpcy1 = df.format(resultObj.getData().get(i).getCpc_y1());
                String cpcy3 = df.format(resultObj.getData().get(i).getCpc_y3());
                Collections.addAll(list, "3个月：" + cpcm3, "6个月：" + cpcm6, "1年：" + cpcy1, "3年：" + cpcy3);
                List<String> codeNameDatePosCpcDoubleList = ((List) codeNameDatePosMap.get(i).getValue());
                codeNameDatePosCpcDoubleList.addAll(list);
                codeNameDatePosMap.get(i).setValue(codeNameDatePosCpcDoubleList);
            }
        }
        return codeNameDatePosMap;
    }

    /**
     * 只返回某一时间点之后的数据
     * @param resultObj
     * @return
     */
    private static Map<String, Object> getCodeNameDate(allIndexCNResult_RootVO resultObj) {
        Map<String, Object> allIndexMap = new HashMap<>();
        for (allIndexCNResult_DataVO datavo : resultObj.getData()) {
            String launchDate = datavo.getLaunchDate().substring(0, 10);
            if (launchDate.compareTo("2014-05-01") <= 0) {
                List<String> list = new ArrayList<>();
                Collections.addAll(list, datavo.getName(), launchDate);
                allIndexMap.put(datavo.getStockCode(), list);
            }
        }
        return allIndexMap;
    }

    // 获取基本面数据
    private static List<indexFundamentalCNReulst_DataVO> getAllIndexFundamental(Map<String, Object> codeNameDateMap, Map indexReqParam) throws IOException, ParseException {
        String indexFundamentalCNURL = (String) indexReqParam.get("indexFundamentalCNURL");
        // 获取所有的code列表--------------------------------
        List<List<String>> doubleList = getAllCodeList(codeNameDateMap);
        // 请求fundamental数据-------------------------
        List<indexFundamentalCNResult_RootVO> resultObjList = getAndRequestFundamentalData(doubleList, indexFundamentalCNURL);
        // 将两个 resultObjList 进行合并--------------------
        for (int i = 1; i < doubleList.size(); i++) {
            resultObjList.get(0).getData().addAll(resultObjList.get(i).getData());
        }
        List<indexFundamentalCNReulst_DataVO> fundamentalDataVOList = new ArrayList<>(resultObjList.get(0).getData());
        System.out.println("总fundamentalDataVO个数==" + fundamentalDataVOList.size());
        return fundamentalDataVOList;

    }


    /**
     * 获取基本面数据
     */
    private static List<indexFundamentalCNResult_RootVO> getAndRequestFundamentalData(List<List<String>> doubleList, String indexFundamentalCNURL) throws IOException, ParseException {
        List<String> paramAllIndexJsonList = new ArrayList<>();
        List<String> resultAllIndexJsonList = new ArrayList<>();
        List<indexFundamentalCNResult_RootVO> resultObjList = new ArrayList<>();
        for (List<String> strings : doubleList) {
            paramAllIndexJsonList.add(getAllIndexParamJsonCN(strings));
        }
        // 请求数据后，得到结果List
        for (int i = 0; i < doubleList.size(); i++) {
            resultAllIndexJsonList.add(netRequest.jsonNetPost(indexFundamentalCNURL, paramAllIndexJsonList.get(i)));
        }
        for (int i = 0; i < doubleList.size(); i++) {
            resultObjList.add((indexFundamentalCNResult_RootVO) getResultObjCN(resultAllIndexJsonList.get(i)));
        }

        return resultObjList;
//        System.out.println(resultObjList.size());
    }

    /**
     * 获取 codeNameCvpos
     *
     * @param codeNameDateMap
     * @param fundamentalDataVOList
     * @return
     */
    private static List<Map.Entry<String, Object>> getCodeNameDateCvpos(Map<String, Object> codeNameDateMap, List<indexFundamentalCNReulst_DataVO> fundamentalDataVOList) {
        for (indexFundamentalCNReulst_DataVO vo : fundamentalDataVOList) {
            double result = calculateAllIndexFundamental(vo);
            for (Map.Entry<String, Object> entry : codeNameDateMap.entrySet()) {
                if (entry.getKey().equals(vo.getStockCode())) {
                    List<String> list = (List) entry.getValue();
                    list.add(String.format("%.2f", result * 100));
                    entry.setValue(list);
                }
            }
        }
        List<Map.Entry<String, Object>> codeNameDateList = new ArrayList<>(codeNameDateMap.entrySet());
        Collections.sort(codeNameDateList, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                double o1Double = Double.parseDouble((String) ((List) o1.getValue()).get(2));
                double o2Double = Double.parseDouble((String) ((List) o2.getValue()).get(2));
                if (o1Double > o2Double) {
                    return 1;
                }
                return -1;
            }
        });

        return codeNameDateList;
    }

    /**
     * 计算综合百分位
     *
     * @param vo 数据vo
     * @return 结算结果
     */
    private static double calculateAllIndexFundamental(indexFundamentalCNReulst_DataVO vo) {
        double pe_10_cvpos = vo.getPe_ttm().getY10().getMedian().getCvpos();
        double pb_10_cvpos = vo.getPb().getY10().getMedian().getCvpos();
        double ps_10_cvpos = vo.getPs_ttm().getY10().getMedian().getCvpos();
        return (pe_10_cvpos + pb_10_cvpos + ps_10_cvpos) / 3;
    }

    // 获取小于指定数量(100个)的多个列表AllCodeList。因为每次请求只能小于100个数据
    private static List<List<String>> getAllCodeList(Map<String, Object> codeNameLaunchdateMap) {
        List<String> allCodeList = new ArrayList<>();
        List<List<String>> doubleList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : codeNameLaunchdateMap.entrySet()) {
            allCodeList.add(entry.getKey());
        }

        // 新建n个列表
        int singleListLength = 100;
        for (int i = 0; i < allCodeList.size() / singleListLength + 1; i++) {
            doubleList.add(new ArrayList<>());
        }
        System.out.println("allCodeList.size()=" + allCodeList.size());
        // doubleList中的每个列表，装入小于 singleListLength 个代码
        int j = 0;
        for (int i = 1; i < allCodeList.size() + 1; i++) {
            doubleList.get(j).add(allCodeList.get(i - 1));
            if (i % singleListLength == 0) {
                j++;
            }
        }
        return doubleList;
    }

    /**
     * 剔除不想要的代码
     *
     * @param codeNameDatePosMap
     * @param indexReqParam
     * @return
     */
    private static List<Map.Entry<String, Object>> removeCodeNameCvpos(List<Map.Entry<String, Object>> codeNameDatePosMap, Map indexReqParam) throws IOException {
        List<String> removeCode = (List<String>) indexReqParam.get("removeCode");
        // 因为从前往后会出错，所以使用从后往前
        for (int i = codeNameDatePosMap.size() - 1; i >= 0; i--) {
            for (int j = 0; j < removeCode.size(); j++) {
                if (codeNameDatePosMap.get(i).getKey().equals(removeCode.get(j))) {
                    codeNameDatePosMap.remove(i);
                }
            }
        }
        System.out.println("剔除自定义的code之后，列表长度为==" + codeNameDatePosMap.size());
        return codeNameDatePosMap;
    }

    public calculate_BestIndexCN() throws FileNotFoundException {
    }
}
