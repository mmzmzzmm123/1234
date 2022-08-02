package com.ruoyi.quartz.task;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import com.ruoyi.quartz.domain.ReadResult;
import com.ruoyi.quartz.service.IModbusService;
import com.ruoyi.quartz.service.IReadResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wjp
 * @date 2022/8/1 9:41
 * @description
 */
@Component("modbusTask")
public class ModbusTask {
    private static final Logger log = LoggerFactory.getLogger(ModbusTask.class);

    @Autowired
    private ApplicationContext applicationContext;

    public void getPlc() {
        IModbusService modbusService = applicationContext.getBean(IModbusService.class);
        int[] values = modbusService.readData(1, 0, 10);
        if (values == null){
            return;
        }
        List<ReadResult> readResults = new ArrayList<>();
        int address = 0;
        for (int value : values) {
            ReadResult readResult = new ReadResult();
            readResult.setSlaveId(1);
            readResult.setAddress(address);
            readResult.setValue(value);
            readResults.add(readResult);
            address++;
        }
        IReadResultService readResultService = applicationContext.getBean(IReadResultService.class);
        readResultService.saveOrUpdateReadResult(readResults);
        log.info("从设备获取数据成功");
    }
}
