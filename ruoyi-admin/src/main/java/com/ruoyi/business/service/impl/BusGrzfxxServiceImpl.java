package com.ruoyi.business.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.business.domain.BusGrzfxx;
import com.ruoyi.business.mapper.BusGrzfxxMapper;
import com.ruoyi.business.service.IBusGrzfxxService;

/**
 * 工人证书信息Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-21
 */
@Service
public class BusGrzfxxServiceImpl implements IBusGrzfxxService 
{
    @Autowired
    private BusGrzfxxMapper busGrzfxxMapper;

    /**
     * 查询工人证书信息
     * 
     * @param id 工人证书信息ID
     * @return 工人证书信息
     */
    @Override
    public BusGrzfxx selectBusGrzfxxById(Long id)
    {
        return busGrzfxxMapper.selectBusGrzfxxById(id);
    }

    /**
     * 查询工人证书信息列表
     * 
     * @param busGrzfxx 工人证书信息
     * @return 工人证书信息
     */
    @Override
    public List<BusGrzfxx> selectBusGrzfxxList(BusGrzfxx busGrzfxx)
    {
        return busGrzfxxMapper.selectBusGrzfxxList(busGrzfxx);
    }

    /**
     * 新增工人证书信息
     * 
     * @param busGrzfxx 工人证书信息
     * @return 结果
     */
    @Override
    public int insertBusGrzfxx(BusGrzfxx busGrzfxx)
    {
        return busGrzfxxMapper.insertBusGrzfxx(busGrzfxx);
    }

    /**
     * 修改工人证书信息
     * 
     * @param busGrzfxx 工人证书信息
     * @return 结果
     */
    @Override
    public int updateBusGrzfxx(BusGrzfxx busGrzfxx)
    {
        return busGrzfxxMapper.updateBusGrzfxx(busGrzfxx);
    }

    /**
     * 批量删除工人证书信息
     * 
     * @param ids 需要删除的工人证书信息ID
     * @return 结果
     */
    @Override
    public int deleteBusGrzfxxByIds(Long[] ids)
    {
        return busGrzfxxMapper.deleteBusGrzfxxByIds(ids);
    }

    /**
     * 删除工人证书信息信息
     * 
     * @param id 工人证书信息ID
     * @return 结果
     */
    @Override
    public int deleteBusGrzfxxById(Long id)
    {
        return busGrzfxxMapper.deleteBusGrzfxxById(id);
    }
    
	/**
	 * 查询承包商资质信息
	 * 
	 * @param id
	 *            承包商资质信息ID
	 * @return 承包商资质信息
	 */
	@Override
	public List<BusGrzfxx> selectByCbsIdAndGrId(Long cbsId, Long grId) {
		return busGrzfxxMapper.selectByCbsIdAndGrId(cbsId, grId);
	}
	
	/**
	 * 查询承包商资质信息
	 * 
	 * @param id
	 *            承包商资质信息ID
	 * @return 承包商资质信息
	 */
	@Transactional
	@Override
	public int deleteByCbsIdAndGrId(Long cbsId, Long grId) {
		List<BusGrzfxx> busCbszzxxList = selectByCbsIdAndGrId(cbsId, grId);
		if(busCbszzxxList.isEmpty()) {
			return 0;
		}
		List<Long> ids = busCbszzxxList.stream().map(BusGrzfxx::getId).collect(Collectors.toList());
		return deleteBusGrzfxxByIds(ids.toArray(new Long[ids.size()]));
	}
}
