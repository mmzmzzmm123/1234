package com.ruoyi.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author qinjin
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        Object createBy = getFieldValByName("createBy", metaObject);
        if (ObjectUtils.isEmpty(createBy)) {
            this.setFieldValByName("createBy", getUsername(), metaObject);
        }
        Object updateBy = getFieldValByName("updateBy", metaObject);
        if (ObjectUtils.isEmpty(updateBy)) {
            this.setFieldValByName("updateBy", getUsername(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(),metaObject);
        Object updateBy = getFieldValByName("updateBy", metaObject);
        if (ObjectUtils.isEmpty(updateBy)) {
            this.setFieldValByName("updateBy", getUsername(), metaObject);
        }
    }

    private String getUsername(){
        String userName;
        try {
            userName  = SecurityUtils.getUsername();
        }catch (Exception e){
            userName = "system";
        }
        return userName;
    }
}
