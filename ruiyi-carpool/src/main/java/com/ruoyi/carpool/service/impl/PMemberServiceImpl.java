package com.ruoyi.carpool.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.carpool.mapper.PMemberMapper;
import com.ruoyi.carpool.domain.PMember;
import com.ruoyi.carpool.service.IPMemberService;

/**
 * 微信注册用户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-25
 */
@Service
public class PMemberServiceImpl implements IPMemberService 
{
    @Autowired
    private PMemberMapper pMemberMapper;

    /**
     * 查询微信注册用户信息
     * 
     * @param id 微信注册用户信息主键
     * @return 微信注册用户信息
     */
    @Override
    public PMember selectPMemberById(Integer id)
    {
        return pMemberMapper.selectPMemberById(id);
    }

    /**
     * 查询微信注册用户信息列表
     * 
     * @param pMember 微信注册用户信息
     * @return 微信注册用户信息
     */
    @Override
    public List<PMember> selectPMemberList(PMember pMember)
    {
        return pMemberMapper.selectPMemberList(pMember);
    }

    /**
     * 新增微信注册用户信息
     * 
     * @param pMember 微信注册用户信息
     * @return 结果
     */
    @Override
    public int insertPMember(PMember pMember)
    {
        pMember.setCreateTime(DateUtils.getNowDate());
        return pMemberMapper.insertPMember(pMember);
    }

    /**
     * 修改微信注册用户信息
     * 
     * @param pMember 微信注册用户信息
     * @return 结果
     */
    @Override
    public int updatePMember(PMember pMember)
    {
        pMember.setUpdateTime(DateUtils.getNowDate());
        return pMemberMapper.updatePMember(pMember);
    }

    /**
     * 批量删除微信注册用户信息
     * 
     * @param ids 需要删除的微信注册用户信息主键
     * @return 结果
     */
    @Override
    public int deletePMemberByIds(Integer[] ids)
    {
        return pMemberMapper.deletePMemberByIds(ids);
    }

    /**
     * 删除微信注册用户信息信息
     * 
     * @param id 微信注册用户信息主键
     * @return 结果
     */
    @Override
    public int deletePMemberById(Integer id)
    {
        return pMemberMapper.deletePMemberById(id);
    }

    @Override
    public PMember selectPMemberByOpenId(String OpenId)
    {
        return pMemberMapper.selectPMemberByOpenId(OpenId);
    }
}
