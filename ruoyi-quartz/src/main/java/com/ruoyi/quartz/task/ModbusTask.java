package com.ruoyi.quartz.task;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author wjp
 * @date 2022/8/1 9:41
 * @description
 */
@Component("modbusTask")
public class ModbusTask {
    private static final Logger log = LoggerFactory.getLogger(ModbusTask.class);

    public void getPlc()
    {
        System.out.println("11212");
        try {
            //设置主机tcp参数
            TcpParameters tcpParameters = new TcpParameters();
            //设置tcp的ip地址-本地地址
            InetAddress address = InetAddress.getByName("127.0.0.1");
            tcpParameters.setHost(address);
            //设置长链接
            tcpParameters.setKeepAlive(true);
            //设置端口，默认端口502
            tcpParameters.setPort(502);
            //创建主机
            ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
            Modbus.setAutoIncrementTransactionId(true);

            int slaveId = 1;
            int offset = 0;
            int quantity = 10;

            try {
                if (!master.isConnected()){
                    master.connect();
                }
                //读取数据
                //功能码01 readCoils
                //功能码02 readDiscreteInputs
                //功能码03 readHoldingRegisters
                //功能码04 readInputRegisters
                int[] registerValues = master.readHoldingRegisters(slaveId, offset, quantity);
                for (int value : registerValues){
                    log.info("result：{}", value);
                }
            } catch (Exception e){
                log.error(e.getMessage());
            } finally {
                try {
                    master.disconnect();
                } catch (ModbusIOException e){

                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
