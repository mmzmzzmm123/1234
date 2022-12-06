package invest.lixinger.company.fs;


import com.alibaba.fastjson.JSON;
import invest.lixinger.company.fs.VO.fsCompanyCNParam_RootVO;
import invest.lixinger.company.fsTypeOfCompany.VO.fsTypeOfCompanyCNParam_RootVO;
import invest.lixinger.utils.getResult_NoHoliday;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;

public class getParam_fsCompanyCN {
    public static String getParamFsTypeOfCompanyCN() throws IOException, ParseException {
        InputStream inputStream = getParam_fsCompanyCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        String dateYml = (String) indexReqParam.get("enddate");
        String date = getResult_NoHoliday.getResult_NoHoliday(Integer.parseInt(dateYml.substring(0, 4)));

        fsCompanyCNParam_RootVO vo = new fsCompanyCNParam_RootVO();
        vo.setToken(token);
        vo.setDate(date);
//        vo.setMetricsList();
//        vo.setStockCodes();
        return JSON.toJSONString(vo);
    }

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(getParamFsTypeOfCompanyCN());
    }


}
