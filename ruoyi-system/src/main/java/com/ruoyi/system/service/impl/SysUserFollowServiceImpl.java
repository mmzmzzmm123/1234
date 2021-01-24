package com.ruoyi.system.service.impl;

import java.util.*;

import com.ruoyi.common.core.redis.RedisKey;
import com.ruoyi.common.core.redis.RedisUtil;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysUserFollowMapper;
import com.ruoyi.system.domain.SysUserFollow;
import com.ruoyi.system.service.ISysUserFollowService;

/**
 * 我的关注Service业务层处理
 *
 * @author ruoyi
 * @date 2021-01-24
 */
@Service
public class SysUserFollowServiceImpl implements ISysUserFollowService
{
    @Autowired
    private SysUserFollowMapper sysUserFollowMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询我的关注
     *
     * @param id 我的关注ID
     * @return 我的关注
     */
    @Override
    public SysUserFollow selectSysUserFollowById(Long id)
    {
        return sysUserFollowMapper.selectSysUserFollowById(id);
    }

    /**
     * 查询我的关注列表
     *
     * @param sysUserFollow 我的关注
     * @return 我的关注
     */
    @Override
    public List<SysUserFollow> selectSysUserFollowList(SysUserFollow sysUserFollow)
    {
        return sysUserFollowMapper.selectSysUserFollowList(sysUserFollow);
    }

    /**
     * 新增我的关注
     *
     * @param sysUserFollow 我的关注
     * @return 结果
     */
    @Override
    public int insertSysUserFollow(SysUserFollow sysUserFollow)
    {
        sysUserFollow.setCreateTime(DateUtils.getNowDate());
        return sysUserFollowMapper.insertSysUserFollow(sysUserFollow);
    }

    /**
     * 修改我的关注
     *
     * @param sysUserFollow 我的关注
     * @return 结果
     */
    @Override
    public int updateSysUserFollow(SysUserFollow sysUserFollow)
    {
        return sysUserFollowMapper.updateSysUserFollow(sysUserFollow);
    }

    /**
     * 批量删除我的关注
     *
     * @param ids 需要删除的我的关注ID
     * @return 结果
     */
    @Override
    public int deleteSysUserFollowByIds(Long[] ids)
    {
        return sysUserFollowMapper.deleteSysUserFollowByIds(ids);
    }

    /**
     * 删除我的关注信息
     *
     * @param id 我的关注ID
     * @return 结果
     */
    @Override
    public int deleteSysUserFollowById(Long id)
    {
        return sysUserFollowMapper.deleteSysUserFollowById(id);
    }


    /**
     * 查看我的关注列表
     *
     * @param  userId
     * @return
     */
    @Override
    public List<Map<String,Object>> listFollwUser(Long userId) {

        return   sysUserFollowMapper.listFollwUserByUid(userId);
    }



    /**
     *
     * redis 分页计算
     * @param
     * @return
     */
    public static HashMap<String, Long> redisPage(Long pageNum, Long pageSize) {
        HashMap<String, Long> map = new HashMap<String, Long>();
        if (pageNum == 1L) {
            pageNum = 0L;
            pageSize = pageSize - 1;
        } else {
            pageNum = pageNum * pageSize;
            pageSize = (pageNum + 1) * pageSize - 1;
        }
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return map;
    }

//    /**
//     * 查看我的关注列表
//     *
//     * @param  userId
//     * @return
//     */
//    @Override
//    public List<String> listFollwUser(Long userId, Long pageNum, Long pageSize) {
//        //  pageNum=5&pageSize=15  第几页 每页 15条    pageNum 起 &pageSize 结束
//        HashMap<String, Long> map = redisPage(pageNum,pageSize);
//        List<String> list = redisUtil.lRange(RedisKey.USER_FOLLOW + userId ,map.get("pageNum"), map.get("pageSize"));
//        if (null == list || list.isEmpty()) {
//            //查库 --> 无序
//            list = sysUserFollowMapper.listFollwUser(userId);
//            if (null != list && !list.isEmpty()) {
//                //list<Long> 转 数组
//                redisUtil.lRightPushAll(RedisKey.USER_FOLLOW + userId, list.stream().toArray(String[]::new));
//            }
//        }
//        return list;
//    }

//    @Override
//    public List<String> listFansUser(Long userId) {
//        return null;
//    }

    @Override
    public List<Map<String, Object>> listFansUser(Long userId) {
        return sysUserFollowMapper.listFansUserByUid(userId);
    }
}
