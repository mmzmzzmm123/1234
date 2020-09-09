package com.ruoyi.web.test.controller;

import com.github.wujun234.uid.UidGenerator;
import org.junit.Test;

import javax.annotation.Resource;

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

    @Test
    public void testSerialGenerate() {
        // Generate UID
        long cachedUidGeneratoruid = cachedUidGenerator.getUID();
        long defaultUidGeneratoruid = defaultUidGenerator.getUID();

        System.out.println("cachedUidGeneratoruid:"+cachedUidGeneratoruid);
        System.out.println("cachedUidGeneratoruid解密:"+cachedUidGenerator.parseUID(cachedUidGeneratoruid));
        System.out.println("defaultUidGeneratoruid:"+defaultUidGeneratoruid);
        System.out.println("defaultUidGeneratoruid解密:"+cachedUidGenerator.parseUID(defaultUidGeneratoruid));

        // Parse UID into [Timestamp, WorkerId, Sequence]
        // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
//        System.out.println(cachedUidGenerator.parseUID(uid));

    }
}
