package com.renxin.app.controller.token;

import com.renxin.common.constant.Constants;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.domain.dto.ConsultDTO;
import com.renxin.framework.web.service.ConsultantTokenService;
import com.renxin.psychology.service.IPsyConsultService;
import com.renxin.psychology.vo.PsyConsultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/app/token")
public class TokenController extends BaseController {

    @Resource
    private ConsultantTokenService consultantTokenService;

    @Resource
    private IPsyConsultService psyConsultService;

    @GetMapping("/uuid/{id}")
    public AjaxResult getToken(@PathVariable("id") Long id){
        PsyConsultVO psyConsult= psyConsultService.getOne(id);
        ConsultDTO consultDTO=new ConsultDTO();
        consultDTO.setConsultId(psyConsult.getId());
        consultDTO.setPhone(psyConsult.getPhonenumber());
        String token = consultantTokenService.createToken(consultDTO,3600000);
        log.info("token:{}",token);
        return AjaxResult.successData(Constants.TOKEN_PREFIX+ token);
    }
}
