package com.renxin.psychology.service;

import com.renxin.psychology.dto.DateNumDTO;
import com.renxin.psychology.vo.PsyConsultConfigByGroupVO;
import com.renxin.psychology.vo.PsyConsultConfigVO;

import java.util.List;

/**
 * 咨询类型Service接口
 * 
 * @author renxin
 * @date 2023-06-16
 */
public interface IPsyConsultConfigService
{

    List<DateNumDTO> getDateNum(Integer num);

    List<PsyConsultConfigVO> getConfigByType(String dictType);

    List<PsyConsultConfigByGroupVO> getConfigByTypes(String[] dictTypes);

    List<String> getNotices();

}