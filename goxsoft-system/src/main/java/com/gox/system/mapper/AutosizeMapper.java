package com.gox.system.mapper;

import java.util.List;
import com.gox.system.domain.form.Autosize;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author gox
 * @date 2021-02-02
 */
public interface AutosizeMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Autosize selectAutosizeById(Long id);
    public Autosize selectAutosizeByItemId(Long itemId);
    /**
     * 查询【请填写功能名称】列表
     * 
     * @param autosize 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Autosize> selectAutosizeList(Autosize autosize);

    /**
     * 新增【请填写功能名称】
     * 
     * @param autosize 【请填写功能名称】
     * @return 结果
     */
    public int insertAutosize(Autosize autosize);

    /**
     * 修改【请填写功能名称】
     * 
     * @param autosize 【请填写功能名称】
     * @return 结果
     */
    public int updateAutosize(Autosize autosize);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteAutosizeById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAutosizeByIds(Long[] ids);
}
