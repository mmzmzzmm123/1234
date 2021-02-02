package com.gox.system.service.impl;

import java.io.File;
import java.util.List;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.gox.common.config.GoxConfig;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.utils.SecurityUtils;
import com.gox.common.utils.file.Chunk;
import com.gox.common.utils.file.UploadUtil;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.system.domain.ElectronicAttributes;
import com.gox.system.mapper.ElectronicAttributesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.MetadataMapper;
import com.gox.system.domain.Metadata;
import com.gox.system.service.IMetadataService;

import javax.servlet.http.HttpServletResponse;

/**
 * 文书类基本元数据Service业务层处理
 * 
 * @author gox
 * @date 2020-12-28
 */
@Service
public class MetadataServiceImpl implements IMetadataService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataServiceImpl.class);
    @Autowired
    private MetadataMapper metadataMapper;
    @Autowired
    private ElectronicAttributesMapper electronicAttributesMapper;
    @Value("${gox.profile}")
    private String profile;
    /**
     * 查询文书类基本元数据
     * 
     * @param id 文书类基本元数据ID
     * @return 文书类基本元数据
     */
    @Override
    public Metadata selectMetadataById(Long id)
    {
        return metadataMapper.selectMetadataById(id);
    }

    /**
     * 查询文书类基本元数据列表
     * 
     * @param metadata 文书类基本元数据
     * @return 文书类基本元数据
     */
    @Override
    public List<Metadata> selectMetadataList(Metadata metadata)
    {
        return metadataMapper.selectMetadataList(metadata);
    }

    /**
     * 新增文书类基本元数据
     * 
     * @param metadata 文书类基本元数据
     * @return 结果
     */
    @Override
    public int insertMetadata(Metadata metadata)
    {
        return metadataMapper.insertMetadata(metadata);
    }

    /**
     * 修改文书类基本元数据
     * 
     * @param metadata 文书类基本元数据
     * @return 结果
     */
    @Override
    public int updateMetadata(Metadata metadata)
    {
        return metadataMapper.updateMetadata(metadata);
    }

    /**
     * 批量删除文书类基本元数据
     * 
     * @param ids 需要删除的文书类基本元数据ID
     * @return 结果
     */
    @Override
    public int deleteMetadataByIds(Long[] ids)
    {
        return metadataMapper.deleteMetadataByIds(ids);
    }

    /**
     * 删除文书类基本元数据信息
     * 
     * @param id 文书类基本元数据ID
     * @return 结果
     */
    @Override
    public int deleteMetadataById(Long id)
    {
        return metadataMapper.deleteMetadataById(id);
    }

    /**
     * 生成档号
     * 1.将字段取出 用’-‘连接
     * 2.在mysql 和 redis中更新
     * @param fields
     * @param values
     */
    @Override
    public String generateArchivalCode(String[] fields, Object[] values) {

        return null;
    }

    /**
     * 根据id导出 excel
     *
     * @param ids 文书类基本元数据ID
     * @return 结果
     */
    @Override
    public AjaxResult exportExcelByIds(Long[] ids) {
        List<Metadata> mds = metadataMapper.selectMetadataByIds(ids);
        ExcelUtil<Metadata> util = new ExcelUtil<Metadata>(Metadata.class);
        return util.exportExcel(mds, "metadata");
    }

    /**
     * 根据id导出 excel 电子原文
     * @param ids 文书类基本元数据ID
     * @return 结果
     */
    @Override
    public AjaxResult exportExcelAndEleByIds(Long[] ids) {
        List<Metadata> mds = metadataMapper.selectMetadataByIds(ids);
        ExcelUtil<Metadata> util = new ExcelUtil<Metadata>(Metadata.class);
        String excel = util.exportExcel(mds,"metadata").get("msg").toString();
        String download =profile+File.separator+"download";
        excel = download+File.separator+excel;
        List<ElectronicAttributes> eas;
        String location;
        String dir;
        long t = System.currentTimeMillis();
        String ds = download + File.separator + t;
        File file ;
        for (Metadata md : mds) {
            eas = md.getElectronicAttributes();
            dir = ds+File.separator+md.getArchivalCode();
            if (eas!=null&&!eas.isEmpty()){
                file = new File(dir);
                file.mkdirs();
                for (ElectronicAttributes ea : eas) {
                    location = ea.getCurrentLocation();
                    FileUtil.copy(location,dir,true);
                }
            }
        }
        FileUtil.copy(excel,ds,true);
        String res = download+File.separator+t+".zip";
        ZipUtil.zip(ds,res);
        FileUtil.del(excel);
        FileUtil.del(ds);
        return AjaxResult.success(t+".zip");
    }

    /**
     * 上传处理
     *
     * @param chunk
     * @param response
     * @return result
     */
    @Override
    public String uploadHandle(Chunk chunk, HttpServletResponse response) {
        String username = SecurityUtils.getUsername();
        String parent = profile+File.separator+"temp"+username;
        String ex= UploadUtil.mergeChunk(parent,chunk,response );
        if (StrUtil.isBlank(ex)){
            //判断文件是excel还是zip

            return "";
        }else {
            return ex;
        }
    }
    private void ExcelImport(String filepath){
        //excel 导入 字段配置
        ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(filepath);
        List<List<Object>> res = reader.read(0, 1);
        
    }

}
