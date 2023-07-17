package com.ruoyi.psychology.service;

import java.util.List;

import com.ruoyi.common.vo.TreeVO;
import com.ruoyi.psychology.domain.PsyConsultType;
import com.ruoyi.psychology.vo.PsyConsultServeConfigVO;
import com.ruoyi.psychology.vo.PsyConsultTypeVO;

/**
 * 咨询类型Service接口
 * 
 * @author ruoyi
 * @date 2023-07-14
 */
public interface IPsyConsultTypeService 
{
    /**
     * 查询咨询类型
     * 
     * @param id 咨询类型主键
     * @return 咨询类型
     */
    PsyConsultType selectPsyConsultTypeById(Long id);

    /**
     * 查询咨询类型列表
     * 
     * @param psyConsultType 咨询类型
     * @return 咨询类型集合
     */
    List<PsyConsultType> selectPsyConsultTypeList(PsyConsultType psyConsultType);

    List<TreeVO> getTrees();

    int add(PsyConsultTypeVO req);

    int update(PsyConsultTypeVO req);

    int deleteAll(Long[] ids);

    int delete(Long id);
}
