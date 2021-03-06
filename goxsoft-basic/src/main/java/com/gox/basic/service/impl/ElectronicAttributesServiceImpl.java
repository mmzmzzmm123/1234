package com.gox.basic.service.impl;

import cn.hutool.core.io.FileUtil;
import com.gox.common.utils.SecurityUtils;
import com.gox.common.utils.file.Chunk;
import com.gox.common.utils.file.FileUtils;
import com.gox.common.utils.file.UploadUtil;
import com.gox.basic.domain.ElectronicAttributes;
import com.gox.basic.mapper.ElectronicAttributesMapper;
import com.gox.basic.service.IElectronicAttributesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 电子文件信息Service业务层处理
 *
 * @author gox
 * @date 2020-12-29
 */
@Service
public class ElectronicAttributesServiceImpl implements IElectronicAttributesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElectronicAttributesServiceImpl.class);
    @Autowired
    private ElectronicAttributesMapper electronicAttributesMapper;
    @Value("${gox.profile}")
    private String profile;

    /**
     * 查询电子文件信息
     *
     * @param id 电子文件信息ID
     * @return 电子文件信息
     */
    @Override
    public ElectronicAttributes selectElectronicAttributesById(Long id) {
        return electronicAttributesMapper.selectElectronicAttributesById(id);
    }

    @Override
    public String fileUploadPost(Chunk chunk, HttpServletResponse response, Long metadataId) throws IOException {
        /**
         * 每一个上传块都会包含如下分块信息：
         * chunkNumber: 当前块的次序，第一个块是 1，注意不是从 0 开始的。
         * totalChunks: 文件被分成块的总数。
         * chunkSize: 分块大小，根据 totalSize 和这个值你就可以计算出总共的块数。注意最后一块的大小可能会比这个要大。
         * currentChunkSize: 当前块的大小，实际大小。
         * totalSize: 文件总大小。
         * identifier: 这个就是每个文件的唯一标示。
         * filename: 文件名。
         * relativePath: 文件夹上传的时候文件的相对路径属性。
         * 一个分块可以被上传多次，当然这肯定不是标准行为，但是在实际上传过程中是可能发生这种事情的，这种重传也是本库的特性之一。
         *
         * 根据响应码认为成功或失败的：
         * 200 文件上传完成
         * 201 文加快上传成功
         * 500 第一块上传失败，取消整个文件上传
         * 507 服务器出错自动重试该文件块上传
         */
        String username = SecurityUtils.getUsername();
        String parent = profile + File.separator + "temp" + username;
        UploadUtil.mergeChunk(parent, chunk, response);
        if (chunk.getChunkNumber().equals(chunk.getTotalChunks())) {
            response.setStatus(200);
            if (!"".equals(chunk.getFilename())) {
                File abFile = new File(parent + File.separator + chunk.getFilename());
                ElectronicAttributes ea = new ElectronicAttributes(abFile, metadataId);
                int res = electronicAttributesMapper.insertElectronicAttributes(ea);
                if (res == 0) {
                    //删除文件
                    abFile.delete();
                }
            }
            return "over";
        } else {
            response.setStatus(201);
            return "ok";
        }
    }

    @Override
    public void fileUploadGet(Chunk chunk, HttpServletResponse response) {
        //存在响应200（浏览器直接提示上传成功），不存在响应304（浏览器将发送POST请求）
//        if (PropertiesUtils.contains(chunk.getFilename())) {
//            //已上传过，秒传
//            response.setStatus(200);
//        }else{
        response.setStatus(304);
        //}
    }

    /**
     * 获取文件base64编码
     *
     * @param id 电子文件id
     */
    @Override
    public String getBase64(String id) {
        ElectronicAttributes ea = electronicAttributesMapper.selectElectronicAttributesById(Long.valueOf(id));
        String path = ea.getCurrentLocation();
        if (FileUtil.exist(path)) {
            try {
                return FileUtils.base64(path);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    /**
     * 查询电子文件信息列表
     *
     * @param electronicAttributes 电子文件信息
     * @return 电子文件信息
     */
    @Override
    public List<ElectronicAttributes> selectElectronicAttributesList(ElectronicAttributes electronicAttributes) {
        return electronicAttributesMapper.selectElectronicAttributesList(electronicAttributes);
    }

    /**
     * 新增电子文件信息
     *
     * @param electronicAttributes 电子文件信息
     * @return 结果
     */
    @Override
    public int insertElectronicAttributes(ElectronicAttributes electronicAttributes) {
        return electronicAttributesMapper.insertElectronicAttributes(electronicAttributes);
    }
    @Override
    public int insertElectronicAttributesBatch(Collection<ElectronicAttributes> electronicAttributes) {
        return electronicAttributesMapper.insertElectronicAttributesBatch(electronicAttributes);
    }

    /**
     * 修改电子文件信息
     *
     * @param electronicAttributes 电子文件信息
     * @return 结果
     */
    @Override
    public int updateElectronicAttributes(ElectronicAttributes electronicAttributes) {
        return electronicAttributesMapper.updateElectronicAttributes(electronicAttributes);
    }

    /**
     * 批量删除电子文件信息
     *
     * @param ids 需要删除的电子文件信息ID
     * @return 结果
     */
    @Override
    public int deleteElectronicAttributesByIds(Long[] ids) {
        return electronicAttributesMapper.deleteElectronicAttributesByIds(ids);
    }

    /**
     * 删除电子文件信息信息
     *
     * @param id 电子文件信息ID
     * @return 结果
     */
    @Override
    public int deleteElectronicAttributesById(Long id) {
        return electronicAttributesMapper.deleteElectronicAttributesById(id);
    }
}
