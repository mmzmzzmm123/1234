package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysCustomerCase;
import com.stdiet.custom.dto.response.CustomerCaseResponse;
import org.apache.ibatis.annotations.Param;

/**
 * 客户案例管理Mapper接口
 *
 * @author xiezhijun
 * @date 2021-03-04
 */
public interface SysCustomerCaseMapper
{
    /**
     * 查询客户案例管理
     *
     * @param id 客户案例管理ID
     * @return 客户案例管理
     */
    public SysCustomerCase selectSysCustomerCaseById(Long id);

    /**
     * 查询客户案例管理列表
     *
     * @param sysCustomerCase 客户案例管理
     * @return 客户案例管理集合
     */
    public List<SysCustomerCase> selectSysCustomerCaseList(SysCustomerCase sysCustomerCase);

    /**
     * 新增客户案例管理
     *
     * @param sysCustomerCase 客户案例管理
     * @return 结果
     */
    public int insertSysCustomerCase(SysCustomerCase sysCustomerCase);

    /**
     * 修改客户案例管理
     *
     * @param sysCustomerCase 客户案例管理
     * @return 结果
     */
    public int updateSysCustomerCase(SysCustomerCase sysCustomerCase);

    /**
     * 删除客户案例管理
     *
     * @param id 客户案例管理ID
     * @return 结果
     */
    public int deleteSysCustomerCaseById(Long id);

    /**
     * 批量删除客户案例管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCustomerCaseByIds(Long[] ids);

    /**
     * 查询微信小程序上展示的客户案例
     * @param sysCustomerCase
     * @return
     */
    public List<CustomerCaseResponse> getWxCustomerCaseList(SysCustomerCase sysCustomerCase);

    /**
     * 更新微信展示状态
     * @param wxShow 是否展示  0不展示 1展示
     * @param ids id数组
     * @return
     */
    public int updateWxShowByIds(@Param("wxShow")Integer wxShow, @Param("array") Long[] ids);
}