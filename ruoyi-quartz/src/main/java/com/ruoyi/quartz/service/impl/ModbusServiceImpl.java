package com.ruoyi.quartz.service.impl;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import com.ruoyi.quartz.service.IModbusService;
import com.ruoyi.quartz.task.ModbusTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

/**
 * @author wjp
 * @date 2022/8/2 9:37
 * @description
 */
@Service
public class ModbusServiceImpl implements IModbusService {

    private static final Logger log = LoggerFactory.getLogger(ModbusTask.class);

    private String ipaddr = "127.0.0.1";

    private int port = 502;


    private TcpParameters getTcpParameters() throws Exception {
            //设置主机tcp参数
            TcpParameters tcpParameters = new TcpParameters();
            //设置tcp的ip地址-本地地址
            InetAddress address = InetAddress.getByName(ipaddr);
            tcpParameters.setHost(address);
            //设置长链接
            tcpParameters.setKeepAlive(true);
            //设置端口，默认端口502
            tcpParameters.setPort(port);
            return tcpParameters;
    }

    @Override
    public int[] readData(int slaveId, int offset, int quantity) {
        try {
            //创建主机
            ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(getTcpParameters());
            Modbus.setAutoIncrementTransactionId(true);
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
                return registerValues;
            } catch (Exception e){
                log.error(e.getMessage());
            } finally {
                try {
                    master.disconnect();
                } catch (ModbusIOException e){
                    log.error(e.getMessage());
                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void writeData(int slaveId, int address, int value) {
        try {
            //创建主机
            ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(getTcpParameters());
            Modbus.setAutoIncrementTransactionId(true);



            try {
                if (!master.isConnected()){
                    master.connect();
                }
                master.writeSingleRegister(slaveId, address, value);
            } catch (Exception e){
                log.error(e.getMessage());
            } finally {
                try {
                    master.disconnect();
                } catch (ModbusIOException e){
                    log.error(e.getMessage());
                }
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}

