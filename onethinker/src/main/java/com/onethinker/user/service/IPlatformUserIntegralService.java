package com.onethinker.user.service;

import com.onethinker.user.domain.PlatformUserIntegral;
import com.onethinker.user.domain.PlatformUserIntegralHistory;
import com.onethinker.user.dto.PlatformUserIntegralReqDTO;
import com.onethinker.user.dto.PlatformUserIntegralResDTO;

import java.util.List;

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
     * 查询用户积分明细
     * @param reqDTO
     * @return
     */
    List<PlatformUserIntegralHistory> queryIntegralDetail(PlatformUserIntegralReqDTO reqDTO);

    /**
     * 积分提现
     * @param reqDTO
     * @return
     */
    int withdrawalIntegral(PlatformUserIntegralReqDTO reqDTO);
}
