package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.SysUserFollow;

/**
 * 我的关注Service接口
 *
 * @author ruoyi
 * @date 2021-01-24
 */
public interface ISysUserFollowService
{
    /**
     * 查询我的关注
     *
     * @param id 我的关注ID
     * @return 我的关注
     */
    public SysUserFollow selectSysUserFollowById(Long id);

    /**
     * 查询我的关注列表
     *
     * @param sysUserFollow 我的关注
     * @return 我的关注集合
     */
    public List<SysUserFollow> selectSysUserFollowList(SysUserFollow sysUserFollow);

    /**
     * 新增我的关注
     *
     * @param sysUserFollow 我的关注
     * @return 结果
     */
    public int insertSysUserFollow(SysUserFollow sysUserFollow);

    /**
     * 修改我的关注
     *
     * @param sysUserFollow 我的关注
     * @return 结果
     */
    public int updateSysUserFollow(SysUserFollow sysUserFollow);

    /**
     * 批量删除我的关注
     *
     * @param ids 需要删除的我的关注ID
     * @return 结果
     */
    public int deleteSysUserFollowByIds(Long[] ids);

    /**
     * 删除我的关注信息
     *
     * @param id 我的关注ID
     * @return 结果
     */
    public int deleteSysUserFollowById(Long id);



    /**
     * 查看我的关注列表
     *
     * @param  userId
     * @return
     */
    List<Map<String,Object>> listFollwUser(Long userId);

    /**
     * 查看我的粉丝列表
     *
     * @param  userId
     * @return
     */
    List<Map<String,Object>> listFansUser(Long userId);
}
