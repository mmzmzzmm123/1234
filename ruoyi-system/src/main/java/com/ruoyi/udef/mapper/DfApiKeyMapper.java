package com.ruoyi.udef.mapper;

import java.util.List;
import com.ruoyi.udef.domain.DfApiKey;

/**
 * APIMapper接口
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
public interface DfApiKeyMapper 
{
    /**
     * 查询API
     * 
     * @param id API主键
     * @return API
     */
    public DfApiKey selectDfApiKeyById(Long id);

    /**
     * 查询API列表
     * 
     * @param dfApiKey API
     * @return API集合
     */
    public List<DfApiKey> selectDfApiKeyList(DfApiKey dfApiKey);

    /**
     * 新增API
     * 
     * @param dfApiKey API
     * @return 结果
     */
    public int insertDfApiKey(DfApiKey dfApiKey);

    /**
     * 修改API
     * 
     * @param dfApiKey API
     * @return 结果
     */
    public int updateDfApiKey(DfApiKey dfApiKey);

    /**
     * 删除API
     * 
     * @param id API主键
     * @return 结果
     */
    public int deleteDfApiKeyById(Long id);

    /**
     * 批量删除API
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDfApiKeyByIds(Long[] ids);
}
