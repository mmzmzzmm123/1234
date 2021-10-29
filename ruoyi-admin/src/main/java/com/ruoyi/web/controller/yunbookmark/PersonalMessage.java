package com.ruoyi.web.controller.yunbookmark;

import com.ruoyi.bookmark.service.ISqUserServise;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: Wang
 * @Date: 2021/10/29 17:32
 * 功能描述:
 */
@RestController
@RequestMapping("/user")
public class PersonalMessage extends BaseController
{

    @Resource
    private ISqUserServise sqUserServise;


    /**
     * 获取個人的信息 >> 首頁的數據 >> redis
     */
    @GetMapping("/getPersonalMessage")
    public AjaxResult getPersonalMessage()
    {
      return sqUserServise.getPersonalMessage(getAuthUser().getUserId());
    }



}
