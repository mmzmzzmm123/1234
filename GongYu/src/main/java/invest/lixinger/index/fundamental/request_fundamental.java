package invest.lixinger.index.fundamental;

import invest.lixinger.index.fundamental.VO.fundamentalResult_RootVO;
import invest.lixinger.utils.netRequest;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Map;

/**
 * 获取单一日期的 沪深A股 信号
 */
public class request_fundamental {
    public static void main(String[] args) throws IOException, ParseException {
        String filePath = request_fundamental.class.getClassLoader().getResource("indexReqParam.yml").getPath();
        Map indexReqParam = new Yaml().load(new FileInputStream(filePath));
        String fundamentalURL = (String) indexReqParam.get("fundamentalURL");
        String paramJson = getParam_fundamental.getSingleIndexParamJson();
        String resultJson = netRequest.jsonNetPost(fundamentalURL, paramJson);
        fundamentalResult_RootVO resultObj = (fundamentalResult_RootVO) getResult_fundamental.getResultObj(resultJson);

        calculateFundamental(resultObj);
        detailPosition(resultObj);
        targetChange(resultObj);
    }

    // 计算综合百分位
    public static double calculateFundamental(fundamentalResult_RootVO resultObj) {
        double pe_10_cvpos = resultObj.getData().get(0).getPe_ttm().getY10().getMedian().getCvpos();
        double pb_10_cvpos = resultObj.getData().get(0).getPb().getY10().getMedian().getCvpos();
        double ps_10_cvpos = resultObj.getData().get(0).getPs_ttm().getY10().getMedian().getCvpos();

        double pe_20_cvpos = resultObj.getData().get(0).getPe_ttm().getY20().getMedian().getCvpos();
        double pb_20_cvpos = resultObj.getData().get(0).getPb().getY20().getMedian().getCvpos();
        double ps_20_cvpos = resultObj.getData().get(0).getPs_ttm().getY20().getMedian().getCvpos();
        double result = (pe_10_cvpos + pb_10_cvpos + ps_10_cvpos + pe_20_cvpos + pb_20_cvpos + ps_20_cvpos) / 6;

        System.out.println("综合百分位为：" + new DecimalFormat("0.00%").format(result));
        return result;
    }

    // 查看10年以及20年的细节
    private static void detailPosition(fundamentalResult_RootVO resultObj) {
        System.out.println("20年 pe 百分位为：" + new DecimalFormat("0.00%").format(resultObj.getData().get(0).getPe_ttm().getY20().getMedian().getCvpos()));
        System.out.println("20年 pb 百分位为：" + new DecimalFormat("0.00%").format(resultObj.getData().get(0).getPb().getY20().getMedian().getCvpos()));
        System.out.println("20年 ps 百分位为：" + new DecimalFormat("0.00%").format(resultObj.getData().get(0).getPs_ttm().getY20().getMedian().getCvpos()));
        System.out.println();
        System.out.println("10年 pe 百分位为：" + new DecimalFormat("0.00%").format(resultObj.getData().get(0).getPe_ttm().getY10().getMedian().getCvpos()));
        System.out.println("10年 pb 百分位为：" + new DecimalFormat("0.00%").format(resultObj.getData().get(0).getPb().getY10().getMedian().getCvpos()));
        System.out.println("10年 ps 百分位为：" + new DecimalFormat("0.00%").format(resultObj.getData().get(0).getPs_ttm().getY10().getMedian().getCvpos()));
        System.out.println();

    }

    // 到达目标点位需要的幅度
    private static void targetChange(fundamentalResult_RootVO resultObj) {
        //当前分位点的具体数值
        double pe_20_cv = resultObj.getData().get(0).getPe_ttm().getY20().getMedian().getCv();
        double pb_20_cv = resultObj.getData().get(0).getPb().getY20().getMedian().getCv();
        double ps_20_cv = resultObj.getData().get(0).getPs_ttm().getY20().getMedian().getCv();

        double pe_10_cv = resultObj.getData().get(0).getPe_ttm().getY10().getMedian().getCv();
        double pb_10_cv = resultObj.getData().get(0).getPb().getY10().getMedian().getCv();
        double ps_10_cv = resultObj.getData().get(0).getPs_ttm().getY10().getMedian().getCv();
        //80分位点的具体数值
        double pe_20_q8v = resultObj.getData().get(0).getPe_ttm().getY20().getMedian().getQ8v();
        double pb_20_q8v = resultObj.getData().get(0).getPb().getY20().getMedian().getQ8v();
        double ps_20_q8v = resultObj.getData().get(0).getPs_ttm().getY20().getMedian().getQ8v();

        double pe_10_q8v = resultObj.getData().get(0).getPe_ttm().getY10().getMedian().getQ8v();
        double pb_10_q8v = resultObj.getData().get(0).getPb().getY10().getMedian().getQ8v();
        double ps_10_q8v = resultObj.getData().get(0).getPs_ttm().getY10().getMedian().getQ8v();

        double result_q8v = ((pe_10_q8v - pe_10_cv) / pe_10_cv + (pb_10_q8v - pb_10_cv) / pb_10_cv + (ps_10_q8v - ps_10_cv) / ps_10_cv + (pe_20_q8v - pe_20_cv) / pe_20_cv + (pb_20_q8v - pb_20_cv) / pb_20_cv + (ps_20_q8v - ps_20_cv) / ps_20_cv) / 6;
        System.out.println("到达80分位点还需要的幅度为：" + new DecimalFormat("0.00%").format(result_q8v));
        //20分位点的具体数值
        double pe_20_q2v = resultObj.getData().get(0).getPe_ttm().getY20().getMedian().getQ2v();
        double pb_20_q2v = resultObj.getData().get(0).getPb().getY20().getMedian().getQ2v();
        double ps_20_q2v = resultObj.getData().get(0).getPs_ttm().getY20().getMedian().getQ2v();

        double pe_10_q2v = resultObj.getData().get(0).getPe_ttm().getY10().getMedian().getQ2v();
        double pb_10_q2v = resultObj.getData().get(0).getPb().getY10().getMedian().getQ2v();
        double ps_10_q2v = resultObj.getData().get(0).getPs_ttm().getY10().getMedian().getQ2v();

        double result_q2v = ((pe_10_q2v - pe_10_cv) / pe_10_cv + (pb_10_q2v - pb_10_cv) / pb_10_cv + (ps_10_q2v - ps_10_cv) / ps_10_cv + (pe_20_q2v - pe_20_cv) / pe_20_cv + (pb_20_q2v - pb_20_cv) / pb_20_cv + (ps_20_q2v - ps_20_cv) / ps_20_cv) / 6;
        System.out.println("到达20分位点还需要的幅度为：" + new DecimalFormat("0.00%").format(result_q2v));

    }

}
