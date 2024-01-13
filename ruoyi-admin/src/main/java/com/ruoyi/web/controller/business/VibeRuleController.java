package com.ruoyi.web.controller.business;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.play.VibeRuleDTO;
import com.ruoyi.system.service.IVibeRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author : zengyi
 */
//todo 调试用，上线前去除
@Anonymous
@Api(tags = "群氛围调度规则")
@RestController
@RequestMapping("/vibeRule")
public class VibeRuleController extends BaseController {
    @Resource
    private IVibeRuleService vibeRuleService;

    @ApiOperation("创建炒群任务")
    @PostMapping(value = "/create")
    public R<String> create(@RequestBody VibeRuleDTO dto) {
        return vibeRuleService.create(dto);
    }

    @ApiOperation("修改炒群任务")
    @PostMapping(value = "/update")
    public R<String> update(@RequestBody VibeRuleDTO dto) {
        return vibeRuleService.update(dto);
    }

    @ApiOperation("获取炒群任务")
    @GetMapping(value = "/{id}")
    public R<VibeRuleDTO> info(@PathVariable Integer id) {
        return vibeRuleService.info(id);
    }
}
