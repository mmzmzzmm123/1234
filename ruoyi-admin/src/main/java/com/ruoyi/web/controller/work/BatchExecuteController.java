package com.ruoyi.web.controller.work;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.vo.BatchVo;
import com.ruoyi.system.domain.vo.ReactionVo;
import com.ruoyi.system.domain.vo.RecordQueryVo;
import com.ruoyi.system.service.IBatchExecuteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * Created by IntelliJ IDEA.
 * Author: WangJinpeng
 * Date: 2022/8/6
 * Time: 20:31
 */
@Api("批次执行管理")
@Anonymous
@RestController
@RequestMapping("/work/batch")
public class BatchExecuteController extends BaseController{

    @Autowired
    private IBatchExecuteService batchExecuteService;

    @PostMapping("/addAndUpdate")
    @ApiOperation("新增或编辑反应批次")
    public R addAndUpdateBatch(@RequestBody BatchVo batchVo){
        //reactionVo.setCreateBy(getUsername());
        batchVo.setCreateBy("wjp");
        batchExecuteService.addAndUpdateBatch(batchVo);
        return R.ok();
    }

    @PostMapping("/list")
    @ApiOperation("反应批次列表")
    public R<List<BatchVo>> getReactionBatchList(@RequestParam(required = false) String batchNo){
        //startPage();
        List<BatchVo> batchVos = batchExecuteService.getReactionBatchList(batchNo);
        return R.ok(batchVos);
    }

    @PostMapping("/feedingRecord")
    @ApiOperation("查询投料配置")
    public R<WorkFeedingRecord> getWorkFeedingRecord(@RequestBody RecordQueryVo recordQueryVo){
        WorkFeedingRecord workFeedingRecord = batchExecuteService.getWorkFeedingRecord(recordQueryVo);
        return R.ok(workFeedingRecord);
    }

    @PostMapping("/heatingRecord")
    @ApiOperation("查询搅拌加热配置")
    public R<WorkHeatingRecord> getWorkHeatingRecord(@RequestBody RecordQueryVo recordQueryVo){
        WorkHeatingRecord workHeatingRecord = batchExecuteService.getWorkHeatingRecord(recordQueryVo);
        return R.ok(workHeatingRecord);
    }

    @PostMapping("/extractionRecord")
    @ApiOperation("查询萃取配置")
    public R<WorkExtractionRecord> getWorkExtractionRecord(@RequestBody RecordQueryVo recordQueryVo){
        WorkExtractionRecord workExtractionRecord = batchExecuteService.getWorkExtractionRecord(recordQueryVo);
        return R.ok(workExtractionRecord);
    }

    @PostMapping("/concentrateRecord")
    @ApiOperation("查询浓缩配置")
    public R<WorkConcentrateRecord> getWorkConcentrateRecord(@RequestBody RecordQueryVo recordQueryVo){
        WorkConcentrateRecord workConcentrateRecord = batchExecuteService.getWorkConcentrateRecord(recordQueryVo);
        return R.ok(workConcentrateRecord);
    }

    @PostMapping("/gelRecord")
    @ApiOperation("查询硅胶拌样配置")
    public R<WorkGelRecord> getWorkGelRecord(@RequestBody RecordQueryVo recordQueryVo){
        WorkGelRecord workGelRecord = batchExecuteService.getWorkGelRecord(recordQueryVo);
        return R.ok(workGelRecord);
    }


    @PostMapping("/feedingRecord/update")
    @ApiOperation("更新投料配置")
    public R updateWorkFeedingRecord(@RequestBody WorkFeedingRecord workFeedingRecord){
        batchExecuteService.updateWorkFeedingRecord(workFeedingRecord);
        return R.ok();
    }

    @PostMapping("/heatingRecord/update")
    @ApiOperation("更新搅拌加热配置")
    public R updateWorkHeatingRecord(@RequestBody WorkHeatingRecord workHeatingRecord){
        batchExecuteService.updateWorkHeatingRecord(workHeatingRecord);
        return R.ok();
    }

    @PostMapping("/extractionRecord/update")
    @ApiOperation("更新萃取配置")
    public R updateWorkExtractionRecord(@RequestBody WorkExtractionRecord workExtractionRecord){
        batchExecuteService.updateWorkExtractionRecord(workExtractionRecord);
        return R.ok();
    }

    @PostMapping("/concentrateRecord/update")
    @ApiOperation("更新浓缩配置")
    public R updateWorkConcentrateRecord(@RequestBody WorkConcentrateRecord workConcentrateRecord){
        batchExecuteService.updateWorkConcentrateRecord(workConcentrateRecord);
        return R.ok();
    }

    @PostMapping("/gelRecord/update")
    @ApiOperation("更新硅胶拌样配置")
    public R updateWorkGelRecord(@RequestBody WorkGelRecord workGelRecord){
        batchExecuteService.updateWorkGelRecord(workGelRecord);
        return R.ok();
    }

    @PostMapping("/button/sampling")
    @ApiOperation("取样按钮")
    public R buttonSampling(){
        return R.ok();
    }

    @PostMapping("/button/dilution")
    @ApiOperation("稀释按钮")
    public R buttonDilution(){
        return R.ok();
    }

    @PostMapping("/button/exit")
    @ApiOperation("失败退出按钮")
    public R buttonExit(){
        return R.ok();
    }

    @PostMapping("/button/finish")
    @ApiOperation("完成按钮")
    public R buttonFinish(){
        return R.ok();
    }

}
