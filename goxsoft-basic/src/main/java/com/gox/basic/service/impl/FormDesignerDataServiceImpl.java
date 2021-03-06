package com.gox.basic.service.impl;

import com.gox.basic.domain.form.FieldsItem;
import com.gox.basic.domain.form.FormDesignerData;
import com.gox.basic.mapper.FormDesignerDataMapper;
import com.gox.basic.service.IFieldsItemService;
import com.gox.basic.service.IFormDesignerDataService;
import com.gox.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *   Service业务层处理
 *
 * @author gox
 * @date 2021-02-02
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@CacheConfig(cacheNames = "formDesignerData")
public class FormDesignerDataServiceImpl implements IFormDesignerDataService {
    @Autowired
    private FormDesignerDataMapper formDesignerDataMapper;
    @Autowired
    private IFieldsItemService fieldsItemService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    @Override
    public FormDesignerData selectFormDesignerDataById(Long id) {
        Object f = redisCache.getCacheMapValue("form", id + "");
        if (f == null) {
            return formDesignerDataMapper.selectFormDesignerDataById(id);
        }
        return (FormDesignerData) f;
    }


    /**
     * 新增
     *
     * @param formDesignerData
     * @return 结果
     */
    @Override
    public int insertFormDesignerData(FormDesignerData formDesignerData) {
        //Long formId = SnowflakesTools.WORKER.nextId();
        List<FieldsItem> fields = formDesignerData.getFields();
        fieldsItemService.insertFieldsItems(fields, formDesignerData.getId());
        if (formDesignerDataMapper.insertFormDesignerData(formDesignerData) == 1) {
            redisCache.setCacheMapValue("form", formDesignerData.getId() + "", formDesignerData);
            return 1;
        }
        return 0;
    }

    /**
     * 修改
     *
     * @param formDesignerData
     * @return 结果
     */
    @Override
    public int updateFormDesignerData(FormDesignerData formDesignerData) {
        if (formDesignerDataMapper.updateFormDesignerData(formDesignerData) == 1) {
            redisCache.setCacheMapValue("form", formDesignerData.getId() + "", formDesignerData);
            return 1;
        }
        return 0;
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteFormDesignerDataByIds(Long[] ids) {
        formDesignerDataMapper.deleteFormDesignerDataByIds(ids);
        for (Long id : ids) {
            redisCache.deleteHashObject("form", id + "");
        }
        return ids.length;
    }

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteFormDesignerDataById(Long id) {
        fieldsItemService.deleteFieldsByFormId(id);
        if (formDesignerDataMapper.deleteFormDesignerDataById(id) == 1) {
            redisCache.deleteHashObject("form", id + "");
            return 1;
        }
        return 0;
    }
}
