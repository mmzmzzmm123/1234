package com.ruoyi.quartz.service;

/**
 * @author wjp
 * @date 2022/8/2 9:36
 * @description
 */
public interface IModbusService {

    int[] readData(int slaveId, int offset, int quantity);

    void writeData(int slaveId, int address, int value);
}
