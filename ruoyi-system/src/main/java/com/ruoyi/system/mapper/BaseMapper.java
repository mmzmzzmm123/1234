package com.ruoyi.system.mapper;

/**
 * mapper公共方法
 *
 * @author songweiwei
 */
public interface BaseMapper
{
    /**
     * 获取上次添加的id
     *
     * @return 刚刚insert的id
     */
    Long getLastInsertId();
}
