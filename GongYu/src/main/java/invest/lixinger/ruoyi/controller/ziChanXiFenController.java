package invest.lixinger.ruoyi.controller;

import GYProject.mybatisTest.mybatisNoSpringUtils.mybatisNoSpringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import invest.lixinger.ruoyi.entity.totalAssetVO;
import invest.lixinger.ruoyi.entity.ziChanXiFenVO;
import invest.lixinger.ruoyi.mapper.totalAssetMapper;
import invest.lixinger.ruoyi.mapper.ziChanXiFenMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;
import work.cib.excel.excelUtils.ExcelUtils_1;
import work.cib.excel.excelUtils.ExcelUtils_1_utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.util.NumberUtil.isDouble;
import static work.cib.excel.excelUtils.ExcelUtils_1_utils.getDefineRC;

public class ziChanXiFenController extends mybatisNoSpringUtils {
    @Test
    //从数据库中读取并放到excel中
    public void DB2excel() {
        ziChanXiFenMapper mapper = session.getMapper(ziChanXiFenMapper.class);
        List<ziChanXiFenVO> list = mapper.selectList(new QueryWrapper<>());
        List<List<Cell>> getZiChanXiFen = new ArrayList<>();
        for (ziChanXiFenVO vo : list) {
            String name = null;
            double huobi = 0;
            double zhaiquan = 0;
            double yitouru = 0;
            double guonei_zichan = 0;
            double lundong_zichan = 0;
            double haiwai_zichan = 0;
            name = vo.getName();
            huobi = vo.getHuobi();
            yitouru = vo.getYitouru();
            guonei_zichan = vo.getGuoneiZichan();
            lundong_zichan = vo.getLundongZichan();
            haiwai_zichan = vo.getHaiwaiZichan();

        }
    }

    @Test
    //从excel中读取资产细分
    public void excel2DB() throws IOException {
        ExcelUtils_1 eu = new ExcelUtils_1();
        ExcelUtils_1 eu_out = new ExcelUtils_1();
        eu.setExcelPath("src/main/resources/excel/importExcel/cangwei.xls");
        eu_out.setExcelPath("src/main/resources/excel/importExcel/getZichanXiFen.xls");
        eu.setPrintMsg(false);
        eu.setOverWrite(false);
        List<List<Cell>> list = eu.readExcel();
        List<List<Cell>> getZiChanXiFen = getDefineRC(list, 11, 8);

        System.out.println("---------下面开始操作VO，cell→vo------------------------");
        List<ziChanXiFenVO> getZiChanXiFenVOList = new ArrayList<>();
        for (int i = 1; i < getZiChanXiFen.size(); i++) {
            getZiChanXiFenVOList.add(new ziChanXiFenVO());
            String name = null;
            double huobi = 0;
            double zhaiquan = 0;
            double yitouru = 0;
            double guonei_zichan = 0;
            double lundong_zichan = 0;
            double haiwai_zichan = 0;
//            System.out.println("11="+getZiChanXiFen.get(i).get(4).getCellFormula());;
//            System.out.println("22="+getZiChanXiFen.get(i).get(4).getCachedFormulaResultType());;
//            System.out.println("33="+getZiChanXiFen.get(i).get(4).getNumericCellValue());;
            name = (String) ExcelUtils_1_utils.getCellValueObj(getZiChanXiFen.get(i).get(0));
            huobi = (Double) operateNumber(ExcelUtils_1_utils.getCellValueString(getZiChanXiFen.get(i).get(1)));
            zhaiquan = (Double) operateNumber(ExcelUtils_1_utils.getCellValueString(getZiChanXiFen.get(i).get(2)));
            yitouru = (Double) operateNumber(ExcelUtils_1_utils.getCellValueString(getZiChanXiFen.get(i).get(3)));
            guonei_zichan = (Double) operateNumber(ExcelUtils_1_utils.getCellValueString(getZiChanXiFen.get(i).get(4)));
            lundong_zichan = (Double) operateNumber(ExcelUtils_1_utils.getCellValueString(getZiChanXiFen.get(i).get(5)));
            haiwai_zichan = (Double) operateNumber(ExcelUtils_1_utils.getCellValueString(getZiChanXiFen.get(i).get(6)));

            getZiChanXiFenVOList.get(i - 1).setName(name);
            getZiChanXiFenVOList.get(i - 1).setHuobi(huobi);
            getZiChanXiFenVOList.get(i - 1).setZhaiquan(zhaiquan);
            getZiChanXiFenVOList.get(i - 1).setYitouru(yitouru);
            getZiChanXiFenVOList.get(i - 1).setGuoneiZichan(guonei_zichan);
            getZiChanXiFenVOList.get(i - 1).setLundongZichan(lundong_zichan);
            getZiChanXiFenVOList.get(i - 1).setHaiwaiZichan(haiwai_zichan);
        }

        getZiChanXiFenVOList.forEach(System.out::println);
        //把excel里面的数据放到
        ziChanXiFenMapper mapper = session.getMapper(ziChanXiFenMapper.class);
        for (ziChanXiFenVO vo : getZiChanXiFenVOList) {
            mapper.updateById(vo);
        }
        calculateAsset();
        eu.writeExcel(getZiChanXiFen, "src/main/resources/excel/exportExcel/zichanxifen_merge.xls");

    }


