package com.gox.basic.service.impl;

import com.gox.basic.domain.ArchivalCodeNum;
import com.gox.basic.domain.ArchivalCodeSetting;
import com.gox.basic.mapper.ArchivalCodeSettingMapper;
import com.gox.basic.service.IArchivalCodeSettingService;
import com.gox.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 档号设置Service业务层处理
 *
 * @author gox
 * @date 2021-01-23
 */
@Service
public class ArchivalCodeSettingServiceImpl implements IArchivalCodeSettingService {
    @Autowired
    private ArchivalCodeSettingMapper archivalCodeSettingMapper;
    @Autowired
    private RedisCache redisCache;
    @PostConstruct
    public void init(){
        List<ArchivalCodeSetting> list = selectArchivalCodeSettingList(new ArchivalCodeSetting());
        Map<String,Object> m = new HashMap<>();
        list.forEach(acs->m.put(acs.getNodeId()+":"+acs.getDeptId(),acs));
        redisCache.setCacheMap("acs",m);
        Map<String,Object> m1 = new HashMap<>();
        List<ArchivalCodeNum> archival = archivalCodeSettingMapper.selectAllNum();
        archival.forEach(acn->m1.put(acn.getArchival(),acn.getNum()));
        redisCache.setCacheMap("archivalCode",m1);
    }
    /**
     * 查询档号设置
     * @param id 档号设置ID
     * @return 档号设置
     */
    @Override
    public ArchivalCodeSetting selectArchivalCodeSettingById(Long id) {
        return archivalCodeSettingMapper.selectArchivalCodeSettingById(id);
    }
    @Override
    public int updateArchivalCodeNum(String archival, int num){
        return archivalCodeSettingMapper.updateArchivalNum(archival,num);
    }
    @Override
    public int insertArchivalCodeNum(ArchivalCodeNum archivalCodeNum){
        return archivalCodeSettingMapper.insertArchivalNum(archivalCodeNum);
    }
    @Override
    public ArchivalCodeSetting selectArchivalCsByNodeIdAndDeptId(Long nodeId, Long deptId) {
        return archivalCodeSettingMapper.selectArchivalCsByNodeIdAndDeptId(nodeId,deptId);
    }

    /**
     * 查询档号设置列表
     * @param archivalCodeSetting 档号设置
     * @return 档号设置
     */
    @Override
    public List<ArchivalCodeSetting> selectArchivalCodeSettingList(ArchivalCodeSetting archivalCodeSetting) {
        return archivalCodeSettingMapper.selectArchivalCodeSettingList(archivalCodeSetting);
    }

    /**
     * 新增档号设置
     *
     * @param archivalCodeSetting 档号设置
     * @return 结果
     */
    @Override
    public int insertArchivalCodeSetting(ArchivalCodeSetting archivalCodeSetting) {
        return archivalCodeSettingMapper.insertArchivalCodeSetting(archivalCodeSetting);
    }

    /**
     * 修改档号设置
     *
     * @param archivalCodeSetting 档号设置
     * @return 结果
     */
    @Override
    public int updateArchivalCodeSetting(ArchivalCodeSetting archivalCodeSetting) {
        return archivalCodeSettingMapper.updateArchivalCodeSetting(archivalCodeSetting);
    }

    /**
     * 批量删除档号设置
     *
     * @param ids 需要删除的档号设置ID
     * @return 结果
     */
    @Override
    public int deleteArchivalCodeSettingByIds(Long[] ids) {
        return archivalCodeSettingMapper.deleteArchivalCodeSettingByIds(ids);
    }

    /**
     * 删除档号设置信息
     * @param id 档号设置ID
     * @return 结果
     */
    @Override
    public int deleteArchivalCodeSettingById(Long id) {
        return archivalCodeSettingMapper.deleteArchivalCodeSettingById(id);
    }
}
