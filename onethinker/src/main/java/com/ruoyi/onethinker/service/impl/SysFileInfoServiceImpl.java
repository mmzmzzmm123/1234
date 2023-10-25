package com.ruoyi.onethinker.service.impl;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruoyi.common.enums.SysConfigKeyEnum;
import com.ruoyi.common.utils.DateUtils;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.onethinker.dto.SysFileInfoReqDTO;
import com.ruoyi.onethinker.dto.SysFileInfoResDTO;
import com.ruoyi.onethinker.mapper.SysFileInfoMapper;
import com.ruoyi.onethinker.domain.SysFileInfo;
import com.ruoyi.onethinker.service.ISysFileInfoService;
import com.ruoyi.system.service.ISysConfigService;

/**
 * 文件信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-25
 */
@Service
public class SysFileInfoServiceImpl implements ISysFileInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(SysFileInfoServiceImpl.class);
    @Autowired
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
    public SysFileInfo upload(SysFileInfoReqDTO sysFileInfoReqDTO, HttpServletRequest request) {
        // 目前只支持服务器上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new RuntimeException("请求方式有误，请重新发起请求");
        }
        // 文件路径
        String path = sysConfigService.selectConfigByKey(SysConfigKeyEnum.DETAIL_FILE_PATH) + File.pathSeparator + request.getMethod() + File.pathSeparator;
        // 文件名
        String fileName = sysFileInfoReqDTO.getName();
        // 文件MD5
        String fileMd5 = sysFileInfoReqDTO.getMd5();
        // 文件后缀名
        String suffixName = fileName.split("\\.")[fileName.split("\\.").length - 1];
        // 文件路径
        if (!new File(path).exists()) {
            new File(path).mkdir();
        }
        File uploadFile = new File(path + File.pathSeparator + fileMd5 + "." + suffixName);
        try {
            FileUtils.copyInputStreamToFile(sysFileInfoReqDTO.getFile().getInputStream(), uploadFile);
        } catch (Exception e) {
            LOG.error("文件上传失败" + e.getMessage());
            e.printStackTrace();
        }
        // 上传成功，保存文件内容信息
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileId(UUID.fastUUID().toString());
        sysFileInfo.setFileName(fileName);
        sysFileInfo.setFileType(sysFileInfoReqDTO.getFileType());
        sysFileInfo.setServerFileId(fileMd5);
        sysFileInfo.setSource(SysFileInfo.SOURCE_TYPE_LOCALHOST);
        sysFileInfo.setSize(uploadFile.length());
        sysFileInfo.setSuffixName(suffixName);
        sysFileInfo.setFileUrl("http://" + IpUtils.getHostIp() + ":18081/");
        sysFileInfo.setCreateUserId(SecurityUtils.getUserId());
        sysFileInfoMapper.insertSysFileInfo(sysFileInfo);
        return sysFileInfo;
    }
}
