package com.xinyu.idol.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyu.idol.common.utils.DateUtils;
import com.xinyu.idol.common.utils.bean.BeanUtils;
import com.xinyu.idol.manager.ContentManager;
import com.xinyu.idol.pojo.entity.ContentEntity;
import com.xinyu.idol.mapper.ContentMapper;
import com.xinyu.idol.pojo.entity.ContentOperLogEntity;
import com.xinyu.idol.pojo.entity.PakPathIdMapEntity;
import com.xinyu.idol.pojo.po.PageContentPo;
import com.xinyu.idol.pojo.vo.*;
import com.xinyu.idol.service.IContentOperLogService;
import com.xinyu.idol.service.IContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyu.idol.service.IPakPathIdMapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

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

    public List testList() {
        return contentManager.selectAllTest();
    }

    public void addContent(AddContentVo addContentVo) {
        //逐字段校验
        addContentVo.verifyFields();


        PakPathIdMapEntity pakPathIdMapEntity=new PakPathIdMapEntity();
        BeanUtils.copyProperties(addContentVo,pakPathIdMapEntity);
        PakPathIdMapEntity byPath = pakPathIdMapService.getByPath(pakPathIdMapEntity);

        ContentEntity contentEntity=new ContentEntity();
        BeanUtils.copyProperties(addContentVo,contentEntity);

        contentEntity.setCurrentUploadTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM_SS));
        contentEntity.generateIdentifier();
        //查询当前identifier是否存在
        ContentEntity contentEntityInDb = contentManager.selectByIdentifier(contentEntity.getIdentifier());

        if(ObjectUtils.isNotEmpty(contentEntityInDb)){
            log.warn("当前identifier已存在,跳过插入：{}",contentEntity.getIdentifier());
            return;
        }


        //byPath为空，新增资源
        if(ObjectUtils.isEmpty(byPath)){
            //分配guid
            pakPathIdMapService.insertGetId(pakPathIdMapEntity);
            //封装实体
            contentEntity.setGuid(pakPathIdMapEntity.getId());
            contentManager.insert(contentEntity);
        }else{//否则,修改资源
            contentEntity.setGuid(byPath.getId());
            contentManager.updateByGuid(contentEntity);
        }

        //插入opera_log表
        ContentOperLogEntity contentOperLogEntity=new ContentOperLogEntity();
        BeanUtils.copyProperties(contentEntity,contentOperLogEntity);
        contentOperLogService.addContentOperaLog(contentOperLogEntity);

    }

    @Override
    public IPage<PageContentResp> pageContent(PageContentReq pageContentReq) {
        if(ObjectUtils.anyNull(pageContentReq.getPage(),pageContentReq.getSize())){
            throw new RuntimeException("page,size字段异常");
        }
        PageContentPo po=new PageContentPo();
        BeanUtils.copyProperties(pageContentReq,po);
        IPage<ContentEntity> contentEntityIPage = contentManager.pageByPo(po);
        contentEntityIPage.setTotal(contentManager.selectCount());

        IPage<PageContentResp> iPage=new Page();
        BeanUtils.copyProperties(contentEntityIPage,iPage);
        return iPage;

    }

    @Override
    public void updateContent(UpdateContentWebListReq updateContentWebListReq) {
        if(ObjectUtils.isEmpty(updateContentWebListReq)){
            throw new RuntimeException("updateContent异常");
        }

        List<ContentEntity> contentEntityList=new ArrayList<>();
        List<UpdateContentWebReq> updateContentWebReqList = updateContentWebListReq.getUpdateContentWebReqList();
        updateContentWebReqList.forEach(it->{
            ContentEntity contentEntity=new ContentEntity();
            contentEntity.setPath(null);
            contentEntity.setPakOsskey(null);

            BeanUtils.copyProperties(it,contentEntity);
            contentEntityList.add(contentEntity);
        });

        this.updateBatchById(contentEntityList);

    }

    @Override
    public void classifications(ClassificationsReq classificationsReq) {
        Assert.notNull(classificationsReq,"classificationsReq非空");


    }

}
