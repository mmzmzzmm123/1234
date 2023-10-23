package com.xinyu.idol.manager;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyu.idol.common.utils.StringUtils;
import com.xinyu.idol.mapper.ContentMapper;
import com.xinyu.idol.pojo.entity.ContentEntity;
import com.xinyu.idol.pojo.po.PageContentPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ContentManager {
    @Autowired
    ContentMapper contentMapper;

    public List selectAllTest(){

        List<ContentEntity> contentEntities = contentMapper.selectList(new QueryWrapper<>());
        return contentEntities;
    }

    public void insert(ContentEntity contentEntity){
        Assert.notNull(contentEntity,"contentEntity非空");
         contentMapper.insert(contentEntity);
    }

    public void updateByGuid(ContentEntity contentEntity){
        Assert.notNull(contentEntity,"contentEntity非空");
        //字段为null自动跳过
        contentMapper.update(contentEntity,new QueryWrapper<ContentEntity>().eq("guid",contentEntity.getGuid()));
    }

    public ContentEntity selectByIdentifier(String identifier){
        Assert.notNull(identifier,"identifier非空");

        ContentEntity contentEntity = contentMapper.selectOne(new QueryWrapper<ContentEntity>().eq("identifier", identifier));
        return contentEntity;
    }

    /**
     * 分页接口
     * 分页插件已失效，后续手写分页逻辑
     * @param po
     * @return
     */
    public IPage<ContentEntity> pageByPo(PageContentPo po){
        Assert.notNull(po,"identifier非空");
        Page<ContentEntity> contentEntityPage = contentMapper.selectPage(new Page<>(po.getPage(), po.getSize())
                , new QueryWrapper<ContentEntity>()
                        .eq(StringUtils.isNotEmpty(po.getClassification1()), "classification1", po.getClassification1())
                        .eq(StringUtils.isNotEmpty(po.getClassification2()), "classification2", po.getClassification2())
                        .eq(StringUtils.isNotEmpty(po.getClassification3()), "classification3", po.getClassification3())
                        .eq(StringUtils.isNotEmpty(po.getClassification4()), "classification4", po.getClassification4())

                        .and(StringUtils.isNotEmpty(po.getIdOrName()),
                                it -> it.like("guid", po.getIdOrName())
                                        .or().like("display_name", po.getIdOrName())
                                        .or().like("file_name", po.getIdOrName())

                        )
                        .last("limit "+(po.getPage()-1)*po.getSize()+","+po.getSize()))
                ;
        return contentEntityPage;
    }
    public Integer selectCount(){
       return contentMapper.selectCount(new QueryWrapper<ContentEntity>().select("1"));
    }
}
