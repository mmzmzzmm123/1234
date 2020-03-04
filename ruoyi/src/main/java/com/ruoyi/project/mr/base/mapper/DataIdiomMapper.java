package com.ruoyi.project.mr.base.mapper;

import com.ruoyi.project.mr.base.domain.DataIdiom;
import java.util.List;

/**
 * 成语存放Mapper接口
 *
 * @author mr
 * @date 2020-03-03
 */
public interface DataIdiomMapper
{
    /**
     * 查询成语存放
     *
     * @param id 成语存放ID
     * @return 成语存放
     */
    public DataIdiom selectDataIdiomById(Long id);

    /**
     * 查询成语存放
     *
     * @param name 成语存放Name
     * @return 成语存放
     */
    public DataIdiom selectDataIdiomByName(String name);

    /**
     * 查询成语存放列表
     *
     * @param dataIdiom 成语存放
     * @return 成语存放集合
     */
    public List<DataIdiom> selectDataIdiomList(DataIdiom dataIdiom);

    /**
     * 新增成语存放
     *
     * @param dataIdiom 成语存放
     * @return 结果
     */
    public int insertDataIdiom(DataIdiom dataIdiom);

    /**
     * 修改成语存放
     *
     * @param dataIdiom 成语存放
     * @return 结果
     */
    public int updateDataIdiom(DataIdiom dataIdiom);

    /**
     * 删除成语存放
     *
     * @param id 成语存放ID
     * @return 结果
     */
    public int deleteDataIdiomById(Long id);

    /**
     * 批量删除成语存放
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataIdiomByIds(Long[] ids);
}
