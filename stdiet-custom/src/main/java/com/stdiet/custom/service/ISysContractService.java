package com.stdiet.custom.service;

import java.util.List;

import com.stdiet.custom.domain.SysContract;
import com.stdiet.custom.page.PdfProcessInfo;

/**
 * 合同Service接口
 *
 * @author wonder
 * @date 2020-10-23
 */
public interface ISysContractService {
    /**
     * 查询合同
     *
     * @param id 合同ID
     * @return 合同
     */
    public SysContract selectSysContractById(Long id);

    /**
     * 查询合同列表
     *
     * @param sysContract 合同
     * @return 合同集合
     */
    public List<SysContract> selectSysContractList(SysContract sysContract);

    /**
     * 新增合同
     *
     * @param sysContract 合同
     * @return 结果
     */
    public int insertSysContract(SysContract sysContract);

    /**
     * 修改合同
     *
     * @param sysContract 合同
     * @return 结果
     */
    public int updateSysContract(SysContract sysContract);

    /**
     * 批量删除合同
     *
     * @param ids 需要删除的合同ID
     * @return 结果
     */
    public int deleteSysContractByIds(Long[] ids);

    /**
     * 删除合同信息
     *
     * @param id 合同ID
     * @return 结果
     */
    public int deleteSysContractById(Long id);

    public PdfProcessInfo signContract(SysContract sysContract);
}
