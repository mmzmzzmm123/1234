package com.ruoyi.web.test.controller;

import cn.hutool.core.date.DateUtil;
import com.github.wujun234.uid.UidGenerator;
import com.ruoyi.bookmark.service.ISqMenuService;
import com.ruoyi.common.core.redis.RedisUtil;
import com.ruoyi.common.utils.bookmarkhtml.Const;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther: Wang
 * @Date: 2020/09/09 21:21
 * 功能描述:
 */
public class UIDTest extends BaseSpringBootTest{


    @Resource
    private UidGenerator defaultUidGenerator;
    @Resource
    private UidGenerator cachedUidGenerator;
    @Autowired
    private ISqMenuService iSqMenuService;
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testSerialGenerate() {
        // Generate UID
        long cachedUidGeneratoruid = cachedUidGenerator.getUID();
        long defaultUidGeneratoruid = defaultUidGenerator.getUID(); //用这个

        System.out.println("cachedUidGeneratoruid:"+cachedUidGeneratoruid);
        System.out.println("cachedUidGeneratoruid解密:"+cachedUidGenerator.parseUID(cachedUidGeneratoruid));
        System.out.println("defaultUidGeneratoruid:"+defaultUidGeneratoruid);
        System.out.println("defaultUidGeneratoruid解密:"+cachedUidGenerator.parseUID(defaultUidGeneratoruid));

        // Parse UID into [Timestamp, WorkerId, Sequence]
        // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
//        System.out.println(cachedUidGenerator.parseUID(uid));

    }
    @Test
    public void dateTest(){
        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
        System.out.println(date3);
    }


    //测试规定时间内 禁能访问次数
    @Test
    public void rest2(){
        boolean phoneCodelFlag = iSqMenuService.countRepetition(Const.PHONE_REGISTER,1L,600L,3);
        if (phoneCodelFlag){
            System.out.println("可以访问请求!");
        }else{
            System.out.println("访问请求失败了!");
        }
    }



}
