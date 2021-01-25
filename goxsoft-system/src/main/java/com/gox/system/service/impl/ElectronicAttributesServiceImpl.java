package com.gox.system.service.impl;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import com.gox.common.utils.SecurityUtils;
import com.gox.common.utils.file.FileUtils;
import com.gox.common.utils.file.MergeRunnable;
import com.gox.system.domain.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gox.system.mapper.ElectronicAttributesMapper;
import com.gox.system.domain.ElectronicAttributes;
import com.gox.system.service.IElectronicAttributesService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 电子文件信息Service业务层处理
 * 
 * @author gox
 * @date 2020-12-29
 */
@Service
public class ElectronicAttributesServiceImpl implements IElectronicAttributesService 
{
    private static final Logger LOGGER= LoggerFactory.getLogger(ElectronicAttributesServiceImpl.class);
    @Autowired
    private ElectronicAttributesMapper electronicAttributesMapper;
    @Value("${system.rootpath}")
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
            //先校验md5 若有问题 直接提示失败 并重传
            String md5 = String.valueOf(map.get("chunkMd5"));
            String nmd5 = SecureUtil.md5(file.getInputStream());
            if (!nmd5.equals(md5)){
                LOGGER.error("md5不匹配");
                return "md5不匹配,上传失败";
            }
            String filename = String.valueOf(map.get("filename"));
            filename = FileUtil.getPrefix(filename);
            path.append(File.separator).append(filename);
            File folder = new File(path.toString());
            if (!folder.exists()){
                folder.mkdirs();
            }
            path.append(File.separator).append("blob-"+map.get("chunkIndex"));
            file.transferTo(Paths.get(path.toString()));
        }
        catch (Exception e){
            LOGGER.error("系统错误",e);
            return "发生异常"+e.getMessage();
        }
        return "";
    }

    @Override
    public String fileUploadPost(Chunk chunk, HttpServletResponse response,Long metadataId) throws IOException {
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
        String parent = rootpath+File.separator+"temp"+username;
        File f = new File(parent);
        if (!f.exists()){
            f.mkdirs();
        }
        File file= new File(parent, chunk.getFilename());
        //第一个块,则新建文件
        if(chunk.getChunkNumber()==1 && !file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                response.setStatus(500);
                return "exception:createFileException";
            }
        }

        //进行写文件操作
        try(
                //将块文件写入文件中
                InputStream fos=chunk.getFile().getInputStream();
                RandomAccessFile raf =new RandomAccessFile(file,"rw")
        ) {
            int len=-1;
            byte[] buffer=new byte[1024];
            raf.seek((chunk.getChunkNumber()-1)*1024*1024);
            while((len=fos.read(buffer))!=-1){
                raf.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(chunk.getChunkNumber()==1) {
                file.delete();
            }
            response.setStatus(507);
            return "exception:writeFileException";
        }
        if(chunk.getChunkNumber().equals(chunk.getTotalChunks())){
            response.setStatus(200);
            if(!"".equals(chunk.getFilename())){
                File abFile = new File(parent+File.separator+chunk.getFilename());
                ElectronicAttributes ea = new ElectronicAttributes(abFile,metadataId);
                int res= electronicAttributesMapper.insertElectronicAttributes(ea);
                if (res==0){
                    //删除文件
                    abFile.delete();
                }
            }
            return "over";
        }else {
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
    @Override
    public boolean merge(String filename, String md5,Long metadataId){
        StringBuilder path1 = new StringBuilder(rootpath);
        String filena = FileUtil.getPrefix(filename);
        path1.append(File.separator).append("temp").append(File.separator).append(filena).append(File.separator);
        String folder = path1.toString();
        String targetFile = rootpath + File.separator + "temp" + File.separator + filename;
        try {
            File f = new File(folder);
            String [] adds = f.list();
            Comparator<String> comparator = (o1, o2) -> {
                String [] os1 =o1.split("-");
                String [] os2 = o2.split("-");
                return Integer.parseInt(os1[1])-Integer.parseInt(os2[1]);
            };
            int partFileSize = 5 * 1024 * 1024;
            if (adds != null) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile,
                        "rw");
                randomAccessFile.setLength(partFileSize * (adds.length - 1)
                        + adds[(adds.length - 1)].length());
                randomAccessFile.close();
                Arrays.sort(adds,comparator);
                ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                        adds.length, adds.length * 3, 1, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(adds.length * 2));
                for (int i = 0; i < adds.length; i++) {
                    File partFile = new File(folder+adds[i]);
                    threadPool.execute(new MergeRunnable(i * partFileSize,
                            targetFile, partFile));
                }
                threadPool.shutdown();
                while (true){
                    if (threadPool.isTerminated()){
                        break;
                    }
                }
                File reFile =new File(targetFile);
                String md = SecureUtil.md5(reFile);
                if (md.equals(md5)){
                    ElectronicAttributes ea = new ElectronicAttributes(reFile,metadataId);
                    int res= electronicAttributesMapper.insertElectronicAttributes(ea);
                    if (res==0){
                        //删除文件
                        reFile.delete();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("系统错误",e);
            return false;
        }
        return true;
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
        if (FileUtil.exist(path)){
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
