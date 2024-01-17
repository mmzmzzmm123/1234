package com.ruoyi.web.controller.business;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.VibeRuleDTO;
import com.ruoyi.system.service.IVibeRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author : zengyi
 */
@Api(tags = "群氛围调度规则")
@RestController
@RequestMapping("/vibeRule")
public class VibeRuleController extends BaseController {
    @Resource
    private IVibeRuleService vibeRuleService;

    @ApiOperation("创建")
    @PostMapping(value = "/create")
    public R<String> create(@RequestBody VibeRuleDTO dto) {
        dto.setMerchantId(getMerchantId());
        return vibeRuleService.create(dto);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public R<String> update(@RequestBody VibeRuleDTO dto) {
        return vibeRuleService.update(dto);
    }

    @ApiOperation("获取详情")
    @GetMapping(value = "info")
    public R<VibeRuleDTO> getOne() {
        return R.ok(vibeRuleService.getOne());
    }
}
