package com.xinyu.idol.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyu.idol.common.Sha1Util;
import com.xinyu.idol.common.utils.DateUtils;
import com.xinyu.idol.common.utils.StringUtils;
import com.xinyu.idol.common.utils.bean.BeanUtils;
import com.xinyu.idol.common.utils.sign.Md5Utils;
import com.xinyu.idol.manager.ContentManager;
import com.xinyu.idol.pojo.entity.ClassificationsEntity;
import com.xinyu.idol.pojo.entity.ContentEntity;
import com.xinyu.idol.mapper.ContentMapper;
import com.xinyu.idol.pojo.entity.ContentOperLogEntity;
import com.xinyu.idol.pojo.entity.PakPathIdMapEntity;
import com.xinyu.idol.pojo.po.PageContentPo;
import com.xinyu.idol.pojo.vo.*;
import com.xinyu.idol.service.IClassificationsService;
import com.xinyu.idol.service.IContentOperLogService;
import com.xinyu.idol.service.IContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyu.idol.service.IPakPathIdMapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.tomcat.util.net.openssl.ciphers.MessageDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源冗余大表 服务实现类
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */
@Slf4j
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, ContentEntity> implements IContentService {

    @Autowired
    private ContentManager contentManager;

    @Autowired
    private IPakPathIdMapService pakPathIdMapService;

    @Autowired
    private IContentOperLogService contentOperLogService;

    @Autowired
    private IClassificationsService classificationsService;

    @Autowired
    private OSS oss;

    @Value("${aliyun.oss.bucket}")
    String bucketName = "content-resource-dev";

    public List testList() {

        return contentManager.selectAllTest();
    }

    public void addContent(AddContentVo addContentVo) {
        //逐字段校验
        addContentVo.verifyFields();


        PakPathIdMapEntity pakPathIdMapEntity = new PakPathIdMapEntity();
        BeanUtils.copyProperties(addContentVo, pakPathIdMapEntity);
        PakPathIdMapEntity byPath = pakPathIdMapService.getByPath(pakPathIdMapEntity);

        ContentEntity contentEntity = new ContentEntity();
        BeanUtils.copyProperties(addContentVo, contentEntity);

        contentEntity.setCurrentUploadTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM_SS));
        contentEntity.generateIdentifier();
        //查询当前identifier是否存在
        ContentEntity contentEntityInDb = contentManager.selectByIdentifier(contentEntity.getIdentifier());

        if (ObjectUtils.isNotEmpty(contentEntityInDb)) {
            log.warn("当前identifier已存在,跳过插入：{}", contentEntity.getIdentifier());
            return;
        }


        //byPath为空，新增资源
        if (ObjectUtils.isEmpty(byPath)) {
            //分配guid
            pakPathIdMapService.insertGetId(pakPathIdMapEntity);
            //封装实体
            contentEntity.setGuid(pakPathIdMapEntity.getId());
            contentManager.insert(contentEntity);
        } else {//否则,修改资源
            contentEntity.setGuid(byPath.getId());
            contentManager.updateByGuid(contentEntity);
        }

        //插入opera_log表
        ContentOperLogEntity contentOperLogEntity = new ContentOperLogEntity();
        BeanUtils.copyProperties(contentEntity, contentOperLogEntity);
        contentOperLogService.addContentOperaLog(contentOperLogEntity);

    }

    @Override
    public IPage<PageContentResp> pageContent(PageContentReq pageContentReq) {
        if (ObjectUtils.anyNull(pageContentReq.getPage(), pageContentReq.getSize())) {
            throw new RuntimeException("page,size字段异常");
        }
        PageContentPo po = new PageContentPo();
        BeanUtils.copyProperties(pageContentReq, po);
        IPage<ContentEntity> contentEntityIPage = contentManager.pageByPo(po);
        contentEntityIPage.setTotal(contentManager.selectCount());

        //查询classification并封装map
        Map<String, ClassificationsEntity> longClassificationsEntityMap = classificationsService.mapAllClassifications();

        IPage<PageContentResp> iPage = new Page();
        List<PageContentResp> pageContentRespList = new ArrayList<>();
        BeanUtils.copyProperties(contentEntityIPage, iPage);
        for (ContentEntity contentEntity : contentEntityIPage.getRecords()) {
            PageContentResp pageContentResp = new PageContentResp();
            BeanUtils.copyProperties(contentEntity, pageContentResp);
            //setClassification1
            PageContentResp.ClassificationRowResp classificationRow1 = new PageContentResp.ClassificationRowResp();
            ClassificationsEntity classification1Entity = longClassificationsEntityMap.getOrDefault(contentEntity.getClassification1(), new ClassificationsEntity());
            BeanUtils.copyProperties(classification1Entity, classificationRow1);
            pageContentResp.setClassification1Row(classificationRow1);
            //setClassification2
            PageContentResp.ClassificationRowResp classificationRow2 = new PageContentResp.ClassificationRowResp();
            ClassificationsEntity classification2Entity = longClassificationsEntityMap.getOrDefault(contentEntity.getClassification2(), new ClassificationsEntity());
            BeanUtils.copyProperties(classification2Entity, classificationRow2);
            pageContentResp.setClassification2Row(classificationRow2);
            //setClassification3
            PageContentResp.ClassificationRowResp classificationRow3 = new PageContentResp.ClassificationRowResp();
            ClassificationsEntity classification3Entity = longClassificationsEntityMap.getOrDefault(contentEntity.getClassification3(), new ClassificationsEntity());
            BeanUtils.copyProperties(classification3Entity, classificationRow3);
            pageContentResp.setClassification3Row(classificationRow3);

            pageContentRespList.add(pageContentResp);
        }

        iPage.setRecords(pageContentRespList);
        return iPage;

    }

    @Override
    public void updateContent(UpdateContentWebListReq updateContentWebListReq) {
        if (ObjectUtils.isEmpty(updateContentWebListReq)) {
            throw new RuntimeException("updateContent异常");
        }
        List<ContentEntity> contentEntityList = new ArrayList<>();
        List<UpdateContentWebReq> updateContentWebReqList = updateContentWebListReq.getUpdateContentWebReqList();
        updateContentWebReqList.forEach(it -> {
            ContentEntity contentEntity = new ContentEntity();
            contentEntity.setPath(null);
            contentEntity.setPakOsskey(null);
            BeanUtils.copyProperties(it, contentEntity);
            //获取classification字段
            if (ObjectUtils.isNotEmpty(it.getClassification1Row())) {
                contentEntity.setClassification1(it.getClassification1Row().getId().toString());
            }
            if (ObjectUtils.isNotEmpty(it.getClassification2Row())) {
                contentEntity.setClassification2(it.getClassification2Row().getId().toString());
            }
            if (ObjectUtils.isNotEmpty(it.getClassification3Row())) {
                contentEntity.setClassification3(it.getClassification3Row().getId().toString());
            }

            contentEntityList.add(contentEntity);
        });

        this.updateBatchById(contentEntityList);

    }

    @Override
    public FileUploadResp uploadIcon(MultipartFile multipartFile) throws IOException {
        Assert.notNull(multipartFile, "multipartFile非空");

        String rootPath = "resource";

        long size = multipartFile.getSize();

        //  byte[] fileMd5 = DigestUtils.md5Digest(multipartFile.getBytes());

        String sha1 = Sha1Util.sha1(multipartFile.getBytes());


        String wholePath = rootPath + "/" + size + "/" + sha1;


        oss.putObject(bucketName, wholePath, multipartFile.getInputStream());


        return FileUploadResp.builder().ossKey("/"+wholePath).fileSize(String.valueOf(size)).build();
    }

    @Override
    public OriginUploadResp uploadOrigins(List<MultipartFile> multipartFileList) throws  IOException {
        Assert.notNull(multipartFileList, "multipartFile非空");

        FileUploadResp fileUploadResp=new FileUploadResp();

        String rootPath = "origin";

        List<String>ossKeyList=new ArrayList<>();

        StringBuffer ossKeyListStr=new StringBuffer();

        String timestampStr=String.valueOf(DateUtils.dateTimeNow());

        for (MultipartFile multipartFile:multipartFileList) {

            String wholePath=rootPath+"/"+timestampStr+"/"+multipartFile.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            // 指定Content-Type

            metadata.setContentType("application/octet-stream");

            oss.putObject(bucketName, wholePath, multipartFile.getInputStream(),metadata);

            log.info("批量上传oss路径:{}",wholePath);

            ossKeyListStr.append("/"+wholePath+",");

            ossKeyList.add("/"+wholePath);
        }
        return OriginUploadResp.builder().ossKeyListStr(ossKeyListStr.toString()).ossKeyList(ossKeyList).build();
    }

    @Override
    public void pullResourceFromEnv(PullResourceFromEnvReq pullResourceFromEnvReq) {
        Assert.notNull(pullResourceFromEnvReq,"pullResourceFromEnvReq非空");
        if(StringUtils.isEmpty(pullResourceFromEnvReq.getFromEnv())){
            throw new RuntimeException("fromEnv异常");
        }

        if(CollectionUtils.isEmpty(pullResourceFromEnvReq.getGuidList())){
            throw new RuntimeException("fromEnv异常");
        }

        //获取对应fromEnv的域名

        //用guidList查询资源列表

        //与本地资源列表进行对比，将特征重复筛选掉

        //遍历数组，数组里每个值进行下载和插入db

        //




    }


}
