package com.ruoyi.carpool.controller;


import com.ruoyi.carpool.domain.CommonVO;
import com.ruoyi.carpool.service.IPCommonService;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carpool/common")
public class PCommonController {


    @Autowired
    private IPCommonService ipCommonService;


    /**
     * 乘客或者司机关闭订单
     * @param commonVO
     * @return
     */
    @PostMapping(value = "/cancelOrder")
    public AjaxResult getDriverInfo(@RequestBody CommonVO commonVO)
    {
        return ipCommonService.cancelOrder(commonVO);
    }



    /**
     * 乘客申请加入拼单
     * @param commonVO
     * @return
     */
    @PostMapping(value = "/joinOrderList")
    public AjaxResult joinOrderList(@RequestBody CommonVO commonVO)
    {
        return ipCommonService.joinOrderList(commonVO);
    }







}
