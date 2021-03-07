package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.BusGrzfxx;

/**
 * 工人证书信息Service接口
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public interface IBusGrzfxxService 
{
    /**
     * 查询工人证书信息
     * 
     * @param id 工人证书信息ID
     * @return 工人证书信息
     */
    public BusGrzfxx selectBusGrzfxxById(Long id);

    /**
     * 查询工人证书信息列表
     * 
     * @param busGrzfxx 工人证书信息
     * @return 工人证书信息集合
     */
    public List<BusGrzfxx> selectBusGrzfxxList(BusGrzfxx busGrzfxx);

    /**
     * 新增工人证书信息
     * 
     * @param busGrzfxx 工人证书信息
     * @return 结果
     */
    public int insertBusGrzfxx(BusGrzfxx busGrzfxx);

    /**
     * 修改工人证书信息
     * 
     * @param busGrzfxx 工人证书信息
     * @return 结果
     */
    public int updateBusGrzfxx(BusGrzfxx busGrzfxx);

    /**
     * 批量删除工人证书信息
     * 
     * @param ids 需要删除的工人证书信息ID
     * @return 结果
     */
    public int deleteBusGrzfxxByIds(Long[] ids);

    /**
     * 删除工人证书信息信息
     * 
     * @param id 工人证书信息ID
     * @return 结果
     */
    public int deleteBusGrzfxxById(Long id);

	List<BusGrzfxx> selectByCbsIdAndGrId(Long cbsId, Long grId);

	int deleteByCbsIdAndGrId(Long cbsId, Long grId);
}
