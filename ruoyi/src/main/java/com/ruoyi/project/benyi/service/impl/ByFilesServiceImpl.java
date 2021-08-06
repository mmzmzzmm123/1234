package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByFilesMapper;
import com.ruoyi.project.benyi.domain.ByFiles;
import com.ruoyi.project.benyi.service.IByFilesService;

/**
 * 文件管理Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-12
 */
@Service
public class ByFilesServiceImpl implements IByFilesService {
    @Autowired
    private ByFilesMapper byFilesMapper;

    /**
     * 查询文件管理
     *
     * @param id 文件管理ID
     * @return 文件管理
     */
    @Override
    public ByFiles selectByFilesById(Long id) {
        return byFilesMapper.selectByFilesById(id);
    }

    /**
     * 查询文件管理列表
     *
     * @param byFiles 文件管理
     * @return 文件管理
     */
    @Override
    public List<ByFiles> selectByFilesList(ByFiles byFiles) {
        if (byFiles.getRoles() == null || "".equals(byFiles.getRoles())) {

        } else {
            String strArr = byFiles.getRoles();
            byFiles.setRoleArr(strArr.split(";"));
        }

        return byFilesMapper.selectByFilesList(byFiles);
    }

    /**
     * 新增文件管理
     *
     * @param byFiles 文件管理
     * @return 结果
     */
    @Override
    public int insertByFiles(ByFiles byFiles) {
        byFiles.setCreateTime(DateUtils.getNowDate());
        return byFilesMapper.insertByFiles(byFiles);
    }

    /**
     * 修改文件管理
     *
     * @param byFiles 文件管理
     * @return 结果
     */
    @Override
    public int updateByFiles(ByFiles byFiles) {
        return byFilesMapper.updateByFiles(byFiles);
    }

    /**
     * 批量删除文件管理
     *
     * @param ids 需要删除的文件管理ID
     * @return 结果
     */
    @Override
    public int deleteByFilesByIds(Long[] ids) {
        return byFilesMapper.deleteByFilesByIds(ids);
    }

    /**
     * 删除文件管理信息
     *
     * @param id 文件管理ID
     * @return 结果
     */
    @Override
    public int deleteByFilesById(Long id) {
        return byFilesMapper.deleteByFilesById(id);
    }
}
