package com.xinyu.idol.controller;


import com.xinyu.idol.common.annotation.Log;
import com.xinyu.idol.common.core.domain.AjaxResult;
import com.xinyu.idol.common.core.domain.R;
import com.xinyu.idol.common.enums.BusinessType;
import com.xinyu.idol.pojo.dto.GuidListDto;
import com.xinyu.idol.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inner")
public class InnerController {

    @Autowired
    private IContentService contentService;


    @RequestMapping(value = "/listByGuids", method = RequestMethod.POST)
    public R listByGuids(@RequestBody GuidListDto guidListDto) {
        return R.ok( contentService.listByGuidList(guidListDto));
    }
}
