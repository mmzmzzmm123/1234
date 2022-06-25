package com.ruoyi.udef.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.udef.mapper.DfApiKeyMapper;
import com.ruoyi.udef.domain.DfApiKey;
import com.ruoyi.udef.service.IDfApiKeyService;

/**
 * APIService业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
@Service
public class DfApiKeyServiceImpl implements IDfApiKeyService 
{
    @Autowired
    private DfApiKeyMapper dfApiKeyMapper;

    /**
     * 查询API
     * 
     * @param id API主键
     * @return API
     */
    @Override
    public DfApiKey selectDfApiKeyById(Long id)
    {
        return dfApiKeyMapper.selectDfApiKeyById(id);
    }

    /**
     * 查询API列表
     * 
     * @param dfApiKey API
     * @return API
     */
    @Override
    public List<DfApiKey> selectDfApiKeyList(DfApiKey dfApiKey)
    {
        return dfApiKeyMapper.selectDfApiKeyList(dfApiKey);
    }

    /**
     * 新增API
     * 
     * @param dfApiKey API
     * @return 结果
     */
    @Override
    public int insertDfApiKey(DfApiKey dfApiKey)
    {
        dfApiKey.setCreateTime(DateUtils.getNowDate());
        return dfApiKeyMapper.insertDfApiKey(dfApiKey);
    }

    /**
     * 修改API
     * 
     * @param dfApiKey API
     * @return 结果
     */
    @Override
    public int updateDfApiKey(DfApiKey dfApiKey)
    {
        return dfApiKeyMapper.updateDfApiKey(dfApiKey);
    }

    /**
     * 批量删除API
     * 
     * @param ids 需要删除的API主键
     * @return 结果
     */
    @Override
    public int deleteDfApiKeyByIds(Long[] ids)
    {
        return dfApiKeyMapper.deleteDfApiKeyByIds(ids);
    }

    /**
     * 删除API信息
     * 
     * @param id API主键
     * @return 结果
     */
    @Override
    public int deleteDfApiKeyById(Long id)
    {
        return dfApiKeyMapper.deleteDfApiKeyById(id);
    }
}
