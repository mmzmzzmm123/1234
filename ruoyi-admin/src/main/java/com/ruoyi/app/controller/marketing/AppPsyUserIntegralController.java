package com.ruoyi.app.controller.marketing;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.user.request.IntegralSearchReq;
import com.ruoyi.user.service.IPsyUserIntegralRecordService;
import com.ruoyi.user.vo.PsyUserIntegralRecordVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/app/user/integral")
public class AppPsyUserIntegralController extends BaseController {

    @Resource
    private IPsyUserIntegralRecordService integralRecordService;

    /**
     * 积分分页列表
     * @param req 搜索条件
     */
    @GetMapping("/list")
    public TableDataInfo getList(IntegralSearchReq req) {
        startPage();
//        req.setUid(getUserId().intValue());
        List<PsyUserIntegralRecordVO> list = integralRecordService.getList(req);
        return getDataTable(list);
    }


}
