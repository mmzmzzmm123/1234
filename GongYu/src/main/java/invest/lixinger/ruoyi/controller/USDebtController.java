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
import java.util.*;

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
        if (Objects.equals(startDate, "1900-01-01")) {
            ArrayList<String>list=new ArrayList<>();
            list.add("1962-01-02");
            list.add("1972-01-03");
            list.add("1982-01-04");
            list.add("1992-01-05");
            list.add("2002-01-06");
            list.add("2012-01-07");
            list.add("2022-01-08");
            for (String s : list) {
                nationalDebtResult_RootVO resultObj = requestUSDebt(s);
                calculateNationalDebtUS(resultObj);
                USDebtDBPOS();
            }
        } else {
            nationalDebtResult_RootVO resultObj = requestUSDebt(startDate);
            calculateNationalDebtUS(resultObj);
            USDebtDBPOS();
        }

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
            String y23510pos = new DecimalFormat("0.0000").format(1 - tempAveragePos);
            vo.setY2_3_5_10pos(y23510pos);
            hsagmapper.updateById(vo);

        }

    }

    /**
     * 将数据机械的放入数据库
     */
    public void calculateNationalDebtUS(nationalDebtResult_RootVO resultObj) throws Exception {
        USDebtMapper USDebtMapper = session.getMapper(USDebtMapper.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < resultObj.getData().size(); i++) {
            USDebtVO vo = new USDebtVO();
            List<nationalDebtResult_DataVO> listData = resultObj.getData();
            nationalDebtResult_DataVO resultVO = listData.get(i);
            String rq = resultVO.getDate();
            vo.setRq(sdf.format(sdf.parse(rq)));
            vo.setM1(resultVO.getTcm_m1());
            vo.setM3(resultVO.getTcm_m3());
            vo.setM6(resultVO.getTcm_m6());
            vo.setY1(resultVO.getTcm_y1());
            vo.setY2(resultVO.getTcm_y2());
            vo.setY3(resultVO.getTcm_y3());
            vo.setY5(resultVO.getTcm_y5());
            vo.setY7(resultVO.getTcm_y7());
            vo.setY10(resultVO.getTcm_y10());
            vo.setY20(resultVO.getTcm_y20());
            vo.setY30(resultVO.getTcm_y30());
            // 计算国债综合百分位---------------------------------
            vo.setY2_3_5_10pos("");
            // 计算2-10年期国债，查看是否倒挂--------------------
            vo.setY2minusy10(Double.parseDouble(new DecimalFormat("0.00000").format(resultVO.getTcm_y2() - resultVO.getTcm_y10())));

            USDebtMapper.insert(vo);
        }
    }

    /**
     * 数据库最新的数据
     */
    public String nearestDateInDB() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        USDebtMapper hsagmapper = session.getMapper(USDebtMapper.class);
        USDebtVO vo = hsagmapper.nearestDateInDB();
        Calendar calendar = new GregorianCalendar();
        if (vo == null) {
            calendar.setTime(sdf.parse("1900-01-01"));
        } else {
            String date = vo.getRq();
            calendar.setTime(sdf.parse(date));
            calendar.add(Calendar.DATE, 1);
        }

        return sdf.format(calendar.getTime());
    }

    public USDebtController() throws FileNotFoundException {
    }
}
