package com.ruoyi.system.service;


import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.PasswordConfig;
import com.ruoyi.system.domain.SysConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author boger
 * @since 2022-05-18
 */
public interface ISysPasswordService  {

    /**
     * 设置passwordConfig
     * @param passwordConfig
     */
    void setPasswordConfig(PasswordConfig passwordConfig);

    /**
     * 验证密码强度，如果不符合强度要求，则报出ServiceException
     * @param password
     */
    public void validatePasswordRule(String password) throws ServiceException;

    /**
     * 验证密码规则，如果不符合规则将报出ServiceException
     * @param password
     * @param passwordConfig 密码强度配置
     */
    public void validatePasswordRule(String password, PasswordConfig passwordConfig ) throws ServiceException;

    /**
     * 查询密码强度配置信息
     * @return 参数配置信息
     */
    public PasswordConfig selectConfig();

    /**
     * 保存密码强度配置
     *
     * @param config 密码强度配置信息
     * @return 结果
     */
    public int saveConfig(PasswordConfig config);


    /**
     * 加载参数缓存数据
     */
    public void CachePasswordConfig();

    /**
     * 清空参数缓存数据
     */
    public void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
    public void resetConfigCache();

}
