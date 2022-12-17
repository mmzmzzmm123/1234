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
     * @param fsTypeOfCompanyCNResult_rootVO 某一行业的所有公司，主要是用来获取股票代码
     * @param doubleFsMap 基本面的双map
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
        }else if (fsType.equals("insurance")) {
            URL = (String) indexReqParam.get("companyFundamentalInsuranceURLCN");
        }
        String paramJson = getParam_companyFundamentalCN.getParamCompanyFundamentalCN(fsTypeOfCompanyCNResult_rootVO);
        String resultJson = netRequest.jsonNetPost(URL, paramJson);
        companyFundamentalCNResult_RootVO resultObj = (companyFundamentalCNResult_RootVO) getResult_companyFundamentalCN.getResultCompanyFundamentalCN(resultJson);
        return getdoubleCompanyFundamentalMap(resultObj.getData(), resultObj.getData().get(0).getDate(), doubleFsMap);
    }

    /**
     * 获取多种指标
     * @param voList 某一行业的基本面，未处理的数据
     * @param date 日期
     * @param doubleFsMap 基本面的双map
     */
    public static Map<String, Map<String, String>> getdoubleCompanyFundamentalMap(List<companyFundamentalCNResult_DataVO> voList, String date, Map<String, Map<String, String>> doubleFsMap) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = sdf.format(sdf.parse(date));
        Map<String, Map<String, String>> doubleFundMap = new HashMap<>();
        for (int i = 0; i < voList.size(); i++) {
            companyFundamentalCNResult_DataVO vo = voList.get(i);
            Map<String, String> mapTemp = doubleFsMap.get(vo.getStockCode());
            mapTemp.put("pe", String.valueOf(new DecimalFormat("0.0000").format(vo.getD_pe_ttm())));
            mapTemp.put("pb", String.valueOf(new DecimalFormat("0.0000").format(vo.getPb_wo_gw())));
            mapTemp.put("ps", String.valueOf(new DecimalFormat("0.0000").format(vo.getPs_ttm())));
            mapTemp.put("pepos", String.valueOf(new DecimalFormat("0.0000").format(vo.getD_pe_ttm_pos10())));
            mapTemp.put("pbpos", String.valueOf(new DecimalFormat("0.0000").format(vo.getPb_wo_gw_pos10())));
            mapTemp.put("pspos", String.valueOf(new DecimalFormat("0.0000").format(vo.getPs_ttm_pos10())));
            mapTemp.put("mc", String.valueOf(new DecimalFormat("0.00").format(vo.getMc() / 100000000)));
            mapTemp.put("syr", String.valueOf(new DecimalFormat("0.0000").format(vo.getDyr())));
            mapTemp.put("sp", String.valueOf(vo.getSp()));
            mapTemp.put("stockCode", String.valueOf(vo.getStockCode()));
            mapTemp.put("date", dateFormat);
            mapTemp.put("rankFund", "");
            doubleFundMap.put(vo.getStockCode(), mapTemp);
        }
        doubleFundMap = getdoubleCompanyFundamentalMapSort(voList, doubleFundMap);
        return doubleFundMap;

    }

    /**
     * 计算pe、pb、ps排名
     * @param voList 某一行业的基本面，未处理的数据
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
            mapTemp.put("rankFund", String.valueOf(Integer.parseInt(doubleFundMap.get(voList.get(i).getStockCode()).get("rankFund")) + i));
            doubleFundMap.put(voList.get(i).getStockCode(), mapTemp);
        }

        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPs_ttm));
        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankFund", String.valueOf(Integer.parseInt(doubleFundMap.get(voList.get(i).getStockCode()).get("rankFund")) + i));
            doubleFundMap.put(voList.get(i).getStockCode(), mapTemp);
        }
        return getdoubleCompanyPosMapSort(voList,doubleFundMap);
    }

    /**
     * 计算百分位排名
     * @param voList 某一行业的基本面，未处理的数据
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
            doubleFundMap.put(voList.get(i).getStockCode(), mapTemp);
        }
        voList.sort(Comparator.comparing(companyFundamentalCNResult_DataVO::getPs_ttm_pos10));
        for (int i = 0; i < voList.size(); i++) {
            String StockCode = voList.get(i).getStockCode();
            Map<String, String> mapTemp = doubleFundMap.get(StockCode);
            mapTemp.put("rankPos", String.valueOf(Integer.parseInt(doubleFundMap.get(StockCode).get("rankPos")) + i));
            doubleFundMap.put(voList.get(i).getStockCode(), mapTemp);
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