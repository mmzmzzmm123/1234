package com.gox.system.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.gox.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.ElectronicAttributesMapper;
import com.gox.system.domain.ElectronicAttributes;
import com.gox.system.service.IElectronicAttributesService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 电子文件信息Service业务层处理
 * 
 * @author gox
 * @date 2020-12-29
 */
@Service
public class ElectronicAttributesServiceImpl implements IElectronicAttributesService 
{
    static String a;
    @Autowired
    private ElectronicAttributesMapper electronicAttributesMapper;
    @Value("system.rootpath")
    String rootpath;
    /**
     * 查询电子文件信息
     * 
     * @param id 电子文件信息ID
     * @return 电子文件信息
     */
    @Override
    public ElectronicAttributes selectElectronicAttributesById(Long id)
    {
        return electronicAttributesMapper.selectElectronicAttributesById(id);
    }
    /**
     * 文件分块合并存储
     * @param map
     */
    @Override
    public String mergeChunk(Map<String, Object> map){
        try{
            StringBuilder path = new StringBuilder(rootpath);
            path.append(File.separator).append("temp");
            MultipartFile file = (MultipartFile) map.get("data");

            //第一个块
            if ("1".equals(map.get("chunkIndex"))){
                File f = new File(path.toString());
                if (!f.exists()){
                    f.mkdirs();
                }
                path.append(File.separator).append(SecurityUtils.getUsername()).append("-").append(map.get("filename"));
                file.transferTo(Paths.get(path.toString()));
            }
            else {
                path.append(File.separator).append(SecurityUtils.getUsername()).append("-").append(map.get("filename"));
                OutputStream outputStream = new FileOutputStream(path.toString());
                IoUtil.write(outputStream,false,file.getBytes());
                IoUtil.close(outputStream);
            }
            System.out.println(map.get("chunkIndex"));
        }
        catch (Exception e){
            return "发生异常"+e.getMessage();
        }
        return "";

    }
    /**
     * 获取文件base64编码
     *
     * @param id 电子文件id
     */
    @Override
    public String getBase64(String id) {
        ElectronicAttributes ea = electronicAttributesMapper.selectElectronicAttributesById(Long.valueOf(id));
        String path = rootpath+ File.separator+ea.getCurrentLocation();
        if (FileUtil.exist(path)){
            return Base64.encode(path);
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
    public List<ElectronicAttributes> selectElectronicAttributesList(ElectronicAttributes electronicAttributes)
    {
        return electronicAttributesMapper.selectElectronicAttributesList(electronicAttributes);
    }

    /**
     * 新增电子文件信息
     * 
     * @param electronicAttributes 电子文件信息
     * @return 结果
     */
    @Override
    public int insertElectronicAttributes(ElectronicAttributes electronicAttributes)
    {
        return electronicAttributesMapper.insertElectronicAttributes(electronicAttributes);
    }

    /**
     * 修改电子文件信息
     * 
     * @param electronicAttributes 电子文件信息
     * @return 结果
     */
    @Override
    public int updateElectronicAttributes(ElectronicAttributes electronicAttributes)
    {
        return electronicAttributesMapper.updateElectronicAttributes(electronicAttributes);
    }

    /**
     * 批量删除电子文件信息
     * 
     * @param ids 需要删除的电子文件信息ID
     * @return 结果
     */
    @Override
    public int deleteElectronicAttributesByIds(Long[] ids)
    {
        return electronicAttributesMapper.deleteElectronicAttributesByIds(ids);
    }

    /**
     * 删除电子文件信息信息
     * 
     * @param id 电子文件信息ID
     * @return 结果
     */
    @Override
    public int deleteElectronicAttributesById(Long id)
    {
        return electronicAttributesMapper.deleteElectronicAttributesById(id);
    }
}
