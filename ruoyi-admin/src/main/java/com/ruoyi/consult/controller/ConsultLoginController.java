package com.ruoyi.consult.controller;


import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.ConsultDTO;
import com.ruoyi.common.core.domain.dto.ConsultLoginDTO;
import com.ruoyi.consult.common.dcloud.CloudFunctions;
import com.ruoyi.framework.web.service.ConsultantTokenService;
import com.ruoyi.psychology.domain.PsyConsult;
import com.ruoyi.psychology.service.IPsyConsultService;
import com.ruoyi.web.controller.common.CommonCosController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consult")
public class ConsultLoginController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CommonCosController.class);

    @Autowired
    ConsultantTokenService consultantTokenService;

    @Autowired
    IPsyConsultService psyConsultService;

    @GetMapping("/login")
    @RateLimiter
    public AjaxResult login(ConsultLoginDTO consultLoginDTO)
    {
        try {
           String phoneNumber= new CloudFunctions().getPhoneNumber(consultLoginDTO);
           //获取咨询师信息
            PsyConsult psyConsult=psyConsultService.getByPhone(phoneNumber);
            ConsultDTO consultDTO=new ConsultDTO();
            consultDTO.setConsultId(psyConsult.getUserId());
            consultDTO.setPhone( psyConsult.getPhonenumber());
            String token= consultantTokenService.createToken(consultDTO,360000);
            return AjaxResult.success(token);
        } catch (Exception e) {
            log.error("login error",e);
            return AjaxResult.error("login error");
        }
    }

}
