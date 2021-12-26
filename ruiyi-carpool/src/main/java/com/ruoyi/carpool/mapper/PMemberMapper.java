package com.ruoyi.carpool.mapper;

import java.util.List;
import com.ruoyi.carpool.domain.PMember;

/**
 * 微信注册用户信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-25
 */
public interface PMemberMapper 
{
    /**
     * 查询微信注册用户信息
     * 
     * @param id 微信注册用户信息主键
     * @return 微信注册用户信息
     */
    public PMember selectPMemberById(Integer id);

    /**
     * 查询微信注册用户信息列表
     * 
     * @param pMember 微信注册用户信息
     * @return 微信注册用户信息集合
     */
    public List<PMember> selectPMemberList(PMember pMember);

    /**
     * 新增微信注册用户信息
     * 
     * @param pMember 微信注册用户信息
     * @return 结果
     */
    public int insertPMember(PMember pMember);

    /**
     * 修改微信注册用户信息
     * 
     * @param pMember 微信注册用户信息
     * @return 结果
     */
    public int updatePMember(PMember pMember);

    /**
     * 删除微信注册用户信息
     * 
     * @param id 微信注册用户信息主键
     * @return 结果
     */
    public int deletePMemberById(Integer id);

    /**
     * 批量删除微信注册用户信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePMemberByIds(Integer[] ids);

    /**
     * 查询微信注册用户信息
     *
     * @param OpenId 微信注册用户信息主键
     * @return 微信注册用户信息
     */
    public PMember selectPMemberByOpenId(String OpenId);

}
