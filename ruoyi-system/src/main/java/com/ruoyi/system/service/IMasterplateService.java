package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.AjaxResult;


public interface IMasterplateService
{

    /**
     * 转发
     * @param body
     * @return
     */
    public AjaxResult sendMasterplate(Object body);

    /**
     * 获取ocr信息
     * @param img
     * @return
     */
    public Object ocr(String img);

}
