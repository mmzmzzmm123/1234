package com.xinyu.idol.service.impl;

import com.xinyu.idol.common.utils.DateUtils;
import com.xinyu.idol.common.utils.bean.BeanUtils;
import com.xinyu.idol.manager.ContentManager;
import com.xinyu.idol.pojo.entity.ContentEntity;
import com.xinyu.idol.mapper.ContentMapper;
import com.xinyu.idol.pojo.entity.ContentOperLogEntity;
import com.xinyu.idol.pojo.entity.PakPathIdMapEntity;
import com.xinyu.idol.pojo.entity.vo.AddContentVo;
import com.xinyu.idol.service.IContentOperLogService;
import com.xinyu.idol.service.IContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyu.idol.service.IPakPathIdMapService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