    /**
     * 计算国内资产，并计算每行资产的总计
     */
    @Test
    public void calculateAsset() {
        ziChanXiFenMapper mapper = session.getMapper(ziChanXiFenMapper.class);
        List<ziChanXiFenVO> list = mapper.selectList(new QueryWrapper<>());
        for (ziChanXiFenVO vo : list) {
            double guoneiZichan = vo.getHuobi() + vo.getZhaiquan() + vo.getYitouru();
            vo.setGuoneiZichan(guoneiZichan);
            mapper.updateById(vo);
        }
        for (ziChanXiFenVO vo : list) {
            double zongji = vo.getGuoneiZichan() + vo.getLundongZichan() + vo.getHaiwaiZichan();
            vo.setZongji(zongji);
            mapper.updateById(vo);
        }
        calculateTotalAsset();
    }

    /**
     * 在另外一张表中添加总资产
     */
    @Test
    public void calculateTotalAsset() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        ziChanXiFenMapper mapperZiChanXiFen = session.getMapper(ziChanXiFenMapper.class);
        List<ziChanXiFenVO> ziChanXiFenVOList = mapperZiChanXiFen.selectList(new QueryWrapper<>());
        double totalAsset = 0;
        for (ziChanXiFenVO vo : ziChanXiFenVOList) {
            totalAsset += vo.getZongji();
        }

        totalAssetMapper mapperTotalAsset = session.getMapper(totalAssetMapper.class);
        System.out.println(sdf.format(now) + " 时的总资产记录将要被删除");

        List<totalAssetVO> totalAssetVOList = mapperTotalAsset.selectList(new QueryWrapper<>());
        for (totalAssetVO vo : totalAssetVOList) {
            if (sdf.format(vo.getRiqi()).equals(sdf.format(now))) {
                mapperTotalAsset.deleteById(vo);
            }
        }

        totalAssetVO totalassetvo = new totalAssetVO();
        totalassetvo.setRiqi(now);
        totalassetvo.setTotalasset(totalAsset / 2);
        mapperTotalAsset.insert(totalassetvo);
    }

    @Test
    public void test() {
        ziChanXiFenMapper mapper = session.getMapper(ziChanXiFenMapper.class);
        List<ziChanXiFenVO> list = mapper.selectList(new QueryWrapper<>());
        list.forEach(System.out::println);
    }


    public static Object operateNumber(String number) {
        if (number.equals("")) {
            return 0.0;
        } else if (isDouble(number)) {
            return Double.parseDouble(number);
        } else {
            return 0.0;
        }
    }

    public ziChanXiFenController() throws FileNotFoundException {
    }
}
