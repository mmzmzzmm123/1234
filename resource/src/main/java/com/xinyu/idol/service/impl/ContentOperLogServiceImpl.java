package com.xinyu.idol.service.impl;

import com.xinyu.idol.manager.ContentOperLogManger;
import com.xinyu.idol.pojo.entity.ContentOperLogEntity;
import com.xinyu.idol.mapper.ContentOperLogMapper;
import com.xinyu.idol.service.IContentOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源冗余大表操作记录表，作为备份留存 服务实现类
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */
@Service
public class ContentOperLogServiceImpl extends ServiceImpl<ContentOperLogMapper, ContentOperLogEntity> implements IContentOperLogService {


    @Autowired
    private ContentOperLogManger contentOperLogManger;

    @Override
    public void addContentOperaLog(ContentOperLogEntity contentOperLogEntity) {

        contentOperLogEntity.setId(null);
        contentOperLogManger.insert(contentOperLogEntity);
    }
}
