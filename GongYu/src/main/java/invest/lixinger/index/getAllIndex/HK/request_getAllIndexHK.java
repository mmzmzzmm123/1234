package invest.lixinger.index.getAllIndex.HK;

import invest.lixinger.index.fundamental.HK.HSI.VO.indexFundamentalHSIResult_DataVO;
import invest.lixinger.index.fundamental.HK.HSI.VO.indexFundamentalHSIResult_RootVO;
import invest.lixinger.index.fundamental.HK.HSI.getResult_indexFundamentalHSI;
import invest.lixinger.index.getAllIndex.HK.VO.allIndexHKResult_DataVO;
import invest.lixinger.index.getAllIndex.HK.VO.allIndexHKResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

import static invest.lixinger.index.fundamental.HK.HSI.getParam_indexFundamentalHSI.getAllIndexParamJsonHK;
import static invest.lixinger.index.getAllIndex.HK.getResult_getAllIndexHK.getResultObjHK;


public class request_getAllIndexHK {
    public static void main(String[] args) throws IOException, ParseException {
        requestGetAllIndexHK();
    }
    public static void requestGetAllIndexHK() throws IOException, ParseException {
        InputStream inputStream = request_getAllIndexHK.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String allIndexURLHK = (String) indexReqParam.get("allIndexURLHK");
//        String paramJson = getParam_getAllIndexHK.getParamJson();
//        String resultAllIndexJson = netRequest.jsonNetPost(allIndexURLHK, paramJson);
        String resultAllIndexJson = "{\"code\":1,\"message\":\"success\",\"data\":[{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSAHP\",\"source\":\"hsi\",\"name\":\"恒生沪深港通AH股溢价指数\",\"currency\":\"HKD\",\"launchDate\":\"2007-07-08T16:00:00.000Z\",\"series\":\"strategy\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSCAIT\",\"source\":\"hsi\",\"name\":\"恒生A股行业龙头指数\",\"currency\":\"CNY\",\"launchDate\":\"2009-09-20T16:00:00.000Z\",\"series\":\"strategy\"},{\"stockCode\":\"HSCEI\",\"areaCode\":\"hk\",\"source\":\"hsi\",\"market\":\"h\",\"name\":\"恒生中国企业指数\",\"launchDate\":\"1994-08-07T16:00:00.000Z\",\"series\":\"thematic\",\"currency\":\"HKD\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSCESI\",\"source\":\"hsi\",\"name\":\"恒生中国企业精明指数\",\"launchDate\":\"2015-06-07T16:00:00.000Z\",\"currency\":\"HKD\",\"series\":\"strategy\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSHCI\",\"source\":\"hsi\",\"currency\":\"HKD\",\"name\":\"恒生医疗保健指数\",\"series\":\"sector\",\"launchDate\":\"2015-08-16T16:00:00.000Z\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSHDYI\",\"source\":\"hsi\",\"name\":\"恒生高股息率指数\",\"launchDate\":\"2012-12-09T16:00:00.000Z\",\"series\":\"thematic\",\"currency\":\"HKD\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSHK35\",\"source\":\"hsi\",\"name\":\"恒生香港35\",\"launchDate\":\"2003-01-19T16:00:00.000Z\",\"series\":\"size\",\"currency\":\"HKD\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSHKBIO\",\"source\":\"hsi\",\"currency\":\"HKD\",\"name\":\"恒生香港上市生物科技指数\",\"series\":\"sector\",\"launchDate\":\"2019-11-30T16:00:00.000Z\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSHYLV\",\"source\":\"hsi\",\"name\":\"恒生港股通高股息低波动指数\",\"launchDate\":\"2017-05-07T16:00:00.000Z\",\"series\":\"strategy\",\"currency\":\"HKD\"},{\"stockCode\":\"HSI\",\"areaCode\":\"hk\",\"source\":\"hsi\",\"market\":\"h\",\"name\":\"恒生指数\",\"launchDate\":\"1989-03-08T16:00:00.000Z\",\"series\":\"size\",\"currency\":\"HKD\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSIII\",\"source\":\"hsi\",\"name\":\"恒生互联网科技业指数\",\"launchDate\":\"2020-07-19T16:00:00.000Z\",\"series\":\"sector\",\"currency\":\"HKD\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSLI\",\"currency\":\"HKD\",\"source\":\"hsi\",\"name\":\"恒生综合大型股指数\",\"launchDate\":\"2001-10-02T16:00:00.000Z\",\"series\":\"size\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSLMI\",\"currency\":\"HKD\",\"source\":\"hsi\",\"name\":\"恒生综合大中型股指数\",\"launchDate\":\"2015-06-07T16:00:00.000Z\",\"series\":\"size\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSMCHYI\",\"source\":\"hsi\",\"currency\":\"HKD\",\"name\":\"恒生中国内地企业高股息率指数\",\"series\":\"strategy\",\"launchDate\":\"2019-08-31T16:00:00.000Z\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSMI\",\"currency\":\"HKD\",\"source\":\"hsi\",\"name\":\"恒生综合中型股指数\",\"launchDate\":\"2001-10-02T16:00:00.000Z\",\"series\":\"size\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSML25\",\"source\":\"hsi\",\"name\":\"恒生中国(香港上市)25指数\",\"launchDate\":\"2003-01-19T16:00:00.000Z\",\"series\":\"size\",\"currency\":\"HKD\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSMSI\",\"currency\":\"HKD\",\"source\":\"hsi\",\"name\":\"恒生综合中小型股指数\",\"launchDate\":\"2015-06-07T16:00:00.000Z\",\"series\":\"size\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSSCHKY\",\"source\":\"hsi\",\"currency\":\"HKD\",\"name\":\"恒生港股通高股息率指数\",\"series\":\"strategy\",\"launchDate\":\"2019-10-31T16:00:00.000Z\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSSCNE\",\"source\":\"hsi\",\"name\":\"恒生港股通新经济指数\",\"series\":\"sector\",\"launchDate\":\"2018-01-07T16:00:00.000Z\",\"currency\":\"HKD\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSSI\",\"currency\":\"HKD\",\"source\":\"hsi\",\"name\":\"恒生综合小型股指数\",\"launchDate\":\"2001-10-02T16:00:00.000Z\",\"series\":\"size\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSSSHID\",\"source\":\"hsi\",\"currency\":\"HKD\",\"name\":\"恒生沪深港创新药精选50指数\",\"series\":\"sector\",\"launchDate\":\"2020-02-29T16:00:00.000Z\"},{\"areaCode\":\"hk\",\"market\":\"h\",\"stockCode\":\"HSTECH\",\"source\":\"hsi\",\"name\":\"恒生科技指数\",\"series\":\"sector\",\"launchDate\":\"2020-06-30T16:00:00.000Z\",\"currency\":\"HKD\"},{\"stockCode\":\"1000015\",\"areaCode\":\"hk\",\"source\":\"lxri\",\"market\":\"h\",\"name\":\"港股全指\",\"launchDate\":\"1999-12-31T16:00:00.000Z\",\"series\":\"size\",\"currency\":\"HKD\"}]}";
        allIndexHKResult_RootVO resultresultAllIndexJsonObj = (allIndexHKResult_RootVO) getResultObjHK(resultAllIndexJson);
//        // 获取指数代码，名称，发布时间
        List<String[]> allIndexList = getCodeNameLaucndate(resultresultAllIndexJsonObj);
//        allIndexList.forEach(System.out::println);
//        // 获取上面获取指数的基本面信息
        String indexFundamentalHSIURL = (String) indexReqParam.get("indexFundamentalHSIURL");
        getAllIndexFundamental(allIndexList, indexFundamentalHSIURL, indexReqParam);
    }

