package com.ruoyi.jyykhgl.mapper;

import java.util.List;
import com.ruoyi.jyykhgl.domain.TsbzJyygryx;

/**
 * 个人研修（教研员）Mapper接口
 * 
 * @author tsbz
 * @date 2021-01-16
 */
public interface TsbzJyygryxMapper 
{
    /**
     * 查询个人研修（教研员）
     * 
     * @param id 个人研修（教研员）ID
     * @return 个人研修（教研员）
     */
    public TsbzJyygryx selectTsbzJyygryxById(Long id);

    /**
     * 查询个人研修（教研员）列表
     * 
     * @param tsbzJyygryx 个人研修（教研员）
     * @return 个人研修（教研员）集合
     */
    public List<TsbzJyygryx> selectTsbzJyygryxList(TsbzJyygryx tsbzJyygryx);

    /**
     * 新增个人研修（教研员）
     * 
     * @param tsbzJyygryx 个人研修（教研员）
     * @return 结果
     */
    public int insertTsbzJyygryx(TsbzJyygryx tsbzJyygryx);

    /**
     * 修改个人研修（教研员）
     * 
     * @param tsbzJyygryx 个人研修（教研员）
     * @return 结果
     */
    public int updateTsbzJyygryx(TsbzJyygryx tsbzJyygryx);

    /**
     * 删除个人研修（教研员）
     * 
     * @param id 个人研修（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyygryxById(Long id);

    /**
     * 批量删除个人研修（教研员）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJyygryxByIds(Long[] ids);
}
