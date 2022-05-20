package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.PasswordConfig;

/**
 * 密码策略Mapper接口
 * 
 * @author boger
 * @date 2022-05-15
 */
public interface PasswordConfigMapper 
{
    /**
     * 查询密码策略
     * 
     * @param id 密码策略主键
     * @return 密码策略
     */
    public PasswordConfig selectPasswordConfigById(Long id);

    /**
     * 查询密码策略列表
     * 
     * @param passwordConfig 密码策略
     * @return 密码策略集合
     */
    public List<PasswordConfig> selectPasswordConfigList(PasswordConfig passwordConfig);

    /**
     * 新增密码策略
     * 
     * @param passwordConfig 密码策略
     * @return 结果
     */
    public int insertPasswordConfig(PasswordConfig passwordConfig);

    /**
     * 修改密码策略
     * 
     * @param passwordConfig 密码策略
     * @return 结果
     */
    public int updatePasswordConfig(PasswordConfig passwordConfig);

    /**
     * 删除密码策略
     * 
     * @param id 密码策略主键
     * @return 结果
     */
    public int deletePasswordConfigById(Long id);

    /**
     * 批量删除密码策略
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePasswordConfigByIds(Long[] ids);
}
