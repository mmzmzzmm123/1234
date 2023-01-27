package invest.lixinger.ruoyi.controller;

import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNResult_RootVO;
import invest.lixinger.index.fundamental.US.spx.VO.indexFundamentalSPXResult_DataVO;
import invest.lixinger.index.fundamental.US.spx.VO.indexFundamentalSPXResult_RootVO;
import invest.lixinger.index.fundamental.US.spx.getParam_indexFundamentalSPX;
import invest.lixinger.index.fundamental.US.spx.getResult_indexFundamentalSPX;
import invest.lixinger.index.fundamental.US.spx.request_indexFundamentalSPX;
import invest.lixinger.ruoyi.entity.hsagVO;
import invest.lixinger.ruoyi.entity.usspxVO;
import invest.lixinger.ruoyi.mapper.hsagMapper;
import invest.lixinger.ruoyi.mapper.usspxMapper;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import static invest.lixinger.index.fundamental.CN.request_indexFundamental_dateRange.request_fundamental_dateRangeCN;
import static invest.lixinger.index.fundamental.US.spx.request_indexFundamentalSPX.requestIndexFundamentalSPX;
import static mybatisNoSpringUtils.mybatisNoSpringUtils.session;

public class usSPXController extends mybatisNoSpringUtils {

    @Test
    public void usSPXcontroller() throws IOException, ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(nearestDateInDB());
        indexFundamentalSPXResult_RootVO resultVO = requestIndexFundamentalSPX(startDate);
        calculateFundamentalSPX(resultVO);
        System.out.println(startDate);
    }

    // 计算综合百分位
    public static void calculateFundamentalSPX(indexFundamentalSPXResult_RootVO resultObj) {
        usspxMapper usspxmapper = session.getMapper(usspxMapper.class);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < resultObj.getData().size(); i++) {
            usspxVO vo = new usspxVO();
            Date currentDate = resultObj.getData().get(i).getDate();
            double spdw = resultObj.getData().get(i).getCp();

            double pe_10_cvpos = resultObj.getData().get(i).getPe_ttm().getY10().getMedian().getCvpos();
            double pb_10_cvpos = resultObj.getData().get(i).getPb().getY10().getMedian().getCvpos();
            double ps_10_cvpos = resultObj.getData().get(i).getPs_ttm().getY10().getMedian().getCvpos();

            double pe_20_cvpos = resultObj.getData().get(i).getPe_ttm().getY20().getMedian().getCvpos();
            double pb_20_cvpos = resultObj.getData().get(i).getPb().getY20().getMedian().getCvpos();
            double ps_20_cvpos = resultObj.getData().get(i).getPs_ttm().getY20().getMedian().getCvpos();
            double result = (pe_10_cvpos + pb_10_cvpos + ps_10_cvpos + pe_20_cvpos + pb_20_cvpos + ps_20_cvpos) / 6;
            // pe、pb、ps的具体数值
            double pe_cv = Double.parseDouble(String.format("%.2f", resultObj.getData().get(i).getPe_ttm().getY10().getMedian().getCv()));
            double pb_cv = Double.parseDouble(String.format("%.2f", resultObj.getData().get(i).getPb().getY10().getMedian().getCv()));
            double ps_cv = Double.parseDouble(String.format("%.2f", resultObj.getData().get(i).getPs_ttm().getY10().getMedian().getCv()));

            System.out.println(sdf.format(currentDate) + "，的综合百分位为：" + new DecimalFormat("0.00%").format(result) );
            vo.setSj(currentDate);
            vo.setSpdw(spdw);
            vo.setPeCv(pe_cv);
            vo.setPbCv(pb_cv);
            vo.setPsCv(ps_cv);
            vo.setPePos10(pe_10_cvpos);
            vo.setPbPos10(pb_10_cvpos);
            vo.setPsPos10(ps_10_cvpos);
            vo.setPePos20(pe_20_cvpos);
            vo.setPbPos20(pb_20_cvpos);
            vo.setPsPos20(ps_20_cvpos);
            vo.setZonghePos(result);
            usspxmapper.insert(vo);
        }

    }
    // 统计在数据库中最新的时间
    public static Date nearestDateInDB() {
        usspxMapper usspxmapper = session.getMapper(usspxMapper.class);
        usspxVO vo = usspxmapper.nearestDateInDB();
        Date date = vo.getSj();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
    public usSPXController() throws FileNotFoundException {
    }
}
