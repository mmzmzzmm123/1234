package com.ruoyi.web.controller.work;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.quartz.service.IModbusService;
import com.ruoyi.system.domain.WorkProcess;
import com.ruoyi.system.domain.vo.ReactionVo;
import com.ruoyi.system.service.IProcessService;
import com.ruoyi.system.service.IReactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private IReactionService reactionService;

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
    public R<List<WorkProcess>> getProcessList()
    {
        List<WorkProcess> processes = processService.getProcessList();
        return R.ok(processes);
    }

    @PostMapping("reaction/addAndUpdate")
    @ApiOperation("新增或编辑反应")
    public R addAndUpdateReaction(@RequestBody ReactionVo reactionVo){
        //reactionVo.setCreateBy(getUsername());
        reactionVo.setCreateBy("wjp");
        reactionService.addAndUpdateReaction(reactionVo);
        return R.ok();
    }

    @PostMapping("reaction/list")
    @ApiOperation("登记反应列表")
    public TableDataInfo getReactionList(){
        startPage();
        List<ReactionVo> reactionVos = reactionService.getReactionList();
        return getDataTable(reactionVos);
    }
}
