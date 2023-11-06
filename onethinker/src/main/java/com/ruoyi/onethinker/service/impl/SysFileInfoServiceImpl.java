package com.ruoyi.onethinker.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.enums.SysConfigKeyEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.onethinker.domain.SysFileInfo;
import com.ruoyi.onethinker.dto.SysFileInfoReqDTO;
import com.ruoyi.onethinker.mapper.SysFileInfoMapper;
import com.ruoyi.onethinker.service.ISysFileInfoService;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.log4j.Log4j2;

/**
 * 文件信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-25
 */
@Service
@Log4j2
public class SysFileInfoServiceImpl implements ISysFileInfoService {

    @Resource
    private SysFileInfoMapper sysFileInfoMapper;
    @Autowired
    private ISysConfigService sysConfigService;

    /**
     * 查询文件信息
     *
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    @Override
    public SysFileInfo selectSysFileInfoByFileId(String fileId) {
        return sysFileInfoMapper.selectSysFileInfoByFileId(fileId);
    }

    /**
     * 查询文件信息列表
     *
     * @param sysFileInfo 文件信息
     * @return 文件信息
     */
    @Override
    public List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo) {
        return sysFileInfoMapper.selectSysFileInfoList(sysFileInfo);
    }

    /**
     * 新增文件信息
     *
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int insertSysFileInfo(SysFileInfo sysFileInfo) {
        sysFileInfo.setCreateTime(DateUtils.getNowDate());
        return sysFileInfoMapper.insertSysFileInfo(sysFileInfo);
    }

    /**
     * 修改文件信息
     *
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    @Override
    public int updateSysFileInfo(SysFileInfo sysFileInfo) {
        return sysFileInfoMapper.updateSysFileInfo(sysFileInfo);
    }

    /**
     * 批量删除文件信息
     *
     * @param fileIds 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteSysFileInfoByFileIds(String[] fileIds) {
        return sysFileInfoMapper.deleteSysFileInfoByFileIds(fileIds);
    }

    /**
     * 删除文件信息信息
     *
     * @param fileId 文件信息主键
     * @return 结果
     */
    @Override
    public int deleteSysFileInfoByFileId(String fileId) {
        return sysFileInfoMapper.deleteSysFileInfoByFileId(fileId);
    }

    @Override
    public SysFileInfo upload(SysFileInfoReqDTO sysFileInfoReqDTO) {
        // 文件路径
        String path = sysConfigService.selectConfigByKey(SysConfigKeyEnum.DETAIL_FILE_PATH) + "/" + sysFileInfoReqDTO.getModule();
        // 文件名
        String fileName = sysFileInfoReqDTO.getName();
        // 文件MD5
        String fileMd5 = sysFileInfoReqDTO.getMd5();
        // 文件后缀名
        String suffixName = fileName.split("\\.")[fileName.split("\\.").length - 1];
        // 文件路径
        try {
            fileName = FileUploadUtils.upload(path, sysFileInfoReqDTO.getFile());
        } catch (Exception e) {
            log.error("文件上传失败" + e.getMessage());
            e.printStackTrace();
        }
        // 上传成功，保存文件内容信息
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileId(UUID.fastUuid().toString());
        sysFileInfo.setFileName(fileName);
        sysFileInfo.setFileType(sysFileInfoReqDTO.getFileType());
        sysFileInfo.setServerFileId(UUID.fastUuid().toString());
        sysFileInfo.setSource(SysFileInfo.SOURCE_TYPE_LOCALHOST);
        sysFileInfo.setSuffixName(suffixName);
        sysFileInfo.setSize(sysFileInfoReqDTO.getSize());
        sysFileInfo.setFileUrl(sysConfigService.selectConfigByKey(SysConfigKeyEnum.SERVICE_FILE_PUBLIC_URL));
        sysFileInfo.setCreateUserId(SecurityUtils.getUserId());
        sysFileInfo.setCreateTime(new Date());
        sysFileInfoMapper.insertSysFileInfo(sysFileInfo);
        return sysFileInfo;
    }
}
