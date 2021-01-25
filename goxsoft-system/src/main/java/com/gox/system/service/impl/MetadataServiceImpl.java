package com.gox.system.service.impl;

import java.io.File;
import java.util.List;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.gox.common.config.RuoYiConfig;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.utils.poi.ExcelUtil;
import com.gox.system.domain.ElectronicAttributes;
import com.gox.system.mapper.ElectronicAttributesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.MetadataMapper;
import com.gox.system.domain.Metadata;
import com.gox.system.service.IMetadataService;

/**
 * 文书类基本元数据Service业务层处理
 * 
 * @author gox
 * @date 2020-12-28
 */
@Service
public class MetadataServiceImpl implements IMetadataService
{
    @Autowired
    private MetadataMapper metadataMapper;
    @Autowired
    private ElectronicAttributesMapper electronicAttributesMapper;
    @Value("${system.rootpath}")
    private String rootPath;
    @Value("${ruoyi.profile}")
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
        excel = profile+File.separator+excel;
        List<ElectronicAttributes> eas;
        String location;
        String dir;
        String ds = rootPath + File.separator + System.currentTimeMillis();
        File file ;
        for (Metadata md : mds) {
            eas = md.getElectronicAttributes();
            dir = ds+File.separator+md.getArchivalCode();
            file = new File(dir);
            file.mkdirs();
            if (eas!=null&&!eas.isEmpty()){
                for (ElectronicAttributes ea : eas) {
                    location = ea.getCurrentLocation();
                    FileUtil.copy(location,dir,true);
                }
            }
        }
        FileUtil.copy(excel,ds,true);
        String res = ds+".zip";
        ZipUtil.zip(ds,res);
        return AjaxResult.success(res);
    }
}
