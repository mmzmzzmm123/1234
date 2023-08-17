package com.ruoyi.psychology.service;

import com.ruoyi.psychology.dto.DateNumDTO;
import com.ruoyi.psychology.vo.PsyConsultConfigByGroupVO;
import com.ruoyi.psychology.vo.PsyConsultConfigVO;

import java.util.List;

/**
 * 咨询类型Service接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface IPsyConsultConfigService
{

    List<DateNumDTO> getDateNum(Integer num);

    List<PsyConsultConfigVO> getConfigByType(String dictType);

    List<PsyConsultConfigByGroupVO> getConfigByTypes(String[] dictTypes);

    List<String> getNotices();

}