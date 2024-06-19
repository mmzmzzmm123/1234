package com.renxin.consultant.controller;


import com.renxin.common.annotation.RateLimiter;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.domain.dto.ConsultDTO;
import com.renxin.framework.web.service.ConsultantTokenService;
import com.renxin.psychology.dto.OrderItemDTO;
import com.renxin.psychology.service.IPsyConsultOrderItemService;
import com.renxin.web.controller.common.CommonCosController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



@RestController
@RequestMapping("/consultant/workspace")
@Api(value = "ConsultantWorkSpaceController" ,tags = {" 工作台api"})
public class ConsultantWorkSpaceController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonCosController.class);

 
    @Autowired
    private IPsyConsultOrderItemService psyConsultOrderItemService;
    @Autowired
    private ConsultantTokenService consultantTokenService;

    @GetMapping("/task/list")
    @RateLimiter
    public AjaxResult list(HttpServletRequest request)
    {
       ConsultDTO loginUser = consultantTokenService.getLoginUser(request);

        //用时间工具类获取当前时间
        // Use time utility class to get the current date
        LocalDate currentDate = LocalDate.now();

        // Format the current date as "yyyy-MM-dd"
        String day = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time=currentDate.format(DateTimeFormatter.ofPattern("hh:mm"));

        List<OrderItemDTO> list = psyConsultOrderItemService.getOrderItemList(loginUser.getConsultId(),day);
        return AjaxResult.success(list);
    }

}
