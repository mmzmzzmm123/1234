package com.ruoyi.carpool.controller;


import com.ruoyi.carpool.domain.CommonVO;
import com.ruoyi.carpool.domain.PMember;
import com.ruoyi.carpool.domain.PPassenger;
import com.ruoyi.carpool.service.IPCommonService;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carpool/common")
@Api(value = "小程序拼单-公共模块", tags = "公共模块")
public class PCommonController {


    @Autowired
    private IPCommonService pCommonService;


    /**
     * 乘客或者司机关闭订单
     * @param commonVO
     * @return
     */
    @PostMapping(value = "/cancelOrder")
    public AjaxResult cancelOrder(@RequestBody CommonVO commonVO)
    {
        return pCommonService.cancelOrder(commonVO);
    }


    /**
     * 乘客或者司机关闭订单
     * @param commonVO
     * @return
     */
    @PostMapping(value = "/finishOrder")
    public AjaxResult finishOrder(@RequestBody CommonVO commonVO)
    {
        return pCommonService.cancelOrder(commonVO);
    }




    /**
     * 乘客申请加入拼单
     * @param commonVO
     * @return
     */
    @PostMapping(value = "/joinOrderList")
    public AjaxResult joinOrderList(@RequestBody CommonVO commonVO)
    {
        return pCommonService.joinOrderList(commonVO);
    }


    /**
     * 初始化微信用户信息
     * @param pPassenger
     * @return
     */
    @PostMapping(value = "/initMember")
    @ApiOperation(value = "carpool_02_初始化微信用户信息", notes = "初始化微信用户信息,新用户系统会创建一个乘客的信息，非新用户会返回系统用户的信息。")
    public AjaxResult initMember( @Validated @RequestBody PPassenger pPassenger)
    {
        return pCommonService.initMember(pPassenger);
    }



}
