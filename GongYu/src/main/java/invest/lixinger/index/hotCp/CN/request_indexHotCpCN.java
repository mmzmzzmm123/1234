package invest.lixinger.index.hotCp.CN;

import invest.lixinger.index.hotCp.CN.VO.indexHotCpCNResult_RootVO;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 指数基金近x年的年化收益
 */
public class request_indexHotCpCN {

    public static void requestIndexFundamentalHSI(List<String> stockCodesList) throws IOException, ParseException {
        InputStream inputStream = request_indexHotCpCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String indexHotCpCNURL = (String) indexReqParam.get("indexHotCpCNURL");
        String paramJson = getParam_indexHotCpCN.getParamIndexHotCpCN(stockCodesList);
//        String resultJson = netRequest.jsonNetPost(indexHotCpCNURL, paramJson);
        String resultJson = "{\"code\":1,\"message\":\"success\",\"data\":[{\"stockCode\":\"000808\",\"cpc_fys\":-0.22593853735079183,\"cpc\":0.008284313169895086,\"cpc_m1\":-0.025265935591084587,\"cpc_m3\":0.1045227201711747,\"cpc_m6\":-0.01662903670050131,\"cpc_y1\":-0.22467360610779274,\"cpc_y3\":0.019234250669953212,\"cpc_y5\":0.0099643493535162,\"cpc_y10\":0.08799514336843361,\"cpc_fs\":0.10717872000871664,\"latestDate\":\"2022-12-15T16:00:00.000Z\"}]}";
        indexHotCpCNResult_RootVO resultObj = (indexHotCpCNResult_RootVO) getResult_indexHotCpCN.getResultIndexHotCpCN(resultJson);
        System.out.println(resultObj);
    }

    public static void main(String[] args) throws IOException, ParseException {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "000808");
        requestIndexFundamentalHSI(list);
    }

}
