package com.gox.basic.mapper;

import java.util.List;
import com.gox.basic.domain.ManClassify;
import org.springframework.stereotype.Repository;

/**
 * 分类管理Mapper接口
 * 
 * @author gox
 * @date 2021-03-09
 */
@Repository
public interface ManClassifyMapper 
{
    /**
     * 查询分类管理
     * 
     * @param id 分类管理ID
     * @return 分类管理
     */
    public ManClassify selectManClassifyById(Long id);

    /**
     * 查询分类管理列表
     * 
     * @param manClassify 分类管理
     * @return 分类管理集合
     */
    public List<ManClassify> selectManClassifyList(ManClassify manClassify);

    /**
     * 新增分类管理
     * 
     * @param manClassify 分类管理
     * @return 结果
     */
    public int insertManClassify(ManClassify manClassify);

    /**
     * 修改分类管理
     * 
     * @param manClassify 分类管理
     * @return 结果
     */
    public int updateManClassify(ManClassify manClassify);

    /**
     * 删除分类管理
     * 
     * @param id 分类管理ID
     * @return 结果
     */
    public int deleteManClassifyById(Long id);

    /**
     * 批量删除分类管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteManClassifyByIds(Long[] ids);
}
