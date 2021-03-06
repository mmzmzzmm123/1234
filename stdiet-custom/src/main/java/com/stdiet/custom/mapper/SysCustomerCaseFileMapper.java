package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysCustomerCaseFile;
import org.apache.ibatis.annotations.Param;

/**
 * 客户案例对应文件管理Mapper接口
 *
 * @author xiezhijun
 * @date 2021-03-04
 */
public interface SysCustomerCaseFileMapper
{
    /**
     * 查询客户案例对应文件管理
     *
     * @param id 客户案例对应文件管理ID
     * @return 客户案例对应文件管理
     */
    public SysCustomerCaseFile selectSysCustomerCaseFileById(Long id);

    /**
     * 查询客户案例对应文件管理列表
     *
     * @param sysCustomerCaseFile 客户案例对应文件管理
     * @return 客户案例对应文件管理集合
     */
    public List<SysCustomerCaseFile> selectSysCustomerCaseFileList(SysCustomerCaseFile sysCustomerCaseFile);

    /**
     * 新增客户案例对应文件管理
     *
     * @param sysCustomerCaseFile 客户案例对应文件管理
     * @return 结果
     */
    public int insertSysCustomerCaseFile(SysCustomerCaseFile sysCustomerCaseFile);

    /**
     * 修改客户案例对应文件管理
     *
     * @param sysCustomerCaseFile 客户案例对应文件管理
     * @return 结果
     */
    public int updateSysCustomerCaseFile(SysCustomerCaseFile sysCustomerCaseFile);

    /**
     * 删除客户案例对应文件管理
     *
     * @param id 客户案例对应文件管理ID
     * @return 结果
     */
    public int deleteSysCustomerCaseFileById(Long id);

    /**
     * 批量删除客户案例对应文件管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCustomerCaseFileByIds(Long[] ids);

    /**
     * 根据案例ID删除该案例下的所有文件记录
     * @param caseId
     */
    int deleteSysCustomerCaseFileByCaseId(@Param("caseId")Long caseId);

    /**
     * 根据案例ID批量删除该案例下的所有文件记录
     * @param caseIds
     */
    int deleteSysCustomerCaseFileByCaseIds(Long[] caseIds);

    /**
     * 批量插入案例文件记录
     * @param list
     */
    int insertBatch(List<SysCustomerCaseFile> list);

    /**
     * 根据案例Id查询案例文件列表
     * @param caseId
     * @return
     */
    List<SysCustomerCaseFile> selectSysCustomerCaseFileListByCaseId(@Param("caseId") Long caseId);
}