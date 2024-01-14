package com.ruoyi.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.listener.entity.MixtureResolveExcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {


        String path = "D:\\java-product\\gcgj\\aa.xlsx";
        MixtureResolveListener listener = new MixtureResolveListener();
        EasyExcel.read(new FileInputStream(path), MixtureResolveExcel.class,listener).extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
        List<MixtureResolveExcel> cachedDataList = listener.getCachedDataList();
        System.out.println(cachedDataList);
        System.out.println("end----");

        System.out.println(cachedDataList.size());

        System.out.println("============================================");
        MixtureResolveExcel lastMixtureResolveExcel = null;
        for (MixtureResolveExcel mixtureResolveExcel : cachedDataList) {
            if (StringUtils.isEmpty(mixtureResolveExcel.getItemNum())){
                mixtureResolveExcel.setItemNum(lastMixtureResolveExcel.getItemNum());
                mixtureResolveExcel.setItemName(lastMixtureResolveExcel.getItemName());
                mixtureResolveExcel.setPartUsed(lastMixtureResolveExcel.getPartUsed());
                mixtureResolveExcel.setSumQuantity(lastMixtureResolveExcel.getSumQuantity());
                mixtureResolveExcel.setCement325R(lastMixtureResolveExcel.getCement325R());
                mixtureResolveExcel.setCement425R(lastMixtureResolveExcel.getCement425R());
                mixtureResolveExcel.setCement430R(lastMixtureResolveExcel.getCement430R());
                mixtureResolveExcel.setWaterReducer(lastMixtureResolveExcel.getWaterReducer());
                mixtureResolveExcel.setSwellingAgent(lastMixtureResolveExcel.getSwellingAgent());
            }else {
                lastMixtureResolveExcel = mixtureResolveExcel;
            }

            System.out.println(mixtureResolveExcel);
        }
    }
}
