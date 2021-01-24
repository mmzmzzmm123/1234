package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.SysUserFollow;

/**
 * 我的关注Mapper接口
 *
 * @author ruoyi
 * @date 2021-01-24
 */
public interface SysUserFollowMapper
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
     * 删除我的关注
     *
     * @param id 我的关注ID
     * @return 结果
     */
    public int deleteSysUserFollowById(Long id);

    /**
     * 批量删除我的关注
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserFollowByIds(Long[] ids);

    /**
     * 查看我的关注列表  删
     *
     * @param  userId
     * @return
     */
    List<String> listFollwUser(Long userId);

    /**
     * 查看我的关注列表
     *
     * @param  userId
     * @return
     */
    List<Map<String,Object>> listFollwUserByUid(Long userId);

    /**
     * 查看我的粉丝列表
     *
     * @param  userId
     * @return
     */
    List<Map<String, Object>> listFansUserByUid(Long userId);
}
