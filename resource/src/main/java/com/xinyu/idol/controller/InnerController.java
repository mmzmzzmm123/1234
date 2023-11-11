package com.xinyu.idol.controller;


import com.xinyu.idol.common.annotation.Log;
import com.xinyu.idol.common.core.domain.AjaxResult;
import com.xinyu.idol.common.core.domain.R;
import com.xinyu.idol.common.enums.BusinessType;
import com.xinyu.idol.pojo.dto.GuidListDto;
import com.xinyu.idol.service.IClassificationsService;
import com.xinyu.idol.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inner")
public class InnerController {

    @Autowired
    private IContentService contentService;

    @Autowired
    private IClassificationsService classificationsService;


    @RequestMapping(value = "/listByGuids", method = RequestMethod.POST)
    public R listByGuids(@RequestBody GuidListDto guidListDto) {
        return R.ok( contentService.listByGuidList(guidListDto));
    }


    @RequestMapping(value = "/listAllClassifications", method = RequestMethod.POST)
    public R listByClassifications() {
        return R.ok( classificationsService.listAll());
    }

    @RequestMapping(value = "/updateAllClassifications", method = RequestMethod.GET)
    public R updateAllClassifications() {
        contentService.updateAllClassifications();
        return R.ok();
    }
}
