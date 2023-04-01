package com.geek.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geek.system.mapper.SysOssMapper;
import com.geek.system.domain.SysOss;
import com.geek.system.service.ISysOssService;

/**
 * 文件上传Service业务层处理
 * 
 * @author xuek
 * @date 2023-04-01
 */
@Service
public class SysOssServiceImpl implements ISysOssService {
    @Autowired
    private SysOssMapper sysOssMapper;

    /**
     * 查询文件上传
     * 
     * @param id 文件上传主键
     * @return 文件上传
     */
    @Override
    public SysOss selectSysOssById(Long id){
        return sysOssMapper.selectSysOssById(id);
    }

    /**
     * 查询文件上传列表
     * 
     * @param sysOss 文件上传
     * @return 文件上传
     */
    @Override
    public List<SysOss> selectSysOssList(SysOss sysOss){
        return sysOssMapper.selectSysOssList(sysOss);
    }

    /**
     * 新增文件上传
     * 
     * @param sysOss 文件上传
     * @return 结果
     */
    @Override
    public int insertSysOss(SysOss sysOss){
        return sysOssMapper.insertSysOss(sysOss);
    }

    /**
     * 修改文件上传
     * 
     * @param sysOss 文件上传
     * @return 结果
     */
    @Override
    public int updateSysOss(SysOss sysOss){
        return sysOssMapper.updateSysOss(sysOss);
    }

    /**
     * 批量删除文件上传
     * 
     * @param ids 需要删除的文件上传主键
     * @return 结果
     */
    @Override
    public int deleteSysOssByIds(Long[] ids){
        return sysOssMapper.deleteSysOssByIds(ids);
    }

    /**
     * 删除文件上传信息
     * 
     * @param id 文件上传主键
     * @return 结果
     */
    @Override
    public int deleteSysOssById(Long id){
        return sysOssMapper.deleteSysOssById(id);
    }
}
