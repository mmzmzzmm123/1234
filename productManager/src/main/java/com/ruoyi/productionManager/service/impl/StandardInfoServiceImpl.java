package com.ruoyi.productionManager.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileTypeUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.productionManager.domain.StandardInfoDetails;
import com.ruoyi.productionManager.service.IStandardInfoDetailsService;
import com.ruoyi.productionManager.vo.StandardManagerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import com.ruoyi.productionManager.mapper.StandardInfoMapper;
import com.ruoyi.productionManager.domain.StandardInfo;
import com.ruoyi.productionManager.service.IStandardInfoService;
import org.springframework.web.multipart.MultipartFile;
import org.apache.http.entity.ContentType;


/**
 * 试验标准管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
@Service
public class StandardInfoServiceImpl implements IStandardInfoService 
{
    @Autowired
    private StandardInfoMapper standardInfoMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IStandardInfoDetailsService standardInfoDetailsService;

    /**
     * 查询试验标准管理
     * 
     * @param standardId 试验标准管理主键
     * @return 试验标准管理
     */
    @Override
    public StandardInfo selectStandardInfoByStandardId(Long standardId)
    {
        return standardInfoMapper.selectStandardInfoByStandardId(standardId);
    }

    /**
     * 查询试验标准管理列表
     * 
     * @param standardInfo 试验标准管理
     * @return 试验标准管理
     */
    @Override
    public List<StandardInfo> selectStandardInfoList(StandardInfo standardInfo)
    {
        return standardInfoMapper.selectStandardInfoList(standardInfo);
    }

    /**
     * 新增试验标准管理
     * 
     * @param standardManagerVO 试验标准管理
     * @return 结果
     */
    @Override
    public int insertStandardInfo(StandardManagerVO standardManagerVO)
    {
        String filePath = RuoYiConfig.getUploadPath();
        //            取出缓存数据
        String name = "";
        String fileName = "";
        StringBuffer sb = new StringBuffer();
//        String s:standardManagerVO.getPath().split("/",9)
        String[] str = standardManagerVO.getPath().split("/",6);
        for(int i=0;i<str.length;i++){
            System.out.println("name>>>"+str[i]);
            if(i>=2){
                sb.append("/");
                sb.append(str[i]);
            }
            name = str[i];
        }
        System.out.println("sb>>>"+sb.toString());
        try{
            byte[] bytes = redisCache.getCacheObject(Constants.UPLOAD_FILE+name);
            InputStream inputStream = new ByteArrayInputStream(bytes);
//            String fileType = FileTypeUtils.getType(bytes);
            MultipartFile files = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            fileName = sb.toString();
            File desc = FileUploadUtils.getAbsoluteFile(filePath, fileName);
            files.transferTo(desc);
            standardManagerVO.setCreateTime(DateUtils.getNowDate());

            standardInfoMapper.insertStandardInfo(standardManagerVO);
            StandardInfoDetails standardInfoDetails = new StandardInfoDetails();
            standardInfoDetails.setFileUrl(filePath+fileName);
            standardInfoDetails.setFileName(name);
            standardInfoDetails.setStandardId(standardManagerVO.getStandardId());
            standardInfoDetails.setCreateBy(SecurityUtils.getLoginUser().getUsername());
            return standardInfoDetailsService.insertStandardInfoDetails(standardInfoDetails);
        }catch (Exception e){
            throw new ServiceException("新增失败：" + e.getMessage());
        }
    }

    /**
     * 修改试验标准管理
     * 
     * @param standardInfo 试验标准管理
     * @return 结果
     */
    @Override
    public int updateStandardInfo(StandardInfo standardInfo)
    {
        standardInfo.setUpdateTime(DateUtils.getNowDate());
        return standardInfoMapper.updateStandardInfo(standardInfo);
    }

    /**
     * 批量删除试验标准管理
     * 
     * @param standardIds 需要删除的试验标准管理主键
     * @return 结果
     */
    @Override
    public int deleteStandardInfoByStandardIds(Long[] standardIds)
    {
        return standardInfoMapper.deleteStandardInfoByStandardIds(standardIds);
    }

    /**
     * 删除试验标准管理信息
     * 
     * @param standardId 试验标准管理主键
     * @return 结果
     */
    @Override
    public int deleteStandardInfoByStandardId(Long standardId)
    {
        return standardInfoMapper.deleteStandardInfoByStandardId(standardId);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String name ="";
            for(String s:fileName.split("/",9)){
                System.out.println("path>>>"+s);
                name = s;
            }

            byte[] data = file.getBytes();
//            Byte[] data = new Byte[file.getResource().getInputStream().available()];

            redisCache.setCacheObject(Constants.UPLOAD_FILE+name,
                    data, 30, TimeUnit.MINUTES);
//            byte[] bytes = redisCache.getCacheObject(Constants.UPLOAD_FILE+name);
//            InputStream inputStream = new ByteArrayInputStream(bytes);
//            String fileType = FileTypeUtils.getType(bytes);
//            MultipartFile testFiles = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
//            File desc = FileUploadUtils.getAbsoluteFile(filePath, name);
//            testFiles.transferTo(desc);
            return fileName;
        }catch (Exception e){
            throw new ServiceException("上传失败：" + e.getMessage());
        }
    }
    /**
     * 设置cache key
     *
     * @param fileName 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String fileName)
    {
        return Constants.UPLOAD_FILE + fileName;
    }
}
