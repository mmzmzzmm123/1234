package com.ruoyi.psychology.service.impl;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysDictType;
import com.ruoyi.psychology.mapper.PsyConsultConfigMapper;
import com.ruoyi.psychology.service.IPsyConsultConfigService;
import com.ruoyi.psychology.vo.PsyConsultConfigByGroupVO;
import com.ruoyi.psychology.vo.PsyConsultConfigVO;
import com.ruoyi.system.service.ISysDictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 咨询类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@Service
public class PsyConsultConfigServiceImpl implements IPsyConsultConfigService
{

    @Resource
    private PsyConsultConfigMapper psyConsultConfigMapper;

    @Resource
    private ISysDictTypeService iSysDictTypeService;

    @Override
    public List<PsyConsultConfigVO> getConfigByType(String dictType) {
        SysDictType sysDictType = iSysDictTypeService.selectDictTypeByType(dictType);
        List<SysDictData> dictData = iSysDictTypeService.selectDictDataByType(dictType);
        List<PsyConsultConfigVO> collect = dictData.stream().map(i -> convertToVo(sysDictType, i)).collect(Collectors.toList());
        return collect;
    }

    private PsyConsultConfigVO convertToVo(SysDictType sysDictType, SysDictData sysDictData) {
        PsyConsultConfigVO vo = new PsyConsultConfigVO();
        vo.setTitle(sysDictType.getDictName());
        vo.setCode(sysDictData.getDictType());
        vo.setName(sysDictData.getDictLabel());
        vo.setValue(sysDictData.getDictValue());
        return vo;
    }

    @Override
    public List<PsyConsultConfigByGroupVO> getConfigByTypes(String[] dictTypes) {
        return psyConsultConfigMapper.getConfigByTypes(dictTypes);
    }

}
