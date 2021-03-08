package com.gox.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gox.basic.domain.TemplatesPreserve;
import com.gox.basic.domain.form.FieldsItem;
import com.gox.basic.service.ITemplatesPreserveService;
import com.gox.common.core.redis.RedisCache;
import com.gox.common.plugin.SnowIdUtils;
import com.gox.common.utils.DateUtils;
import com.gox.basic.domain.FormJson;
import com.gox.basic.domain.form.FormDesignerData;
import com.gox.basic.mapper.FormJsonMapper;
import com.gox.basic.service.IFormJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 单json存储Service业务层处理
 *
 * @author gox
 * @date 2020-12-25
 */
@Service
@Transactional
public class FormJsonServiceImpl implements IFormJsonService {
    @Autowired
    private FormJsonMapper formJsonMapper;

    @Autowired
    private ITemplatesPreserveService templatesPreserveService;
    @Autowired
    private RedisCache redisCache;

    @PostConstruct
    public void init() {
        List<FormJson> list = formJsonMapper.selectFormJsonList(new FormJson());
        for (FormJson formJson : list) {
            redisCache.setCacheMapValue("form", formJson.getId() + "", formJson);
        }
    }

    /**
     * 查询单json存储
     *
     * @param id 单json存储ID
     * @return 单json存储
     */
    @Override
    public FormJson selectFormJsonById(Long id) {
        FormJson f = redisCache.getCacheMapValue("form", id + "");
        if (f == null) {
            return formJsonMapper.selectFormJsonById(id);
        }
        return f;
    }

    /**
     * 查询单json存储列表
     *
     * @param formJson 单json存储
     * @return 单json存储
     */
    @Override
    public List<FormJson> selectFormJsonList(FormJson formJson) {
        return formJsonMapper.selectFormJsonList(formJson);
    }

    /**
     * 新增单json存储
     *
     * @param formJson 单json存储
     * @return 结果
     */
    @Override
    public int insertFormJson(FormJson formJson) {
        formJson.setId(SnowIdUtils.uniqueLong());
        List<TemplatesPreserve> templates = createTemplates(formJson);
        formJson.setCreateTime(DateUtils.getNowDate());
        formJson.setOrder(formJsonMapper.countFormByNodeIdAndDeptId(formJson.getNodeId(), formJson.getDeptId()) + 1);
        if (formJsonMapper.insertFormJson(formJson) == 1) {
            redisCache.setCacheMapValue("form", formJson.getId() + "", formJson);
        }
        templatesPreserveService.insertTemplatesPreserveBatch(templates);
        return 1;
    }
    @Override
    public List<TemplatesPreserve> createTemplates(FormJson formJson){
        FormDesignerData form = formJson.getFormDesignerData();
        if (form==null){
            JSONObject json = JSON.parseObject(formJson.getFormData());
            form = json.toJavaObject(FormDesignerData.class);
        }
        List<FieldsItem> fields= form.getFields();
        List<TemplatesPreserve> res = new ArrayList<>();
        TemplatesPreserve t ;
        Random r = new Random(10);
        long i=1;
        for (FieldsItem field : fields) {
            t = getTemplatesPreserve(formJson, field);
            t.setSort(i);
            i++;
            t.setTableFieldFlag(r.nextInt(2)%2==0);
            t.setTableSearchFlag(r.nextInt(2)%2==0);
            res.add(t);
        }
        return res;
    }
    @Override
    public List<TemplatesPreserve> getTemplates(FormJson formJson){
        FormDesignerData form = formJson.getFormDesignerData();
        if (form==null){
            JSONObject json = JSON.parseObject(formJson.getFormData());
            form = json.toJavaObject(FormDesignerData.class);
        }
        List<FieldsItem> fields= form.getFields();
        List<TemplatesPreserve> res = new ArrayList<>();
        TemplatesPreserve t ;
        for (FieldsItem field : fields) {
            t = getTemplatesPreserve(formJson, field);
            res.add(t);
        }
        return res;
    }

    private TemplatesPreserve getTemplatesPreserve(FormJson formJson, FieldsItem field) {
        TemplatesPreserve t;
        t = new TemplatesPreserve();
        t.setFormId(formJson.getId());
        t.setDeptId(formJson.getDeptId());
        t.setNodeId(formJson.getNodeId());
        t.setTableFieldName(field.getConfig().getLabel());
        t.setvModel(field.getVModel());
        t.setWidth(120L);
        return t;
    }

    @Override
    public int updateFormOrderBatch(Iterable<FormJson> formJsons) {
        formJsonMapper.updateFormOrderBatch(formJsons);
        for (FormJson formJson : formJsons) {
            redisCache.setCacheMapValue("form", formJson.getId() + "", selectFormJsonById(formJson.getId()));
        }
        return 1;
    }

    /**
     * 修改单json存储
     *
     * @param formJson 单json存储
     * @return 结果
     */
    @Override
    public int updateFormJson(FormJson formJson) {
        FormJson f = selectFormJsonById(formJson.getId());
        Long nodeId = f.getNodeId();
        Long deptId = f.getDeptId();
        formJson.setUpdateTime(DateUtils.getNowDate());
        if (formJsonMapper.updateFormJson(formJson) == 1) {
            formJson.setNodeId(nodeId);
            formJson.setDeptId(deptId);
            List<TemplatesPreserve> list = getTemplates(formJson);
            TemplatesPreserve t = new TemplatesPreserve(nodeId,deptId,f.getId());
            List<TemplatesPreserve> databaseList = templatesPreserveService.selectTemplatesPreserveList(t);
            templatesPreserveService.updateTemplatesPreserveBatch(list,databaseList);
            redisCache.setCacheMapValue("form", formJson.getId() + "", formJsonMapper.selectFormJsonById(f.getId()));
            return 1;
        }
        return 0;
    }

    /**
     * 批量删除单json存储
     *
     * @param ids 需要删除的单json存储ID
     * @return 结果
     */
    @Override
    public int deleteFormJsonByIds(Long[] ids) {

        if (formJsonMapper.deleteFormJsonByIds(ids) == ids.length) {
            for (Long id : ids) {
                redisCache.deleteHashObject("form", id + "");
            }
        }
        return ids.length;
    }

    /**
     * 删除单json存储信息
     *
     * @param id 单json存储ID
     * @return 结果
     */
    @Override
    public int deleteFormJsonById(Long id) {
        if (formJsonMapper.deleteFormJsonById(id) == 1) {
            redisCache.deleteHashObject("form", id + "");
            return 1;
        }
        return 0;
    }
}
