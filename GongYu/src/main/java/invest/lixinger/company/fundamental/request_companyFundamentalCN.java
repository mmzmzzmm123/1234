package invest.lixinger.company.fundamental;

import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNResult_RootVO;
import invest.lixinger.company.fundamental.VO.companyFundamentalCNResult_DataVO;
import invest.lixinger.company.fundamental.VO.companyFundamentalCNResult_RootVO;
import invest.lixinger.ruoyi.entity.codeAndNameVO;
import invest.lixinger.ruoyi.mapper.codeAndNameMapper;
import invest.lixinger.utils.netRequest;
import mybatisNoSpringUtils.mybatisNoSpringUtils;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static invest.lixinger.company.fs.request_fsCompanyCN.requestFsCompanyCN;
import static invest.lixinger.company.fsTypeOfCompany.request_fsTypeOfCompanyCN.requestFsTypeOfCompanyCN;
import static mybatisNoSpringUtils.mybatisNoSpringUtils.session;

/**
 * 计算pe、pb、ps、pepos、pbpos、pspos
 */
public class request_companyFundamentalCN extends mybatisNoSpringUtils {

    /**
     * 返回基本面的总结果
     *
     * @param fsTypeOfCompanyCNResult_rootVO 某一行业的所有公司，主要是用来获取股票代码
     * @param doubleFsMap                    基本面的双map
     */
    public static Map<String, Map<String, String>> requestCompanyFundamentalCN(fsTypeOfCompanyCNResult_RootVO fsTypeOfCompanyCNResult_rootVO, Map<String, Map<String, String>> doubleFsMap) throws IOException, ParseException {
        InputStream inputStream = request_companyFundamentalCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String fsType = (String) indexReqParam.get("fsType");
        String URL = null;
        if (fsType.equals("bank")) {
            URL = (String) indexReqParam.get("companyFundamentalBankURLCN");
        } else if (fsType.equals("security")) {
            URL = (String) indexReqParam.get("companyFundamentalSecurityURLCN");
        } else if (fsType.equals("insurance")) {
            URL = (String) indexReqParam.get("companyFundamentalInsuranceURLCN");
        }
        String paramJson = getParam_companyFundamentalCN.getParamCompanyFundamentalCN(fsTypeOfCompanyCNResult_rootVO);
        String resultJson = netRequest.jsonNetPost(URL, paramJson);
        companyFundamentalCNResult_RootVO resultObj = (companyFundamentalCNResult_RootVO) getResult_companyFundamentalCN.getResultCompanyFundamentalCN(resultJson);
        return getdoubleCompanyFundamentalMap(resultObj, doubleFsMap);
    }


    /**
     * 获取多种指标
     *
     * @param resultObj   请求得到的基本面数据
     * @param doubleFsMap 基本面的双map
     */
    public static Map<String, Map<String, String>> getdoubleCompanyFundamentalMap(companyFundamentalCNResult_RootVO resultObj, Map<String, Map<String, String>> doubleFsMap) throws ParseException {
        List<companyFundamentalCNResult_DataVO> voList = resultObj.getData();
        String date = resultObj.getData().get(0).getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = sdf.format(sdf.parse(date));
        Map<String, Map<String, String>> doubleFundMap = new HashMap<>();
        DecimalFormat df = new DecimalFormat("0.00%");

        for (int i = 0; i < voList.size(); i++) {
            companyFundamentalCNResult_DataVO vo = voList.get(i);
            Map<String, String> mapTemp = doubleFsMap.get(vo.getStockCode());
            mapTemp.put("pe", String.valueOf(new DecimalFormat("0.00").format(vo.getD_pe_ttm())));
            mapTemp.put("pb", String.valueOf(new DecimalFormat("0.00").format(vo.getPb_wo_gw())));
            mapTemp.put("ps", String.valueOf(new DecimalFormat("0.00").format(vo.getPs_ttm())));
            mapTemp.put("pepos", df.format(vo.getD_pe_ttm_pos10()));
            mapTemp.put("pbpos", df.format(vo.getPb_wo_gw_pos10()));
            mapTemp.put("pspos", df.format(vo.getPs_ttm_pos10()));
            mapTemp.put("市值/亿", String.valueOf(new DecimalFormat("0.00").format(vo.getMc() / 100000000)));
            mapTemp.put("股息率", df.format(vo.getDyr()));
            mapTemp.put("股价", String.valueOf(vo.getSp()));
            mapTemp.put("代码", String.valueOf(vo.getStockCode()));
            mapTemp.put("日期", dateFormat);
            mapTemp.put("rankFund", "");
            doubleFundMap.put(vo.getStockCode(), mapTemp);
        }
        doubleFundMap = getdoubleCompanyFundamentalMapSort(voList, doubleFundMap);
        return doubleFundMap;

    }

    /**
     * 计算pe、pb、ps排名
     *
     * @param voList        某一行业的基本面，未处理的数据
     * @param doubleFundMap 基本面的双map
     */
    public static Map<String, Map<String, String>> getdoubleCompanyFundamentalMapSort(List<companyFundamentalCNResult_DataVO> voList, Map<String, Map<String, String>> doubleFundMap) {
        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getD_pe_ttm));
        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankFund", String.valueOf(i));
            doubleFundMap.put(StockCode, mapTemp);
        }

        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPb_wo_gw));

        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankFund", String.valueOf(Integer.parseInt(doubleFundMap.get(StockCode).get("rankFund")) + i));
            doubleFundMap.put(StockCode, mapTemp);
        }

        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPs_ttm));
        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankFund", String.valueOf(Integer.parseInt(doubleFundMap.get(StockCode).get("rankFund")) + i));
            doubleFundMap.put(StockCode, mapTemp);
        }
        return getdoubleCompanyPosMapSort(voList, doubleFundMap);
    }

    /**
     * 计算百分位排名
     *
     * @param voList        某一行业的基本面，未处理的数据
     * @param doubleFundMap 基本面的双map
     */
    public static Map<String, Map<String, String>> getdoubleCompanyPosMapSort(List<companyFundamentalCNResult_DataVO> voList, Map<String, Map<String, String>> doubleFundMap) {
        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getD_pe_ttm_pos10));
        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankPos", String.valueOf(i));
            doubleFundMap.put(StockCode, mapTemp);
        }
        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPb_wo_gw_pos10));
        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankPos", String.valueOf(Integer.parseInt(doubleFundMap.get(StockCode).get("rankPos")) + i));
            doubleFundMap.put(StockCode, mapTemp);
        }
        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPs_ttm_pos10));
        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankPos", String.valueOf(Integer.parseInt(doubleFundMap.get(StockCode).get("rankPos")) + i));
            doubleFundMap.put(StockCode, mapTemp);
        }
        return doubleFundMap;
    }


    @Test
    public void test() throws IOException, ParseException {
        fsTypeOfCompanyCNResult_RootVO fsTypeOfCompanyCNResult_rootVO = requestFsTypeOfCompanyCN();
        codeAndNameMapper codeAndNameMapper = session.getMapper(codeAndNameMapper.class);
        List<codeAndNameVO> codeAndNameList = codeAndNameMapper.findAll();
        Map<String, Map<String, String>> doubleFsMap = requestFsCompanyCN(fsTypeOfCompanyCNResult_rootVO, codeAndNameList);
        requestCompanyFundamentalCN(fsTypeOfCompanyCNResult_rootVO, doubleFsMap);
    }

    public request_companyFundamentalCN() throws FileNotFoundException {
    }
}
