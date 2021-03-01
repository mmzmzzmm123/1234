package com.gox.basic.service;

import com.gox.basic.domain.FormJson;

import java.util.List;

/**
 * 单json存储Service接口
 *
 * @author gox
 * @date 2020-12-25
 */
public interface IFormJsonService {
    /**
     * 查询单json存储
     *
     * @param id 单json存储ID
     * @return 单json存储
     */
    public FormJson selectFormJsonById(Long id);

    /**
     * 查询单json存储列表
     *
     * @param formJson 单json存储
     * @return 单json存储集合
     */
    public List<FormJson> selectFormJsonList(FormJson formJson);

    /**
     * 新增单json存储
     *
     * @param formJson 单json存储
     * @return 结果
     */
    public int insertFormJson(FormJson formJson);

    int updateFormOrderBatch(Iterable<FormJson> formJsons);

    /**
     * 修改单json存储
     *
     * @param formJson 单json存储
     * @return 结果
     */
    public int updateFormJson(FormJson formJson);

    /**
     * 批量删除单json存储
     *
     * @param ids 需要删除的单json存储ID
     * @return 结果
     */
    public int deleteFormJsonByIds(Long[] ids);

    /**
     * 删除单json存储信息
     *
     * @param id 单json存储ID
     * @return 结果
     */
    public int deleteFormJsonById(Long id);
}
