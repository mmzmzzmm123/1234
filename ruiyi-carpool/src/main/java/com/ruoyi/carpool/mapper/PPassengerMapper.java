package com.ruoyi.carpool.mapper;

import java.util.List;
import com.ruoyi.carpool.domain.PPassenger;
import org.springframework.security.core.parameters.P;

/**
 * 乘客信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public interface PPassengerMapper 
{
    /**
     * 查询乘客信息
     * 
     * @param id 乘客信息主键
     * @return 乘客信息
     */
    public PPassenger selectPPassengerById(Long id);

    /**
     * 查询乘客信息列表
     * 
     * @param pPassenger 乘客信息
     * @return 乘客信息集合
     */
    public List<PPassenger> selectPPassengerList(PPassenger pPassenger);

    /**
     * 新增乘客信息
     * 
     * @param pPassenger 乘客信息
     * @return 结果
     */
    public int insertPPassenger(PPassenger pPassenger);

    /**
     * 修改乘客信息
     * 
     * @param pPassenger 乘客信息
     * @return 结果
     */
    public int updatePPassenger(PPassenger pPassenger);

    /**
     * 删除乘客信息
     * 
     * @param id 乘客信息主键
     * @return 结果
     */
    public int deletePPassengerById(Long id);

    /**
     * 批量删除乘客信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePPassengerByIds(Long[] ids);

    /**
     * 查询乘客详情
     * @param pPassenger
     * @return
     */
    public PPassenger queryUserInfo(PPassenger pPassenger);

    /**
     * 通过openId查询乘客的信息
     * @param openId
     * @return
     */
    public PPassenger selectPPassengerByOpenId(String openId);

    /**
     * 通过openId更新身份
     * @param openId
     * @return
     */
    public int updatePPassengerApplystate(String openId);
}
