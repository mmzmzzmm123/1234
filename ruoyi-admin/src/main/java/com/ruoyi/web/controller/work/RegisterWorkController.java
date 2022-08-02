package com.ruoyi.web.controller.work;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.quartz.service.IModbusService;
import com.ruoyi.system.domain.Process;
import com.ruoyi.system.service.IProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wjp
 * @date 2022/8/1 10:26
 * @description
 */
@Api("登记反应管理")
@Anonymous
@RestController
@RequestMapping("/work/register")
public class RegisterWorkController extends BaseController {

    @Autowired
    private IModbusService modbusService;
    @Autowired
    private IProcessService processService;

    @GetMapping()
    @ApiOperation("test读")
    public int[] read(@RequestParam int slaveId, @RequestParam int offset, @RequestParam int quantity)
    {
        return modbusService.readData(slaveId,offset,quantity);
    }

    @GetMapping("/w")
    @ApiOperation("test写")
    public void write(@RequestParam int slaveId, @RequestParam int address, @RequestParam int value)
    {
        modbusService.writeData(slaveId,address,value);
    }

    @GetMapping("/process/list")
    @ApiOperation("工艺查询")
    public R<List<Process>> getProcessList()
    {
        List<Process> processes = processService.getProcessList();
        return R.ok(processes);
    }
}
