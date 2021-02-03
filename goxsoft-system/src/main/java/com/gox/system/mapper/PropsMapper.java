package com.gox.system.mapper;

import java.util.List;
import com.gox.system.domain.form.Props;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface PropsMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Props selectPropsById(Long id);
    public Props selectPropsByItemId(Long itemId);

    /**
     *
     * @param id
     * @return
     */
    public Props selectPropsByProId(Long id);
    /**
     * 查询【请填写功能名称】列表
     * 
     * @param props 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Props> selectPropsList(Props props);

    /**
     * 新增【请填写功能名称】
     * 
     * @param props 【请填写功能名称】
     * @return 结果
     */
    public int insertProps(Props props);

    /**
     * 修改【请填写功能名称】
     * 
     * @param props 【请填写功能名称】
     * @return 结果
     */
    public int updateProps(Props props);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deletePropsById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePropsByIds(Long[] ids);
}
