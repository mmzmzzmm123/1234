package com.ruoyi.common.tencent;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.tencent.model.dto.OssUploadSingleFileDto;
import com.ruoyi.common.tencent.model.vo.OssUploadSingleFileVo;
import com.ruoyi.common.tencent.properties.OssProperties;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LAM
 * @date 2023/9/12 18:43
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OssService {

    private final OssProperties ossProperties;
    private final COSClient cosClient;

    /**
     * 文件上传到oss
     *
     * @param dto 文件数据
     * @return 结果
     * */
    public OssUploadSingleFileVo uploadSingleFile(OssUploadSingleFileDto dto) {
        TransferManager transferManager = null;
        InputStream inputStream = null;
        try{
            // 获取文件输出字节流
            inputStream = dto.getFile().getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(dto.getFile().getSize());
            objectMetadata.setContentType(dto.getFile().getContentType());
            // 雪花id的oss文件名
            String ossName = IdUtil.getSnowflakeNextIdStr();
            String fileName = ossName + "." + StrUtil.split(dto.getFile().getOriginalFilename(), ".").get(1);
            String key = dto.getOssKey() + "/" + DateUtil.format(DateUtil.date(), "yyyy/MM/dd") + "/" + fileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), key, inputStream, objectMetadata);
            transferManager = transferManager();
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            log.info("文件上传到oss：描述，请求返回结果：{}", uploadResult);
            // 封装返回信息
            OssUploadSingleFileVo vo = new OssUploadSingleFileVo();
            vo.setRequestId(uploadResult.getRequestId())
                    .setFileName(dto.getFile().getOriginalFilename())
                    .setNewFileName(fileName)
                    .setFileUrl(ossProperties.getBaseUrl() + "/" + key);
            log.info("文件上传到oss：成功，返回参数：{}", vo);
            return vo;
        }catch (IOException | InterruptedException e) {
            log.warn("文件上传到oss：失败，异常信息：{}", e.getMessage());
            throw new ServiceException("文件上传失败，异常信息："+e.getMessage());
        } finally {
            if (ObjectUtil.isNotNull(inputStream)){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ServiceException("文件上传失败，异常信息："+e.getMessage());
                }
            }
            if (ObjectUtil.isNotNull(transferManager)){
                transferManager.shutdownNow(true);
            }
        }
    }

    private TransferManager transferManager(){
        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(ossProperties.getThreadPool());
        // 传入一个 threadPool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        // 分块上传的块大小，单位：字节（Byte），默认为5MB
        transferManagerConfiguration.setMinimumUploadPartSize(1024*1024);
        // 大于等于该值则并发的分块上传文件，单位：字节（Byte），默认为5MB
        transferManagerConfiguration.setMultipartUploadThreshold(5*1024*1024);
        transferManager.setConfiguration(transferManagerConfiguration);
        return transferManager;
    }
}
