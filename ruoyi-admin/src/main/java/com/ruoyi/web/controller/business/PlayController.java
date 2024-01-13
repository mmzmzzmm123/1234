package com.ruoyi.web.controller.business;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.play.PlayDTO;
import com.ruoyi.system.service.IPlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author : zengyi
 */
//todo 调试用，上线前去除
@Anonymous
@Api(tags = "炒群任务")
@RestController
@RequestMapping("/play")
public class PlayController extends BaseController {
    @Resource
    private IPlayService playService;

    @ApiOperation("创建炒群任务")
    @PostMapping(value = "/create")
    public R<String> create(@RequestBody PlayDTO dto) {
       return playService.create(dto);
    }

    @ApiOperation("修改炒群任务")
    @PostMapping(value = "/update")
    public R<String> update(@RequestBody PlayDTO dto) {
        return playService.update(dto);
    }

    @ApiOperation("获取炒群任务详情")
    @GetMapping(value = "/{id}")
    public R<PlayDTO> info(@PathVariable String id) {
        return playService.info(id);
    }
}
