package com.ruoyi.excelRequest.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.excelRequest.service.SendPostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/excel/request")
public class ExcelRequestController extends BaseController {
    @Resource
    private SendPostService service;
    @PostMapping("/send")
    @PreAuthorize("@ss.hasPermi('system:sendMail:send')")
    public void list(@RequestBody String filePath)
    {
        service.sendPost(filePath);
    }


}
