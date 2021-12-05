package com.ruoyi.carpool.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.carpool.mapper.PBlacklistMapper;
import com.ruoyi.carpool.domain.PBlacklist;
import com.ruoyi.carpool.service.IPBlacklistService;

/**
 * 黑名单Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
@Service
public class PBlacklistServiceImpl implements IPBlacklistService 
{
    @Autowired
    private PBlacklistMapper pBlacklistMapper;

    /**
     * 查询黑名单
     * 
     * @param id 黑名单主键
     * @return 黑名单
     */
    @Override
    public PBlacklist selectPBlacklistById(Long id)
    {
        return pBlacklistMapper.selectPBlacklistById(id);
    }

    /**
     * 查询黑名单列表
     * 
     * @param pBlacklist 黑名单
     * @return 黑名单
     */
    @Override
    public List<PBlacklist> selectPBlacklistList(PBlacklist pBlacklist)
    {
        /*查询黑名单列表*/
        List<PBlacklist> pBlacklists = new ArrayList<>();
        if("1".equals(pBlacklist.getType())){
            pBlacklists = pBlacklistMapper.selectPBlacklistListPassenger(pBlacklist);
        }else if ("2".equals(pBlacklist.getType())){
            pBlacklists = pBlacklistMapper.selectPBlacklistListDriver(pBlacklist);
        }else {
            pBlacklists = pBlacklistMapper.selectPBlacklistList(pBlacklist);
        }
        return pBlacklists;
    }

    /**
     * 新增黑名单
     *
     * @param pBlacklist 黑名单
     * @return 结果
     */
    @Override
    public int insertPBlacklist(PBlacklist pBlacklist)
    {
        /*添加黑名单*/
        String type = pBlacklist.getType();
        pBlacklist.setUpdateTime(DateUtils.getNowDate());
        if("1".equals(type)){
            return pBlacklistMapper.insertPBlacklistPassenger(pBlacklist);
        }else {
            return pBlacklistMapper.insertPBlacklistDriver(pBlacklist);
        }
    }

    /**
     * 修改黑名单
     * 
     * @param pBlacklist 黑名单
     * @return 结果
     */
    @Override
    public int updatePBlacklist(PBlacklist pBlacklist)
    {
        pBlacklist.setUpdateTime(DateUtils.getNowDate());
        return pBlacklistMapper.updatePBlacklist(pBlacklist);
    }

    /**
     * 批量删除黑名单
     * 
     * @param userIDs 需要删除的黑名单主键
     * @return 结果
     */
    @Override
    public int deletePBlacklistByIds(String[] userIDs)
    {
        PBlacklist pBlacklist = new PBlacklist();
        int flag = 0 ;
        for (int i = 0; i < userIDs.length; i++) {
            pBlacklist.setUserId(userIDs[i]);
            List<PBlacklist> pBlacklists = pBlacklistMapper.selectPBlacklistList(pBlacklist);
            if(pBlacklists.size() > 0){
                if("1".equals(pBlacklists.get(0).getType())){
                    flag = pBlacklistMapper.deletePBlacklistByIdPassenger(pBlacklists.get(0).getUserId());
                }else {
                    flag = pBlacklistMapper.deletePBlacklistByIdDriver(pBlacklists.get(0).getUserId());
                }
            }
        }
        return flag;
    }

    /**
     * 删除黑名单信息
     * 
     * @param id 黑名单主键
     * @return 结果
     */
    @Override
    public int deletePBlacklistById(Long id)
    {
        return pBlacklistMapper.deletePBlacklistById(id);
    }
}
