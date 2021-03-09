package com.gox.basic.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.poi.excel.ExcelReader;
import com.gox.basic.domain.*;
import com.gox.basic.domain.vo.ImportFieldMap;
import com.gox.basic.domain.vo.TableFieldVo;
import com.gox.basic.service.*;
import com.gox.basic.utils.ExportUtil;
import com.gox.basic.utils.ImportUtils;
import com.gox.common.core.domain.AjaxResult;
import com.gox.common.core.redis.RedisCache;
import com.gox.common.plugin.SnowIdUtils;
import com.gox.common.utils.SecurityUtils;
import com.gox.common.utils.file.Chunk;
import com.gox.common.utils.file.UploadUtil;
import com.gox.basic.mapper.MetadataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 文书类基本元数据Service业务层处理
 *
 * @author gox
 * @date 2020-12-28
 */
@Service
public class MetadataServiceImpl implements IMetadataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataServiceImpl.class);
    @Autowired
    private MetadataMapper metadataMapper;
    @Autowired
    private ITemplatesPreserveService templatesPreserveService;
    @Autowired
    private IArchivalCodeSettingService archivalCodeSettingService;
    @Autowired
    private IElectronicAttributesService electronicAttributesService;
    @Autowired
    private IMetadataReserveService metadataReserveService;
    @Autowired
    private RedisCache redisCache;
    @Value("${gox.profile}")
    private String profile;

    /**
     * 查询文书类基本元数据
     *
     * @param id 文书类基本元数据ID
     * @return 文书类基本元数据
     */
    @Override
    public Metadata selectMetadataById(Long id) {
        return metadataMapper.selectMetadataById(id);
    }

    /**
     * 查询文书类基本元数据列表
     *
     * @param metadata 文书类基本元数据
     * @return 文书类基本元数据
     */
    @Override
    public List<Metadata> selectMetadataList(Metadata metadata) {
        return metadataMapper.selectMetadataList(metadata);
    }

    /**
     * 新增文书类基本元数据
     *
     * @param metadata 文书类基本元数据
     * @return 结果
     */
    @Override
    @Transactional
    public int insertMetadata(Metadata metadata) {
        long id = SnowIdUtils.uniqueLong();
        if (metadata.getId()==null){
            metadata.setId(id);
        }
        MetadataReserve mdr = metadata.getMetadataReserve();
        if (mdr!=null){
            mdr.setId(metadata.getId());
            metadataReserveService.insertMetadataReserve(mdr);
        }
        List<ElectronicAttributes> ea = metadata.getElectronicAttributes();
        if (ea != null&&!ea.isEmpty()){
            ea.forEach(e->e.setMetadataId(metadata.getId()));
            electronicAttributesService.insertElectronicAttributesBatch(ea);
        }
        return metadataMapper.insertMetadata(metadata);
    }

    /**
     * 修改文书类基本元数据
     *
     * @param metadata 文书类基本元数据
     * @return 结果
     */
    @Override
    public int updateMetadata(Metadata metadata) {
        return metadataMapper.updateMetadata(metadata);
    }

    /**
     * 批量增加
     * @param metadatas
     * @return
     */
    @Override
    @Transactional
    public int insertMetadataBatch(Collection<Metadata> metadatas){
        if (metadatas.isEmpty()){
            return 0;
        }
        MetadataReserve mdr;
        List<MetadataReserve> mdrs = new ArrayList<>();
        List<ElectronicAttributes> ea ;
        List<ElectronicAttributes> eas = new ArrayList<>();
        for (Metadata md : metadatas) {
            long id = SnowIdUtils.uniqueLong();
            if (md.getId()==null){
                md.setId(id);
            }
            mdr = md.getMetadataReserve();
            if (mdr!=null){
                mdr.setId(md.getId());
                mdrs.add(mdr);
            }
            ea = md.getElectronicAttributes();
            if (ea != null&&!ea.isEmpty()){
                long finalId = md.getId();
                ea.forEach(e->e.setMetadataId(finalId));
                eas.addAll(ea);
            }
        }
        if (!mdrs.isEmpty()){
            metadataReserveService.insertMetadataReserve(mdrs);
        }
        if (!eas.isEmpty()){
            electronicAttributesService.insertElectronicAttributesBatch(eas);
        }
        return metadataMapper.insertMetadataBatch(metadatas);
    }
    /**
     * 批量删除文书类基本元数据
     *
     * @param ids 需要删除的文书类基本元数据ID
     * @return 结果
     */
    @Override
    public int deleteMetadataByIds(Long[] ids) {
        return metadataMapper.deleteMetadataByIds(ids);
    }

    /**
     * 删除文书类基本元数据信息
     *
     * @param id 文书类基本元数据ID
     * @return 结果
     */
    @Override
    public int deleteMetadataById(Long id) {
        return metadataMapper.deleteMetadataById(id);
    }

    /**
     * 生成档号
     * 1.将字段取出 用’-‘连接
     *
     * 2.在mysql 和 redis中更新
     */
    @Override
    public String generateArchivalCode(Metadata metadata) throws Exception {
        StringBuilder archivalCode = new StringBuilder();
        Long nodeId = metadata.getNodeId();
        Long deptId = metadata.getDeptId();
        ArchivalCodeSetting setting = archivalCodeSettingService.selectArchivalCsByNodeIdAndDeptId(nodeId, deptId);
        String [] fieldNames = setting.getFields();
        for (int i = 0; i < fieldNames.length; i++) {
            Field field = Metadata.class.getDeclaredField(fieldNames[i]);
            field.setAccessible(true);
            Object obj = field.get(metadata);
            if (obj!=null){
                archivalCode.append(obj).append("-");
            }
            //最后一位需要生成
            else if (i==fieldNames.length-1){
                String f = redisCache.getCacheMapValue("archivalCode",archivalCode.toString());
                if (StrUtil.isNotBlank(f)){
                    archivalCodeSettingService.updateArchivalCodeNum(archivalCode.toString(),1);
                    archivalCode.append(Integer.parseInt(f)+1);
                }
                else {
                    redisCache.setCacheMapValue("archivalCode",archivalCode.toString(),1);
                    archivalCodeSettingService.insertArchivalCodeNum(new ArchivalCodeNum(1,archivalCode.toString()));
                    archivalCode.append(1);
                }
                return archivalCode.toString();
            }
            else {
                return "不正确的输入";
            }
        }
        return archivalCode.toString();
    }

    /**
     * 根据id导出 excel
     *
     * @param ids 文书类基本元数据ID
     * @return 结果
     */
    @Override
    public AjaxResult exportExcelByIds(Long[] ids) throws Throwable {
        if (ids.length==0){
            return AjaxResult.error();
        }
        List<Metadata> mds = metadataMapper.selectMetadataByIds(ids);
        List<TemplatesPreserve>vos = templatesPreserveService.
                selectTemplatesPreserveList(new TemplatesPreserve(mds.get(0).getNodeId(),mds.get(0).getDeptId()));
        String fn = System.currentTimeMillis()+".xlsx";
        ExportUtil.exportExcel(mds,vos,fn);
        return AjaxResult.success(fn);
    }
    @Override
    public AjaxResult exportXmlByIds(Long[] ids) throws Throwable{
        if (ids.length==0){
            return AjaxResult.error();
        }
        List<Metadata> mds = metadataMapper.selectMetadataByIds(ids);
        List<TemplatesPreserve> vos = templatesPreserveService.selectTemplatesPreserveList(new TemplatesPreserve(mds.get(0).getNodeId(),mds.get(0).getDeptId()));
        String fn = System.currentTimeMillis()+".xml";
        ExportUtil.exportXml(mds,vos,fn);
        return AjaxResult.success(fn);
    }
    @Override
    public AjaxResult exportXmlAndEleByIds(Long[] ids) throws Throwable{
        if (ids.length==0){
            return AjaxResult.error();
        }
        List<Metadata> mds = metadataMapper.selectMetadataByIds(ids);
        List<TemplatesPreserve> vos = templatesPreserveService.selectTemplatesPreserveList(new TemplatesPreserve(mds.get(0).getNodeId(),mds.get(0).getDeptId() ));
        long t = System.currentTimeMillis();
        String fn = t+".xml";
        ExportUtil.exportXml(mds,vos,fn);
        return getZip(mds,t,fn);
    }
    /**
     * 根据id导出 excel 电子原文
     *
     * @param ids 文书类基本元数据ID
     * @return 结果
     */
    @Override
    public AjaxResult exportExcelAndEleByIds(Long[] ids) throws Throwable {
        if (ids.length==0){
            return AjaxResult.error();
        }
        List<Metadata> mds = metadataMapper.selectMetadataByIds(ids);
        List<TemplatesPreserve> vos = templatesPreserveService.selectTemplatesPreserveList(new TemplatesPreserve(mds.get(0).getNodeId(),mds.get(0).getDeptId()));
        long t = System.currentTimeMillis();
        String fn = t+".xlsx";
        ExportUtil.exportExcel(mds,vos,fn);
        return getZip(mds, t, fn);
    }

    private AjaxResult getZip(List<Metadata> mds, long t, String fn) {
        String download = profile + File.separator + "download";
        List<ElectronicAttributes> eas;
        String location;
        String dir;
        String ds = download + File.separator + t;
        File file;
        for (Metadata md : mds) {
            eas = md.getElectronicAttributes();
            String archivalCode = md.getArchivalCode();
            if (StrUtil.isBlank(archivalCode)){
                archivalCode = String.valueOf(md.getId());
            }
            dir = ds + File.separator + archivalCode;
            if (eas != null && !eas.isEmpty()) {
                file = new File(dir);
                file.mkdirs();
                for (ElectronicAttributes ea : eas) {
                    location = ea.getCurrentLocation();
                    FileUtil.copy(location, dir, true);
                }
            }
        }
        FileUtil.copy(download+File.separator+fn, ds+File.separator+fn, true);
        FileUtil.del(download+File.separator+fn);
        String res = download + File.separator + t + ".zip";
        ZipUtil.zip(ds, res);
        FileUtil.del(ds);
        return AjaxResult.success(t + ".zip");
    }

    /**
     * 上传处理
     *
     * @param chunk
     * @param response
     * @return result
     */
    @Override
    public String uploadHandle(Chunk chunk, HttpServletResponse response,Long nodeId,Long deptId) {
        String username = SecurityUtils.getUsername();
        String parent = profile + File.separator + nodeId+File.separator+deptId+File.separator+username;
        String ex = UploadUtil.mergeChunk(parent, chunk, response);
        if (StrUtil.isBlank(ex)) {
            //判断文件是excel还是zip
            return "";
        } else {
            return ex;
        }
    }

    /**
     * 上传文件处理 返回结果
     *
     * @param nodeId
     * @param deptId
     * @return
     */
    @Override
    public AjaxResult handUpload(Long nodeId, Long deptId,String filename) throws Throwable {
        String username = SecurityUtils.getUsername();
        String parent = profile + File.separator + nodeId+File.separator+deptId+File.separator+username;
        String filepath = parent + File.separator+ filename;
        return AjaxResult.success(ImportUtils.extract(filepath));
    }
    @Override
    public AjaxResult importHandle(Long nodeId, Long deptId,Long parentId, String filename, List<ImportFieldMap> maps)
            throws Throwable {
        String username = SecurityUtils.getUsername();
        String parent = profile + File.separator + nodeId+File.separator+deptId+File.separator+username;
        String filepath = parent + File.separator+ filename;
        List<TemplatesPreserve> list = templatesPreserveService.selectTemplatesPreserveList(new TemplatesPreserve(nodeId, deptId));
        //现数据对应map
        Map<String,String> map2 = new HashMap<>();
        list.forEach(f->map2.put(f.getTableFieldName(),f.getvModel()));
        //上传数据对应
        Map<String,String> map1 = new HashMap<>();
        maps.forEach(f->map1.put(f.getSrc(),f.getDest()));
        List<Metadata> res = ImportUtils.importHandler(filepath, map1, map2);
        res.forEach(md->{
            md.setNodeId(nodeId);
            md.setDeptId(deptId);
            md.setParentId(parentId);
        });
        return AjaxResult.success(insertMetadataBatch(res));
    }

    /**
     * 插件
     * step1:获取nodeId deptId 对应所有的元数据
     * @param metadata
     * @return
     */
    @Override
    @Transactional
    public AjaxResult insertArchival(Metadata metadata) throws Throwable {
        Metadata md = new Metadata(metadata.getDeptId(),metadata.getParentId(),metadata.getNodeId());
        ArchivalCodeSetting setting = archivalCodeSettingService.selectArchivalCsByNodeIdAndDeptId(metadata.getNodeId(),metadata.getDeptId());
        String [] fs = setting.getFields();
        //最后一位档号的字段 一定是数字
        String field= fs[fs.length-1];
        Object o = ExportUtil.getValue(field,metadata);
        if (o==null){
            return AjaxResult.error("系统异常,获取关键字段失败,请检查输入数据!");
        }
        int v = Convert.toInt(o);
        List<Metadata> mds = selectMetadataList(md);
        List<Metadata> modifies = new ArrayList<>();
        insertMetadata(metadata);
        for (Metadata m : mds) {
            //关键字段
            int k = Convert.toInt(ExportUtil.getValue(field,m));
            //如果 数据库的关键字段大于等于传回来的关键值 则将数据库字段全部加一
            if (k>=v){
                ImportUtils.setValue(m,field,k+1);
                modifies.add(m);
            }
        }
        return AjaxResult.success();
    }

}
