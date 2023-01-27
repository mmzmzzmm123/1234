package invest.lixinger.index.fundamental.US.spx;

import invest.lixinger.index.fundamental.US.spx.VO.indexFundamentalSPXResult_DataVO;
import invest.lixinger.index.fundamental.US.spx.VO.indexFundamentalSPXResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Map;

/**
 * 获取单一日期的 沪深A股 信号
 */
public class request_indexFundamentalSPX {
    public static void main(String[] args) throws IOException, ParseException {
        requestIndexFundamentalSPX("");
    }
    public static indexFundamentalSPXResult_RootVO requestIndexFundamentalSPX(String startDate) throws IOException, ParseException {
        InputStream inputStream = request_indexFundamentalSPX.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String indexFundamentalUSURL = (String) indexReqParam.get("indexFundamentalUSURL");
        String paramJson = getParam_indexFundamentalSPX.getSingleIndexParamJson(startDate);
        String resultJson = netRequest.jsonNetPost(indexFundamentalUSURL, paramJson);
        indexFundamentalSPXResult_RootVO resultObj = (indexFundamentalSPXResult_RootVO) getResult_indexFundamentalSPX.getResultObj(resultJson);
        double result = calculateFundamentalSPX(resultObj);
        System.out.println("综合百分位为：" + new DecimalFormat("0.00%").format(result));
//
        detailPositionSPX(resultObj);
        targetChangeSPX(resultObj);
        return resultObj;
    }

    // 计算综合百分位
    public static double calculateFundamentalSPX(indexFundamentalSPXResult_RootVO resultObj) {
        indexFundamentalSPXResult_DataVO vo = resultObj.getData().get(0);
        double pe_10_cvpos = vo.getPe_ttm().getY10().getMedian().getCvpos();
        double pb_10_cvpos = vo.getPb().getY10().getMedian().getCvpos();
        double ps_10_cvpos = resultObj.getData().get(0).getPs_ttm().getY10().getMedian().getCvpos();

        double pe_20_cvpos = vo.getPe_ttm().getY20().getMedian().getCvpos();
        double pb_20_cvpos = vo.getPb().getY20().getMedian().getCvpos();
        double ps_20_cvpos = vo.getPs_ttm().getY20().getMedian().getCvpos();
        return (pe_10_cvpos + pb_10_cvpos + ps_10_cvpos + pe_20_cvpos + pb_20_cvpos + ps_20_cvpos) / 6;
    }

    // 查看10年以及20年的细节
    public static void detailPositionSPX(indexFundamentalSPXResult_RootVO resultObj) {
        indexFundamentalSPXResult_DataVO vo = resultObj.getData().get(0);
        System.out.println("20年 pe 百分位为：" + new DecimalFormat("0.00%").format(vo.getPe_ttm().getY20().getMedian().getCvpos()));
        System.out.println("20年 pb 百分位为：" + new DecimalFormat("0.00%").format(vo.getPb().getY20().getMedian().getCvpos()));
        System.out.println("20年 ps 百分位为：" + new DecimalFormat("0.00%").format(vo.getPs_ttm().getY20().getMedian().getCvpos()));
        System.out.println();
        System.out.println("10年 pe 百分位为：" + new DecimalFormat("0.00%").format(vo.getPe_ttm().getY10().getMedian().getCvpos()));
        System.out.println("10年 pb 百分位为：" + new DecimalFormat("0.00%").format(vo.getPb().getY10().getMedian().getCvpos()));
        System.out.println("10年 ps 百分位为：" + new DecimalFormat("0.00%").format(vo.getPs_ttm().getY10().getMedian().getCvpos()));
        System.out.println();

    }

    // 到达目标点位需要的幅度
    public static void targetChangeSPX(indexFundamentalSPXResult_RootVO resultObj) {
        indexFundamentalSPXResult_DataVO vo = resultObj.getData().get(0);
        //当前分位点的具体数值
        double pe_20_cv = vo.getPe_ttm().getY20().getMedian().getCv();
        double pb_20_cv = vo.getPb().getY20().getMedian().getCv();
        double ps_20_cv = vo.getPs_ttm().getY20().getMedian().getCv();

        double pe_10_cv = vo.getPe_ttm().getY10().getMedian().getCv();
        double pb_10_cv = vo.getPb().getY10().getMedian().getCv();
        double ps_10_cv = vo.getPs_ttm().getY10().getMedian().getCv();
        //80分位点的具体数值
        double pe_20_q8v = vo.getPe_ttm().getY20().getMedian().getQ8v();
        double pb_20_q8v = vo.getPb().getY20().getMedian().getQ8v();
        double ps_20_q8v = vo.getPs_ttm().getY20().getMedian().getQ8v();

        double pe_10_q8v = vo.getPe_ttm().getY10().getMedian().getQ8v();
        double pb_10_q8v = vo.getPb().getY10().getMedian().getQ8v();
        double ps_10_q8v = vo.getPs_ttm().getY10().getMedian().getQ8v();

        double result_q8v = ((pe_10_q8v - pe_10_cv) / pe_10_cv + (pb_10_q8v - pb_10_cv) / pb_10_cv + (ps_10_q8v - ps_10_cv) / ps_10_cv + (pe_20_q8v - pe_20_cv) / pe_20_cv + (pb_20_q8v - pb_20_cv) / pb_20_cv + (ps_20_q8v - ps_20_cv) / ps_20_cv) / 6;
        System.out.println("到达80分位点还需要的幅度为：" + new DecimalFormat("0.00%").format(result_q8v));
        //20分位点的具体数值
        double pe_20_q2v = vo.getPe_ttm().getY20().getMedian().getQ2v();
        double pb_20_q2v = vo.getPb().getY20().getMedian().getQ2v();
        double ps_20_q2v = vo.getPs_ttm().getY20().getMedian().getQ2v();

        double pe_10_q2v = vo.getPe_ttm().getY10().getMedian().getQ2v();
        double pb_10_q2v = vo.getPb().getY10().getMedian().getQ2v();
        double ps_10_q2v = vo.getPs_ttm().getY10().getMedian().getQ2v();

        double result_q2v = ((pe_10_q2v - pe_10_cv) / pe_10_cv + (pb_10_q2v - pb_10_cv) / pb_10_cv + (ps_10_q2v - ps_10_cv) / ps_10_cv + (pe_20_q2v - pe_20_cv) / pe_20_cv + (pb_20_q2v - pb_20_cv) / pb_20_cv + (ps_20_q2v - ps_20_cv) / ps_20_cv) / 6;
        System.out.println("到达20分位点还需要的幅度为：" + new DecimalFormat("0.00%").format(result_q2v));

    }

}
