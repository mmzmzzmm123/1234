package com.gox.basic.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.gox.basic.mapper.TemplatesPreserveMapper;
import com.gox.basic.domain.TemplatesPreserve;
import com.gox.basic.service.ITemplatesPreserveService;

/**
 * 模板维护Service业务层处理
 * 
 * @author gox
 * @date 2021-03-06
 */
@Service
public class TemplatesPreserveServiceImpl implements ITemplatesPreserveService 
{
    @Autowired
    private TemplatesPreserveMapper templatesPreserveMapper;

    /**
     * 查询模板维护
     * 
     * @param id 模板维护ID
     * @return 模板维护
     */
    @Override
    public TemplatesPreserve selectTemplatesPreserveById(Long id)
    {
        return templatesPreserveMapper.selectTemplatesPreserveById(id);
    }

    /**
     * 查询模板维护列表
     * 
     * @param templatesPreserve 模板维护
     * @return 模板维护
     */
    @Override
    public List<TemplatesPreserve> selectTemplatesPreserveList(TemplatesPreserve templatesPreserve)
    {
        return templatesPreserveMapper.selectTemplatesPreserveList(templatesPreserve);
    }

    /**
     * 新增模板维护
     * 
     * @param templatesPreserve 模板维护
     * @return 结果
     */
    @Override
    public int insertTemplatesPreserve(TemplatesPreserve templatesPreserve)
    {
        return templatesPreserveMapper.insertTemplatesPreserve(templatesPreserve);
    }

    /**
     * 修改模板维护
     * 
     * @param templatesPreserve 模板维护
     * @return 结果
     */
    @Override
    public int updateTemplatesPreserve(TemplatesPreserve templatesPreserve)
    {
        return templatesPreserveMapper.updateTemplatesPreserve(templatesPreserve);
    }

    /**
     * 批量删除模板维护
     * 
     * @param ids 需要删除的模板维护ID
     * @return 结果
     */
    @Override
    public int deleteTemplatesPreserveByIds(Long[] ids)
    {
        return templatesPreserveMapper.deleteTemplatesPreserveByIds(ids);
    }

    /**
     * 删除模板维护信息
     * @param id 模板维护ID
     * @return 结果
     */
    @Override
    public int deleteTemplatesPreserveById(Long id)
    {
        return templatesPreserveMapper.deleteTemplatesPreserveById(id);
    }

    @Override
    public int insertTemplatesPreserveBatch(List<TemplatesPreserve> templates) {
        return templatesPreserveMapper.insertTemplatesPreserveBatch(templates);
    }

    /**
     * 更新
     * @param templates 更新的
     * @param dblist 数据库的
     * @return 1
     */
    @Override
    public int updateTemplatesPreserveBatch(List<TemplatesPreserve> templates, List<TemplatesPreserve> dblist) {
        //清空
        if (templates==null){
            Long[] ids= new Long[dblist.size()];
            for (int i = 0; i < dblist.size(); i++) {
                ids[i] = dblist.get(i).getId();
            }
            return deleteTemplatesPreserveByIds(ids);
        }
        TemplatesPreserve templatesPreserve;
        List<TemplatesPreserve > addList = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        //在更新的所有字段中 查询
        for (TemplatesPreserve tp : templates) {
            templatesPreserve = dblist.stream().filter(t->tp.getTableFieldName().
                    equals(t.getTableFieldName())&&tp.getvModel().
                    equals(t.getvModel())).findFirst().orElse(null);
            if (templatesPreserve==null){
                //如果没查询到 就新增
                addList.add(tp);
            }
        }
        for (TemplatesPreserve tp:dblist){
            templatesPreserve = templates.stream().filter(t->tp.getTableFieldName().
                    equals(t.getTableFieldName())&&tp.getvModel().
                    equals(t.getvModel())).findFirst().orElse(null);
            if (templatesPreserve==null){
                //如果 数据库中有 而更改数据没有的 删除数据库中数据
                ids.add(tp.getId());
            }
        }
        if (addList.size()>0){
            insertTemplatesPreserveBatch(addList);
        }
        if (ids.size()>0){
            Long [] idArr= new Long[ids.size()];
            deleteTemplatesPreserveByIds(ids.toArray(idArr));
        }

        return 1;
    }
}