    // 只返回某一时间点之后的数据
    private static List<String[]> getCodeNameLaucndate(allIndexHKResult_RootVO resultObj) {
        List<String[]> list = new ArrayList<>();
        for (allIndexHKResult_DataVO datavo : resultObj.getData()) {
            String launchDate = datavo.getLaunchDate().substring(0, 10);
//            if (launchDate.compareTo("2014-05-01") <= 0) {
                list.add(new String[]{datavo.getStockCode(), datavo.getName(), launchDate});
//            }
        }
        return list;
    }

    //
    private static void getAllIndexFundamental(List<String[]> codeNameLaunchdateList, String indexFundamentalHSIURL, Map indexReqParam) throws IOException, ParseException {
        // 获取所有的code列表--------------------------------
        List<String> allCodeList = new ArrayList<>();
        List<List<String>> doubleList = new ArrayList<>();
        getAllCodeList(codeNameLaunchdateList, allCodeList, doubleList);
        // 请求数据 and 得到结果-------------------------
        List<String> paramAllIndexJsonList = new ArrayList<>();
        List<String> resultAllIndexJsonList = new ArrayList<>();
        List<indexFundamentalHSIResult_RootVO> resultObjList = new ArrayList<>();
        getAndRequestData(doubleList, paramAllIndexJsonList, indexFundamentalHSIURL, resultAllIndexJsonList, resultObjList);

        // 将两个 resultObjList 进行合并--------------------
        for (int i = 1; i < doubleList.size(); i++) {
            resultObjList.get(0).getData().addAll(resultObjList.get(i).getData());
        }
        List<indexFundamentalHSIResult_DataVO> fundamentalDataVOList = new ArrayList<>();
        fundamentalDataVOList.addAll(resultObjList.get(0).getData());
        System.out.println("总fundamentalDataVO个数==" + fundamentalDataVOList.size());

        // 获取codeNameCvpos--------------------------------------
        List<String[]> codeNameCvposList = new ArrayList<>();
        getCodeNameCvpos(codeNameCvposList, fundamentalDataVOList);
        // 将 codeNameCvpos 和 codeNameDate 进行合并
        for (String[] codeNameCvpos : codeNameCvposList) {
            for (String[] codeNameDate : codeNameLaunchdateList) {
                // 如果code相同，那么就进行替换
                if (codeNameCvpos[0].equals(codeNameDate[0])) {
                    codeNameCvpos[1] = codeNameDate[1];
                }
            }
        }
        List<String> removeCode = (List<String>) indexReqParam.get("removeCode");
        removeCodeNameCvpos(codeNameCvposList, removeCode);
        for (int i = 0; i < codeNameCvposList.size(); i++) {
            System.out.println("codeNameCvposList=" + Arrays.toString(codeNameCvposList.get(i)));
        }
    }

