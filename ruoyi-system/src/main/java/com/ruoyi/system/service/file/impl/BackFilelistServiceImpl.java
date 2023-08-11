package com.ruoyi.system.service.file.impl;

import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.system.domain.BackFilelist;
import com.ruoyi.system.mapper.BackFilelistMapper;
import com.ruoyi.system.service.file.IBackFilelistService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 已上传文件列表Service业务层处理
 */
@Service
public class BackFilelistServiceImpl implements IBackFilelistService
{
    @Autowired
    private BackFilelistMapper backFilelistMapper;

    @Value("${ruoyi.profile}")
    private String filePath;

    private final static String folderPath = "/file";
    /**
     * 查询已上传文件列表
     * 
     * @param id 已上传文件列表ID
     * @return 已上传文件列表
     */
    @Override
    public BackFilelist selectBackFilelistById(Long id)
    {
        return backFilelistMapper.selectBackFilelistById(id);
    }

    /**
     * 查询已上传文件列表列表
     * 
     * @param backFilelist 已上传文件列表
     * @return 已上传文件列表
     */
    @Override
    public List<BackFilelist> selectBackFilelistList(BackFilelist backFilelist)
    {
        return backFilelistMapper.selectBackFilelistList(backFilelist);
    }

    /**
     * 新增已上传文件列表
     * 
     * @param backFilelist 已上传文件列表
     * @return 结果
     */
    @Override
    public int insertBackFilelist(BackFilelist backFilelist)
    {
        return backFilelistMapper.insertBackFilelist(backFilelist);
    }

    /**
     * 修改已上传文件列表
     * 
     * @param backFilelist 已上传文件列表
     * @return 结果
     */
    @Override
    public int updateBackFilelist(BackFilelist backFilelist)
    {
        return backFilelistMapper.updateBackFilelist(backFilelist);
    }

    /**
     * 批量删除已上传文件列表
     * 
     * @param ids 需要删除的已上传文件列表ID
     * @return 结果
     */
    @Override
    public int deleteBackFilelistByIds(Long[] ids)
    {
        ArrayList<Boolean> objects = Lists.newArrayList();
        //先删除文件再删除对应的表记录
        for (Long id : ids) {
            BackFilelist backFilelist = backFilelistMapper.selectBackFilelistById(id);
            String fileFolder = filePath + folderPath + "/" + backFilelist.getIdentifier();
            FileUtils.deleteFolder(new File(fileFolder));
        }
        return backFilelistMapper.deleteBackFilelistByIds(ids);
    }

    /**
     * 删除已上传文件列表信息
     * 
     * @param id 已上传文件列表ID
     * @return 结果
     */
    @Override
    public int deleteBackFilelistById(Long id)
    {
        return backFilelistMapper.deleteBackFilelistById(id);
    }


}
