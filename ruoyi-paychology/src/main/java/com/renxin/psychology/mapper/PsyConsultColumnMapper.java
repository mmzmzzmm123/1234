package com.renxin.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renxin.psychology.domain.PsyConsultColumn;
import com.renxin.psychology.vo.PsyConsultColumnVO;

import java.util.List;

/**
 * 咨询专栏Mapper接口
 *
 * @author renxin
 * @date 2023-09-04
 */
public interface PsyConsultColumnMapper extends BaseMapper<PsyConsultColumn> {

    /**
     * 查询咨询专栏
     *
     * @param id 咨询专栏主键
     * @return 咨询专栏
     */
    PsyConsultColumnVO getOne(Long id);

    /**
     * 查询咨询专栏列表
     *
     * @param req 咨询专栏
     * @return 咨询专栏集合
     */
    List<PsyConsultColumnVO> getList(PsyConsultColumnVO req);

    /**
     * 删除咨询专栏
     *
     * @param id 咨询专栏主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除咨询专栏
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

}
