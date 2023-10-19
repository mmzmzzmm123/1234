package com.xinyu.idol.service.impl;

import com.xinyu.idol.common.utils.StringUtils;
import com.xinyu.idol.manager.PakPathIdMapManager;
import com.xinyu.idol.pojo.entity.PakPathIdMapEntity;
import com.xinyu.idol.mapper.PakPathIdMapMapper;
import com.xinyu.idol.service.IPakPathIdMapService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * pak资源path和guid的映射表 服务实现类
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */
@Service
public class PakPathIdMapServiceImpl extends ServiceImpl<PakPathIdMapMapper, PakPathIdMapEntity> implements IPakPathIdMapService {

    @Autowired
    private PakPathIdMapManager pakPathIdMapManager;

    @Override
    public PakPathIdMapEntity insertGetId(PakPathIdMapEntity pakPathIdMapEntity) {
        if(StringUtils.isEmpty(pakPathIdMapEntity.getPath())){
            throw new RuntimeException("path字段为空");
        }
        PakPathIdMapEntity byPath = getByPath(pakPathIdMapEntity);
        if(ObjectUtils.isNotEmpty(byPath)){
            throw new RuntimeException("该路径已经存在，guid:"+byPath.getPath());
        }
        pakPathIdMapManager.insert(pakPathIdMapEntity);

        return pakPathIdMapEntity;
    }

    @Override
    public PakPathIdMapEntity getByPath(PakPathIdMapEntity pakPathIdMapEntity) {
        if(ObjectUtils.isEmpty(pakPathIdMapEntity)){
            throw new RuntimeException("pakPathIdMapEntity对象为空");
        }
        if(StringUtils.isEmpty(pakPathIdMapEntity.getPath())){
            throw new RuntimeException("pakPathIdMapEntity对象path字段为空");
        }

        return pakPathIdMapManager.getByPath(pakPathIdMapEntity);
    }
}
