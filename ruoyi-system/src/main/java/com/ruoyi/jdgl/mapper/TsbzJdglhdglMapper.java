package com.ruoyi.jdgl.mapper;

import java.util.List;
import com.ruoyi.jdgl.domain.TsbzJdglhdgl;

/**
 * 基地管理活动管理Mapper接口
 * 
 * @author tsbz
 * @date 2021-05-13
 */
public interface TsbzJdglhdglMapper 
{
    /**
     * 查询基地管理活动管理
     * 
     * @param id 基地管理活动管理ID
     * @return 基地管理活动管理
     */
    public TsbzJdglhdgl selectTsbzJdglhdglById(Long id);

    /**
     * 查询基地管理活动管理列表
     * 
     * @param tsbzJdglhdgl 基地管理活动管理
     * @return 基地管理活动管理集合
     */
    public List<TsbzJdglhdgl> selectTsbzJdglhdglList(TsbzJdglhdgl tsbzJdglhdgl);

    /**
     * 新增基地管理活动管理
     * 
     * @param tsbzJdglhdgl 基地管理活动管理
     * @return 结果
     */
    public int insertTsbzJdglhdgl(TsbzJdglhdgl tsbzJdglhdgl);

    /**
     * 修改基地管理活动管理
     * 
     * @param tsbzJdglhdgl 基地管理活动管理
     * @return 结果
     */
    public int updateTsbzJdglhdgl(TsbzJdglhdgl tsbzJdglhdgl);

    /**
     * 删除基地管理活动管理
     * 
     * @param id 基地管理活动管理ID
     * @return 结果
     */
    public int deleteTsbzJdglhdglById(Long id);

    /**
     * 批量删除基地管理活动管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJdglhdglByIds(Long[] ids);
}
