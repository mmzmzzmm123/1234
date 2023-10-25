package com.ruoyi.onethinker.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ruoyi.onethinker.domain.SysFileInfo;
import com.ruoyi.onethinker.dto.SysFileInfoReqDTO;
import com.ruoyi.onethinker.dto.SysFileInfoResDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件信息Service接口
 *
 * @author yangyouqi
 * @date 2023-10-25
 */
public interface ISysFileInfoService {
    /**
     * 查询文件信息
     *
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public SysFileInfo selectSysFileInfoByFileId(String fileId);

    /**
     * 查询文件信息列表
     *
     * @param sysFileInfo 文件信息
     * @return 文件信息集合
     */
    public List<SysFileInfo> selectSysFileInfoList(SysFileInfo sysFileInfo);

    /**
     * 新增文件信息
     *
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    public int insertSysFileInfo(SysFileInfo sysFileInfo);

    /**
     * 修改文件信息
     *
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    public int updateSysFileInfo(SysFileInfo sysFileInfo);

    /**
     * 批量删除文件信息
     *
     * @param fileIds 需要删除的文件信息主键集合
     * @return 结果
     */
    public int deleteSysFileInfoByFileIds(String[] fileIds);

    /**
     * 删除文件信息信息
     *
     * @param fileId 文件信息主键
     * @return 结果
     */
    public int deleteSysFileInfoByFileId(String fileId);

    /**
     * 文件上传
     * @param sysFileInfoReqDTO
     * @return
     */
    SysFileInfo upload(SysFileInfoReqDTO sysFileInfoReqDTO);
}
