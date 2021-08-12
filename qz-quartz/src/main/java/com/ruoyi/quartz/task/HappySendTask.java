package com.ruoyi.quartz.task;

import com.ruoyi.system.domain.QzHappy;
import com.ruoyi.system.mapper.QzHappyMapper;
import com.ruoyi.system.mapper.QzNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 定时任务调度测试
 * 
 * @author zlx
 */
@Component("happySendTask")
public class HappySendTask
{
    @Autowired
    private QzHappyMapper qzHappyMapper;
    @Autowired
    private QzNoticeMapper qzNoticeMapper;

    public void checkHappy(Integer i)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -i);
        Date checkdate = c.getTime();
        Map<String,Boolean>  sendMap =  new HashMap<String,Boolean>();
        //找到所有到期需要推送的快乐，批量进行推送
        QzHappy qzHappy = new QzHappy();
        qzHappy.setReleaseTime(checkdate);
        List<QzHappy> sendQzHappyList =  qzHappyMapper.selectQzHappyList(qzHappy);
      /*  for(DeviceFeeInfo deviceFeeInfo:deviceFeeInfoList){
            String deviceid = deviceFeeInfo.getDeviceId();
            if(deviceFeeInfo.getStartdate().before(checkdate) && deviceFeeInfo.getDuedate().after(checkdate)){
               overdueMap.put(deviceid,true);
            }else{
                if(overdueMap.containsKey(deviceid)){
                    if(overdueMap.get(deviceid)){
                        continue;
                    }else{
                        overdueMap.put(deviceid,false);
                    }
                }else{
                    overdueMap.put(deviceid,false);
                }
            }
        }*/
        for(QzHappy  happy :sendQzHappyList){
            happy.setReleaseTime(checkdate);
            happy.setStatus("1");
            qzHappyMapper.updateQzHappy(happy);

        }
        System.out.println("执行检查的方法");
    }
}
