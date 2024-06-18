package com.ruoyi.app.controller.token;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.ConsultDTO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.framework.web.service.ConsultantTokenService;
import com.ruoyi.psychology.service.IPsyConsultService;
import com.ruoyi.psychology.vo.PsyConsultVO;
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
        consultDTO.setConsultId(psyConsult.getUserId());
        consultDTO.setPhone(psyConsult.getPhonenumber());
        String token = consultantTokenService.createToken(consultDTO,3600000);
        log.info("token:{}",token);
        return AjaxResult.successData(Constants.TOKEN_PREFIX+ token);
    }
}
