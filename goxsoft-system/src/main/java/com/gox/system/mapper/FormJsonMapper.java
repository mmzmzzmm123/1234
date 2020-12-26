package com.gox.system.mapper;

import java.util.List;
import com.gox.system.domain.FormJson;
import org.apache.ibatis.annotations.Mapper;

/**
 * 单json存储Mapper接口
 * 
 * @author gox
 * @date 2020-12-25
 */
@Mapper
public interface FormJsonMapper 
{
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

    /**
     * 修改单json存储
     * 
     * @param formJson 单json存储
     * @return 结果
     */
    public int updateFormJson(FormJson formJson);

    /**
     * 删除单json存储
     * 
     * @param id 单json存储ID
     * @return 结果
     */
    public int deleteFormJsonById(Long id);

    /**
     * 批量删除单json存储
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFormJsonByIds(Long[] ids);
}
