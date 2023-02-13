package invest.lixinger.ruoyi.controller;

import invest.lixinger.macro.nationalDebt.VO.nationalDebtResult_DataVO;
import invest.lixinger.macro.nationalDebt.VO.nationalDebtResult_RootVO;
import invest.lixinger.ruoyi.entity.USDebtVO;
import invest.lixinger.ruoyi.mapper.USDebtMapper;
import mybatisNoSpringUtils.mybatisNoSpringUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static invest.lixinger.macro.nationalDebt.request_nationDebt.*;

/**
 * 计算美债
 */
@RestController
@RequestMapping("/GongYu")
public class USDebtController extends mybatisNoSpringUtils {

    /**
     * 统计美债入库
     */
    @Test
    public void USDebt() throws Exception {
//        String startDate ="1962-01-02";
        String startDate = nearestDateInDB();
        nationalDebtResult_RootVO resultObj = requestUSDebt(startDate);
        calculateNationalDebt(resultObj);
    }

    /**
     * 更新数据库中的美债百分位
     */
    @Test
    public void USDebtDBPOS() throws Exception {
        USDebtMapper hsagmapper = session.getMapper(USDebtMapper.class);
        List<USDebtVO> allDataVOList = hsagmapper.allDataInDB();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < allDataVOList.size(); i++) {
            USDebtVO vo = allDataVOList.get(i);
            String endDate = vo.getRq();
            Calendar cal = new GregorianCalendar();
            cal.setTime(sdf.parse(endDate));
            cal.add(Calendar.YEAR, -10);
            // 以 十年前 ~ 当前日期 为周期
            String startDate = sdf.format(cal.getTime());
            System.out.println(startDate + "  " + endDate);
            List<USDebtVO> usDebtDataRangeVOList = hsagmapper.dateRangeInDB(startDate, endDate);
            Map<String, String> mapCalculteDBPos = calculteDBPosUS(usDebtDataRangeVOList);
            double tempAveragePos = Double.parseDouble(mapCalculteDBPos.get("averagePos"));
            double y23510pos = Double.parseDouble(new DecimalFormat("0.0000").format(tempAveragePos));
            vo.setY2_3_5_10pos(y23510pos);
            hsagmapper.updateById(vo);

        }

    }

    public void calculateNationalDebt(nationalDebtResult_RootVO resultObj) throws Exception {
        USDebtMapper USDebtMapper = session.getMapper(USDebtMapper.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < resultObj.getData().size(); i++) {
            USDebtVO vo = new USDebtVO();
            List<nationalDebtResult_DataVO> listData = resultObj.getData();
            nationalDebtResult_DataVO resultVO = listData.get(i);
            String rq = resultVO.getDate();
            vo.setRq(sdf.format(sdf.parse(rq)));
            vo.setM1(resultVO.getMir_m1());
            vo.setM3(resultVO.getMir_m3());
            vo.setM6(resultVO.getMir_m6());
            vo.setY1(resultVO.getMir_y1());
            vo.setY2(resultVO.getMir_y2());
            vo.setY3(resultVO.getMir_y3());
            vo.setY5(resultVO.getMir_y5());
            vo.setY7(resultVO.getMir_y7());
            vo.setY10(resultVO.getMir_y10());
            vo.setY20(resultVO.getMir_y20());
            vo.setY30(resultVO.getMir_y30());
            // 计算国债综合百分位---------------------------------
            Map<String, String> map = calcultePos(resultObj);
            String averagePos = map.get("averagePos");
//            vo.setY2_3_5_10pos(Double.parseDouble(averagePos));
            // 计算2-10年期国债，查看是否倒挂--------------------
            vo.setY2minusy10(Double.parseDouble(new DecimalFormat("0.00000").format(resultVO.getMir_y2() - resultVO.getMir_y10())));

            USDebtMapper.insert(vo);
        }
    }

    public String nearestDateInDB() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        USDebtMapper hsagmapper = session.getMapper(USDebtMapper.class);
        USDebtVO vo = hsagmapper.nearestDateInDB();
        String date = vo.getRq();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(sdf.parse(date));
        calendar.add(Calendar.DATE, 1);
        return sdf.format(calendar.getTime());
    }

    public USDebtController() throws FileNotFoundException {
    }
}
