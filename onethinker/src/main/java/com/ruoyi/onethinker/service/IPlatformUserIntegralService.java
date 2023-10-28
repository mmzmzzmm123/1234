package com.ruoyi.onethinker.service;

import java.util.List;
import com.ruoyi.onethinker.domain.PlatformUserIntegral;
import com.ruoyi.onethinker.dto.PlatformUserIntegralReqDTO;
import com.ruoyi.onethinker.dto.PlatformUserIntegralResDTO;

/**
 * 平台用户积分Service接口
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
public interface IPlatformUserIntegralService {
    /**
     * 查询平台用户积分
     *
     * @param id 平台用户积分主键
     * @return 平台用户积分
     */
    public PlatformUserIntegral selectPlatformUserIntegralById(Long id);

    /**
     * 查询平台用户积分列表
     *
     * @param platformUserIntegral 平台用户积分
     * @return 平台用户积分集合
     */
    public List<PlatformUserIntegralResDTO> selectPlatformUserIntegralList(PlatformUserIntegral platformUserIntegral);

    /**
     * 新增平台用户积分
     *
     * @param reqDTO 平台用户积分
     * @return 结果
     */
    public int insertPlatformUserIntegral(PlatformUserIntegralReqDTO reqDTO);

    /**
     * 修改平台用户积分
     *
     * @param platformUserIntegral 平台用户积分
     * @return 结果
     */
    public int updatePlatformUserIntegral(PlatformUserIntegral platformUserIntegral);

    /**
     * 批量删除平台用户积分
     *
     * @param ids 需要删除的平台用户积分主键集合
     * @return 结果
     */
    public int deletePlatformUserIntegralByIds(Long[] ids);

    /**
     * 删除平台用户积分信息
     *
     * @param id 平台用户积分主键
     * @return 结果
     */
    public int deletePlatformUserIntegralById(Long id);
}
