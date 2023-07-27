package com.ruoyi.psychology.dto;

import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.vo.PsyConsultServeConfigVO;
import com.ruoyi.psychology.vo.PsyConsultVO;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 咨询服务对象 psy_consult
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@Data
public class PsyConsultInfoDTO implements Serializable
{
    private static final long serialVersionUID = -1670719183435508851L;

    private PsyConsultVO consult;

    private PsyConsultServeConfigVO serve;

    private List<PsyConsultWorkVO> works;

}