    //  剔除不想要的代码
    private static void removeCodeNameCvpos(List<String[]> codeNameCvposList, List<String> removeCode) throws IOException {
        for (int i = codeNameCvposList.size() - 1; i >= 0; i--) {
            for (int j = 0; j < removeCode.size(); j++) {
                if (codeNameCvposList.get(i)[0].equals(removeCode.get(j))) {
                    codeNameCvposList.remove(i);
                    break;
                }
            }
        }
        System.out.println("剔除自定义的code之后，列表长度为==" + codeNameCvposList.size());
    }

    // 获取AllCodeList列表：包含小于指定数量的多个列表。在2022年时，每次请求只支持小于100个数据
    private static List<List<String>> getAllCodeList(List<String[]> codeNameLaunchdateList, List<String> allCodeList, List<List<String>> doubleList) {
        for (int i = 0; i < codeNameLaunchdateList.size(); i++) {
            allCodeList.add(codeNameLaunchdateList.get(i)[0]);
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
        return null;
    }

    private static void getAndRequestData(List<List<String>> doubleList, List<String> paramAllIndexJsonList, String indexFundamentalHSIURL, List<String> resultAllIndexJsonList, List<indexFundamentalHSIResult_RootVO> resultObjList) throws IOException, ParseException {
        for (List<String> strings : doubleList) {
            paramAllIndexJsonList.add(getAllIndexParamJsonHK(strings));
        }
        // 请求数据后，得到结果List
        for (int i = 0; i < doubleList.size(); i++) {
            resultAllIndexJsonList.add(netRequest.jsonNetPost(indexFundamentalHSIURL, paramAllIndexJsonList.get(i)));
        }

        for (int i = 0; i < doubleList.size(); i++) {
            resultObjList.add((indexFundamentalHSIResult_RootVO) getResult_indexFundamentalHSI.getAllIndexResultObjHK(resultAllIndexJsonList.get(i)));
        }
    }

    // 获取codeNameCvpos
    private static void getCodeNameCvpos(List<String[]> codeNameCvposList, List<indexFundamentalHSIResult_DataVO> fundamentalDataVOList) {
        for (indexFundamentalHSIResult_DataVO vo : fundamentalDataVOList) {
//            System.out.println(vo);
            double result = calculateAllIndexFundamental(vo);
            codeNameCvposList.add(new String[]{vo.getStockCode(), "", String.format("%.2f", result * 100)});
        }
        // 按综合百分位进行升序
        Collections.sort(codeNameCvposList, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return Double.compare(Double.parseDouble(o1[2]), Double.parseDouble(o2[2]));
            }
        });
    }

    // 计算综合百分位
    private static double calculateAllIndexFundamental(indexFundamentalHSIResult_DataVO vo) {
        double pe_10_cvpos = vo.getPe_ttm().getY10().getMedian().getCvpos();
        double pb_10_cvpos = vo.getPb().getY10().getMedian().getCvpos();
        double ps_10_cvpos = vo.getPs_ttm().getY10().getMedian().getCvpos();
        //        System.out.println("股票代码为：" + vo.getStockCode() + "，综合百分位为：" + new DecimalFormat("0.00%").format(result));
        return (pe_10_cvpos + pb_10_cvpos + ps_10_cvpos) / 3;
    }
}
