package com.ruoyi.web.controller.marketing;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.user.request.IntegralSearchReq;
import com.ruoyi.user.service.IPsyUserIntegralRecordService;
import com.ruoyi.user.vo.PsyUserIntegralRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/marketing/user/integral")
@Api(tags = "用户积分管理")
public class UserIntegralController extends BaseController {

    @Resource
    private IPsyUserIntegralRecordService integralRecordService;

    /**
     * 积分分页列表
     * @param req 搜索条件
     */
    @PreAuthorize("@ss.hasPermi('marketing:integral:query')")
    @ApiOperation(value = "积分分页列表")
    @GetMapping("/list")
    public TableDataInfo getList(IntegralSearchReq req) {
        startPage();
        List<PsyUserIntegralRecordVO> list = integralRecordService.getList(req);
        return getDataTable(list);
    }


}
