package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.gauge.constant.GaugeConstant;
import com.ruoyi.gauge.domain.PsyGauge;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsResult;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsResultService;
import com.ruoyi.gauge.service.IPsyGaugeService;
import com.ruoyi.gauge.service.IPsyOrderService;
import com.ruoyi.gauge.vo.GaugeVO;
import com.ruoyi.psychology.service.IPsyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 心理测评Controller
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/app/home/gauge")
@Api(value = "AppPsyGaugeController" ,tags = {"测评量表api"})
public class AppPsyGaugeController extends BaseController
{
    @Autowired
    private IPsyGaugeService psyGaugeService;

    @Autowired
    private IPsyOrderService psyOrderService;

    @Autowired
    private AppTokenService appTokenService;

    @Autowired
    private IPsyGaugeQuestionsResultService psyGaugeQuestionsResultService;
    /**
     * 查询心理测评列表
     */
//    @PreAuthorize("@ss.hasPermi('psychology:gauge:list')")
    @GetMapping("/list")
    @ApiOperation("查询量表数据列表")
    public TableDataInfo list(PsyGauge psyGauge)
    {
        startPage();
        List<PsyGauge> list = psyGaugeService.selectPsyGaugeList(psyGauge);
        return getDataTable(list);
    }

    /**
     * 获取心理测评详细信息
     */
//    @PreAuthorize("@ss.hasPermi('psychology:gauge:query')")
    @PostMapping(value = "/getInfo")
    @ApiOperation("查询量表详细信息")
    public AjaxResult getInfo(@RequestParam(value = "id") Integer id, HttpServletRequest request)
    {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        Integer userId = loginUser.getUserId();
        PsyGauge psyGauge = psyGaugeService.selectPsyGaugeById(id);
        GaugeVO gaugeVO = new GaugeVO();
        BeanUtils.copyProperties(psyGauge, gaugeVO);
        PsyOrder psyOrder = psyOrderService.getPsyOrder(userId, id);
        if (psyOrder != null) { // 用户已经下单
            gaugeVO.setOrderId(psyOrder.getId());
            gaugeVO.setIsBuy(GaugeConstant.GAUGE_IS_BUY);
        } else {
            gaugeVO.setIsBuy(GaugeConstant.GAUGE_NOT_BUY);
        }
        PsyGaugeQuestionsResult psyGaugeQuestionsResult = new PsyGaugeQuestionsResult();
        psyGaugeQuestionsResult.setUserId(userId);
        psyGaugeQuestionsResult.setGaugeId(id);
        if (psyOrder != null) {
            psyGaugeQuestionsResult.setOrderId(psyOrder.getId());
        }
        List<PsyGaugeQuestionsResult> psyGaugeQuestionsResultList = psyGaugeQuestionsResultService.selectPsyGaugeQuestionsResultList(psyGaugeQuestionsResult);
        // 将多选题的答案选项分组归并
        Map<Integer, Long> result  = psyGaugeQuestionsResultList.stream().collect(Collectors.groupingBy(PsyGaugeQuestionsResult::getQuestionsId, Collectors.counting()));
        gaugeVO.setFinishedNum(result.keySet().size());

        return AjaxResult.success(gaugeVO);
    }

    /**
     * 根据搜素条件查询课程列表
     */
    @PostMapping("/search")
    @ApiOperation("查询课程列表")
    public AjaxResult getList(@RequestParam String searchValue)    {
        List<PsyGauge> list = psyGaugeService.selectPsyGaugeList(null);
        list = list.stream()
                .filter(item -> item.getTitle().contains(searchValue))
                .collect(Collectors.toList());
        return AjaxResult.success(list);
    }

}
